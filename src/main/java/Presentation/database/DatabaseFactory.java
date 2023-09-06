package Presentation.database;

import Server.database.Database;
import Server.sql.PsqlEngine;
import client.ServerDatabase;
import clientConnection.ClientReceiveHandler;
import clientConnection.ClientRequestHandlerImplementation;
import clientConnection.ClientSendHandler;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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


    public Database getServerDatabase(ClientReceiveHandler receiveHandler) throws DatabaseFactoryException {
            return new SecureDatabase(new ServerDatabase(
                    new ClientRequestHandlerImplementation(
                            receiveHandler
                    )
            ));
    }


    public static class DatabaseFactoryException extends Exception{
        public DatabaseFactoryException(String message) {
            super(message);
        }
    }
}
