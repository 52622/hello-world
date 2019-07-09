#### 1.连接MySQL

格式： mysql -h主机地址 -u用户名 －p用户密码

---

#### 2.存储过程

```sql
-- 基本格式
create procedure proc_name(proc_params)
begin
	proc_body;
end
```

**参数**

in：输入参数，可以传入字面量或者变量。

out：返回参数，只能是变量。

inout：输入输出参数，尽量少用。

**调用方式**

`call proc_name([params]);`

**变量声明**

```sql
DECLARE variable_name [,variable_name...] datatype [DEFAULT value];

DECLARE l_int int unsigned default 4000000;  
DECLARE l_numeric number(8,2) DEFAULT 9.95;  
DECLARE l_date date DEFAULT '1999-12-31';  
DECLARE l_datetime datetime DEFAULT '1999-12-31 23:59:59';  
DECLARE l_varchar varchar(255) DEFAULT 'This will not be padded';
```

**if-then-else语句**

```sql
CREATE PROCEDURE proc2(IN parameter int)  
begin 
declare var int;  
set var=parameter+1;  
if var=0 then 
insert into t values(17);  
end if;  
if parameter=0 then 
update t set s1=s1+1;  
else 
update t set s1=s1+2;  
end if;  
end;  
```

**case语句**

```sql
CREATE PROCEDURE proc3 (in parameter int)  
begin 
declare var int;  
set var=parameter+1;  
case var  
when 0 then   
insert into t values(17);  
when 1 then   
insert into t values(18);  
else   
insert into t values(19);  
end case;  
end;  

-- 格式
case
    when var=0 then
        insert into t values(30);
    when var>0 then
    when var<0 then
    else
end case
```

**循环语句**

1. while ··· end while

```sql
CREATE PROCEDURE proc4()  
begin 
declare var int;  
set var=0;  
while var<6 do  
insert into t values(var);  
set var=var+1;  
end while;  
end;  

-- 格式
while 条件 do
    --循环体
end while
```

2. repeat ··· end repeat

```sql
CREATE PROCEDURE proc5 ()  
begin   
declare v int;  
set v=0;  
repeat  
insert into t values(v);  
set v=v+1;  
until v>=5  
end repeat;  
end;

-- 格式
repeat
    --循环体
until 循环条件  
end repeat;
mysql > DELIMITER ;
```

3. loop ··· end loop

```sql
CREATE PROCEDURE proc6 ()  
begin 
declare v int;  
set v=0;  
LOOP_LABLE:loop  
insert into t values(v);  
set v=v+1;  
if v >=5 then 
leave LOOP_LABLE;  
end if;  
end loop;  
end;   
```
一个例子
```sql
CREATE PROCEDURE proc_list(in tableName varchar(100), in startTime varchar(20))
begin 
	declare m_sql LONGTEXT;
	set m_sql = 'select * from tableName where sex != \'0\' and updateTime >= startTime';
	set m_sql = replace(m_sql,'tableName',tableName);
	set m_sql = replace(m_sql,'startTime',concat('\'',startTime,'\''));
	-- select m_sql;
	set @m_sql = m_sql;
	prepare s1 from @m_sql;
	EXECUTE s1;
	DEALLOCATE prepare s1;
end
```
---

#### 3.函数

**`date_format`**

```sql
select date_format(now(),'%Y-%m-%d %T');
select date_format(now(),'%Y-%m-%d %H:%i:%s');
```

**`sub_str`**

```sql
select substr('2019-06-23 23:59:59' from 1 for 10);
select concat(substr('2019-06-23 23:59:59',1,10),',',substr('2019-06-23 23:59:59',12,8));
```



---

#### 4.`alter`命令

1.改表格的列的名称和属性

alter table [表名] change [旧列名] [新列名] 属性。

```sql
alter table user change name user_name varchar(50) default null; 
```

2.修改表名

命令：rename table 原表名 to 新表名;

3.增加字段

命令：alter table 表名 add 字段 类型 其他;

```sql
-- 例如：在表MyClass中添加了一个字段passtest，类型为int(4)，默认值为0
alter table MyClass add passtest int(4) default '0'
```

4.加索引
   mysql> alter table 表名 add index 索引名 (字段名1[，字段名2 …]);

```sql
alter table employee add index emp_name (name);
```

5.加主关键字的索引
  mysql> alter table 表名 add primary key (字段名);

```sql
alter table employee add primary key(id);
```

6.加唯一限制条件的索引
   mysql> alter table 表名 add unique 索引名 (字段名);

```sql
alter table employee add unique emp_name2(cardnumber);
```

7.删除某个索引
   mysql> alter table 表名 drop index 索引名;

```sql
alter table employee drop index emp_name;
```

8.删除字段
ALTER TABLE table_name DROP field_name;

#### 修改密码

格式：mysqladmin -u用户名 -p旧密码 password 新密码

  