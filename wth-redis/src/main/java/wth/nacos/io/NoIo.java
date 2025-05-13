package wth.nacos.io;

/**
 * receivefrom:只能等待一个fd 数据就绪了就读取。
 *
 * 非阻塞IO:进程反复调用receivefrom并返回是否success或者fail
 * 非阻塞IO指的是在读写数据时，如果缓冲区中没有数据或数据没有写完，不会阻塞当前线程等待。
 * 而是直接返回，后续可以通过轮询的方式来检查数据是否已经准备好。
 *
 * (再等待设备数据到内核时时非阻塞等待)
 *
 * 非阻塞IO要结合IO多路复用技术来实现，例如select、poll和epoll。
 *
 * IO多路复用可以监听多个fd。监听fd的数据（设备到内核）
 *
 * 比如我使用select或者poll可以加载fd数据，并查找设备就绪状态。就绪了就使用receivefrom读取数据。
 * ...
 *
 *
 * */
public class NoIo {
}
