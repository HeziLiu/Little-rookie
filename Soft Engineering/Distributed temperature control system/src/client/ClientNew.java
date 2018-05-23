package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import org.json.JSONObject;


public class ClientNew implements Runnable {

    private static final String IP="10.8.196.18";
    private static final int PORT=10000;
    private Selector selector;

    public ClientNew(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            ReceiveMessage receiveMessage=new ReceiveMessage();
            // select()����ֻ��ʹ��һ�Σ�����֮��ͻ��Զ�ɾ��,ÿ�����ӵ���������ѡ�������Ƕ�����
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys()
                        .iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        if(channel.read(buffer)<0)
                        {
                            channel.close();
                            continue;
                        }
                        buffer.flip();
                        String msg=Charset.forName("UTF-8").decode(buffer).toString();
                        System.out.println("received:"+msg);
                        receiveMessage.deal(new JSONObject(msg));
                        //System.out.println("Client received ["+msg+"] from server address:" + channel.getRemoteAddress());
                        Thread.sleep(1000);
                        //���buffer
                        buffer.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        Selector selector = null;
        SocketChannel channel = null;
        Scanner scanner = null;
        SendMessage sendMessage=new SendMessage();
        ClientNew clientNew;
        try {
            // ����Selector
            selector = Selector.open();
            clientNew=new ClientNew(selector);
            // ����������Socket Channel
            channel = SocketChannel.open(new InetSocketAddress(IP,
                    PORT));
            channel.configureBlocking(false);
            // channel.connect();

            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            System.out.print("Connection setting up.");

            //��ʱ����ͨ�汨��
            sendMessage.SendStatus(channel);

            while (!channel.isConnected()) {
                System.out.print(".");
                Thread.sleep(1000);
            }

            //channel.write(ByteBuffer.wrap("Client startup..".getBytes()));

            // ����Selector�ĸ����¼�
            Executors.newFixedThreadPool(1).submit(clientNew);
            sendMessage.SendRequest(channel);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (channel != null) {
                channel.close();
            }
            if (selector != null) {
                selector.close();
            }
        }
    }

}