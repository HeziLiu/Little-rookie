package client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpServer implements Runnable {
    private int port;
    private ServerListener listener;
    private Socket serverSocket;
    private BufferedReader br;

    public TcpServer(int port, ServerListener listener) {
        this.port = port;
        this.listener = listener;
    }

    public TcpServer(ServerListener listener) {
        this(Configure.DEFAULT_RECV_PORT,listener);
    }

    @Override
    public void run() {
        try {
            br= new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            String recv=br.readLine();
            JSONObject jsonObject=new JSONObject(recv);
            listener.onReceive(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
