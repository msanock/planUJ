package Server;

import Server.database.Database;
import java.net.Inet6Address;

public interface Packet {
    //placeholder
    public Inet6Address getIp();

    public void execute(Database database);
}
