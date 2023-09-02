package Utils.OperationResults;

import Connection.protocol.Packable;

public abstract class OperationResult {
    protected boolean success;
    protected Exception exception;

    public OperationResult() {
        this.success = false;
        exception = null;
    }

    public Packable toResponsePackage() {
        throw new UnsupportedOperationException();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Exception getError() {
        return exception;
    }
}
