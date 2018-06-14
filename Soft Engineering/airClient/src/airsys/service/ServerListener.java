package airsys.service;

public interface ServerListener {
    void onReceive(String msg);
    void onException(Exception e);
}
