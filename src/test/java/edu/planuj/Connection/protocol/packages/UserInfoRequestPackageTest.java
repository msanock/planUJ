package edu.planuj.Connection.protocol.packages;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.protocol.RespondInformation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoRequestPackageTest extends BasicPackageTestPrep{

    @Test
    void accept() {
        //given
        prepare();
        UserInfoRequestPackage userInfoRequestPackage = new UserInfoRequestPackage();
        Mockito.when(packageVisitor.handleUserInfoRequestPack(userInfoRequestPackage, serverClient)).thenReturn(respondInformation);

        //when
        RespondInformation result = userInfoRequestPackage.accept(packageVisitor, serverClient);

        //then
        Mockito.verify(packageVisitor).handleUserInfoRequestPack(userInfoRequestPackage, serverClient);
        assertEquals(respondInformation, result);
    }

    @Test
    void testAccept() {
        //given
        ClientPackageVisitor clientPackageVisitor = Mockito.mock(ClientPackageVisitor.class);
        UserInfoRequestPackage userInfoRequestPackage = new UserInfoRequestPackage();

        //when
        userInfoRequestPackage.accept(clientPackageVisitor);

        //then
        Mockito.verify(clientPackageVisitor).handleUserInfoRequestPack(userInfoRequestPackage);
    }
}