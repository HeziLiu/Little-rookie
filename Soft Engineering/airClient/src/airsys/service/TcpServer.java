package airsys.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TcpServer implements Runnable {

    private ServerListener listener;
    private Socket serverSocket;
    private BufferedReader br;

    public TcpServer(Socket serverSocket,ServerListener listener) throws IOException {
        this.listener = listener;
        this.serverSocket=serverSocket;
    }

    @Override
    public void run() {

        try {
            br = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            try {
                while (true) {
                    String msg = "";
                    int is;
                    while ((is = br.read()) != -1) {
                        msg += (char) is;
                        if ((char) is == '}')
                            break;
                    }
                    System.out.println("received from server: "+msg);
                    listener.onReceive(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
