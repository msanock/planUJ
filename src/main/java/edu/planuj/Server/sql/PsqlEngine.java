package edu.planuj.Server.sql;
import edu.planuj.Server.database.Database;
import edu.planuj.Utils.OperationResults.GetTasksResult;
import edu.planuj.Utils.OperationResults.GetTeamsResult;
import edu.planuj.Utils.OperationResults.GetUsersResult;
import edu.planuj.Utils.OperationResults.IdResult;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import edu.planuj.Utils.UserInfo;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PsqlEngine implements Database {
    private final String url = "jdbc:postgresql://localhost:5432/ProjektUJ";
    private static final String ADD_USER_QUERY = "INSERT INTO projektuj.users (name) VALUES (?) ON CONFLICT (name)\n" +
            "DO UPDATE SET name=EXCLUDED.name RETURNING id;";
    private static final String ADD_TEAM_QUERY = "INSERT INTO projektuj.teams (name) VALUES (?) ON CONFLICT (name)\n" +
            "DO UPDATE SET name=EXCLUDED.name RETURNING id;";
    private static final String ADD_USER_TASK_QUERY = "INSERT INTO projektuj.users_tasks (user_id, task_id, is_notified) VALUES (?, ?, false);";
    private static final String ADD_TASK_QUERY = "INSERT INTO projektuj.tasks (info, deadline, status, team_id, priority) VALUES (?, ?, ?, ?, ?) RETURNING id;";
    private static final String ADD_TEAM_MEMBER_QUERY = "INSERT INTO projektuj.teams_users (team_id, user_id, role, position, is_notified) VALUES (?, ?, ?, ?, false);";
    private static final String GET_USERS_QUERY = "SELECT * FROM projektuj.users;";

    private static final String GET_TEAM_TASKS_QUERY = "SELECT t.*, array_agg(u.name) as \"names\" , array_agg(u.id) as \"ids\" \n" +
            "FROM projektuj.tasks as t\n" +
            "JOIN projektuj.users_tasks as ut ON t.id = ut.task_id\n" +
            "JOIN projektuj.users as u ON ut.user_id = u.id\n" +
            "WHERE t.team_id = ?\n" +
            "GROUP BY t.id, t.team_id, t.info, t.status, t.priority, t.deadline;";
    private static final String GET_USER_TASKS_QUERY = "SELECT(h.*) FROM (\n" +
            "SELECT t.*, array_agg(u.name) as \"names\" , array_agg(u.id) as \"ids\"\n" +
            "FROM projektuj.tasks as t\n" +
            "JOIN projektuj.users_tasks as ut ON t.id = ut.task_id\n" +
            "JOIN projektuj.users as u ON ut.user_id = u.id\n" +
            "GROUP BY t.id, t.team_id, t.info, t.status, t.priority, t.deadline\n" +
            ") as \"h\" WHERE ? = ANY(h.ids)";
    private static final String GET_TEAMS_QUERY = "SELECT t.id as \"tid\", t.name as \"tname\", tu.user_id, tu.team_id, tu.role , tu.position, u.id, u.name " +
            "FROM projektuj.teams as \"t\" " +
            "JOIN projektuj.teams_users as \"tu\" ON t.id = tu.team_id " +
            "JOIN projektuj.users as \"u\" ON tu.user_id = u.id";
    private static final String GET_TEAM_USERS_QUERY = "SELECT * FROM projektuj.users WHERE id IN (SELECT user_id FROM projektuj.teams_users WHERE team_id = ?);";
    private static final String UPDATE_TASK_QUERY = "UPDATE projektuj.tasks SET info = ?, deadline = ?, status = ?, priority = ? WHERE id = ?;";
    private static final String GET_USER_TEAMS_QUERY = "SELECT t.id as \"tid\", t.name as \"tname\", tu.user_id, tu.team_id, tu.role , tu.position, u.id, u.name\n" +
            "FROM projektuj.teams as \"t\"\n" +
            "JOIN projektuj.teams_users as \"tu\" ON t.id = tu.team_id\n" +
            "JOIN projektuj.users as \"u\" ON tu.user_id = u.id\n" +
            "WHERE t.id IN (SELECT team_id FROM projektuj.teams_users WHERE user_id = ?)";
    private static final String REMOVE_USER_FROM_TASK_QUERY = "DELETE FROM projektuj.users_tasks WHERE user_id = ? AND task_id = ?;";

    private static final String MARK_USER_TEAM_AS_NOTIFIED_QUERY = "UPDATE projektuj.teams_users SET is_notified = true WHERE user_id = ? AND team_id = ?;";
    private static final String MARK_USER_TASK_AS_NOTIFIED_QUERY = "UPDATE projektuj.users_tasks SET is_notified = true WHERE user_id = ? AND task_id = ?;";

    private static final String GET_UNNOTIFIED_USER_TASKS_QUERY = "SELECT(h.*) FROM (\n" +
            "SELECT t.*, array_agg(u.name) as \"names\" , array_agg(u.id) as \"ids\"\n" +
            "FROM projektuj.tasks as t\n" +
            "JOIN projektuj.users_tasks as ut ON t.id = ut.task_id\n" +
            "JOIN projektuj.users as u ON ut.user_id = u.id\n" +
            "WHERE ut.is_notified = false\n" +
            "GROUP BY t.id, t.team_id, t.info, t.status, t.priority, t.deadline\n" +
            ") as \"h\" WHERE ? = ANY(h.ids)";

    private static final String START_TRANSACTION_QUERY = "BEGIN TRANSACTION;";
    private static final String COMMIT_TRANSACTION_QUERY = "COMMIT TRANSACTION;";
    private static final String ADD_TEAM_MEMBER_FROM_NAME = "INSERT INTO projektuj.teams_users (team_id, user_id, role, position, is_notified) " +
            "VALUES ((SELECT t.id FROM projektuj.teams as t WHERE t.name = ?), ?, ?, ?, false);";
    private static final String GET_TEAM_FROM_NAME = "SELECT t.id FROM projektuj.teams as t WHERE t.name = ?;";
    private static class Holder {
        private static final PsqlEngine INSTANCE = new PsqlEngine();
    }

    private PsqlEngine() {
        try {
            createDatabase();
        } catch (DatabaseException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create a Database");
            throw new RuntimeException(e);
        }
    }


    public static PsqlEngine getInstance() {
        return Holder.INSTANCE;
    }

    private Connection getConnection() throws SQLException {
        String username = "postgres";
        return DriverManager.getConnection(url, username, null);
    }

    private void createDatabase() throws DatabaseException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("edu/planuj/database/database.sql");

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
            sql.setString(3, taskInfo.getStatus().toString());
            sql.setInt(4, taskInfo.getTeamID());
            sql.setInt(5, taskInfo.getPriority());
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
        StringBuilder query = new StringBuilder(START_TRANSACTION_QUERY);
        query.append(ADD_TEAM_QUERY);
        for (TeamUser teamUser : teamInfo.getUsers()) {
            query.append(ADD_TEAM_MEMBER_FROM_NAME);
        }
        query.append(COMMIT_TRANSACTION_QUERY);
        String queryStr = query.toString();
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(queryStr);
             PreparedStatement sql2 = connection.prepareStatement(GET_TEAM_FROM_NAME)) {
            sql.setString(1, teamInfo.getName());
            for (int i = 0; i < teamInfo.getUsers().size(); i++) {
                sql.setString(2 + i * 4, teamInfo.getName());
                sql.setInt(3 + i * 4, teamInfo.getUsers().get(i).getId());
                sql.setString(4 + i * 4, teamInfo.getUsers().get(i).getRole().name());
                sql.setString(5 + i * 4, teamInfo.getUsers().get(i).getPosition());
            }
            sql.execute();
            sql2.setString(1, teamInfo.getName());
            try (ResultSet rs = sql2.executeQuery()) {
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
            sql.setString(3, taskInfo.getStatus().toString());
            sql.setInt(4, taskInfo.getPriority());
            sql.setInt(5, taskInfo.getId());
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void removeUserFromTask(int user_id, int task_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     REMOVE_USER_FROM_TASK_QUERY)) {
            sql.setInt(1, user_id);
            sql.setInt(2, task_id);
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }


    public void markUserTaskAsNotified(int user_id, int task_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     MARK_USER_TASK_AS_NOTIFIED_QUERY)) {
            sql.setInt(1, user_id);
            sql.setInt(2, task_id);
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    public void markUserTeamAsNotified(int user_id, int team_id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     MARK_USER_TEAM_AS_NOTIFIED_QUERY)) {
            sql.setInt(1, user_id);
            sql.setInt(2, team_id);
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    public GetTeamsResult getUnNotifiedTeamsForUser(long clientID) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     GET_USER_TEAMS_QUERY + " AND tu.is_notified = false")) {
            sql.setInt(1, Math.toIntExact(clientID));
            try (ResultSet rs = sql.executeQuery()) {
                return new GetTeamsResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    public GetTasksResult getUnNotifiedTasksForUser(long clientID) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     GET_UNNOTIFIED_USER_TASKS_QUERY)) {
            sql.setInt(1, Math.toIntExact(clientID));
            try (ResultSet rs = sql.executeQuery()) {
                return new GetTasksResult(rs);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

}
