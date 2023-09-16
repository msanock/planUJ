package edu.planuj.Connection.protocol;

import java.util.List;
import java.util.Map;

public class RespondInformation {

    private Map<Long, List<Packable>> responses;

    public RespondInformation(RespondInformationBuilder builder) {
        this.responses = builder.responses;
    }
    public Map<Long, List<Packable>> getResponses() {
        return responses;
    }

    public static class RespondInformationBuilder {
        Map<Long, List<Packable>> responses;

        public RespondInformationBuilder() {
            responses = new java.util.HashMap<>();
        }


        public RespondInformationBuilder addRespond(long clientID, Packable respond) {
            responses.computeIfAbsent(clientID, k -> new java.util.ArrayList<>());
            responses.get(clientID).add(respond);
            return this;
        }

        public RespondInformation build() {
            return new RespondInformation(this);
        }
    }
}
