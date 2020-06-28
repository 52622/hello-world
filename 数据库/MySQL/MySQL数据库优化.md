<center><strong>MySQL数据库优化</strong></center>

1. 开启慢查询日志

   ```sql
   show variables like 'slow_query_log';
   show variables like 'slow_query_log_file';
   set gloable log_queries_not_using_indexes=on;
   set gloable long_query_time=1;
   ```

2. 慢查询日志分析工具

   ```
   ## mysql自带的
   mysqldumpslow --help
   
   -s, 是表示按照何种方式排序，
   c: 访问计数
   l: 锁定时间
   r: 返回记录
   t: 查询时间
   al:平均锁定时间
   ar:平均返回记录数
   at:平均查询时间
   -t, 是top n的意思，即为返回前面多少条的数据；
   -g, 后边可以写一个正则匹配模式，大小写不敏感的；
   
   比如:
   得到返回记录集最多的10个SQL。
   mysqldumpslow -s r -t 10 /database/mysql/mysql06_slow.log
   
   得到访问次数最多的10个SQL
   mysqldumpslow -s c -t 10 /database/mysql/mysql06_slow.log
   
   得到按照时间排序的前10条里面含有左连接的查询语句。
   mysqldumpslow -s t -t 10 -g “left join” /database/mysql/mysql06_slow.log
   
   另外建议在使用这些命令时结合 | 和more 使用 ，否则有可能出现刷屏的情况。
   mysqldumpslow -s r -t 20 /mysqldata/mysql/mysql06-slow.log | more
   ```

   ```
   ## 另外安装
   pt-query-digest
   1.输出到文件
     pt-query-digest show.log > slow_log.report
   2.输出到数据库表
     pt-query-digest slow.log -review \
       h=127.0.0.1,D=test,u=root,p=root,P=3306,t=query_review \
       --create-reviewtable \
       --review-history t=hostname_slow
       
   3.通过慢查询日志发现有问题的SQL
   	1. 查询次数多，且每次查询时间长的SQL
   	2. IO大的SQL
   	   pt-query-digest 分析中的Rows examine项
   	3. 未使用索引的SQL
   	   pt-query-digest 分析中Rows examine 和 Rows Send对比
   ```

3. Explain查看执行计划

   | 列            | 描述                                                         |
   | ------------- | :----------------------------------------------------------- |
   | id            | 查询的序号，包含一组数字，表示查询中执行select子句或操作表的顺序<br/>**两种情况**<br/>id相同，执行顺序从上往下<br/>id不同，id值越大，优先级越高，越先执行 |
   | select_type   | 查询类型，主要用于区别普通查询，联合查询，子查询等的复杂查询<br/>1、simple ——简单的select查询，查询中不包含子查询或者UNION<br/>2、primary ——查询中若包含任何复杂的子部分，最外层查询被标记<br/>3、subquery——在select或where列表中包含了子查询<br/>4、derived——在from列表中包含的子查询被标记为derived（衍生），MySQL会递归执行这些子查询，把结果放到临时表中<br/>5、union——如果第二个select出现在UNION之后，则被标记为UNION，如果union包含在from子句的子查询中，外层select被标记为derived<br/>6、union result:UNION 的结果<br/> |
   | table         | 输出的行所引用的表                                           |
   | type          | 显示联结类型，显示查询使用了何种类型，按照从最佳到最坏类型排序<br/>1、system：表中仅有一行（=系统表）这是const联结类型的一个特例。<br/>2、const：表示通过索引一次就找到，const用于比较primary key或者unique索引。因为只匹配一行数据，所以如果将主键置于where列表中，mysql能将该查询转换为一个常量<br/>3、eq_ref:唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于唯一索引或者主键扫描<br/>4、ref:非唯一性索引扫描，返回匹配某个单独值的所有行，本质上也是一种索引访问，它返回所有匹配某个单独值的行，可能会找多个符合条件的行，属于查找和扫描的混合体<br/>5、range:只检索给定范围的行，使用一个索引来选择行。key列显示使用了哪个索引，一般就是where语句中出现了between,in等范围的查询。这种范围扫描索引扫描比全表扫描要好，因为它开始于索引的某一个点，而结束另一个点，不用全表扫描<br/>6、index:index 与all区别为index类型只遍历索引树。通常比all快，因为索引文件比数据文件小很多。<br/>7、all：遍历全表以找到匹配的行<br/>注意:一般保证查询至少达到range级别，最好能达到ref。<br/> |
   | possible_keys | 指出MySQL能使用哪个索引在该表中找到行                        |
   | key           | 显示MySQL实际决定使用的键(索引)。如果没有选择索引,键是NULL。查询中如果使用覆盖索引，则该索引和查询的select字段重叠。 |
   | key_len       | 表示索引中使用的字节数，该列计算查询中使用的索引的长度在不损失精度的情况下，长度越短越好。如果键是NULL,则长度为NULL。该字段显示为索引字段的最大可能长度，并非实际使用长度。 |
   | ref           | 显示索引的哪一列被使用了，如果有可能是一个常数，哪些列或常量被用于查询索引列上的值 |
   | rows          | 根据表统计信息以及索引选用情况，大致估算出找到所需的记录所需要读取的行数 |
   | Extra         | 包含不适合在其他列中显示，但是十分重要的额外信息<br/>1、Using filesort：说明mysql会对数据适用一个外部的索引排序。而不是按照表内的索引顺序进行读取。MySQL中无法利用索引完成排序操作称为“文件排序”<br/>2、Using temporary:使用了临时表保存中间结果，mysql在查询结果排序时使用临时表。常见于排序order by和分组查询group by。<br/>3、Using index:表示相应的select操作用使用覆盖索引，避免访问了表的数据行。如果同时出现using where，表名索引被用来执行索引键值的查找；如果没有同时出现using where，表名索引用来读取数据而非执行查询动作。<br/>4、Using where :表明使用where过滤<br/>5、using join buffer:使用了连接缓存<br/>6、impossible where:where子句的值总是false，不能用来获取任何元组<br/>7、select tables optimized away：在没有group by子句的情况下，基于索引优化Min、max操作或者对于MyISAM存储引擎优化count（*），不必等到执行阶段再进行计算，查询执行计划生成的阶段即完成优化。<br/>8、distinct：优化distinct操作，在找到第一匹配的元组后即停止找同样值的动作。 |

