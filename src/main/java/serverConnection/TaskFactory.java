package serverConnection;

import java.util.concurrent.ThreadFactory;

public class TaskFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
