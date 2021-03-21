package nio.multiplexing;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author 景行
 * @date 2021/03/14
 **/
public class MultiplexingClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8000));

        socketChannel.configureBlocking(false);
        System.out.println("client starting...");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            byte[] bytes = scanner.nextLine().getBytes();
            socketChannel.write(ByteBuffer.wrap(bytes));
            ByteBuffer inBuffer = ByteBuffer.allocate(4096);

            int read = socketChannel.read(inBuffer);
            if (read != -1) {
                inBuffer.flip();
                byte[] messageBytes = inBuffer.array();
                String messageEncode = new String(messageBytes , "UTF-8");
                String message = URLDecoder.decode(messageEncode, "UTF-8");
                System.out.println(message);
                if (message.contains("over")) {
                    System.exit(0);
                }
            }
        }
    }
}
