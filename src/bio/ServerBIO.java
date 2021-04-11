package bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
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
        // 阻塞中...
        System.out.println("serverSocket starting...");
        System.out.println("阻塞中...");
        Socket socket = serverSocket.accept();
        while (true) {
            InputStream is = socket.getInputStream();
            // 需要清除数组缓存，不然数据会重复，比如第一次读取 'abc' 第二次读取 '1'，数组的数据是 '1bc'
            byte[] data = new byte[1024];
            int read = is.read(data);
            if (read != -1) {
                System.out.println("接收到数据");
                String reqStr = new String(data, 0, data.length);
                System.out.println(reqStr);
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("接收到客户端" + inetAddress.getHostAddress() + "的消息");
                String response = "你是：" + inetAddress.getHostAddress();
                OutputStream os = socket.getOutputStream();
                if (reqStr.startsWith("over")) {
                    os.write("over".getBytes());
                    os.flush();
                    is.close();
                    os.close();
                    socket.close();
                    break;
                } else {
                    os.write(response.getBytes());
                    // 立即写出数据
                    os.flush();
                }
            }
        }

    }
}
