package Server.sql;
import Server.database.Database;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;

public class PsqlEngine implements Database {
    private final String url = "jdbc:postgresql://localhost:5432/ProjektUJ";
    private static final String ADD_USER_QUERY = "INSERT INTO projektuj.users (name) VALUES (?) RETURNING id;";
    private static final String ADD_TEAM_QUERY = "INSERT INTO projektuj.teams (name) VALUES (?) RETURNING id;";
    private static final String ADD_USER_TASK_QUERY = "INSERT INTO projektuj.users_tasks (user_id, task_id) VALUES (?, ?);";
    private static final String ADD_TASK_QUERY = "INSERT INTO projektuj.tasks (info, deadline, status, team_id, priority) VALUES (?, ?, ?, ?, ?) RETURNING id;";
    private static final String ADD_TEAM_MEMBER_QUERY = "INSERT INTO projektuj.teams_users (team_id, user_id, role, position) VALUES (?, ?, ?, ?);";

    static PsqlEngine instance;

    private PsqlEngine() {
    }

    public static PsqlEngine getInstance() {
        if (instance == null) {
            instance = new PsqlEngine();
            instance.createDatabase();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        String username = "postgres";
        return DriverManager.getConnection(url, username, null);
    }

    private void createDatabase(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("database/database.sql");

        String createQuery;
        try {
            createQuery = new String(Objects.requireNonNull(is).readAllBytes());
        } catch (IOException | NullPointerException e) {
            throw new PsqlException(e);
        }
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(createQuery)) {
            sql.executeUpdate();
        } catch (SQLException throwables) {
            throw new PsqlException(throwables);
        }
    }

    @Override
    public void addUser(UserInfo userInfo){
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(ADD_USER_QUERY)) {
            sql.setString(1, userInfo.getUsername());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                userInfo.setId(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }

    @Override
    public void addTask(TaskInfo taskInfo) {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     ADD_TASK_QUERY)) {
            sql.setString(1, taskInfo.getInfo());
            sql.setDate(2, Date.valueOf(taskInfo.getDeadline().toLocalDate()));
            sql.setString(3, taskInfo.getStatus());
            sql.setInt(4, taskInfo.getTeam_id());
            sql.setString(5, taskInfo.getPriority());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                taskInfo.setId(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }

    @Override
    public void addTeam(TeamInfo teamInfo) {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(ADD_TEAM_QUERY)) {
            sql.setString(1, teamInfo.getName());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                teamInfo.setId(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     ADD_TEAM_MEMBER_QUERY)) {
            sql.setInt(1, teamUser.getId());
            sql.setInt(2, team_id);
            sql.setString(3, teamUser.getRole());
            sql.setString(4, teamUser.getPosition());
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }

    @Override
    public void addUserTask(int user_id, int task_id) {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     ADD_USER_TASK_QUERY)) {
            sql.setInt(1, user_id);
            sql.setInt(2, task_id);
            sql.executeUpdate();
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }
}
