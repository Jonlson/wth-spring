package wth.socket;

public class NettyEventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 创建 EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建 Bootstrap
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 添加解码器和编码器
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());

                            // 添加自定义处理器
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                    System.out.println("Server response: " + msg);
                                }
                            });
                        }
                    });

            // 连接到服务器
            ChannelFuture f = b.connect("localhost", 8080).sync();
            System.out.println("Connected to server on port 8080");

            // 发送消息到服务器
            Channel channel = f.channel();
            channel.writeAndFlush("Hello, Netty Server!").sync();

            // 等待服务器关闭连接
            f.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭 EventLoopGroup
            group.shutdownGracefully();
        }
    }
}
