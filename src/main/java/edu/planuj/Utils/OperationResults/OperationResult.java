package edu.planuj.Utils.OperationResults;

import edu.planuj.Connection.protocol.Packable;

import java.util.UUID;

public abstract class OperationResult {
    protected boolean success;
    protected Exception exception;

    public OperationResult() {
        this.success = false;
        exception = null;
    }

    public Packable toResponsePackage(UUID uuid) {
        throw new UnsupportedOperationException();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Exception getError() {
        return exception;
    }
}
