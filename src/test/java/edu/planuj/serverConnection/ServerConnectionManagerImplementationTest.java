package edu.planuj.serverConnection;

import edu.planuj.Connection.ObjectOutputFactory;
import edu.planuj.Connection.connector.download.MultiSocketStreamReader;
import edu.planuj.Connection.connector.download.MultiSocketStreamReaderFactory;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.serverConnection.ServerClientFactory;
import edu.planuj.serverConnection.ServerConnectionManagerImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import javax.net.ServerSocketFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

class ServerConnectionManagerImplementationTest {

    ServerSendHandler sendHandler;
    PackageVisitor packageVisitor;
    SocketSelector socketSelector;
    MultiSocketStreamReaderFactory multiSocketStreamReaderFactory;
    MultiSocketStreamReader multiSocketStreamReader;
    ServerSocketFactory serverSocketFactory;
    ServerSocket serverSocket;
    ExecutorService executorService;
    ServerClientFactory serverClientFactory;
    Socket socket;
    ServerClient serverClient;
    IOException ioException;
    ObjectOutputFactory objectOutputFactory;

    private void prepareForTesting() throws IOException {
        sendHandler = Mockito.mock(ServerSendHandler.class);
        packageVisitor = Mockito.mock(PackageVisitor.class);
        socketSelector = Mockito.mock(SocketSelector.class);
        multiSocketStreamReaderFactory = Mockito.mock(MultiSocketStreamReaderFactory.class);
        serverSocketFactory = Mockito.mock(ServerSocketFactory.class);
        serverSocket = Mockito.mock(ServerSocket.class);
        serverClientFactory = Mockito.mock(ServerClientFactory.class);
        multiSocketStreamReader = Mockito.mock(MultiSocketStreamReader.class);
        assertDoesNotThrow(()->Mockito.when(serverSocketFactory.createServerSocket(Mockito.anyInt())).thenReturn(serverSocket));
        executorService = Mockito.mock(ExecutorService.class);
        Mockito.when(multiSocketStreamReaderFactory.createMultiSocketStreamReader(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(multiSocketStreamReader);
        socket = Mockito.mock(Socket.class);
        ioException = Mockito.mock(IOException.class);
        assertDoesNotThrow(()->Mockito.when(serverSocket.accept()).thenReturn(socket)).thenThrow(ioException);
        serverClient = Mockito.mock(ServerClient.class);
        Mockito.when(serverClientFactory.createServerClient(Mockito.any())).thenReturn(serverClient);
        objectOutputFactory = Mockito.mock(ObjectOutputFactory.class);
        Mockito.when(objectOutputFactory.createObjectOutput(Mockito.any())).thenReturn(Mockito.mock(ObjectOutputStream.class));

    }

    @Test
    void restartService() {
        //TODO
    }

    @Test
    void startService() throws IOException {
        //given
        prepareForTesting();
        ServerConnectionManagerImplementation serverConnectionManagerImplementation =
                Mockito.spy(new ServerConnectionManagerImplementation(
                        sendHandler, packageVisitor, socketSelector, serverSocketFactory, multiSocketStreamReaderFactory, serverClientFactory, objectOutputFactory));

        //when
        assertDoesNotThrow(serverConnectionManagerImplementation::startService);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {}


        //then
        assertDoesNotThrow(()->Mockito.verify(serverConnectionManagerImplementation, Mockito.times(2)).acceptNewConnection(serverSocket));
        assertDoesNotThrow(()->Mockito.verify(sendHandler, Mockito.times(1)).send(Mockito.any(), eq(serverClient)));
        Mockito.verify(socketSelector, Mockito.times(1)).AddNewClient(serverClient);
        try {
            Mockito.verify(serverSocket, Mockito.times(2)).accept();
        } catch (IOException ignore) {}
    }

    @Test
    void acceptLogOut() {
        //TODO
    }

    @Test
    void finishConnection() {
        //TODO
    }
}