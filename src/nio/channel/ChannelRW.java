package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 景行
 * @date 2021/04/15
 **/
public class ChannelRW {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("file/read.txt");
        FileChannel inChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        FileOutputStream fileOutputStream = new FileOutputStream("file/write.txt");
        FileChannel outChannel = fileOutputStream.getChannel();
        for (;;) {
            // read: 向byteBuffer中追加的数据大小
            int read = inChannel.read(byteBuffer);
            if (read != -1) {
                // 状态重置，limit设置为position，position 设置为0
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                // 重置为初试状态，position=0， limit=capacity
                // 必须clear，不然读到最后，limit=position， read时会一直返回0
                byteBuffer.clear();
            } else {
                break;
            }
        }
    }
}
