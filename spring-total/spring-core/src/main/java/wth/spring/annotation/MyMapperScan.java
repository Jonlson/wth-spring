package wth.spring.annotation;

import org.springframework.context.annotation.Import;
import wth.spring.mybatis.MapperClassBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 86156
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MapperClassBean.class})
public @interface MyMapperScan {
}