4. 索引维护及优化

   * 查找重复及冗余索引

     pt-duplicate-key-checker -uroot -p123456 -h127.0.0.1

   * 删除不用索引

     pt-index-usage -uroot -p123456 mysql-slow.log

5. 选择合适的数据类型

   * 使用可以存下数据的最小的数据类型

     使用int来存储日期，利用from_unixtime(),unix_timestamp()两个函数来进行转换

     ```sql
     create table test(
         id int auto_increment not null,
         timestr int,
         primary key(id)
     );
     
     insert into test(timestr) values(unix_timestamp('2020-05-01 12:13:14'));
     
     select from_unixtime(timestr) from test;
     ```

     使用bigint来存储IP地址，利用inet_aton(),inet_ntoa()两个函数来进行转换

     ```sql
     create table sessions(
     	id int auto_increment not null,
     	ipaddress bigint,
     	primary key(id)
     );
     
     insert into sessions(ipaddress) values(inet_aton('192.168.0.1'));
     
     select inet_ntoa(ipaddress) from sessions;
     ```

     

   * 使用简单的数据类型。MySQL处理int要比varchar类型更简单

   * 尽可能使用not null定义字段

   * 尽量少用text类型，非用不可时最好考虑分表

6. 垂直分表
   * 把不常用的字段单独放到一个表中
   * 把大字段独立存放到一个表中
   * 把经常一起使用的字段放到一起

7. 水平分表

   * 对id进行hash运算，然后在取模

   **挑战**：

   1. 跨分区表进行数据查询

   2. 统计后台报表操作

      后台合成一个大表专门用于统计操作，不影响前端业务

8. mysql配置

   针对安装在linux系统上的mysql，修改/etc/sysctl.conf文件

   * 增加tcp支持的队列数

     `net.ipv4.tcp_max_syn_backlog = 65535`

   * 减少断开连接时的资源回收

     `net.ipv4.tcp_max_tw_buckets = 8000`

     `net.ipv4.tcp_tw_reuser = 1`

     `net.ipv4.tcp_tw_recycle = 1`

     `net.ipv4.tcp_fin_timeout = 10`

   打开文件的限制，可以使用`ulimit -a` 查看，可以修改`/etc/security/limits.conf`文件，增加一下内容以修改打开文件数量的限制

   * `soft nofile 65535`

   * `hard nofile 65535`

     > 除此之外，最好关闭iptables,selinux等防火墙软件

9. MySQL配置

   * `innodb_buffer_pool_size`**非常重要**的一个参数，用于配置Innodb的缓冲池。如果数据库中只有Innodb表，推荐配置量未总内存的75%

     ```sql
     select engine,
     	round(sum(data_length + index_length)/1024/1024,1) as 'total mb'
     from information_schema.tables 
     where table_schema not in ('information_schema','performance_schema')
     group by engine;
     
     innodb_buffer_pool_size >= total mb
     ```

   * `innodb_buffer_pool_instances`

     MySQL5.5中新增加的参数，可以控制缓冲池的个数，默认情况下只有一个。

   * `innodb_flush_log_at_trx_commit`

     **关键参数**，对innodb的io小绿影响很大。

     默认为1，可以取0，1，2，一般建议设为2，但如果数据安全性2要求比较高则使用默认值1。

   * `innodb_read_io_threads` `innodb_write_io_threads`

     以上两个参数决定了innodb读写的io进程数，默认为4

   * `innodb_file_per_table`

     **关键参数**，控制innodb每一个表使用独立的表空间，默认为OFF，也就是所有表都会建立在共享表空间中。

   10. 单节点数据库压力测试

       ```sql
       mysqlslap -hlocalhost -uroot -p123456 -P3306
       --concurrency=5000 
       --iterations=1 
       --auto-generate-sql
       --auto-generate-sql-load-type=mixed
       --auto-generate-sql-add-autoincrement
       --engine=innodb
       --number-of-queries=5000
       --debug-info
       ```

   11. 建立MySQL数据库集群

       * PXC 集群方案

         每一个数据库节点都可读可写

         牺牲性能换取高可靠性，数据安全性高

       * Replication集群方案

         分为Master和Slave节点，Master可读可写，Slave只读

         对数据安全性要求低

