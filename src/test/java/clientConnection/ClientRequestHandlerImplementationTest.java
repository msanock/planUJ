package clientConnection;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientRequestHandlerImplementationTest {
    ClientSendHandler sendHandler;
    ClientReceiveHandler receiveHandler;
    Packable pack;

    void prepareForTest(){
        sendHandler = Mockito.mock(ClientSendHandler.class);
        receiveHandler = Mockito.mock(ClientReceiveHandler.class);
        pack = Mockito.mock(Packable.class);
        Mockito.when(receiveHandler.getSendHandler()).thenReturn(sendHandler);
    }


    @Test
    void sendUnrespondablePackage() throws IOException {
        //given
        prepareForTest();
        ClientRequestHandlerImplementation clientRequestHandlerImplementation = new ClientRequestHandlerImplementation(receiveHandler);

        //when
        clientRequestHandlerImplementation.sendUnrespondablePackage(pack);

        //then
        Mockito.verify(sendHandler).send(pack);
    }

    @Test
    void update() {
        //when
        prepareForTest();
        ClientRequestHandlerImplementation clientRequestHandlerImplementation = new ClientRequestHandlerImplementation(receiveHandler);
        ResponsePackage pack = Mockito.mock(ResponsePackage.class);

        //then
        clientRequestHandlerImplementation.update(pack);

        //then
        assertEquals(pack, clientRequestHandlerImplementation.getCurrentResponse());
    }

    @Test
    void sendAndGetResponse() throws IOException {
        //given
        prepareForTest();
        ClientRequestHandlerImplementation clientRequestHandlerImplementation =
                Mockito.spy(new ClientRequestHandlerImplementation(receiveHandler));
        ResponsePackage responsePack = Mockito.mock(ResponsePackage.class);
        Mockito.when(clientRequestHandlerImplementation.getCurrentResponse()).thenReturn(responsePack);

        //when
        ResponsePackage response = clientRequestHandlerImplementation.sendAndGetResponse(pack);

        //then
        Mockito.verify(sendHandler).send(pack);
        Mockito.verify(receiveHandler).setReceiver(clientRequestHandlerImplementation);
        Mockito.verify(receiveHandler).deleteReceiver(clientRequestHandlerImplementation);
        assertEquals(responsePack, response);
    }

    @Test
    void sendAndGetResponseTimeout(){
        //given
        ConnectionSettings.REQUEST_TIMEOUT = 1;
        prepareForTest();
        ClientRequestHandlerImplementation clientRequestHandlerImplementation =
                Mockito.spy(new ClientRequestHandlerImplementation(receiveHandler));
        Mockito.when(clientRequestHandlerImplementation.getCurrentResponse()).thenReturn(null);

        //when & then
        assertThrows(SendTimeoutException.class, () -> clientRequestHandlerImplementation.sendAndGetResponse(pack));
        Mockito.verify(receiveHandler).deleteReceiver(clientRequestHandlerImplementation);
    }
}