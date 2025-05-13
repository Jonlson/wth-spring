package wth.nacos.io;


/**
 * 异步IO:
 * aio_read:通知内核读数据。直到内核完成数据等待和内核拷贝完成之后才通知
 *
 * 缺点：高并发下，内核积累数量多。压力大
 *
 * 要做好限流。
 *
 *
 *
 *
 * 同步还是异步看：内核和用户的拷贝的过程
 * */
public class AsyncIo {
}
