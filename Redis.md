#### Windows下安装

下载地址：<https://github.com/MSOpenTech/redis/releases>

下载**Redis-x64-xxx.zip**并解压，进入解压后的目录。

运行`redis-service.exe redis.windows.conf`，后面那个如果省略，会启用默认配置。

#### 打开Redis

运行`redis-cli.exe`，出现如下显示表示启动成功。

```
127.0.0.1:6379>
```

#### Redis配置

配置文件：`redis.confi`(Windons名为redis.windows.conf)

1. 获取某个配置项

```
redis 127.0.0.1:6379> CONFIG GET CONFIG_SETTING_NAME
```

> 使用*号获取所有配置项

2. 编辑配置

```
redis 127.0.0.1:6379> CONFIG SET CONFIG_SETTING_NAME NEW_CONFIG_VALUE
```

3. 参数说明
序号|配置项|说明
:-:|:---|:--
1|daemonize no|	Redis 默认不是以守护进程的方式运行，可以通过该配置项修改，使用 yes 启用守护进程（Windows 不支持守护线程的配置为 no ）
2|pidfile /var/run/redis.pid| 当 Redis 以守护进程方式运行时，Redis 默认会把 pid 写入 /var/run/redis.pid 文件，可以通过 pidfile 指定 
3|port 6379| 指定 Redis 监听端口，默认端口为 6379，作者在自己的一篇博文中解释了为什么选用 6379 作为默认端口，因为 6379 在手机按键上 MERZ 对应的号码，而 MERZ 取自意大利歌女 Alessia Merz 的名字 
4|bind 127.0.0.1| 绑定的主机地址 
5|timeout 300| 当客户端闲置多长时间后关闭连接，如果指定为 0，表示关闭该功能 
6|loglevel notice| 指定日志记录级别，Redis 总共支持四个级别：debug、verbose、notice、warning，默认为 notice 
7|logfile stdout| 日志记录方式，默认为标准输出，如果配置 Redis 为守护进程方式运行，而这里又配置为日志记录方式为标准输出，则日志将会发送给 /dev/null 
8|databases 16| 设置数据库的数量，默认数据库为0，可以使用SELECT 命令在连接上指定数据库id 
9|save <seconds> <changes><br/>Redis 默认配置文件中提供了三个条件：<br/>**save 900 1**<br/>**save 300 10**<br/>**save 60 10000**<br/>分别表示 900 秒（15 分钟）内有 1 个更改，300 秒（5 分钟）内有 10 个更改以及 60 秒内有 10000 个更改。| 指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合 
10|rdbcompression yes| 指定存储至本地数据库时是否压缩数据，默认为 yes，Redis 采用 LZF 压缩，如果为了节省 CPU 时间，可以关闭该选项，但会导致数据库文件变的巨大 
11|dbfilename dump.rdb| 指定本地数据库文件名，默认值为 dump.rdb 
12|dir ./| 指定本地数据库存放目录 
13|slaveof <masterip> <masterport>| 设置当本机为 slav 服务时，设置 master 服务的 IP 地址及端口，在 Redis 启动时，它会自动从 master 进行数据同步 
14|masterauth <master-password>| 当 master 服务设置了密码保护时，slav 服务连接 master 的密码 
15|requirepass foobared| 设置 Redis 连接密码，如果配置了连接密码，客户端在连接 Redis 时需要通过 AUTH <password> 命令提供密码，默认关闭 
16|maxclients 128| 设置同一时间最大客户端连接数，默认无限制，Redis 可以同时打开的客户端连接数为 Redis 进程可以打开的最大文件描述符数，如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis 会关闭新的连接并向客户端返回 max number of clients reached 错误信息 
17|maxmemory <bytes>| 指定 Redis 最大内存限制，Redis 在启动时会把数据加载到内存中，达到最大内存后，Redis 会先尝试清除已到期或即将到期的 Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis 新的 vm 机制，会把 Key 存放内存，Value 会存放在 swap 区 
18|appendonly no| 指定是否在每次更新操作后进行日志记录，Redis 在默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。因为 redis 本身同步数据文件是按上面 save 条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为 no 
19|appendfilename appendonly.aof| 指定更新日志文件名，默认为 appendonly.aof 
20|appendfsync everysec| 指定更新日志条件，共有 3 个可选值：<br/> **no**：表示等操作系统进行数据缓存同步到磁盘（快）<br/>**always**：表示每次更新操作后手动调用 fsync() 将数据写到磁盘（慢，安全）<br/>**everysec**：表示每秒同步一次（折中，默认值） 
21|vm-enabled no| 指定是否启用虚拟内存机制，默认值为 no，简单的介绍一下，VM 机制将数据分页存放，由 Redis 将访问量较少的页即冷数据 swap 到磁盘上，访问多的页面由磁盘自动换出到内存中（在后面的文章我会仔细分析 Redis 的 VM 机制） 
22|vm-swap-file /tmp/redis.swap| 虚拟内存文件路径，默认值为 /tmp/redis.swap，不可多个 Redis 实例共享 
23|vm-max-memory 0|  
24||  
25||  
26||  
27||  
28||  
29||  
30||  