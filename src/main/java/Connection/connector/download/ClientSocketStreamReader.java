package Connection.connector.download;

import Connection.protocol.Packable;
import clientConnection.ClientReceiveHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicBoolean;


public class ClientSocketStreamReader extends Thread {
    private final ObjectInputStream stream;
    private final ClientReceiveHandler handler;
    private volatile Boolean active;
    private volatile AtomicBoolean continueReading;

    public ClientSocketStreamReader(InputStream stream, ClientReceiveHandler handler) throws IOException {
        this.stream = new ObjectInputStream(stream);;
        this.handler = handler;
        active = false;
        continueReading = new AtomicBoolean(true);

        this.setDaemon(true);
    }


    @Override
    public void run() {
        try {
            active = true;
            while(true) {
                if (continueReading.get()) {
                    Packable newPackage = (Packable) stream.readObject();
                    if (newPackage == null)
                        break;

                    handler.onNewPackage(newPackage);
                }
                else {
                    synchronized (active) {
                        active = false;
                        active.notify();
                    }
                    try {
                        continueReading.wait();
                    } catch (InterruptedException ignore) { }
                    if (continueReading.get())
                        active = true;
                }
            }
        } catch (Exception e) {
            handler.onLostConnection();
            // ???
            active = false;

        }
    }


    public boolean getActive() {
        return active;
    }

    public boolean suspendReading() {
        if (!continueReading.compareAndSet(true, false))
            return false;
        while (active){
            try {
                active.wait();
            } catch (InterruptedException ignore) { }

        }
        return true;
    }

    public boolean continueReading() {
        return continueReading.compareAndSet(false, true);
    }
}