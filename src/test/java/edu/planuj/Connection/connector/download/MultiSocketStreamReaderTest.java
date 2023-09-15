package edu.planuj.Connection.connector.download;

import edu.planuj.Connection.connector.download.MultiSocketStreamReader;
import edu.planuj.Connection.connector.download.ObjectInputFactory;
import edu.planuj.Connection.connector.download.ServerSocketStreamReader;
import edu.planuj.Connection.connector.download.ServerSocketStreamReaderFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerReceiveHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MultiSocketStreamReaderTest {
    ServerReceiveHandler handler;
    ObjectInputFactory objectInputFactory;
    ServerSocketStreamReaderFactory serverSocketStreamReaderFactory;
    ServerClient client;
    ServerSocketStreamReader serverSocketStreamReader;

    @Test
    void addNewReader() throws IOException {
        //given
        handler = Mockito.mock(ServerReceiveHandler.class);
        objectInputFactory = Mockito.mock(ObjectInputFactory.class);
        serverSocketStreamReaderFactory = Mockito.mock(ServerSocketStreamReaderFactory.class);
        MultiSocketStreamReader multiSocketStreamReader = new MultiSocketStreamReader(handler, objectInputFactory, serverSocketStreamReaderFactory);
        client = Mockito.mock(ServerClient.class);
        serverSocketStreamReader = Mockito.mock(ServerSocketStreamReader.class);
        Mockito.when(serverSocketStreamReaderFactory.createServerSocketStreamReader(client, objectInputFactory, handler)).thenReturn(serverSocketStreamReader);


        //when
        ServerSocketStreamReader reader = multiSocketStreamReader.addNewReader(client);

        //then
        Mockito.verify(serverSocketStreamReaderFactory).createServerSocketStreamReader(client, objectInputFactory, handler);
        Mockito.verify(serverSocketStreamReader).start();
        assertEquals(reader, serverSocketStreamReader);
    }
}