package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Blocking IO 服务器端
 * @author: 景行
 * @create: 2021/03/08
 **/
public class ServerBIO {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            // 阻塞中...
            System.out.println("serverSocket starting...");
            System.out.println("阻塞中...");
            Socket socket = serverSocket.accept();
            System.out.println("接收到数据");
            InputStream inputStream = socket.getInputStream();
            byte[] data = new byte[1024];
            while (inputStream.read(data) != -1) {
                System.out.println(new String(data));
            }
        }

    }
}
