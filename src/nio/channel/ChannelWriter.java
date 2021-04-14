package nio.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 景行
 * @date 2021/04/14
 * @see FileChannel
 * @see ByteBuffer
 **/
public class ChannelWriter {

    public static void main(String[] args) throws IOException {
        String msg = "hello NIO";
        String filePath = "/Users/kevin/Desktop/ChannelWriter.txt";
        // OutputStream没有FileChannel，所以不能用多态接收
        FileOutputStream os = new FileOutputStream(filePath);
        FileChannel channel = os.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        // 1. 写数据
        byteBuffer.put(msg.getBytes());
        // 2. 重置位置 将position置为0
        byteBuffer.flip();
        // 3. 再次从0写入数据
        channel.write(byteBuffer);
    }
}
