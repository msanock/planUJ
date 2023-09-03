package Server.sql;
import Server.database.Database;
import Utils.OperationResults.*;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PsqlEngine implements Database {
    private final String url = "jdbc:postgresql://localhost:5432/ProjektUJ";
    private static final String ADD_USER_QUERY = "INSERT INTO projektuj.users (name) VALUES (?) ON CONFLICT (id)\n" +
            "DO NOTHING RETURNING id;";
    private static final String ADD_TEAM_QUERY = "INSERT INTO projektuj.teams (name) VALUES (?) RETURNING id;";
    private static final String ADD_USER_TASK_QUERY = "INSERT INTO projektuj.users_tasks (user_id, task_id) VALUES (?, ?);";
    private static final String ADD_TASK_QUERY = "INSERT INTO projektuj.tasks (info, deadline, status, team_id, priority) VALUES (?, ?, ?, ?, ?) RETURNING id;";
    private static final String ADD_TEAM_MEMBER_QUERY = "INSERT INTO projektuj.teams_users (team_id, user_id, role, position) VALUES (?, ?, ?, ?);";
    private static final String GET_USERS_QUERY = "SELECT * FROM projektuj.users;";

    private static final String GET_TEAM_TASKS_QUERY = "SELECT * FROM projektuj.tasks WHERE team_id = ?;";
    private static final String GET_USER_TASKS_QUERY = "SELECT * FROM projektuj.tasks WHERE id IN (SELECT task_id FROM projektuj.users_tasks WHERE user_id = ?);";
    private static final String GET_TEAMS_QUERY = "SELECT t.id as \"tid\", t.name as \"tname\", tu.user_id, tu.team_id, tu.role , tu.position, u.id, u.name " +
            "FROM projektuj.teams as \"t\" " +
            "JOIN projektuj.teams_users as \"tu\" ON t.id = tu.team_id " +
            "JOIN projektuj.users as \"u\" ON tu.user_id = u.id";
    private static final String GET_TEAM_USERS_QUERY = "SELECT * FROM projektuj.users WHERE id IN (SELECT user_id FROM projektuj.teams_users WHERE team_id = ?);";
    private static final String UPDATE_TASK_QUERY = "UPDATE projektuj.tasks SET info = ?, deadline = ?, status = ?, priority = ? WHERE id = ?;";
    private static final String GET_USER_TEAMS_QUERY = "SELECT * FROM projektuj.teams WHERE id IN (SELECT team_id FROM projektuj.teams_users WHERE user_id = ?);";
    static PsqlEngine instance;

    private PsqlEngine() {
    }

    public static PsqlEngine getInstance() {
        if (instance == null) {
            instance = new PsqlEngine();
            try {
                instance.createDatabase();
            } catch (DatabaseException e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create a Database");
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        String username = "postgres";
        return DriverManager.getConnection(url, username, null);
    }

    private void createDatabase() throws DatabaseException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("database/database.sql");

        String createQuery;
        try {
            createQuery = new String(Objects.requireNonNull(is).readAllBytes());
        } catch (IOException | NullPointerException e) {
            throw new DatabaseException(e);
        }
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(createQuery)) {
            sql.executeUpdate();
        } catch (SQLException throwables) {
            throw new DatabaseException(throwables);
        }
    }

    @Override
    public IdResult addUser(UserInfo userInfo) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(ADD_USER_QUERY)) {
            sql.setString(1, userInfo.getUsername());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                userInfo.setId(rs.getInt("id"));
                return new IdResult(userInfo.getId());
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public GetUsersResult getUsers() throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(GET_USERS_QUERY)) {
            try (ResultSet rs = sql.executeQuery()) {
                return new GetUsersResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public IdResult addTask(TaskInfo taskInfo) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     ADD_TASK_QUERY)) {
            sql.setString(1, taskInfo.getInfo());
            sql.setDate(2, Date.valueOf(taskInfo.getDeadline().toLocalDate()));
            sql.setString(3, taskInfo.getStatus());
            sql.setInt(4, taskInfo.getTeamID());
            sql.setString(5, taskInfo.getPriority());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                taskInfo.setId(rs.getInt("id"));
                return new IdResult(taskInfo.getId());
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public IdResult addTeam(TeamInfo teamInfo) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(ADD_TEAM_QUERY)) {
            sql.setString(1, teamInfo.getName());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                teamInfo.setId(rs.getInt("id"));
                return new IdResult(teamInfo.getId());
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     ADD_TEAM_MEMBER_QUERY)) {
            sql.setInt(1, teamUser.getId());
            sql.setInt(2, team_id);
            sql.setString(3, teamUser.getRole().name());
            sql.setString(4, teamUser.getPosition());
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public GetTeamsResult getTeams() throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     GET_TEAMS_QUERY)) {
            try (ResultSet rs = sql.executeQuery()) {
                return new GetTeamsResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public GetUsersResult getTeamUsers(int team_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     GET_TEAM_USERS_QUERY)) {
            sql.setInt(1, team_id);
            try (ResultSet rs = sql.executeQuery()) {
                return new GetUsersResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     GET_USER_TEAMS_QUERY)) {
            sql.setInt(1, user_id);
            try (ResultSet rs = sql.executeQuery()) {
                return new GetTeamsResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void addUserTask(int user_id, int task_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     ADD_USER_TASK_QUERY)) {
            sql.setInt(1, user_id);
            sql.setInt(2, task_id);
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public GetTasksResult getTeamTasks(int team_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                 GET_TEAM_TASKS_QUERY)) {
            sql.setInt(1, team_id);
            try (ResultSet rs = sql.executeQuery()) {
                return new GetTasksResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public GetTasksResult getUserTasks(int user_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     GET_USER_TASKS_QUERY)) {
            sql.setInt(1, user_id);
            try (ResultSet rs = sql.executeQuery()) {
                return new GetTasksResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     UPDATE_TASK_QUERY)) {
            sql.setString(1, taskInfo.getInfo());
            sql.setDate(2, Date.valueOf(taskInfo.getDeadline().toLocalDate()));
            sql.setString(3, taskInfo.getStatus());
            sql.setString(4, taskInfo.getPriority());
            sql.setInt(5, taskInfo.getId());
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }
}
