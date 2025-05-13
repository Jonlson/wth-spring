package wth.spring.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import wth.spring.annotation.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class MySqlSessionBean {
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        return sqlSessionFactory;
    }
}
