package wth.spring.transaction;

/**
 * @author 86156
 *
 * 0.本质也是一个日志切面
 * 1.用在方法上，表示该方法是一个事务方法
 * 2.搜先是要开启事务，事务管理器新建一个连接conn（为什么不使用jdbc的template-》要确保是同一个连接）(ThreadLocal<>来拿到同一个相同的数据库连接)
 * （Threadlocla<Map<dataSouce,conn>> ）
 * 3.设置conn.autocommit = false
 * 4. 调用对应方法
 * 5. 调用成功，提交事务
 * 6. 调用失败，回滚事务
 * 7. 释放连接
 */


/**
 * 事务传播行为
 *  除了配置上的不同事务方法调用的配置
 *  还有注意同一个类中的事务方法的调用时事务失效情况（@Transaction本质是一个AOP代理对像，如果是再
 *  执行方法内容时target的方法，再去执行同个类的其他事务方法其实就相当于时没有调用@Transationc的bean的普通对象）
 *  解决：隔离类 + 自己注入自己
 *
 *
 * */
public class Transactional {
}
