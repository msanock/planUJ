package edu.planuj.serverConnection;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.serverConnection.ServerReceiveHandlerImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerReceiveHandlerImplementationTest {

    ServerSendHandler sendHandler;
    PackageVisitor packageVisitor;
    SocketSelector socketSelector;

    @Test
    void onNewPackage() {
        //given
        sendHandler = Mockito.mock(ServerSendHandler.class);
        packageVisitor = Mockito.mock(PackageVisitor.class);
        socketSelector = Mockito.mock(SocketSelector.class);
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerReceiveHandlerImplementation serverReceiveHandlerImplementation = new ServerReceiveHandlerImplementation(sendHandler, packageVisitor, socketSelector, executorService);
        Packable packable = Mockito.mock(Packable.class);
        RespondInformation respondInformation = Mockito.mock(RespondInformation.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        Mockito.when(packable.accept(packageVisitor, serverClient)).thenReturn(respondInformation);

        //when
        serverReceiveHandlerImplementation.onNewPackage(packable, serverClient);

        try {
            executorService.shutdown();
            while(!executorService.awaitTermination(100, java.util.concurrent.TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //then
        Mockito.verify(packable, Mockito.times(1)).accept(packageVisitor, serverClient);
        Mockito.verify(sendHandler, Mockito.times(1)).sendResponses(respondInformation, socketSelector);
    }

    @Test
    void onLostConnection() {
        //given
        sendHandler = Mockito.mock(ServerSendHandler.class);
        packageVisitor = Mockito.mock(PackageVisitor.class);
        socketSelector = Mockito.mock(SocketSelector.class);
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerReceiveHandlerImplementation serverReceiveHandlerImplementation = new ServerReceiveHandlerImplementation(sendHandler, packageVisitor, socketSelector, executorService);
        ServerClient serverClient = Mockito.mock(ServerClient.class);

        //when
        serverReceiveHandlerImplementation.onLostConnection(serverClient);

        try {
            executorService.shutdown();
            while(!executorService.awaitTermination(100, java.util.concurrent.TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //then
        Mockito.verify(socketSelector, Mockito.times(1)).removeClient(serverClient);
    }
}