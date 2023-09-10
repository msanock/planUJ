package clientConnection;

import java.io.IOException;

public class SendTimeoutException extends IOException {
    public SendTimeoutException(String message) {
        super(message);
    }
}
