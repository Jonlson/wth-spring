package wth.nacos.io;

/**
 * 信号驱动Io:与内核建立SIGIO的信号关联，并设置回调，当内核fd就绪时发出SIGO信号通知，而在此期间用户进程/应用不用阻塞等待
 * （主要使用内核监听和通知）
 *
 * 确定当有大量IO操作。消耗多。队列溢出。和内核拷贝频繁不高效
 *
 *
 * */
public class SinalIo {
}
