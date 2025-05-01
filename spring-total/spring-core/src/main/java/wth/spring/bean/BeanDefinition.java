package wth.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bean定义类：这个类的类型是什么？
 * 区分是多例bean还是单例bean
 * @author wth
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeanDefinition {
    private Class type;//bean类的类型
    private String scope;//bean的作用域



}
