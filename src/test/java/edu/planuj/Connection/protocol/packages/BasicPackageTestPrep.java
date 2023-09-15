package edu.planuj.Connection.protocol.packages;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import org.mockito.Mockito;
import edu.planuj.serverConnection.abstraction.ServerClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicPackageTestPrep {
    public PackageVisitor packageVisitor;
    public RespondInformation respondInformation;
    public ServerClient serverClient;
    public RespondInformation result;

    public void prepare(){
        packageVisitor = Mockito.mock(PackageVisitor.class);
        respondInformation = Mockito.mock(RespondInformation.class);
        serverClient = Mockito.mock(ServerClient.class);
    }

    public void basicTest(Packable packable){
        //when
        result = packable.accept(packageVisitor, serverClient);

        //then
        assertEquals(respondInformation, result);
    }
}
