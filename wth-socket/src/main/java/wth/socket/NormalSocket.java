package wth.socket;
import java.io.*;
import java.net.*;
public class NormalSocket {


    public class MultiThreadedSocketServer {
        private ServerSocket serverSocket;
        private int port;

        public MultiThreadedSocketServer(int port) throws IOException {
            this.port = port;
            this.serverSocket = new ServerSocket(port); // 创建服务器套接字并绑定端口
            System.out.println("Server started on port " + port);
        }

        public void start() throws IOException {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // 阻塞等待客户端连接
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // 为每个客户端创建一个新线程来处理连接
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }

        private  class ClientHandler implements Runnable {
            private Socket clientSocket;

            public ClientHandler(Socket clientSocket) {
                this.clientSocket = clientSocket;
            }

            @Override
            public void run() {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String message;
                    while ((message = reader.readLine()) != null) { // 阻塞等待读取数据
                        System.out.println("Received from " + clientSocket.getInetAddress() + ": " + message);

                        // 回复客户端
                        writer.println("Message received: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close(); // 关闭客户端连接
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }
    public void main1(String[] args) throws IOException {
        MultiThreadedSocketServer server = new MultiThreadedSocketServer(8082);
        server.start();
    }


}
