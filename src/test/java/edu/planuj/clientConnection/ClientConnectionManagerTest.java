package edu.planuj.clientConnection;

import edu.planuj.Connection.ObjectOutputFactory;
import edu.planuj.Connection.SocketFactory;
import edu.planuj.Connection.connector.download.ClientSocketStreamReader;
import edu.planuj.Connection.connector.download.ClientSocketStreamReaderFactory;
import edu.planuj.Connection.connector.download.ObjectInputFactory;
import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.clientConnection.ClientConnectionManager;
import edu.planuj.clientConnection.ClientReceiveHandler;
import edu.planuj.clientConnection.ClientSendHandler;
import edu.planuj.clientConnection.ConnectionSettings;
import edu.planuj.clientConnection.abstraction.ClientRequestHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientConnectionManagerTest {
    Socket serverSocket;
    ClientSocketStreamReader socketStreamReader;
    ClientSendHandler sendHandler;
    ClientReceiveHandler receiveHandler;
    ClientRequestHandler requestHandler;
    ObjectInputFactory objectInputFactory;
    ObjectInput objectInput;
    ClientConnectionManager clientConnectionManager;
    ObjectOutputFactory objectOutputFactory;
    SocketFactory serverSocketFactory;
    ClientSocketStreamReaderFactory clientSocketStreamReaderFactory;
    ClientPackageVisitor packageVisitor;
    ObjectOutputStream objectOutput;
    void prepareForTest() throws IOException {
        serverSocket = Mockito.mock(Socket.class);
        socketStreamReader = Mockito.mock(ClientSocketStreamReader.class);
        sendHandler = Mockito.mock(ClientSendHandler.class);
        receiveHandler = Mockito.mock(ClientReceiveHandler.class);
        requestHandler = Mockito.mock(ClientRequestHandler.class);
        objectInputFactory = Mockito.mock(ObjectInputFactory.class);
        objectInput = Mockito.mock(ObjectInput.class);
        objectOutputFactory = Mockito.mock(ObjectOutputFactory.class);
        serverSocketFactory = Mockito.mock(SocketFactory.class);
        clientSocketStreamReaderFactory = Mockito.mock(ClientSocketStreamReaderFactory.class);
        packageVisitor = Mockito.mock(ClientPackageVisitor.class);
        clientConnectionManager = new ClientConnectionManager(
                sendHandler, objectInputFactory, receiveHandler, requestHandler, objectOutputFactory, serverSocketFactory, clientSocketStreamReaderFactory, packageVisitor);
        Mockito.when(objectInputFactory.createObjectInput(Mockito.any())).thenReturn(objectInput);
        Mockito.when(serverSocket.getInputStream()).thenReturn(null);
        Mockito.when(clientSocketStreamReaderFactory.createClientSocketStreamReader(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(socketStreamReader);
        objectOutput = Mockito.mock(ObjectOutputStream.class);
        Mockito.when(objectOutputFactory.createObjectOutput(serverSocket)).thenReturn(objectOutput);
    }


    @Test
    void startService() throws IOException {
        //given
        prepareForTest();
        Mockito.when(serverSocketFactory.createSocket(Mockito.anyString(), Mockito.anyInt())).thenReturn(serverSocket);

        //when
        clientConnectionManager.startService();

        //then
        Mockito.verify(serverSocketFactory, Mockito.times(1)).createSocket(Mockito.anyString(), Mockito.anyInt());
        Mockito.verify(objectInputFactory, Mockito.times(1)).createObjectInput(Mockito.any());
        Mockito.verify(clientSocketStreamReaderFactory, Mockito.times(1)).createClientSocketStreamReader(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(socketStreamReader, Mockito.times(1)).start();
        Mockito.verify(objectOutputFactory, Mockito.times(1)).createObjectOutput(serverSocket);
        Mockito.verify(sendHandler, Mockito.times(1)).trySetOutputStream(objectOutput);
    }

    @Test
    void startServiceWithTwoRetries() throws IOException {
        //given
        prepareForTest();
        Mockito.when(serverSocketFactory.createSocket(Mockito.anyString(), Mockito.anyInt())).thenThrow(new ConnectException()).thenThrow(new ConnectException()).thenReturn(serverSocket);

        //when
        clientConnectionManager.startService();

        //then
        Mockito.verify(serverSocketFactory, Mockito.times(3)).createSocket(Mockito.anyString(), Mockito.anyInt());
        Mockito.verify(objectInputFactory, Mockito.times(1)).createObjectInput(Mockito.any());
        Mockito.verify(clientSocketStreamReaderFactory, Mockito.times(1)).createClientSocketStreamReader(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(socketStreamReader, Mockito.times(1)).start();
        Mockito.verify(objectOutputFactory, Mockito.times(1)).createObjectOutput(serverSocket);
        Mockito.verify(sendHandler, Mockito.times(1)).trySetOutputStream(objectOutput);
    }

    @Test
    void startServiceFails() throws IOException {
        //given
        prepareForTest();
        Mockito.when(serverSocketFactory.createSocket(Mockito.anyString(), Mockito.anyInt())).thenThrow(new ConnectException());

        //when & then
        assertThrows(ConnectException.class,()->clientConnectionManager.startService());
        Mockito.verify(serverSocketFactory, Mockito.times(ConnectionSettings.NUMBER_OF_CONNECTION_TRIES)).createSocket(Mockito.anyString(), Mockito.anyInt());
    }

}