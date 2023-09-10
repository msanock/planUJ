package Connection.connector.download;

import Connection.manager.ClientPackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import clientConnection.ClientReceiveHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.ObjectInput;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ClientSocketStreamReaderTest {
    ObjectInput stream;
    Socket socket;
    ClientReceiveHandler handler;
    ClientPackageVisitor packageVisitor;

    void prepareForTest() {
        socket = Mockito.mock(Socket.class);
        stream = Mockito.mock(ObjectInput.class);
        handler = Mockito.mock(ClientReceiveHandler.class);
        stream = Mockito.mock(ObjectInput.class);
        packageVisitor = Mockito.mock(ClientPackageVisitor.class);
    }

    @Test
    void run() throws IOException, ClassNotFoundException, InterruptedException {
        //given
        prepareForTest();
        ClientSocketStreamReader clientSocketStreamReader =
                Mockito.spy(new ClientSocketStreamReader(socket, handler, stream, packageVisitor));
        ClientPackable packable = Mockito.mock(ClientPackable.class);
        Mockito.when(stream.readObject()).thenReturn(packable).thenReturn(null);

        //when
        clientSocketStreamReader.run();
        Thread.sleep(100);

        //then
        Mockito.verify(handler, Mockito.times(1)).onNewPackage(packable, packageVisitor);
        Mockito.verify(socket, Mockito.times(1)).close();
        Mockito.verify(stream, Mockito.times(1)).close();
        Mockito.verify(stream, Mockito.times(2)).readObject();
    }

    @Test
    void runWithException() throws IOException, ClassNotFoundException {
        //given
        prepareForTest();
        ClientSocketStreamReader clientSocketStreamReader =
                Mockito.spy(new ClientSocketStreamReader(socket, handler, stream, packageVisitor));
        ClientPackable packable = Mockito.mock(ClientPackable.class);
        Mockito.when(stream.readObject()).thenThrow(new IOException());

        //when
        clientSocketStreamReader.run();

        //then
        Mockito.verify(handler, Mockito.times(0)).onNewPackage(packable, packageVisitor);
        Mockito.verify(socket, Mockito.times(1)).close();
        Mockito.verify(stream, Mockito.times(1)).close();
        Mockito.verify(stream, Mockito.times(1)).readObject();
    }
}