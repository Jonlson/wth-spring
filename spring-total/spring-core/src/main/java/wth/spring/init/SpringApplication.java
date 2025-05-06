package wth.spring.init;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import wth.spring.mybatis.MapperClassBean;

@ComponentScan(basePackages = "wth.spring")
@Import(MapperClassBean.class)
public class SpringApplication {
}
