package wth.nacos.naming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.jvm.hotspot.oops.Instance;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/instance")
public class InstaceController {

    @Autowired
    public ServiceManager serviceManager;

    @PostMapping("/register")
    public String register(HttpServletRequest httpServletRequest) {
        // 解析namespcaeId
        String namespaceId = httpServletRequest.getParameter("namespace");

        // 解析serviceName
        String serviceName = httpServletRequest.getParameter("serviceName");

        // 校验
        if (namespaceId == null || serviceName == null) {
            return "参数不合法";
        }

        // 获取实例信息：ip + port + weight + metadata + cluster + valid +enable + healthy
        final Instance instance = null;

        /**
         * addInstance(获取实例列表->放在DelegateconsistencyService) -> updateInstance -> removeInstance
         *
         * 注册表写到内存中，注重实时性：
         * */
        serviceManager.registerService(namespaceId, serviceName, instance);
        // 优化传输
        return "ok";
    }

    @GetMapping("/list")
    public String list(HttpServletRequest httpServletRequest) {
        // 解析namespcaeId
        String namespaceId = httpServletRequest.getParameter("namespace");

        // 解析serviceName
        String serviceName = httpServletRequest.getParameter("serviceName");

        if (namespaceId == null || serviceName == null) {
            return "参数不合法";
        }

        // 根据负载均衡策略进行服务列表的发现
    }
}
