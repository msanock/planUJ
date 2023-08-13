package Presentation.database;

import Connection.ServerDatabase;
import Server.database.Database;
import Server.sql.PsqlEngine;

public class DatabaseFactory {
    static DatabaseFactory instance;
    private DatabaseFactory() {
    }
    public static DatabaseFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseFactory();
        }
        return instance;
    }

    public Database getLocalDatabase() {
        return PsqlEngine.getInstance();
    }

    public Database getServerDatabase() {
        return new ServerDatabase();
    }
}
