package serverConnection;

import Connection.protocol.Packable;

public class Task implements Runnable {
    Packable packable;
    public Task(Packable packable) {
        this.packable = packable;
    }

    @Override
    public void run() {
        packable.accept(new ServerPackageVisitor());
    }
}
