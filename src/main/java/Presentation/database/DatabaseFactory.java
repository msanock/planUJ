package Presentation.database;

import Server.database.Database;
import Server.sql.PsqlEngine;
import client.ServerDatabase;
import clientConnection.ClientReceiveHandler;
import clientConnection.ClientRequestHandlerImplementation;
import clientConnection.ClientSendHandler;
import clientConnection.abstraction.ClientRequestHandler;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            return new SecureDatabase(new ServerDatabase(
                    requestHandler
            ));
    }


    public static class DatabaseFactoryException extends Exception{
        public DatabaseFactoryException(String message) {
            super(message);
        }
    }
}
