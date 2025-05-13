package wth.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 并要配置启动命令:-Dnacos standalone = true
 * */

/**
 * 本质理解nacos就是一个用springboot开发的服务器提供对应的接口，用来注册服务，配置中心等。
 * 有集群就进行数据的同步
 * 其他应用一启动时就尝试发送注册请求。拉取对应的配置信息
 * 然后这个nacos服务器就进行心跳检测，
 * 进行服务拉取：
 * */

/**
 * nacos的启动流程：
 *
 * SpringApplication.run(...)
 *  ├── 创建 Environment（此时触发 Bootstrap 加载）
 *  │   └── 执行 BootstrapApplicationListener
 *  │       └── 加载 spring.factories 中的 BootstrapConfiguration
 *  │           └── 创建 NacosConfigBootstrapConfiguration
 *  │               └── 创建 NacosPropertySourceLocator - >拉取配置信息
 *  └── 创建 ApplicationContext（主容器）
 *
 *      └── 加载普通 Bean
 *
 *
 * SpringApplication.run()
 *  └── ApplicationContext.refresh() - 》 SmartLifecycle 的 Bean 的 start() 方法。（）
 *      └── LifecycleProcessor.onRefresh()
 *        |_ onApplicationEvent(ContextRefreshedEvent)的bind方法
 *          └── NacosAutoServiceRegistration.start()
 *              └── NacosServiceRegistry.register()
 *                  └── NamingService.registerInstance() ← 发送 HTTP/gRPC 注册到 Nacos Server
 *
 *
 *
 * 心跳：NacosClient调用的：每五秒： Service端要记录心跳时间：
 *
 *
 * 健康检查:NacosService调用的： 每15秒 -》 healthy = false 每30秒把healthy 为 false删除的
 *
 *
 * 服务拉取：{拉取 + 缓存 + 定时拉取}
 *
 * */


/**
 * 关键在于nacos实现SpringListerner的onApplicationEvent方法，
 *
 *
 * */
@SpringBootApplication(scanBasePackages = "wth.nacos")
@EnableScheduling
@ServletComponentScan
public class Nacos {
    public static void main(String[] args) {
        SpringApplication.run(Nacos.class, args);
    }
}
