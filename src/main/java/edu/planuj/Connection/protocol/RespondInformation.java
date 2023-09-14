package edu.planuj.Connection.protocol;

import java.util.Map;

public class RespondInformation {
    // TODO shouldn't it handle sender response differently just so sendResponses could focus on this response in particular
    // what?

    private Map<Long, Packable> responses;

    public RespondInformation(RespondInformationBuilder builder) {
        this.responses = builder.responses;
    }
    public Map<Long, Packable> getResponses() {
        return responses;
    }

    public static class RespondInformationBuilder {
        Map<Long, Packable> responses;

        public RespondInformationBuilder() {
            responses = new java.util.HashMap<>();
        }


        public RespondInformationBuilder addRespond(long clientID, Packable respond) {
            responses.put(clientID, respond);
            return this;
        }

        public RespondInformation build() {
            return new RespondInformation(this);
        }
    }
}
