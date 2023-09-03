package Connection.protocol.packages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponsePackageTest {


    public ResponsePackage getResponsePackage() {
        return new ResponsePackage.Builder().addData("test", "test").addData("test2", "test2").build();
    }

    @Test
    void builderTest() {
        ResponsePackage.Builder builder = new ResponsePackage.Builder();
        builder.addData("test", "test");
        builder.addData("test2", "test2");
        builder.setSuccess(true);
        ResponsePackage responsePackage = builder.build();
        assertEquals("test", responsePackage.getData("test"));
        assertEquals("test2", responsePackage.getData("test2"));
    }

    @Test
    void accept() {
        assertThrows(UnsupportedOperationException.class, () -> {
            getResponsePackage().accept(null, null);
        });
    }

    @Test
    void isSuccess() {
        ResponsePackage responsePackage = getResponsePackage();
        assertTrue(responsePackage.isSuccess());
        responsePackage.setSuccess(false);
        assertFalse(responsePackage.isSuccess());
    }

    @Test
    void getData() {
        ResponsePackage responsePackage = getResponsePackage();
        assertEquals("test", responsePackage.getData("test"));
        assertEquals("test2", responsePackage.getData("test2"));
    }

    @Test
    void setSuccess() {
        ResponsePackage responsePackage = getResponsePackage();
        responsePackage.setSuccess(false);
        assertFalse(responsePackage.isSuccess());
        responsePackage.setSuccess(true);
        assertTrue(responsePackage.isSuccess());
    }
}