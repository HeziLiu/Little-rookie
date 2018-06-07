##Collection和Collections的区别

collection是集合类的上级接口，继承与他的接口主要有Set和List

Collections是针对集合类的一个帮助类，提供一系列静态方法实现对各种集合的搜索、排序、线程安全化等操作

## Set中的元素

不可重复，重复与否用equals()进行判断

## 常用集合类及其主要方法

List和map，list包括ArrayList和Vector，均是可变大小的列表，适合构建、存储和操作任何类型对象的元素列表，List适用场景：按数值索引访问元素

Map集合类用于存储元素对（键值对），每个键映射到一个值，键不可重复

## 对象值相同（x.equals(y)==true）,但可有不同的Hash code

 正确。若对象保存在HashSet和HashMap中，它们的equals相等，那么其hashcode值就必须相等

## TreeSet中存放对象，若同时放入了父类和子类的实例对象，比较时使用的是父类的CompareTo方法还是子类的CompareTo方法还是抛出异常

当前的add方法放入的是哪个对象，就调用哪个对象的CompareTo方法

实验代码：

```java
public class Parent implements Cmparable{
    private int age=0;
    public parent(int age){
        this.age=age;
    }
    public int CompareTo(Object o){
        System.out.println("method of parent");
        Parent o1=(Parent)o;
        return age>o1.age?1:age<o1.age?-1:0;
    }
}
publicclass Childextends Parent {
    public Child(){
       super(3);
    }
    public int compareTo(Object o){
           // TODO Auto-generated methodstub
           System.out.println("methodof child");
//         Child o1 = (Child)o;
           return 1;
    }
}
publicclass TreeSetTest {
    /**
     * @paramargs
     */
    public static voidmain(String[] args) {
       // TODO Auto-generated method stub
       TreeSet set = new TreeSet();
       set.add(newParent(3));
       set.add(new Child());
       set.add(newParent(4));
       System.out.println(set.size());
    }
}
```



## java序列化

将一个java对象变成字节流的形式传出去或从一个字节流中恢复成一个java对象。jre本身提供这种机制，要被传输的对象必须实现serializable接口，换言之，需要被序列化的类必须实现Serializable接口，标注该对象可被序列化

## 字节流和字符流

字符流是字节流的包装，字符流则是直接接收字符串，字符向字节转换时，要注意编码的问题。

## JVM加载class文件的原理机制

使用ClassLoader和它的子类来实现的，ClassLoader是一个重要的Java运行时系统组件，它负责在运行时查找和装入类文件的类

## Heap和Stack的区别

java内存分为：栈内存和堆内存

+ 栈内存：指程序进入一个方法时，会为这个方法单独分配一块私属存储空间，用于存储这个方法内部的局部变量，当这个方法结束时，分配给这个方法的栈会释放，这个栈中的变量也会随之释放。
+ 堆内存：一般用于存放不放在当前方法栈中的那些数据 ，e.g.使用new创建的对象都放在堆里，不会随方法的结束而消失
+ 方法中的局部变量使用final修饰后，放在堆中，而不是栈中。

## 垃圾回收机制（GC）

自动监测对象是否超过作用域从而达到自动回收内存的目的，Java中没有提供释放已分配内存的显式操作方法

+ 优点

  + 编程时不用考虑内存管理（对象不再有“作用域”的概念，对象的引用才有“作用域”？？？）
  + 有效防止内存泄漏，有效使用可使用的内存

+ 原理

  + 垃圾回收器通常作为一个单独的低级别的线程运行，不可预知的情况下对内存堆中已经死亡的或者长时间没有使用的对象进行清除和回收，程序员不能实时地调用垃圾回收器对某个对象或所有对象进行垃圾回收

  + 当创建对象时，GC开始监控这个对象的地址、大小以及使用情况。GC采用**采用有向图**的方式记录和管理**堆（heap）**中的所有对象，来确定哪些对象是"可达的"，哪些对象是“不可达的”，当确定为“不可达”时，GC就有责任回收这些内存控件。

    #### GC可以马上回收内存吗？

    可以，通过手动调用`System.gc()`，通知GC运行，但Java语言规范并不保证GC一定会执行

+ 2种回收机制

  + 分代复制垃圾回收
  + 标记垃圾回收
  + 增量垃圾回收 

## assert(断言)

一种常用的调试方式，许多语言都支持

具体实现就是对一个boolean表达式进行检查，正确的程序必须保证这个布尔表达式的值为true;若该值为false，表明程序处于不正确的状态，assert将会给出警告或退出

**assertion用于保证程序最基本、关键的正确性**

软件发布后assertion检查通常是关闭的

## Java中内存泄漏

内存泄漏：一个不再被程序使用的对象或变量一直被占据在内存中

java中的内存泄漏情况：**长生命周期的对象持有段生命周期对象的引用就可能发生内存泄漏**

e.g. 缓存系统，加载了一个对象放在缓存中，然后一直不再使用它，该对象一直被缓存引用，但却不再被使用