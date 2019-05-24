我只是一个搬运工，原文链接：https://www.cnblogs.com/gxyandwmm/p/9713672.html

mybatis-spring-boot-starter就是springboot+mybatis可以完全注解不用配置文件，也可以简单配置轻松上手。

## mybatis-spring-boot-starter

任何模式都需要首先引入mybatis-spring-boot-starter的pom文件。

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```

## 一、极简xml版本 【个人推荐使用这种方式，松耦合性】

极简xml版本保持映射文件的老传统，优化主要体现在不需要实现dao的实现层【只需要定义接口类和方法】，系统会自动根据方法名在映射文件中找对应的sql【由namespace和名称坐标确定】。

### 1、配置

pom.xml文件【部分】：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.1.1</version>
    </dependency>
     <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

`application.properties`新增以下配置

```properties
mybatis.config-location=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
```

指定了mybatis基础配置文件和实体类映射文件的地址

mybatis-config.xml 配置

```xml
<configuration>
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```

这里也可以添加一些mybatis基础的配置

### 2、添加User的映射文件

```xml
<mapper namespace="com.neo.mapper.UserMapper" >
    <resultMap id="BaseResultMap" type="com.neo.entity.UserEntity" >
        <id column="id" property="id" jdbcType="BIGINT" />
        <result column="userName" property="userName" jdbcType="VARCHAR" />
        <result column="passWord" property="passWord" jdbcType="VARCHAR" />
        <result column="user_sex" property="userSex" javaType="com.neo.enums.UserSexEnum"/>
        <result column="nick_name" property="nickName" jdbcType="VARCHAR" />
    </resultMap>
    
    <sql id="Base_Column_List" >
        id, userName, passWord, user_sex, nick_name
    </sql>

    <select id="getAll" resultMap="BaseResultMap"  >
       SELECT 
       <include refid="Base_Column_List" />
       FROM users
    </select>

    <select id="getOne" parameterType="java.lang.Long" resultMap="BaseResultMap" >
        SELECT 
       <include refid="Base_Column_List" />
       FROM users
       WHERE id = #{id}
    </select>

    <insert id="insert" parameterType="com.neo.entity.UserEntity" >
       INSERT INTO 
               users
               (userName,passWord,user_sex) 
           VALUES
               (#{userName}, #{passWord}, #{userSex})
    </insert>
    
    <update id="update" parameterType="com.neo.entity.UserEntity" >
       UPDATE 
               users 
       SET 
           <if test="userName != null">userName = #{userName},</if>
           <if test="passWord != null">passWord = #{passWord},</if>
           nick_name = #{nickName}
       WHERE 
               id = #{id}
    </update>
    
    <delete id="delete" parameterType="java.lang.Long" >
       DELETE FROM
                users 
       WHERE 
                id =#{id}
    </delete>
</mapper>
```

其实就是把上个版本中mapper的sql搬到了这里的xml中了

### 3、编写Dao层的代码【定义接口】

```java
public interface UserMapper {
    List<UserEntity> getAll();
    UserEntity getOne(Long id);
    void insert(UserEntity user);
    void update(UserEntity user);
    void delete(Long id);
}
```

对比上一步这里全部只剩了接口方法

## 二、无配置文件注解版【耦合性高】

就是一切使用注解搞定。

### 1 添加相关maven文件【同上】

### 2、`application.properties` 添加相关配置

```properties
mybatis.type-aliases-package=com.neo.entity
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = root
```

springboot会自动加载spring.datasource.*相关配置，数据源就会自动注入到sqlSessionFactory中，sqlSessionFactory会自动注入到Mapper中，对了你一切都不用管了，直接拿起来使用就行了。

在启动类中添加对mapper包扫描`@MapperScan`

```java
@SpringBootApplication
@MapperScan("com.neo.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

或者直接在Mapper类上面添加注解`@Mapper`,建议使用上面那种，不然每个mapper加个注解也挺麻烦的

### 3、开发Mapper

第三步是最关键的一块，sql生产都在这里

```java
public interface UserMapper {
    
    @Select("SELECT * FROM users")
    @Results({
        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
        @Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
        @Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);
}
```

为了更接近生产我特地将user_sex、nick_name两个属性在数据库加了下划线和实体类属性名不一致，另外user_sex使用了枚举

### 4、使用

上面三步就基本完成了相关dao层开发，使用的时候当作普通的类注入进入就可以了

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper UserMapper;

    @Test
    public void testInsert() throws Exception {
        UserMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
        UserMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
        UserMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

        Assert.assertEquals(3, UserMapper.getAll().size());
    }
}
```

