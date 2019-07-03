#### 1.JVM内存模型

![](images\jvm内存.png)

#### 2.垃圾回收算法

![](images\垃圾回收算法.png)

#### 3. JVM类加载机制

1. 加载(Loading):

在方法区中生成class对象，

类的来源可以是class文件，ZIP包（jar 或者 war），运行时生成的动态代理，或者其他文件生成（jsp转class）。

2. 验证(Verification):

确保 Class 文件的字节流中包含的信息是否符合当前虚拟机的要求。

3. 准备(Preparation):为静态变量分配内存，并初始化为默认值。

正式为类变量分配内存并设置类变量的初始值阶段，即在方法区中分配这些变量所使
用的内存空间。

```java
//注意这里所说的初始值概念，比如一个类变量定义为：
public static int v = 8080;
//实际上变量 v 在准备阶段过后的初始值为 0 而不是 8080，将 v 赋值为 8080 的 put static 指令是
//程序被编译后，存放于类构造器方法之中。
//但是注意如果声明为：
public static final int v = 8080;
//在编译阶段会为 v 生成 ConstantValue 属性，在准备阶段虚拟机会根据 ConstantValue 属性将 v
//赋值为 8080。
```

4. 解析(Resolution):

虚拟机将常量池中的符号引用替换为直接引用的过程。

5. 初始化(Initialization)：执行构造函数

执行类构造器<client>方法的过程。

<client>方法是由编译器自动收集类中的类变量的赋值操作和静态语句块中的语句合并而成的，

虚拟机会保证子<client>方法执行之前，父类的<client>方法已经执行完毕，

如果一个类中没有对静态变量赋值也没有静态语句块，那么编译器可以不为这个类生成<client>()方法。

#### 4.类加载器

![](images\类加载器.png)

1. 启动类加载器(Bootstrap ClassLoader)

负责加载 JAVA_HOME\lib 目录中的，或通过-Xbootclasspath 参数指定路径中的，且被
虚拟机认可（按文件名识别，如 rt.jar）的类。

2. 扩展类加载器(Extension ClassLoader)

负责加载 JAVA_HOME\lib\ext 目录中的，或通过 java.ext.dirs 系统变量指定路径中的类
库。

3. 应用程序类加载器(Application ClassLoader)

负责加载用户路径（classpath）上的类库。
JVM 通过双亲委派模型进行类的加载，当然我们也可以通过继承 java.lang.ClassLoader
实现自定义的类加载器。

#### 5. 双亲委派

![](images\双亲委派.png)

