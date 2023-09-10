package clientConnection;

import Connection.protocol.Packable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.io.IOException;
import java.io.ObjectOutput;

import static org.junit.jupiter.api.Assertions.*;

class ClientSendHandlerTest {


    @Test
    void trySetOutputStream() {
        //given
        ClientSendHandler clientSendHandler = new ClientSendHandler();
        ObjectOutput objectOutput = Mockito.mock(ObjectOutput.class);

        //when
        clientSendHandler.trySetOutputStream(objectOutput);

        //then
        assertTrue(clientSendHandler.isOnline());
    }

    @Test
    void send() throws IOException {
        //given
        ClientSendHandler clientSendHandler = new ClientSendHandler();
        Packable pack = Mockito.mock(Packable.class);
        ObjectOutput objectOutput = Mockito.mock(ObjectOutput.class);

        //when
        clientSendHandler.trySetOutputStream(objectOutput);
        clientSendHandler.send(pack);

        //then
        Mockito.verify(objectOutput, Mockito.times(1)).writeObject(pack);
    }

    @Test
    void sendNoStream(){
        //given
        ClientSendHandler clientSendHandler = new ClientSendHandler();
        Packable pack = Mockito.mock(Packable.class);

        //when & then
        assertThrows(IOException.class, () -> clientSendHandler.send(pack));
        assertFalse(clientSendHandler.isOnline());
    }
}