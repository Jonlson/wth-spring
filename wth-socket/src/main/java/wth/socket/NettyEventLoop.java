package wth.socket;

/**
 * EventLoop：事件循环机制
 *
 * 1.
 * Netty 使用直接缓冲区（DirectBuffer）来存储数据，避免了数据在用户态和内核态之间的多次拷贝。
 * 直接缓冲区可以直接与底层 I/O 操作交互，减少了上下文切换的开销（这个DirectBuffer可以通过和内核建立内存映射表，并且最终发送到磁盘和网卡使用transeTO直接传输）
 *
 * 2.EventLoop 使用任务队列来管理任务，支持任务的异步提交和执行：
 * EventLoop 线程不断从任务队列中获取任务并执行。
 * 任务的执行是串行的，避免了多线程竞争的开销。
 *
 * 3. 使用引用计数和内存池复用机制，减少了垃圾回收的开销。
 * -
 *   ```
 *   创建缓冲区：
 *   当创建一个新的 ByteBuf 时，其引用计数初始化为 1。
 *   共享缓冲区：
 *   如果一个 ByteBuf 被共享（例如传递给另一个组件），引用计数会增加。
 *   释放缓冲区：
 *   当一个组件不再使用 ByteBuf 时，引用计数会减少。当引用计数为 0 时，缓冲区会被自动释放，避免内存泄漏。
 *
 *   ```
 * -
 * ```
 * 内存块划分：
 * 内存池预先分配了一组固定大小的内存块。这些内存块被划分为不同的区域，用于存储不同大小的数据。
 * 内存分配：
 * 当需要分配内存时，内存池会从预先分配的内存块中分配一个合适的区域，而不是每次都通过 new 或 allocate 创建新的内存。
 * 内存释放：
 * 当一个 ByteBuf 被释放时，其占用的内存区域会被归还到内存池中，供后续的分配请求复用。
 *
 * ```
 *
 *
 * */
public class NettyEventLoop {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 用于处理连接请求
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 用于处理 I/O 事件

        try {
            // 创建服务器引导类，配置服务器的参数
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());

                            // 自定义处理器，处理接收到的消息
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                    System.out.println("Received: " + msg);
                                    // 发送响应消息
                                    ctx.writeAndFlush("Message received: " + msg);
                                }
                            });
                        }
                    });

            ChannelFuture f = b.bind(8080).sync(); // 绑定端口并启动服务器
            System.out.println("Server started on port 8080");
            f.channel().closeFuture().sync(); // 等待服务器关闭
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
