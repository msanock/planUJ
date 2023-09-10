package Connection.protocol.packages;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import org.mockito.Mockito;
import serverConnection.abstraction.ServerClient;

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
