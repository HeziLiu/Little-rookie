package client;

public interface Configure {
    public static final int DEFAULT_PORT=6667;
    public static final int DEFAULT_RECV_PORT=12345;
    public static final int REMOTE_PORT=6666;

    public static String ROOM_ID="4";

    public static final String REMOTE_IP="localhost";

    public static final int DEFAULT_TICK=1000;//1 sec

    public static float CURRENT_TEMP=16.0f;
    public static float DEFAULT_TARGET_TEMP=27.0f;
}
