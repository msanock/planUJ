package Server;

import Server.database.Database;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler implements Notifiable {
    private final IServer server;
    private final Database engine;
    private ArrayList<Client> clients = new ArrayList<Client>();
    private ExecutorService executorService;
    public ServerHandler (IServer server, Database engine){
        this.server = server;
        this.engine = engine;
        executorService = Executors.newCachedThreadPool();
    }

    public void notify(Packet packet) {
        executorService.execute(() -> {
            packet.execute(engine);
        });
    }

    public void Dispose() {
        executorService.shutdown();
    }
}
