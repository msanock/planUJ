package Connection.protocol;

import serverConnection.abstraction.ServerClient;

import java.util.Map;

public class RespondInformation {
    private Map<Long, Packable> responses;

    public RespondInformation(RespondInformationBuilder builder) {
        this.responses = builder.responses;
    }
    public Map<Long, Packable> getResponses() {
        return responses;
    }

    public static class RespondInformationBuilder {
        Map<Long, Packable> responses;

        public RespondInformationBuilder addRespond(long clientID, Packable respond) {
            responses.put(clientID, respond);
            return this;
        }

        public RespondInformation build() {
            return new RespondInformation(this);
        }
    }
}
