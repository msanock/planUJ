package connection.connectorSoundsStupid.download;

import connection.connectorSoundsStupid.download.downloadProxy.DownloadManager;

import java.net.Socket;

public class SocketStreamReader extends Thread {
    private final Socket socket;
    private final DownloadManager downloadManager;

    public SocketStreamReader(Socket socket, DownloadManager downloadManager) {
        this.socket = socket;
        this.downloadManager = downloadManager;
    }

    @Override
    public void run() {

    }


}
