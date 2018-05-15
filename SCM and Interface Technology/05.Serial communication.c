#include "reg52.h"
#include "intrins.h"
typedef unsigned char u8;
typedef unsigned int u16;
#define HEAD 0x68;
#define TAIL 0x23;
#define ADDRESS 0x01;
u8 status = 0;
u8 ReceiveArr[20];
u8 *Rece;
u8 ByteLength;
u8 flag = 0;
u8 code smgduan[9] = {0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,0x7f};
u8 arr[20],send[10];
u8 *s,SendLength;
void UsartInit()
{
	TMOD = 0x20;   //��ʱ������ѡ���Զ���װ�ط�ʽ2������λ���ƶ�ʱ������1������λ��ʱ������2��
	TH1 = 0xF3;
	TL1 = 0xF3;//������4800
	PCON = 0x80;  //�����ʱ���
	EA = 1;
	TR1 = 1;	  //���п���λ��1��ʼ����
	SCON = 0x50; //ѡ��ʽһ	0101
	ES = 1;
}
void process(void){
	u8 i,temp=0,j=0;
	send[4]=0x00;//Ĭ�ϳ�ʼֵ�޴���
	send[0]=0x68;
	send[1]=0x01;
	send[2]=ReceiveArr[2];
	send[3]=0x01;
	send[6]=0x23;
	if(ReceiveArr[1]!=0x01)return;
	
	if(ReceiveArr[9]!=0x23)
	{
		send[4]=0x01;//֡β����
	}
	for(i=1;i<8;i++){
		temp+=ReceiveArr[i];
	}
	if(temp!=ReceiveArr[8]){
		send[4]=0x02;  //У��ͳ���
	}else if(temp=ReceiveArr[8]&&send[4]!=0x01){
		arr[0] = (ReceiveArr[4]&0xF0)>>4;//�ֱ�ȡ����λ����λ����  
		arr[1] = ReceiveArr[4]&0x0F;		//��ʾ����Ӧ���������
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
	for (i = 0; i<8; i++)	//����ܶ�̬������ʾ
	{
		P2 = _crol_(i, 2);
		j = arr[i];
		P0 = smgduan[j];//���Ͷ���
		delay(100);//���һ��ʱ��ɨ��
		P0 = 0x00;	//����
	}
}

void main()
{
	UsartInit();
	while (1){
		display();
		if (flag){
			flag = 0;
			process();	
		}
	}
}