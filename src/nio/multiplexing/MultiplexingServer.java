package nio.multiplexing;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author 景行
 * @date 2021/03/14
 **/
public class MultiplexingServer {

    private ServerSocketChannel serverChannel;

    private Selector selector;

    final int port = 8000;

    public void init() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        serverChannel.bind(new InetSocketAddress(port));

        // Selector: 选择器，即多路复用器
        /**
         * select、poll: JVM 保存这个数组
         * epoll: epoll_ctl 注册客户端到内核
         */
        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {
        init();

        while (true) {
            if (selector.select(1000) == 0) {
                // no clients
                continue;
            } else {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 不移除那么当前这个key就会一直存在在selector.selectedKeys集合中，
                    // 下次处理时会重复消费
                    iterator.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        // 测试这个key的channel通道是否准备好去接收一个新的socket连接
                        SocketChannel client = channel.accept();
                        client.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(4096);
                        // 遇到新的连接，则将连接注册到selector中
                        // Client Channel只能注册读事件
                        client.register(selector, SelectionKey.OP_READ, buffer);
                    } else if (key.isReadable()) {
                        // 测试这个key的channel通道是否可读
                        SocketChannel clientChannel = (SocketChannel) key.channel();

                        ByteBuffer dataBuffer = (ByteBuffer) key.attachment();
                        int readLine = clientChannel.read(dataBuffer);
                        if (readLine == -1) {
                            continue;
                        }
                        // 读写模式切换
                        dataBuffer.flip();
                        byte[] messageBytes = dataBuffer.array();
                        String messageEncode = new String(messageBytes , "UTF-8");
                        String message = URLDecoder.decode(messageEncode, "UTF-8");
                        System.out.println(message);

                        // TODO

                        if(!message.contains("over")) {
                            //清空已经读取的缓存，并从新切换为写状态(这里要注意clear()和capacity()两个方法的区别)
                            dataBuffer.clear();
                            //回发数据，并关闭channel
                            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果, over", "UTF-8").getBytes());
                            clientChannel.write(sendBuffer);
                            clientChannel.close();
                        } else {
                            //这是，limit和capacity的值一致，position的位置是realLen的位置
                            dataBuffer.position(readLine);
                            dataBuffer.limit(dataBuffer.capacity());
                        }


                    }
                }
            }
        }

    }


    public static void main(String[] args) throws IOException {
        MultiplexingServer multiplexingServer = new MultiplexingServer();
        multiplexingServer.start();
    }



}
