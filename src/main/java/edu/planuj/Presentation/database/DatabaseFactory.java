package edu.planuj.Presentation.database;

import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.PsqlEngine;
import edu.planuj.client.ServerDatabase;
import edu.planuj.clientConnection.abstraction.ClientRequestHandler;

public class DatabaseFactory {
    private static class Holder {
        private static final DatabaseFactory INSTANCE = new DatabaseFactory();
    }

    public static DatabaseFactory getInstance() {
        return Holder.INSTANCE;
    }


    private DatabaseFactory() {
    }

    public Database getLocalDatabase() {
        return PsqlEngine.getInstance();
    }


    public Database getServerDatabase(ClientRequestHandler requestHandler) throws DatabaseFactoryException {
            return new SecureDatabase(new ServerDatabase(requestHandler));
    }


    public static class DatabaseFactoryException extends Exception{
        public DatabaseFactoryException(String message) {
            super(message);
        }
    }
}
