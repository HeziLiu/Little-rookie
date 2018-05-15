#include "reg52.h"
#include "intrins.h"
typedef unsigned char u8;
typedef unsigned int u16;
#define led P2
void Timer0Init()
{
	TMOD=0x01;	   //低四位T0 16位定时计数器
	TH0=46080/256;	 //50000*(11.0592/12MHz)=46080
	TL0= 46080%256;	 //定1s,也就是50ms计20次
	ET0=0;			 //50ms=50000us
	EA=0;			//TH0=65536-50000=15536	=3CB0
	TR0=1;
}
void main()
{	u8 i,j;
	Timer0Init();
	led=0xFE;
	while(1)
	{
		if(TF0==1){
			TF0=0;	   //查询方式
			i++; 
			if(i==20){
				for(j=0;j<7;j++)
				{
					
					led=_cror_(led,1);
				}
				i=0;
			}			
		}

	}
}