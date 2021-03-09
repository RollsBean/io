package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Non-blocking IO 客户端
 * @author 景行
 * @date 2021/03/08
 * @see SocketChannel
 **/
public class ClientNIO {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8000));

        socketChannel.configureBlocking(false);
        System.out.println("client starting...");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            byte[] bytes = scanner.nextLine().getBytes();
            socketChannel.write(ByteBuffer.wrap(bytes));
        }
    }
}
