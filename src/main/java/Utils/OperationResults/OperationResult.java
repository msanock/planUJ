package Utils.OperationResults;

import Server.Packet;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class OperationResult {
    protected boolean success;
    protected Exception exception;

    public OperationResult() {
        this.success = false;
        exception = null;
    }

    public Packet toPacket() {
        throw new UnsupportedOperationException();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Exception getError() {
        return exception;
    }
}
