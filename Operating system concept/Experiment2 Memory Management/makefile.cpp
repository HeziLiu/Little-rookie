/////////////////////////////////////////////////////////////////////////////////////////////
//makefile.cpp
/////////////////////////////////////////////////////////////////////////////////////////////
//�ļ����ɳ���
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
struct operation
{
	int time;//��ʼʱ��
	int block;//�ڴ�ҳ��
	int oper;//����
	int protection;//Ȩ��
};
int main()
{
	FILE* file;
	file = fopen("opfile", "wb");//��opfile��Ϊ����������ȷ���ڴ����
	operation op;

	for (int j = 0; j<6; j++) //0-������1-�ύ��2-����3-������4-���գ�5-�ͷ�
		for (int i = 0; i<5; i++)
			//0-PAGE_READONLY;
			//1-PAGE_READWRITE;
			//2-PAGE_EXECUTE;
			//3-PAGE_EXECUTE_READ;
			//4-PAGE_EXECUTE_READWRITE;
		{
			op.time = rand() % 1000;//������ɵȴ�ʱ��
			op.block = rand() % 5 + 1;//������ɿ��С
			op.oper = j;
			op.protection = i;
			fwrite(&op, sizeof(operation), 1, file);//�����ɵĽṹд���ļ�
		}
	return 0;
}
