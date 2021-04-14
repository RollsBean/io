package nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 景行
 * @date  2021/04/14
 * @see FileChannel
 * @see ByteBuffer
 **/
public class ChannelReader {

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/kevin/Desktop/ChannelWriter.txt";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
