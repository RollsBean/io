package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Non-blocking IO 服务器端
 * @author 景行
 * @date 2021/03/08
 * @see ServerSocketChannel
 * @see ByteBuffer
 **/
public class ServerNIO {

    public static void main(String[] args) throws IOException, InterruptedException {


        // 建立socket并且绑定8000端口
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(8000));
        // 设置为非阻塞
        socketChannel.configureBlocking(false);
        System.out.println("server starting...");

        List<SocketChannel> clientList =  new LinkedList<>();

        Long delay = 2000L;

        while (true) {
            SocketChannel client = socketChannel.accept();
            if (client != null) {
                // 将client设置成非阻塞，接下来的代码继续执行
                client.configureBlocking(false);

                clientList.add(client);
            } else {
                // 返回为null 内核返回-1
                System.out.println("sleep "+delay+"ms...");
                Thread.sleep(delay);
            }

            ByteBuffer buffer = ByteBuffer.allocate(4096);

            for (SocketChannel channel : clientList) {
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    String s = new String(buffer.array());
                    System.out.println(s);
                }
            }
        }

    }
}
