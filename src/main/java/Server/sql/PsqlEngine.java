package Server.sql;

import Server.database.Engine;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;

public class PsqlEngine implements Engine {
    private final String url;

    public PsqlEngine(String url) {
        this.url = url;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    void createDatabase(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("database.sql");

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
    public int addUser(String name){
        try (Connection connection = getConnection();
             PreparedStatement sql = connection.prepareStatement(
                     "INSERT INTO users (name) VALUES (?) RETURNING id")) {
            sql.setString(1, name);
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
