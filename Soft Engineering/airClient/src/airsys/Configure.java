package airsys;

public interface Configure {
    int DEFAULT_PORT=9996;
    int REMOTE_PORT=9996;

    String ROOM_ID="4";

    String REMOTE_IP="localhost";

    int DEFAULT_TICK=1000;//1 sec

    float CURRENT_TEMP=26.0f;//初始温度默认26°
    float DEFAULT_TARGET_TEMP=20.0f;
}
