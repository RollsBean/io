package bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Blocking IO 客户端
 * @author: 景行
 * @create: 2021/03/08
 **/
public class ClientBIO {

    public static void main(String[] args) throws IOException {
        // 连接服务器端 localhost:8000
        System.out.println("连接服务器端 localhost:8000");
        Socket socket = new Socket("localhost", 8000);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            // 写出数据
            OutputStream os = socket.getOutputStream();
            os.write(scanner.nextLine().getBytes());
            os.flush();
            byte[] response = new byte[1024];
            InputStream is = socket.getInputStream();
            int read = is.read(response);
            if (read != -1) {
                String resStr = new String(response);
                System.out.println(resStr);
                if (resStr.startsWith("over")) {
                    is.close();
                    os.close();
                    socket.close();
                    break;
                }
            }
        }
    }
}
