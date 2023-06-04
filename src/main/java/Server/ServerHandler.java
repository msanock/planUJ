package Server;

import Server.database.Engine;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler implements Notifiable {
    private final IServer server;
    private final Engine engine;
    private ArrayList<Client> clients = new ArrayList<Client>();
    private ExecutorService executorService;
    public ServerHandler (IServer server, Engine engine){
        this.server = server;
        this.engine = engine;
        executorService = Executors.newCachedThreadPool();
    }

    public void notify(Packet packet) {
        if(packet instanceof LoginPacket) {
            LoginPacket loginPacket = (LoginPacket) packet;
            String username = loginPacket.getUsername();

            executorService.execute(() -> engine.addUser(username));

            Client client = new Client(username, loginPacket.getIp());
            clients.add(client);
        }
    }

    public void Dispose() {
        executorService.shutdown();
    }
}
