package wth.nacos.redisSokcet;

/**
 * 初始化服务端：
 *
 *
 *
 * */
public class InitServer {

    /**
     * 类似epoll_create()创建
     * */
    public void AeCreateEventLoop() {

    }

    /**
     * ServerSocket 监听端口
     *
     */
    public void ListenToPort() {

    }

    /**
     * 注册连接处理器，内部调用aeApiAddEvent(&server.ipfd)
     *
     * */
    public void createSocketAcceptHandler() {

    }

    // 在sleep之前做一些事情，比如执行定时器事件

    public void aeSetBeforeSleep() {
        // 创建迭代器。取出server.clients_pending_list中的客户端，写到对应的客户端中去
    }

}
