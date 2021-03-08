package bio;

import java.io.IOException;
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
            socket.getOutputStream().write(scanner.nextLine().getBytes());
        }
    }
}
