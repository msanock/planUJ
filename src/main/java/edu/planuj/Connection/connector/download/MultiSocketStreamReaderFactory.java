package edu.planuj.Connection.connector.download;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.serverConnection.ServerReceiveHandlerImplementation;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import java.util.concurrent.ExecutorService;

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
