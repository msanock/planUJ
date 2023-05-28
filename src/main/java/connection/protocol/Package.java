package connection.protocol;

import java.io.Serializable;

public abstract class Package implements Serializable {
    final MessageTypes type;

    protected Package(MessageTypes type) {
        this.type = type;
    }

    public MessageTypes getType() {
        return type;
    }
}
