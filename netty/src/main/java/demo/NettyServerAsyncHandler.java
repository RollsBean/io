package demo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 异步消息处理器
 * @author 景行
 * @date 2021/05/16
 **/
public class NettyServerAsyncHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().execute(() -> {
            try {
                System.out.println("time sleep begin:" + System.currentTimeMillis());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("time sleep end:" + System.currentTimeMillis());
            ctx.writeAndFlush(Unpooled.copiedBuffer("来自客户端的消息", CharsetUtil.UTF_8));
        });
        super.channelRead(ctx, msg);
    }
}
