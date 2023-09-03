package serverConnection.abstraction;

import java.io.ObjectOutputStream;

public interface ServerClientInfo {
    Long getClientID();
    ObjectOutputStream getObjectOutputStream();
}
