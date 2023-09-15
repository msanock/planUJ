package edu.planuj.Connection.protocol.packages;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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