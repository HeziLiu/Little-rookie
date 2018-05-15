#include "reg52.h"
#include "intrins.h"
typedef unsigned char u8;
typedef unsigned int u16;
/*1/要实现定时器T1定时让数码管1357数码管显示
	（特定操作：+一个数或者-一个数）
  2/同时串口发送数据改变数码管的数据，并及时应答*/
#define HEAD 0x68;	 //帧头
#define TAIL 0x23;	 //帧尾
#define ADDRESS 0x01; //地址
u8 status = 0;		  //状态
u8 ReceiveArr[20];	 //接收帧数组
u8 *Rece;			 //接收指针
u8 ByteLength;		 //数据长度
u8 flag = 0;		 //状态标志
u8 code smgduan[10] = {0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,0x7f,0x6f};
u8 send[10]; //arr为发送字型码的暂存数组 send为应答帧
u8 *s,SendLength;
u8 arr[]={0,0,0,0,0,0,0,0};
void Timer0() interrupt 1
{
	static u8 k;
	static u8 h;

	TH0=0x3C;	 //50ms
	TL0=0xB0;
	k++;
	if(k>=20)	   //计20次1s变一次
	{
		k=0;
		for(h=0;h<8;h++)
		{
			if(h%2!=0){
			  arr[h]=arr[h]+2;
			}else{
				arr[h]++;
			}	
		}
	}

}
void Timer0Init(){
	TMOD=0x01;
	TH0=0x3c;
	TL0=0xb0;
	ET0=1;
	EA=1;
	TR0=1;
}
void UsartInit()
{
	TMOD = 0x20;
	TH1 = 0xF3;
	TL1 = 0xF3;//原来的数据 F3 波特率4800
	PCON = 0x80;
	EA = 1;
	TR1 = 1;
	SCON = 0x50;
	ES = 1;
}
void process(void){
	u8 i,temp=0,j=0;
	send[4]=0x00;//默认初始值无错误
	send[0]=0x68;
	send[1]=0x01;
	send[2]=ReceiveArr[2];
	send[3]=0x01; //长度
	send[6]=0x23;//尾地址
	if(ReceiveArr[1]!=0x01)return;
	
	if(ReceiveArr[9]!=0x23)
	{
		send[4]=0x01;//帧尾出错
	}
	for(i=1;i<8;i++){
		
		temp=temp+ReceiveArr[i];
	} 
	if(temp!=ReceiveArr[8]){
		send[4]=0x02;	   //校验和出错
	}else if(temp=ReceiveArr[8]&&send[4]!=0x01){
		arr[0] = (ReceiveArr[4]&0xF0)>>4;
		arr[1] = ReceiveArr[4]&0x0F;
		arr[2] = (ReceiveArr[5]&0xF0)>>4;
		arr[3] = ReceiveArr[5]&0x0F;			
		arr[4] = (ReceiveArr[6]&0xF0)>>4;
		arr[5] = ReceiveArr[6]&0x0F;
		arr[6] = (ReceiveArr[7]&0xF0)>>4;
		arr[7] = ReceiveArr[7]&0x0F;		
	}
	temp=0;
	for(i=1;i<5;i++){
		temp+=send[i];
	}
	send[5]=temp;
	for(i=0;i<7;i++){
		TI=0;
		SBUF=send[i];
		while(!TI);
		TI=0;
	}	
	
}
void Usart() interrupt 4
{
	if (RI){
		RI = 0;
			switch (status){
			case(0) :
				if (SBUF == 0x68){
					Rece = ReceiveArr;
					*Rece++ = SBUF;
					status = 1;
					ByteLength = 3;
				}
				break;
			case(1) :
				*Rece++ = SBUF;
				ByteLength--;
				if (ByteLength == 0){
					status = 2;
					ByteLength = SBUF + 2;
				}
				break;
			case(2) :
				*Rece++ = SBUF;
				ByteLength--;
				if (ByteLength == 0){
					status = 0;
					flag=1;
				}
				break;
		}
	}
	
	}
void delay(u8 i){
	while (i--);
}
void display()
{
	u8 i;
	u8 j;
	for (i = 0; i<8; i++)	//数码管动态递增显示
	{
		if(i%2!=0){
			P2 = _crol_(i, 2);
			j = arr[i]%10;
			P0 = smgduan[j];//发送段码
			delay(100);//间隔一段时间扫描
			P0 = 0x00;	//消隐
		}else{
			P2 = _crol_(i, 2);
			j = arr[i]%10;
			P0 = smgduan[j];//发送段码
			delay(100);//间隔一段时间扫描
			P0 = 0x00;	//消隐
		}
		
	}
}

void main()
{
	Timer0Init();
	UsartInit();
	while (1){
		display();
		if (flag){
			flag = 0;
			process();	
		}
	
	}
}