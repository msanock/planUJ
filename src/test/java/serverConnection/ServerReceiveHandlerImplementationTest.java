package serverConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerSendHandler;
import serverConnection.abstraction.SocketSelector;

import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;

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
        ServerReceiveHandlerImplementation serverReceiveHandlerImplementation = new ServerReceiveHandlerImplementation(sendHandler, packageVisitor, socketSelector);
        Packable packable = Mockito.mock(Packable.class);
        RespondInformation respondInformation = Mockito.mock(RespondInformation.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        Mockito.when(packable.accept(packageVisitor, serverClient)).thenReturn(respondInformation);

        //when
        serverReceiveHandlerImplementation.onNewPackage(packable, serverClient);

        //then
        Mockito.verify(packable, Mockito.times(1)).accept(packageVisitor, serverClient);
        Mockito.verify(sendHandler, Mockito.times(1)).sendResponses(respondInformation, socketSelector);
    }

    @Test
    void onLostConnection() {
        //TODO
    }
}