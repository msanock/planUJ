package client;

import Utils.UserInfo;

import java.util.concurrent.atomic.AtomicBoolean;

public class ClientInformation extends UserInfo {

    private static final Object lock = new Object();
    private AtomicBoolean isLoggedIn;
    private ClientInformation(){
        super(null, 0);
        isLoggedIn = new AtomicBoolean(false);
    }

    public static boolean isCorrectLogin(String login) {
        // TODO  Rafał skrytykuj, ewentualnie dodaj jakieś factory
        if (login == null)
            return false;
        if (login.isBlank())
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

    public void LogInWithId(int id){
        super.setId(id);
        isLoggedIn.set(true);
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public void setClientInfo(String name, int id){
        super.setName(name);
        super.setId(id);
    }

    public boolean isLoggedIn(){
        return isLoggedIn.get();
    }


    public void waitForLogin(){
        synchronized (lock){
            while(!isLoggedIn.get()){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
