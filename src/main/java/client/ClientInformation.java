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

    private static ClientInformation instance;

    public static ClientInformation getInstance(){
        if(instance == null){
            instance = new ClientInformation();
        }
        return instance;
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
