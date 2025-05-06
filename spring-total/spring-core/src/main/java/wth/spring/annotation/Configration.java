package wth.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 86156
 *
 * 1.使用该注解的类最终产生的是类的代理对象（@Configration 和 AOP和@lazy）
 * 2. 达到的效果是：执行代理逻辑+执行父类(target)的方法逻辑
 * 并且找到对应的bean方法是先去bean工厂获取。（使得创建调用的bean类的一样）
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configration {
}
