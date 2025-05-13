package wth.nacos.socket;

/**
 * serverSocket网络传输服务
 *
 * epoll_create -> epoll_ctl*(注册fd) -> 循环：epoll_wait（直到有fd就绪且可读-》accept()接收客户端 -> socket获取fd - >ctr(注册)）
 * 如果是ssfd不可读，就进行读取操作并进行redis的命令执行
 * @author wth
 * */
public class SocketWeb {
}
