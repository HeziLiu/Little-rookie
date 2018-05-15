


////////////////////////////////////////////////////////////////////////////////////////////////////////
//memory-op.cpp

//�ڴ����ʵϰ
//��������ļ�����ÿ�εĲ���������������뵽out.txt�ļ���
////////////////////////////////////////////////////////////////////////////////////////////////////////
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <iostream>
using namespace std;
struct operation
{
	int time;//��ʼʱ��
	int block;//�ڴ�ҳ��
	int oper;//����
	int protection;//Ȩ��
};

struct trace //����ÿһ�η��������ݽṹ
{
	LPVOID start;//��ʼ��ַ
	long size;   //����Ĵ�С
};

HANDLE allo, trac; //�ź����ľ��

DWORD Tracker(LPDWORD lpdwparm) //����allocator�̵߳��ڴ���Ϊ���������Ҫ��Ϣ
{
	ofstream outfile;//����ļ�
	outfile.open("out.txt");

	for (int i = 0; i <= 30; i++)
	{
		WaitForSingleObject(trac, INFINITE); //�ȴ�allocatorһ���ڴ��������
											 //��ӡ�ڴ�״����ϵͳ״��
		outfile << i << endl;
		//����һ����ʾϵͳ��Ϣ��ÿ��ִ�в�����ϵͳ��Ϣ����
		//���Ҫ�鿴ϵͳ��Ϣ������ȡ��ע��

		SYSTEM_INFO info;  //ϵͳ��Ϣ
		GetSystemInfo(&info);
		outfile << "dwActiveProcessorMask" << '\t' << info.dwActiveProcessorMask << endl;
		outfile << "dwAllocationGranularity" << '\t' << info.dwAllocationGranularity << endl;
		outfile << "dwNumberOfProcessors" << '\t' << info.dwNumberOfProcessors << endl;
		outfile << "dwOemId" << '\t' << info.dwOemId << endl;
		outfile << "dwPageSize" << '\t' << info.dwPageSize << endl;
		outfile << "dwProcessorType" << '\t' << info.dwProcessorType << endl;
		outfile << "lpMaximumApplicationAddress" << '\t' << info.lpMaximumApplicationAddress << endl;
		outfile << "lpMinimumApplicationAddress" << '\t' << info.lpMinimumApplicationAddress << endl;
		outfile << "wProcessorArchitecture" << '\t' << info.wProcessorArchitecture << endl;
		outfile << "wProcessorLevel" << '\t' << info.wProcessorLevel << endl;
		outfile << "wProcessorRevision" << '\t' << info.wProcessorRevision << endl;
		outfile << "wReserved" << '\t' << info.wReserved << endl;
		outfile << "************************************************************************************" << endl;

		//�ڴ�״��
		MEMORYSTATUS status; //�ڴ�״̬
		GlobalMemoryStatus(&status);
		outfile << "dwAvailPageFile" << '\t' << status.dwAvailPageFile << endl;
		outfile << "dwAvailPhys" << '\t' << status.dwAvailPhys << endl;
		outfile << "dwAvailVirtual" << '\t' << status.dwAvailVirtual << endl;
		outfile << "dwLength" << '\t' << status.dwLength << endl;
		outfile << "dwMemoryLoad" << '\t' << status.dwMemoryLoad << endl;
		outfile << "dwTotalPageFile" << '\t' << status.dwTotalPageFile << endl;
		outfile << "dwTotalPhys" << '\t' << status.dwTotalPhys << endl;
		outfile << "dwTotalVirtual" << '\t' << status.dwTotalVirtual << endl;
		outfile << "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" << endl;

		//����һ����ʾ�ڴ������Ϣ��ÿ�β������ڴ������Ϣ����
		//��Ҫ�鿴�ڴ������Ϣ������ȡ��ע��

		MEMORY_BASIC_INFORMATION mem;//�ڴ������Ϣ
		VirtualQuery(info.lpMinimumApplicationAddress, &mem,
			sizeof(MEMORY_BASIC_INFORMATION));
		outfile << "AllocationBase" << '\t' << mem.AllocationBase << endl;
		outfile << "AllocationProtect" << '\t' << mem.AllocationProtect << endl;
		outfile << "BaseAddress" << '\t' << mem.BaseAddress << endl;
		outfile << "Protect" << '\t' << mem.Protect << endl;
		outfile << "RegionSize" << '\t' << mem.RegionSize << endl;
		outfile << "State" << '\t' << mem.State << endl;
		outfile << "Type" << '\t' << mem.Type << endl;
		outfile << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;

		//�ͷ��ź���֪ͨallocator����ִ����һ���ڴ����
		ReleaseSemaphore(allo, 1, NULL);
	}
	return 0;
}
void Allocator() //ģ���ڴ�������߳�
{
	trace traceArray[5];
	int index = 0;
	FILE* file;
	file = fopen("opfile", "rb");//�����ļ�
	operation op;
	SYSTEM_INFO info;
	DWORD temp;
	GetSystemInfo(&info);
	for (int i = 0; i<=30; i++)
	{
		WaitForSingleObject(allo, INFINITE);  //�ȴ�tracker��ӡ�������ź���
		cout << i << ':';
		fread(&op, sizeof(operation), 1, file);
		Sleep(op.time); //ִ��ʱ�䣬�������ָ��ʱ��ִ�п���ȡ��ע��
		GetSystemInfo(&info);
		switch (op.protection) //�����ļ�����ȷ��Ȩ��
		{
		case 0:
		{
			index = 0;
			temp = PAGE_READONLY;
			break;
		}
		case 1:
			temp = PAGE_READWRITE;
			break;
		case 2:
			temp = PAGE_EXECUTE;
			break;
		case 3:
			temp = PAGE_EXECUTE_READ;
			break;
		case 4:
			temp = PAGE_EXECUTE_READWRITE;
			break;
		default:
			temp = PAGE_READONLY;
		}
		switch (op.oper)
		{
		case 0://����һ������
		{
			cout << "reserve now" << endl;

			traceArray[index].start = VirtualAlloc(NULL, op.block*info.dwPageSize,
				MEM_RESERVE, PAGE_NOACCESS);
			traceArray[index++].size = op.block*info.dwPageSize;
			cout << "starting address:"
				<< traceArray[index - 1].start << '\t' << "size:" << traceArray[index - 1].size << endl;
			break;
		}
		case 1://�ύһ������
		{
			cout << "commit now" << endl;

			traceArray[index].start = VirtualAlloc(traceArray[index].start, traceArray[index].size, MEM_COMMIT, temp);
			index++;
			cout << "starting address:"
				<< traceArray[index - 1].start << '\t' << "size:" << traceArray[index - 1].size << endl;
			break;
		}
		case 2: //��һ������
		{
			cout << "lock now" << endl;
			cout << "starting address:" << traceArray[index].start << '\t' << "size:" << traceArray[index].size << endl;
			if (!VirtualLock(traceArray[index].start, traceArray[index++].size))
				cout << GetLastError() << endl;//GetLastError()�������ش����
			break;
		}
		case 3: //����һ������
		{
			cout << "unlock now" << endl;
			cout << "starting address:" << traceArray[index].start << '\t' << "size:" << traceArray[index].size << endl;
			if (!VirtualUnlock(traceArray[index].start, traceArray[index++].size))
				cout << GetLastError() << endl;
			break;
		}
		case 4: //����һ������
		{
			cout << "decommit now" << endl;
			cout << "starting address:" << traceArray[index].start << '\t' << "size:" << traceArray[index].size << endl;
			if (!VirtualFree(traceArray[index].start, traceArray[index++].size, MEM_DECOMMIT))
				cout << GetLastError() << endl;
			break;
		}
		case 5: //�ͷ�һ������
		{
			cout << "release now" << endl;
			cout << "starting address:" << traceArray[index].start << '\t' << "size:" << traceArray[index].size << endl;
			if (!VirtualFree(traceArray[index++].start, 0, MEM_RELEASE))
				cout << GetLastError() << endl;
			break;
		}
		default:
			cout << "error" << endl;
		}
		ReleaseSemaphore(trac, 1, NULL); //�ͷ��ź���֪ͨtracker���Դ�ӡ��Ϣ
	}
}
int main()
{
	DWORD dwThread;
	HANDLE handle[2];
	//���������߳�
	handle[0] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)Tracker, NULL, 0, &dwThread);
	handle[1] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)Allocator, NULL, 0, &dwThread);

	//���������ź���
	allo = CreateSemaphore(NULL, 0, 1, (LPCWSTR)"allo");
	trac = CreateSemaphore(NULL, 1, 1, (LPCWSTR)"trac");
	//�ȴ��߳�ִ�е�ִ�н��������˳�
	WaitForMultipleObjects(2, handle, TRUE, INFINITE);
	system("pause");
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//The End
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





