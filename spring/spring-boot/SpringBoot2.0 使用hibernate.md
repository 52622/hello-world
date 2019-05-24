SpringBoot的原则是尽可能简化配置，因次想要在spring-boot中使用hibernate，只需要添加spring-boot-starter-data-jpa依赖包和根据需要设置合适的hibernate属性。

1. 添加依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   ```



2. 配置属性

   ```properties
   #spring.jpa.properties.hibernate.hbm2ddl.auto=update
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
   spring.jpa.show-sql=true
   ```



3. 编写dao层接口，并且继承接口CrudRepository<T, ID>(或者JpaRepository)，CurdRepository内置了很多常用的方法。

   > waring: 自己的repository接口可以没有具体的实现类，因为CurdRepository声明了@NoRepositoryBean注解，因此spring启动时会检测所有继承了CurdRepository接口的接口，然后注册到容器中去。

​       要实现自定义的函数，在函数上使用**@Query**注解，然后*value*属性的值设为要查询的sql语句，可以使用变量。变量为函数的形式参数。

```java
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    List<Customer> findByLastName(String lastName);

    @Query(value = "select c from Customer c where c.lastName = :name")
    List<Customer> findByFirstName(@Param("name") String lastName);
}
```

