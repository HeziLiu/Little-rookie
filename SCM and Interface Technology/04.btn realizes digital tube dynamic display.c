#include "reg52.h"
#include "intrins.h"
typedef unsigned char u8;
typedef unsigned int u16;
sbit k3=P3^2;
sbit k4=P3^3;
u8 code smgduan[16]={0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,
					0x7f,0x6f,0x77,0x7c,0x39,0x5e,0x79,0x71};//显示0~F的值
u8 a[]={1,2,3,4,5,6,7,8}; 
u8 h=0;
void delay(u16 i){
	while(i--);
	}
void display()
{	
	u8 i;
	u8 j;
	for(i=0;i<8;i++)	//数码管动态递增显示
	{
		P2=_crol_(i,2);
		j=(a[i]+h)%10;
		P0=smgduan[j];//发送段码
		delay(100);//间隔一段时间扫描
		P0=0x00;	//消隐
	}		
}
void Int0Init()
{
	IT0=1;
	EX0=1;
	EA=1;
}
void Int1Init()
{
	IT1=1;
	EX1=1;
	EA=1;	
}
void Int0() interrupt 0
{
	if(k3==0){
		delay(1000);//按键消抖
		if(k3==0){
			h++;
		}
	}
	
}
void Int1() interrupt 3
{
	if(k4==0){
		delay(1000);//按键消抖
		if(k4==0){
			h--;
		}
	}
}	
void main()
{	
	Int0Init();
	Int1Init();
	while(1){
		display();
	}
}
