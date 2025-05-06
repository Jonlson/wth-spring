package wth.spring.mybatis;

import org.apache.ibatis.annotations.Select;

public interface ExampleMapper {
    @Select("select table_name from information_schema.tables where table_schema = 'wth' limit 1")
    String getTableName();
}
