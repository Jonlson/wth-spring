package wth.spring.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import wth.spring.annotation.Component;

/**
 * 这个factoryBean的实现类，用于自定义bean的创建过程.
 * 下面会创建两个bean,一个是MybatisFactoryBean这个类名的bean(name为&mybatisFactoryBean),一个是自定义的bean（getObject这个代理对象的bean:名字为mybatisFactoryBean）.
 *
 * */
@Component
@DependsOn("mapperClassBean")
public class MybatisFactoryBean implements FactoryBean {

    private SqlSession sqlSession;

    private Class mapperClass;

    public MybatisFactoryBean(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    // 创建bean :拿到Mybatis生成的SqlSessionFactory和Mapper代理对象

    @Autowired
    public void setSqlSession(SqlSessionFactory sqlSessionFactory) {

        this.sqlSession = sqlSessionFactory.openSession();
    }

    @Override
    public Object getObject() throws Exception {
        return sqlSession.getMapper(mapperClass);
    }

    // bean的类型
    @Override
    public Class<?> getObjectType() {

        return mapperClass;
    }
}
