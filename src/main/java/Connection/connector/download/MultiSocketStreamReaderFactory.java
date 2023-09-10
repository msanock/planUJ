package Connection.connector.download;

import Connection.connector.upload.SendHandler;
import Connection.manager.PackageVisitor;
import serverConnection.ServerReceiveHandlerImplementation;
import serverConnection.abstraction.ServerSendHandler;
import serverConnection.abstraction.SocketSelector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketStreamReaderFactory {

    public MultiSocketStreamReader createMultiSocketStreamReader(
            ServerSendHandler sendHandler,
            PackageVisitor packageVisitor,
            SocketSelector socketSelector,
            ExecutorService executors
    ) {
        return new MultiSocketStreamReader(
                new ServerReceiveHandlerImplementation(
                        sendHandler,
                        packageVisitor,
                        socketSelector,
                        executors
                ),
                new ObjectInputFactory(),
                new ServerSocketStreamReaderFactory()
        );
    }
}
