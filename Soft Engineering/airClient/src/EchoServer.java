import airsys.org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class EchoServer {
    private static final int PORT=9996;
    public static void main(String[] args) throws Exception {

        Selector selector = null;
        ServerSocketChannel serverChannel = null;

        try {
            // 创建Selector
            selector = Selector.open();

            // 创建非阻塞Server Socket Channel
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(PORT));

            // 将ServerSocketChannel注册到Selector
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 等待信道超时，超时事件为3秒
                if (selector.select(5000) == 0) {
                    System.out.println("等待中...");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys()
                        .iterator();
                while (iterator.hasNext()) {
                    iterator.remove();
                    SelectionKey key = iterator.next();
                    if (key.isConnectable()) {
                        System.out.println("Connectable.");
                    } else if (key.isAcceptable()) {
                        System.out.println("Acceptedable.");
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        System.out.println("Readable.");
                        handleRead(key);
                    } else if (key.isValid() && key.isWritable()) {
                        // Do nothing
                        System.out.println("Writeable.");
                        handleWrite(key);
                    }

                    System.out.println("this it is removed");
                }
            }
        } catch (Exception e) {
            if (serverChannel != null) {
                serverChannel.close();
            }
            if (selector != null) {
                selector.close();
            }
        }
    }

    private static void handleWrite(SelectionKey key) {
        // TODO Auto-generated method stub

    }

    public static void handleAccept(SelectionKey key) throws IOException {
        // 接受客户端建立连接的请求
        SocketChannel clientChannel = ((ServerSocketChannel) key.channel())
                .accept();
        // 非阻塞式
        clientChannel.configureBlocking(false);
        // 注册到selector
        clientChannel.register(key.selector(), SelectionKey.OP_READ,
                ByteBuffer.allocate(1024));
        // clientChannel.register(key.selector(), SelectionKey.OP_READ
        // | SelectionKey.OP_WRITE, ByteBuffer.allocate(1024));
    }

    public static void handleRead(SelectionKey key) throws IOException, InterruptedException {
        // 获得与客户端通信的信道
        SocketChannel clientChannel = (SocketChannel) key.channel();
        // 得到并清空缓冲区
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        if (clientChannel.read(buffer) != -1) {
            buffer.flip();//make buffer ready for read
            String msg=Charset.forName("UTF-8").decode(buffer).toString();
            System.out.println("Server received:"+msg+"\n"+"from client:"+clientChannel.getRemoteAddress());
            JSONObject jsonObject=new JSONObject(msg);
            int _switch=jsonObject.getInt("switch");
            float temp=jsonObject.getFloat("temperature");
            int wind=jsonObject.getInt("wind");
            float cost= (float) 2.00;
            String remsg="{\"switch\":"+_switch+",\"temperature\":"+temp+",\"wind\":"+wind+",\"cost\":"+cost+"}";
            //处理接收报文
            //发送通告报文
            //格式如下：
            //{"switch":1/0,"temperature":25.80,"wind":1,"cost":2.00}
            clientChannel.write(ByteBuffer.wrap(remsg.getBytes(Charset.forName("UTF-8"))));
        } else {
            // 没有读取到内容的情况
            clientChannel.close();
            System.out.println("channel has been closed");
        }
    }

}