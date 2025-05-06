package wth.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 需要注意的地方：
 * Async也是要创建bean和生成代理对象的
 * 是再初始化时再生成一个bean的代理对象，所以在循环依赖时会前后的bean的对象不一致就会报错
 *
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Async {
}
