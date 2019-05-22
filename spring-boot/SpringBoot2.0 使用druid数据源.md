### 参考链接

Druid Github:https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter

1. 添加依赖

   ```xml
   <!-- druid数据库连接池依赖包 -->
   <dependency>
   	<groupId>com.alibaba</groupId>
       <artifactId>druid-spring-boot-starter</artifactId>
       <version>1.1.10</version>
   </dependency>
   <!-- 选择合适的mysql连接包 -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>5.1.47</version>
   </dependency>
   ```

   

2. 配置属性

```yml
spring:
  application:
    name: orange
  # 数据库连接池
  datasource:
    druid:
      url: jdbc:mysql://localhost:3306/test?useSSL=false
      username: root
      password: 123456
      driver-class-name: com.mysql.jdbc.Driver
      type: com.alibaba.druid.pool.DruidDataSource
```

3. 注册数据源的javaBean

   ```java
   @Configuration
   public class DataSourceConfigurer {
       @Bean
       public DataSource dataSource(Environment environment) {
           return DruidDataSourceBuilder.create().build(environment,"spring.datasource.druid.");
       }
   }
   ```

4. 在代码中使用一下代码，就可以正常使用druid了

   ```java
   @Resource
   private DataSource dataSource;
   ```

   