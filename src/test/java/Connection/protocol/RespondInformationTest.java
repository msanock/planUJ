package Connection.protocol;

import Connection.protocol.packages.EmptyPack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RespondInformationTest {

    @Test
    void getResponses() {
        RespondInformation.RespondInformationBuilder builder = new RespondInformation.RespondInformationBuilder();
        Packable emptyPack = new EmptyPack();
        builder.addRespond(1, emptyPack);
        RespondInformation respondInformation = builder.build();
        assertEquals(1, respondInformation.getResponses().size());
        assertEquals(emptyPack, respondInformation.getResponses().get(1L));
    }

    @Test
    void builderTest(){
        RespondInformation.RespondInformationBuilder builder = new RespondInformation.RespondInformationBuilder();
        Packable emptyPack = new EmptyPack();
        builder.addRespond(1, emptyPack);
        RespondInformation respondInformation = builder.build();
        assertEquals(1, respondInformation.getResponses().size());
        assertEquals(emptyPack, respondInformation.getResponses().get(1L));
    }
}