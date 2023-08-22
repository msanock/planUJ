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
    private static final String ADD_TEAM_QUERY = "INSERT INTO projektuj.team (name) VALUES (?) RETURNING id;";
    private static final String ADD_TEAM_MEMBER_QUERY = "INSERT INTO projetuj.teams_users (team_id, user_id, role, position) VALUES (?, ?, ?, ?);";

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
        System.out.println(createQuery);
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
                     "INSERT INTO projetuj.tasks (info, deadline, status, team_id, priority) VALUES (?, ?, ?, ?, ?) RETURNING id;")) {
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
             PreparedStatement sql = connection.prepareStatement(
                     "with temp_team_id as ( " + ADD_TEAM_QUERY + " ) " +
                             " INSERT INTO projetuj.teams_users (team_id, user_id, role, position) VALUES (temp_team_id, ?, 'admin', 'admin');")) {
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
                     "INSERT INTO projetuj.teams_users (team_id, user_id, role, position) VALUES (?, ?, ?, ?);")) {
            sql.setInt(1, teamUser.getId());
            sql.setInt(2, team_id);
            sql.setString(3, teamUser.getRole());
            sql.setString(4, teamUser.getPosition());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                teamUser.setId(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }

    @Override
    public void addUserTask(int user_id, int task_id) {
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     "INSERT INTO projetuj.users_tasks (user_id, task_id) VALUES (?, ?);")) {
            sql.setInt(1, user_id);
            sql.setInt(2, task_id);
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
            }
        } catch (SQLException exception) {
            throw new PsqlException(exception);
        }
    }
}
