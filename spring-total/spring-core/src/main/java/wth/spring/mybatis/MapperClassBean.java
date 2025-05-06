package wth.spring.mybatis;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import wth.spring.annotation.Configration;
import wth.spring.annotation.MyMapperScan;
import wth.spring.bean.BeanDefinition;

import java.util.Map;

/**
 * 优化：实现ImportBeanDefinitionRegistrar接口的registerBeanDefinitionRegistrar方法
 *
 * */
@Component
public class MapperClassBean implements ImportBeanDefinitionRegistrar {

    @Bean
    public void mapperClass() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MybatisFactoryBean.class);
        // TODO :循环扫描到的mapper接口类
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(MybatisFactoryBean.class);
        // 加入对应扫描到的mapper接口类
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(ExampleMapper.class);
        applicationContext.registerBeanDefinition("",beanDefinition);



        applicationContext.refresh();

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(MyMapperScan.class.getName());
        if (annotationAttributes == null) {
            return;
        }
        String path = (String)annotationAttributes.get("value");

        MyScanner scan = new MyScanner(registry);
        scan.addIncludeFilter((a, b) ->{return true;});
        scan.scan(path);


        // TODO :循环扫描到的mapper接口类
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(MybatisFactoryBean.class);
        // 加入对应扫描到的mapper接口类
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(ExampleMapper.class);
        registry.registerBeanDefinition("",beanDefinition);

    }

}
