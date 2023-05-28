package connection.protocol;

import java.io.Serializable;

public abstract class AbstractPackage implements Serializable {
    final MessageTypes type;

    protected AbstractPackage(MessageTypes type) {
        this.type = type;
    }

    public MessageTypes getType() {
        return type;
    }
}
