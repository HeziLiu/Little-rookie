package client;

import org.json.JSONObject;

public interface ServerListener {
    public abstract void onReceive(JSONObject jsonObject);
    public abstract void onException(Exception e);
}
