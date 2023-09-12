package Connection.connector.download;

import Connection.protocol.Packable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerReceiveHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;

import static org.junit.jupiter.api.Assertions.*;

class ServerSocketStreamReaderTest {
    InputStream stream;
    ServerReceiveHandler handler;
    ServerClient client;
    ObjectInputFactory objectInputFactory;
    ObjectInput objectInput;
    Packable packable;

    void prepareForTesting() throws IOException, ClassNotFoundException {
        stream = Mockito.mock(InputStream.class);
        handler = Mockito.mock(ServerReceiveHandler.class);
        client = Mockito.mock(ServerClient.class);
        Mockito.when(client.getInputStream()).thenReturn(stream);
        objectInputFactory = Mockito.mock(ObjectInputFactory.class);
        objectInput = Mockito.mock(ObjectInput.class);
        packable = Mockito.mock(Packable.class);
        Mockito.when(objectInputFactory.createObjectInput(stream)).thenReturn(objectInput);
    }

    @Test
    void run() throws IOException, ClassNotFoundException, InterruptedException {
        //given
        prepareForTesting();
        Mockito.when(objectInput.readObject()).thenReturn(packable).thenReturn(null);
        ServerSocketStreamReader serverSocketStreamReader =
                Mockito.spy(new ServerSocketStreamReader(client, handler, objectInputFactory));

        //when
        serverSocketStreamReader.run();
        Thread.sleep(500);

        //then
        Mockito.verify(objectInputFactory, Mockito.times(1)).createObjectInput(stream);
        Mockito.verify(objectInput, Mockito.times(2)).readObject();
        Mockito.verify(handler, Mockito.times(1)).onNewPackage(Mockito.any(Packable.class), Mockito.eq(client));
        Mockito.verify(handler, Mockito.times(1)).onLostConnection(client);
    }

    @Test
    void runOnException() throws IOException, ClassNotFoundException, InterruptedException {
        //given
        prepareForTesting();
        ServerSocketStreamReader serverSocketStreamReader =
                Mockito.spy(new ServerSocketStreamReader(client, handler, objectInputFactory));
        Mockito.when(objectInput.readObject()).thenThrow(new IOException());

        //when
        serverSocketStreamReader.run();
        Thread.sleep(500);

        //then
        Mockito.verify(objectInputFactory, Mockito.times(1)).createObjectInput(stream);
        Mockito.verify(handler, Mockito.times(1)).onLostConnection(client);
    }

}