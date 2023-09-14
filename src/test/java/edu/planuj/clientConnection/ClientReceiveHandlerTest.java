package edu.planuj.clientConnection;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.clientConnection.ClientReceiveHandler;
import edu.planuj.clientConnection.ClientSendHandler;
import edu.planuj.clientConnection.abstraction.ConnectionReceiver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ClientReceiveHandlerTest {
    ClientSendHandler sendHandler;
    ExecutorService executorService;
    ClientPackageVisitor packageVisitor;

    void prepareForTest(){
        sendHandler = Mockito.mock(ClientSendHandler.class);
        executorService = Executors.newCachedThreadPool();
        packageVisitor = Mockito.mock(ClientPackageVisitor.class);
    }
    @Test
    void setReceiver() {
        //given
        prepareForTest();
        ClientReceiveHandler clientReceiveHandler = new ClientReceiveHandler(sendHandler, executorService);
        ConnectionReceiver connectionReceiver = Mockito.mock(ConnectionReceiver.class);

        //when
        clientReceiveHandler.setReceiver(connectionReceiver);

        //then
        assertEquals(connectionReceiver, clientReceiveHandler.getReceiver());
    }

    @Test
    void deleteReceiver() {
        //given
        prepareForTest();
        ClientReceiveHandler clientReceiveHandler = new ClientReceiveHandler(sendHandler, executorService);
        ConnectionReceiver connectionReceiver = Mockito.mock(ConnectionReceiver.class);
        clientReceiveHandler.setReceiver(connectionReceiver);

        //when
        clientReceiveHandler.deleteReceiver(connectionReceiver);

        //then
        assertNull(clientReceiveHandler.getReceiver());
    }

    @Test
    void onNewPackageClientPackable() {
        //given
        prepareForTest();
        ClientReceiveHandler clientReceiveHandler = new ClientReceiveHandler(sendHandler, executorService);
        ConnectionReceiver connectionReceiver = Mockito.mock(ConnectionReceiver.class);
        ClientPackable clientPackable = Mockito.mock(ClientPackable.class);

        //when
        clientReceiveHandler.setReceiver(connectionReceiver);
        clientReceiveHandler.onNewPackage(clientPackable, packageVisitor);

        //then
        try {
            executorService.shutdown();
            while(!executorService.awaitTermination(100, java.util.concurrent.TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(clientPackable, Mockito.times(1)).accept(packageVisitor);
        Mockito.verify(connectionReceiver, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    void onNewPackageResponsePackage(){
        //given
        prepareForTest();
        ClientReceiveHandler clientReceiveHandler = new ClientReceiveHandler(sendHandler, executorService);
        ConnectionReceiver connectionReceiver = Mockito.mock(ConnectionReceiver.class);
        ResponsePackage responsePackage = Mockito.mock(ResponsePackage.class);

        //when
        clientReceiveHandler.setReceiver(connectionReceiver);
        clientReceiveHandler.onNewPackage(responsePackage, packageVisitor);

        //then
        try {
            executorService.shutdown();
            while(!executorService.awaitTermination(100, java.util.concurrent.TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(connectionReceiver, Mockito.times(1)).update(responsePackage);
        Mockito.verify(responsePackage, Mockito.times(0)).accept(packageVisitor);
    }

    @Test
    void onNewPackageResponsePackageNullReceiver(){
        //given
        prepareForTest();
        ClientReceiveHandler clientReceiveHandler = new ClientReceiveHandler(sendHandler, executorService);
        ResponsePackage responsePackage = Mockito.mock(ResponsePackage.class);

        //when
        assertDoesNotThrow(()->clientReceiveHandler.onNewPackage(responsePackage, packageVisitor));

        //then
        try {
            executorService.shutdown();
            while(!executorService.awaitTermination(100, java.util.concurrent.TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(responsePackage, Mockito.times(0)).accept(packageVisitor);
    }


}