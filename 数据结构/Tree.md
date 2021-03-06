**参考链接：**

<https://www.jianshu.com/p/e136ec79235c>

https://my.oschina.net/u/3272058/blog/1914452

#### 二叉树

* 两种实现方式：

无序链表：插入和删除快

有序数组：查找快



#### 红黑树

五条性质

1. 节点是红色或者黑色
2. 根节点是黑色
3. 每个叶节点（NIL或者空节点）是黑色
4. 每个红色节点的子节点都是黑色（也就是说不存在两个连续的红色节点，父节点和子节点不能同时为红色）
5. 从任一节点到其每个叶节点的所有路径都包含相同数目的黑色节点

![红黑树](images\红黑树.png)



插入

为了满足这五条性质，如果我们插入的是黑色节点，那就违反了性质五，需要进行大规模调整，如果我们插入的是红色节点，那就只有在要插入节点的父节点也是红色的时候违反性质四或者是当插入的节点是根节点时，违反性质二，所以，我们把要插入的节点的颜色变成红色。

（1）新节点位于根节点，其没有父节点时，处理思路：将该节点直接设为黑色即可

（2）新节点的父节点是黑色时，处理思路：不用动，这已然是一颗红黑树

（3）父节点和叔节点都是红色时，处理思路：a.将父节点和叔节点设为黑色;b.将祖父节点设为红色;c.将祖父节点设为当前节点，并继续对新当前节点进行操作

（4）父节点是红色，叔节点是黑色时，又分如下四种情况：

- 当前节点是父亲的左孩子，父亲是祖父的左孩子（Left-Left），处理思路：a.将祖父节点右旋;b.交换父节点和祖父节点的颜色
- 当前节点是父亲的右孩子，父亲是祖父的左孩子（Right-Left），处理思路：a.将父节点左旋，并将父节点作为当前节点; b.然后再使用Left Left情形
- 当前节点是父亲的右孩子，父亲是祖父的右孩子（Right-Right），处理思路：a.将祖父节点左旋;b.交换父节点和祖父节点的颜色
- 当前节点是父亲的左孩子，父亲是祖父的右孩子（Left-Right），处理思路：a.将父节点右旋，并将父节点作为当前节点; b.然后再使用Right Right情形



![](images\红黑树删除结点的叫法约定.png)

**删除**

一、从树中删除节点X（**以寻找后继节点的方式进行删除**）

情况①：如果X没有孩子，且如果X是红色，直接删除X；如果X是黑色，则以X为当前节点进行旋转调色，最后删掉X

情况②：如果X只有一个孩子C，交换X和C的数值，再对新X进行删除。根据红黑树特性，此时X不可能为红色，因为红色节点要么没有孩子，要么有两个黑孩子。此时以新X为当前节点进行情况①的判断

情况③：如果X有两个孩子，则从后继中找到最小节点D，交换X和D的数值，再对新X进行删除。此时以新X为当前节点进行情况①或②的判断

二、旋转调色（R=旋转调色的当前节点[等于情况①中的X]，P=R的父亲，S=R的兄弟，Nf=R的远侄子，Nn=R的近侄子）

情况1：R是根或者R是红色，则：直接将R设为黑色

情况2：R不是根且R是黑色，且S为红色，则：将S设为黑色，P设为红色，对P进行旋转(R为P的左子时进行左旋，R为P的右子时进行右旋)，将情况转化为情况1、2、3、4、5

情况3：R不是根且R是黑色，且S为黑色，且S的左右子均为黑色，则：将S设为红色，将P设为当前节点进行旋转调色，将情况转化为情况1、2、3、4、5

情况4：R不是根且R是黑色，且S为黑色，且Nf为黑色，Nn为红色，则：交换S与Nn的颜色，并对S进行旋转(R为P的左子进行右旋，R为P的右子进行左旋)，旋转后R的新兄弟S有一个红色Nf，则转换为情况5

情况5：R不是根且R是黑色，且S为黑色，且Nf为红色，Nn为黑色，则：将S设为P的颜色，P和Nf设为黑色，并对P进行旋转(R为P的左子进行左旋，R为P的右子进行右旋)，R设为根