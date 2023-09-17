package edu.planuj.client;

import edu.planuj.Utils.UserInfo;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientInformation extends UserInfo implements Serializable{

    private static final Object lock = new Object();
    private AtomicBoolean isLoggedIn;
    private ClientInformation(){
        super(null, 0);
        isLoggedIn = new AtomicBoolean(false);
    }

    public static boolean isCorrectLogin(String login) {
        if (login == null || login.isBlank())
            return false;

        return true;
    }

    private static class Holder{
        private static final ClientInformation INSTANCE = new ClientInformation();
    }

    public static ClientInformation getInstance(){
        return Holder.INSTANCE;
    }

    public void setClientName(String name){
        super.setName(name);
    }

    public void logInWithId(int id){
        super.setId(id);
        isLoggedIn.set(true);
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}
