package wth.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Reactor {
    Selector selector;
    public void start(int port) throws IOException {
        Selector selector = Selector.open();
        // 注入Server端信息
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 非阻塞模式
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select(); // 阻塞等待就绪的通道(轮询就绪fd)

            // 优化：多线程
            List<CompletableFuture> tasks = new ArrayList<>();
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isAcceptable()) {
                    tasks.add(CompletableFuture.runAsync(() -> {
                        ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = null;
                        try {
                            accept = socketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                        } catch (ClosedChannelException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));

                } else if (key.isReadable()) {
                    tasks.add(CompletableFuture.runAsync(() -> {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024); // 创建缓冲区
                        int bytesRead = 0; // 读取数据
                        try {
                            bytesRead = clientChannel.read(buffer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (bytesRead == -1) { // 如果客户端关闭连接
                            try {
                                System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                clientChannel.close(); // 关闭通道
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            key.cancel(); // 取消注册
                            return;
                        }

                        buffer.flip(); // 切换到读模式
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        String message = new String(data);
                        try {
                            System.out.println("Received message from " + clientChannel.getRemoteAddress() + ": " + message);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // 回复客户端
                        ByteBuffer responseBuffer = ByteBuffer.wrap("Message received".getBytes());
                        try {
                            clientChannel.write(responseBuffer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));



                }
            }

            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
            selector.selectedKeys().clear();
        }
    }
    public static void main(String[] args) throws IOException {
        Reactor server = new Reactor();
        server.start(8081);
    }

}
