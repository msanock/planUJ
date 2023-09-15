package edu.planuj.Connection.protocol.packages.userOperations;

import edu.planuj.Connection.protocol.packages.BasicPackageTestPrep;
import edu.planuj.Utils.UserInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserOperationsPackageTest extends BasicPackageTestPrep {

    @Test
    void acceptLogin() {
        //given
        LoginPackage aPackage = new LoginPackage(Mockito.mock(UserInfo.class));
        prepare();
        Mockito.when(packageVisitor.handleLoginPackage(aPackage, serverClient)).thenReturn(respondInformation);

        basicTest(aPackage);

        Mockito.verify(packageVisitor, Mockito.times(1)).handleLoginPackage(aPackage, serverClient);
    }

    @Test
    void acceptGetUsers() {
        //given
        GetUsersPackage aPackage = new GetUsersPackage();
        prepare();
        Mockito.when(packageVisitor.handleGetUsersPackage(aPackage, serverClient)).thenReturn(respondInformation);

        basicTest(aPackage);

        Mockito.verify(packageVisitor, Mockito.times(1)).handleGetUsersPackage(aPackage, serverClient);
    }
}