package wth.spring.mybatis;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * 继承一个扫描器，自定义扫描规则
 * */
public class MyScanner extends ClassPathBeanDefinitionScanner {
    public MyScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 扫描接口
     *
     * */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }


    /**
     * 所以factoryBean的作用就是声明了创建bean的工厂bean，这里我们自定义了一个MybatisFactoryBean。
     * 在拓展就是实现ImportBeanDefinitionRegistery接口：注册beanDefinition到容器中使得
     *
     *
     * */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);// 过滤操作：



        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            // 自定义逻辑处理
            BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClassName(MybatisFactoryBean.class.getName());

        }

        return beanDefinitionHolders;
    }
}
