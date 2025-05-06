package wth.spring.context;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.ComponentScan;

import wth.spring.annotation.*;
import wth.spring.bean.BeanDefinition;
import wth.spring.bean.BeanName;
import wth.spring.bean.BeanPostProcessor;
import wth.spring.init.InitializingBean;
import wth.spring.init.SpringApplication;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.cglib.proxy.Mixin.createBean;

/**
 * @author 86156
 *
 * 问题1：为什么使用同一个context的类加载器？
 * 因为cntext为类的上下文对象，需要维护对应加载器的线程上下文
 *
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpringApplicationContext {


    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionConcurrentHashMap = new ConcurrentHashMap<>();

    // TODO 要知道什么时候进行map的put(就是完成createSingleBean方法之后)
    private ConcurrentHashMap<String, Object> singleBeanMap = new ConcurrentHashMap<>();
    public SpringApplicationContext(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 获取ComponentScan注解
        if (clazz.isAnnotationPresent(ComponentScan.class)){
            ComponentScan annotation = clazz.getAnnotation(ComponentScan.class);
            String[] value = annotation.value();
            // HashMap去重
            // TODO 最好先全部获取到对应的bean类先。可以使用多线程加快
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                System.out.println("开始扫描组件");
                return "success";
            });

            for (String s : value) {
                String path = s.replace(".", "/");

                System.out.println(path);
                // 进行扫描对应文件
                //先获取类加载器：
                ClassLoader classLoader = SpringApplicationContext.class.getClassLoader();
                URL resource = classLoader.getResource(path);
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        if (f.getName().endsWith(".class")){
                            System.out.println(f.getPath());
                            // 判断是否有对应的Component、Configuration注解
                            //先获取类名：

                            String className = f.getName().substring(f.getName().indexOf(path) + path.length(), f.getName().lastIndexOf(".class"));
                            Class<?> aClass = null;
                            try {
                                aClass = classLoader.loadClass(s);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            // 还有@Service、@Repository、@Controller 、@Configuration等注解
                            if (clazz.isAnnotationPresent(Component.class) ) {
                                // 考虑bean的类型
                                Component component = clazz.getAnnotation(Component.class);
                                if (!StringUtils.isEmpty(component.value())) {
                                    className = component.value();
                                }
                                else {
//                                    className 的首字母要小写：
                                }
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(aClass);
                                beanDefinition.setScope("singleton");
                                if (aClass.isAnnotationPresent(Scope.class)) {
                                    beanDefinition.setScope(clazz.getAnnotation(Scope.class).value());
                                }
                                // BeanPostProcessor 后置初始化方法：AOP等
                                if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                                    BeanPostProcessor beanPostProcessor = (BeanPostProcessor) aClass.getConstructor().newInstance();
                                    //
                                }
                                beanDefinitionConcurrentHashMap.put(className, beanDefinition);

                                // 考虑多示例的bean和循环依赖bean

                                if (beanDefinition.getScope().equals("singleton")) {
                                    createSingleBean(className,beanDefinition);
                                } else if (beanDefinition.getScope().equals("prototype")) {
                                    // TODO: 创建多示例bean
                                }
                            }


                        }
                    }
                }

            }

        }

        // 从SpringFactories中获取对应的Bean(先在配置类中获取)
        // 初始化Bean
        // 注册Bean
    }

    private Object createSingleBean(String className, BeanDefinition beanDefinition)  {
        Object instance;
        // TODO: 考虑三级缓存 :主要是考虑到AOP的情况：前后的对象要一样（
        //  1. 循环依赖且有AOP情况：提前AOP 但是怎么判断循环依赖？
        //  2. 要考虑到多个线程依赖同一个bean的情况，这时候需要使用map来判重复：不要过多创建bean
        //  ）
        // 1. 先找一级缓存：真正初始化的bean
        // 2. 再找二级缓存：主要是解决AOP的情况：
        // 3. 再找三级缓存：主要是解决循环依赖的情况




        try {
            Class aClass = beanDefinition.getType();
            instance = aClass.getConstructor().newInstance();
            // TODO 当一个 Bean 被实例化后，但尚未完成依赖注入和初始化时，Spring 会将它的早期引用放入二级缓存。

            // TODO:进行内部字段依赖注入： 要考虑循环依赖问题. AutoWired和Resource注解
            for (Field field : aClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoWired.class)) {
                    field.setAccessible(true);
                    try {
                        // 属性赋值
                        field.set(instance, getBean(className));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            // 初始化前bean->postConstuct思路：
            //TODO :要执行构造防范：
            /**
             * 1.先找有没有一个无参的构造方法
             * 2.再找只有一个的构造方法
             * 3.再找多个构造方法：有没有@AutoWired注解的构造方法
             * */

            // 而且初始化构造方法时有其他bean为参数时会先byType，再byName找对应的type

            for (Method method : aClass.getMethods()) {


                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.invoke(instance);

                }
                //  //TODO 构造方法方法上有AutoWired注解：

            }
            // 前置初始化bean思路：
            if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) aClass.getConstructor().newInstance();
                beanPostProcessor.postProcessBeforeInitialization(beanDefinition,className);
                //
            }

            // 设置bean的Name: 回调思路：
            if (instance instanceof BeanName) {
                ((BeanName) instance).setBeanName(className);
            }
            // PostConstructor:后置初始化bean思路：


            // 初始化bean思路：
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();

            }





            // BeanPostProcessor 后置初始化方法：AOP等 但是会有JDK和cglib的代理对象（）和bean的类型不同:TODO 解决：把代理对象放入对应bean工厂中
            if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) aClass.getConstructor().newInstance();
                //
                beanPostProcessor.postProcessAfterInitialization(beanDefinition, className );

            }

            // AOP的使用：
            /**
             * 1. 获取到@Aspect注解的类
             *
             * 2. 解析@Aspect注解的类中的方法
             *
             * 3. 创建代理对象：根据所在类的类型。JDK动态代理和Cglib动态代理
             *
             * 4. 为代理对象创建对应的target对象(隐私父类)
             *
             * 5，放入到bean工厂中：
             *
             * */



            // TODO 放入一级缓存： 存储完全初始化完成的 Bean。

            //




        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        singleBeanMap.put(className, instance);

        return instance;
    }

    public Object  getBean(String beanName) {
        // 从SpringFactories中获取对应的Bean
        BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
        if (beanDefinition == null) {
            throw new RuntimeException("beanName is not found");
        } else if (beanDefinition.getScope().equals("singleton")){
            if (singleBeanMap.get(beanName) == null) {
                // TODO 循环依赖问题：
                createSingleBean(beanName, beanDefinition);
            }
            return singleBeanMap.get(beanName);
        } else if (beanDefinition.getScope().equals("prototype")){
            if (singleBeanMap.get(beanName) == null) {
                // TODO 循环依赖问题：



            }
            return beanDefinition.getType();
        }
        return null;
    }

    public Object  getBean(Class<?> tclass) {
        // 从SpringFactories中获取对应的Bean

        return null;
    }

}
