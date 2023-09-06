package Connection.protocol.packages;

import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.abstraction.ServerClient;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class ResponsePackage extends UUIDHolder implements Packable, ClientPackable {
    private boolean success;
    private Map<String, Object> data;

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender){
        throw new UnsupportedOperationException();
    }

    private ResponsePackage(Builder builder) {
        this.data = builder.data;
        this.success = builder.success;
        setUuid(builder.uuid);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public void accept(ClientPackageVisitor v) {
        return;
    }

    public static class Builder {
        private UUID uuid;
        private boolean success;
        private Map<String, Object> data;

        public Builder() {
            data = new java.util.HashMap<>();
        }

        public Builder addData(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        public ResponsePackage build() {
            return new ResponsePackage(this);
        }

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }
    }

    public static class Dictionary {
        // enums?
        public static final String ID = "id";
        public static final String USERS_LIST = "users";
        public static final String TEAMS_LIST = "teams";
        public static final String TASKS_LIST = "tasks";
        public static final String ERROR = "error";
    }
}
