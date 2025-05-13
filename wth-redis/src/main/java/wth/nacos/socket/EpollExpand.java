package wth.nacos.socket;


/**
 * epoll的事件通知机制：
 * 当使用epoll_wait()等待事件时，会有两次通知，如levelTrrigger(告知多次，直到处理完（数据可能没有一次性完成），默认)和EdgeTrigger（只告知一次 ，-》手动编码优化（epoll_ctl(查询最新数据，再写入就绪的)））
 * LT的效率低 ，惊群问题（多个进程都得到通知）
 * */
public class EpollExpand {
}
