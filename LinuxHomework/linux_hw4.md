# 作业四 C语言编程作业

#####  ``add.c``提供``add(int,int)``函数

```c
#include "add.h"
#include <stdio.h>
int add(int a,int b){
	return a+b;
}
```

#####  ``add.h``提供``add``函数声明

```c
#ifndef _ADD_H
#define _ADD_H
#include<stdio.h>
int add(int a,int b);
#endif
```

#####  ``add(int,int)``打包成库``libadd.a``

```bash
➜  hw4 ar -crv libadd.a add.o
```

#####  ``calc.c``利用``libadd.a``中的``add``函数，完成计算``add(100,100)``并输出结果

```c
#include <stdio.h>
int main(){
	printf("100+100=%d\n",add(100,100));
	return 0;
}
```

##### Makefile内容

```makefile
calc:calc.c add.o libadd.a
	gcc -o calc calc.c -L. -ladd
add.o:add.c
	gcc -c -o add.o add.c
libadd.a:add.c
	ar -crv libadd.a add.o
clean:
	rm calc *.o
```

##### 程序运行结果

![微信图片_20180527122429](C:\Users\78914\Desktop\微信图片_20180527122429.jpg)

