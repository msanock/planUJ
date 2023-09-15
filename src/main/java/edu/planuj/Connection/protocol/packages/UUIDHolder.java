package edu.planuj.Connection.protocol.packages;

import java.util.UUID;

public class UUIDHolder {
    private UUID uuid;

    public UUIDHolder() {
        uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
