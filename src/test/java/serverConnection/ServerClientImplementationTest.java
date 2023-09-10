package serverConnection;

import Connection.connector.download.ServerSocketStreamReader;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerClientImplementationTest {

    Socket socket;
    ObjectOutputStream objectOutputStream;
    ServerSocketStreamReader socketStreamReader;
    InputStream inputStream;
    void prepareForTesting(){
        socket = Mockito.mock(Socket.class);
        objectOutputStream = Mockito.mock(ObjectOutputStream.class);
        socketStreamReader = Mockito.mock(ServerSocketStreamReader.class);
        inputStream = Mockito.mock(InputStream.class);
        assertDoesNotThrow(()->Mockito.when(socket.getInputStream()).thenReturn(inputStream));
    }

    @Test
    void getInputStream() throws IOException {
        //given
        prepareForTesting();
        ServerClientImplementation serverClientImplementation = new ServerClientImplementation(socket);

        //when
        InputStream res = serverClientImplementation.getInputStream();

        //then
        Mockito.verify(socket, Mockito.times(1)).getInputStream();
        assertEquals(inputStream, res);
    }

    @Test
    void startSocketStreamReader() throws IOException {
        //given
        prepareForTesting();
        ServerClientImplementation serverClientImplementation = new ServerClientImplementation(socket);
        serverClientImplementation.setSocketStreamReader(socketStreamReader, objectOutputStream);

        //when
        assertDoesNotThrow(serverClientImplementation::startSocketStreamReader);

        //then
        Mockito.verify(socketStreamReader, Mockito.times(1)).start();
    }

    @Test
    void startNoSocketStreamReader() {
        //given
        prepareForTesting();
        ServerClientImplementation serverClientImplementation = new ServerClientImplementation(socket);

        //when & then
        assertThrows( NoStreamReaderException.class,serverClientImplementation::startSocketStreamReader);
    }
}