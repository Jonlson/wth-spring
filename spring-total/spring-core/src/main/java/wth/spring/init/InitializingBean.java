package wth.spring.init;

/**
 * 提供给自己创建的类进行定义自己bean的初始化方法
 * */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
