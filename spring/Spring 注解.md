参考链接：https://www.cnblogs.com/xiangjune/p/6605215.html

##### Spring 注解

@Autowired	自动注入 （存在多个可注入Bean时，通过 @Qualifier 指定）
@Resource	与@Autowired作用相同
@Repository 只能标注在 DAO 类上。该注解的作用不只是将类识别为 Bean，同时它还能将所标注的类中抛出的数据访问异常封装为 Spring 的数据访问异常类型
@Component 是一个泛化的概念，仅仅表示一个组件 (Bean) ，可以作用在任何层次。
@Service 通常作用在业务层，但是目前该功能与 @Component 相同。
@Constroller 通常作用在控制层，但是目前该功能与 @Component 相同。
@Scope 指定 Bean 的作用范围

@SpringBootApplication:
包含@Configuration、@EnableAutoConfiguration、@ComponentScan
通常用在主类上。

@RestController:
用于标注控制层组件(如struts中的action)，包含@Controller和@ResponseBody。

@ResponseBody：
表示该方法的返回结果直接写入HTTP response body中
一般在异步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径，加上

@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。比如
异步获取json数据，加上@responsebody后，会直接返回json数据。

@ComponentScan：
组件扫描。个人理解相当于<context:component-scan>，如果扫描到有@Component 
@Controller@Service等这些注解的类，则把这些类注册为bean。


@Configuration：
指出该类是 Bean 配置的信息源，相当于XML中的<beans></beans>，一般加在主类上。


@Bean:
相当于XML中的<bean></bean>,放在方法的上面，而不是类，意思是产生一个bean,并交给spring
管理。


@EnableAutoConfiguration：
让 Spring Boot 根据应用所声明的依赖来对 Spring 框架进行自动配置，一般加在主类上。

2.Jpa


@Entity：
@Table(name="")：
表明这是一个实体类。一般用于jpa
这两个注解一般一块使用，但是如果表名和实体类名相同的话，@Table可以省略

@MappedSuperClass:

用在确定是父类的entity上。父类的属性子类可以继承。


@NoRepositoryBean:
一般用作父类的repository，有这个注解，spring不会去实例化该repository。
```java
package org.springframework.data.repository;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to exclude repository interfaces from being picked up and thus in consequence getting an instance being
 * created.
 * <p/>
 * This will typically be used when providing an extended base interface for all repositories in combination with a
 * custom repository base class to implement methods declared in that intermediate interface. In this case you typically
 * derive your concrete repository interfaces from the intermediate one but don't want to create a Spring bean for the
 * intermediate interface.
 *
 * @author Oliver Gierke
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface NoRepositoryBean {

}
```

@Column：
如果字段名与列名相同，则可以省略。


@Id：
表示该属性为主键。


@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "repair_seq")：
表示主键生成策略是sequence（可以为Auto、IDENTITY、native等，Auto表示可在多个数据库间
切换），指定sequence的名字是repair_seq。


@SequenceGeneretor(name = "repair_seq", sequenceName = "seq_repair", allocationSize = 1)
name为sequence的名称，以便使用，sequenceName为数据库的sequence名称，两个名称可以一致。


@Transient：
表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性.
如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient,否则,ORM框架默认其注解为@Basic

@Basic(fetch=FetchType.LAZY)：
标记可以指定实体属性的加载方式


@JsonIgnore：
作用是json序列化时将java bean中的一些属性忽略掉,序列化和反序列化都受影响。


@JoinColumn（name="loginId"）:
一对一：本表中指向另一个表的外键。
一对多：另一个表指向本表的外键。


@OneToOne
@OneToMany
@ManyToOne
对应Hibernate配置文件中的一对一，一对多，多对一。

\---------------------------------------------------------------------
SpringMVC
@RequestMapping：
RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
该注解有六个属性：
params:指定request中必须包含某些参数值是，才让该方法处理。
headers:指定request中必须包含某些指定的header值，才能让该方法处理请求。
value:指定请求的实际地址，指定的地址可以是URI Template 模式
method:指定请求的method类型， GET、POST、PUT、DELETE等
consumes:指定处理请求的提交内容类型（Content-Type），如application/json,text/html;
produces:指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回

@RequestParam：
用在方法的参数前面。
@RequestParam String a =request.getParameter("a")。

@PathVariable:
路径变量。
如 RequestMapping("user/get/mac/{macAddress}")
public String getByMacAddress(@PathVariable String macAddress){
//do something;
}
参数与大括号里的名字一样要相同

@ControllerAdvice：
包含@Component。可以被扫描到。统一处理异常。
@ExceptionHandler（Exception.class）：
用在方法上面表示遇到这个异常就执行以下方法。