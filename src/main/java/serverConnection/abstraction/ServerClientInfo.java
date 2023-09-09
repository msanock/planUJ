package serverConnection.abstraction;

import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public interface ServerClientInfo {
    Long getClientID();
    ObjectOutput getObjectOutput();
}
