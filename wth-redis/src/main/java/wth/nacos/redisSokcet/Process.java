package wth.nacos.redisSokcet;


public class Process {

    public static void main(String[] args) {
     while (true) {
         // 如果时触发了fd就绪

         // 执行beforeSleep

         //进行epoll_wait等待

         //遍历处理就绪的FD，调用对应的处理器


     }


    }
    /**
     *
     * 处理服务端的读事件
     *
     * */
    public void acceptHandler() {
        // 接收socket连接， 获取fd

        // 创建对应的连接

        // 内部addEvent

        // 监听socket的fd的读事件，绑定读处理器：readQueryFromClient
    }

    /**
     * 处理客户端的读事件
     * */
    public void readQueryFromClient() {
        // 读数据，获取fd -> client = connGetPrivateData(conn)
        // 解析数据

        // 根据不同的指令执行对应的业务逻辑


        // 响应客户端结果：
        // 写入数据到c-buf到写缓存区
        // 将c-buf放到server.client_write_buf_list的队列等待被写出


       }
}
