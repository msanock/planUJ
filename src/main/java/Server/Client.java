package Server;

import java.net.Inet6Address;

public class Client {
    private String username;
    private Inet6Address ip;

    public Client(String username, Inet6Address ip) {
        this.username = username;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public Inet6Address getIp() {
        return ip;
    }
}
