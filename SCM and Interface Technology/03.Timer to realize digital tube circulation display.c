#include "reg52.h"
#include "intrins.h"			 
typedef unsigned int u16;	  //对数据类型进行声明定义
typedef unsigned char u8;
sbit LSA=P2^2;
sbit LSB=P2^3;
sbit LSC=P2^4;
u8 code smgduan[16]={0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,
					0x7f,0x6f,0x77,0x7c,0x39,0x5e,0x79,0x71};//显示0~F的值
u8 a[]={1,2,3,4,5,6,7,8};
void Timer0() interrupt 1
{
	static u8 k;
	static u8 h;

	TH0=0x3C;	 //50000*(11.0592/12MHz)=46080
	TL0=0xB0;
	k++;
	if(k>=20)  //计20次 20*50=1000ms=1s
	{
		k=0;
		for(h=0;h<8;h++)
		{
			a[h]++;
		}
	}
}
void delay(u16 i)
{
	while(i--);	
}
void DigDisplay()
{
	u8 i;
	u8 j;
	for(i=0;i<8;i++)
	{	
		P2=_crol_(i,2);
		j=a[i];
		P0=smgduan[j%9];//发送段码
		delay(100); //间隔一段时间扫描	
		P0=0x00; 
		} 
		   	//消隐}
	}
void main()
{
	TMOD=0x01;
	TH0=0x3C;	 //65536-50000*(11.0592/12MHz)
	TL0=0xB0;	 //计50ms
	ET0=1;
	EA=1;
	TR0=1;
	while(1)
	{	 
		DigDisplay(); 	
		  //数码管显示函数	
	}		
}
