package Connection.protocol.packages;

import Connection.protocol.RespondInformation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class EmptyPackTest extends BasicPackageTestPrep{

    @Test
    void accept() {
        //given
        EmptyPack emptyPack = new EmptyPack();
        prepare();
        Mockito.when(packageVisitor.handleEmptyPack(emptyPack, serverClient)).thenReturn(respondInformation);

        basicTest(emptyPack);

        Mockito.verify(packageVisitor, Mockito.times(1)).handleEmptyPack(emptyPack, serverClient);
    }
}