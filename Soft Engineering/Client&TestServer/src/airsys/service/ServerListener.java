package airsys.service;

import airsys.org.json.JSONObject;

public interface ServerListener {
    void onReceive(String msg);
    void onException(Exception e);
}
