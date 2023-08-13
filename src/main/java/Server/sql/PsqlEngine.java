package Server.sql;
import Server.database.Database;
import Utils.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;

public class PsqlEngine implements Database {
    private final String url = "jdbc:postgresql://localhost:5432/ProjektUJ";

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
    public int addUser(UserInfo userInfo){
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     "INSERT INTO projektuj.users (name) VALUES (?) RETURNING id")) {
            sql.setString(1, userInfo.name());
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                return rs.getInt("id");
            }
        } catch (SQLException throwables) {
            throw new PsqlException(throwables);
        }
    }

    @Override
    public int addTask(int team_id, String name, String info, String status, String priority, String deadline) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int addTeam(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int addTeamUser(int team_id, int user_id, String role, String position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int addUserTask(int user_id, int task_id) {
        throw new UnsupportedOperationException();
    }
}
