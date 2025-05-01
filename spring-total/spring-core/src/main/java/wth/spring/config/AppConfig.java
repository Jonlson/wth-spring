package wth.spring.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ComponentScan
public class AppConfig {
    // 获取ComponentScan的扫描路径
    private String scanPath;

}
