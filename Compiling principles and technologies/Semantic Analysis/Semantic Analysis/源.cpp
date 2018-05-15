#include<iostream>
#include<cmath>
#include<vector>
#include<sstream>
#include<string>
using namespace std;

vector<char> VN;	//���ս������
vector<string> VT;	//�ս������
vector<string>P;	//����ʽ����
int State[50];		//״̬ջ
double Value[50];   //��ֵջ
int Type[50];       //��ֵ���ͣ�Ϊ1��Ϊinteger,Ϊ2��Ϊreal
int top = 0;        //ջ��ָ��

//��ʼ��Action��
string Action[20][20] = {
	{ "e1","e1","e1","e1","S4","e2","e1","S5","e1" },
	{ "S6","S7","e5","e5","e3","e2","e1","e3","ACC" },
	{ "R3","R3","S8","S9","e3","R3","e1","e3","R3" },
	{ "R6","R6","R6","R6","e6","R6","e1","e6","R6" },
	{ "e1","e1","e1","e1","S4","e2","e1","S5","e1" },
	{ "R8","R8","R8","R8","e6","R8","S11","e6","R8" },
	{ "e1","e1","e1","e1","S4","e2","e1","S5","e1" },
	{ "e1","e1","e1","e1","S4","e2","e1","S5","e1" },
	{ "e1","e1","e1","e1","S4","e2","e1","S5","e1" },
	{ "e1","e1","e1","e1","S4","e2","e1","S5","e1" },
	{ "S6","S7","e5","e5","e3","S16","e1","e3","e4" },
	{ "e7","e7","e7","e7","e7","e7","e7","S17","e7" },
	{ "R1","R1","S8","S9","e3","R1","e1","e3","R1" },
	{ "R2","R2","S8","S9","e3","R2","e1","e3","R2" },
	{ "R4","R4","R4","R4","e6","R4","e1","e6","R4" },
	{ "R5","R5","R5","R5","e6","R5","e1","e6","R5" },
	{ "R7","R7","R7","R7","e6","R7","e1","e6","R7" },
	{ "R9","R9","R9","R9","e6","R9","e1","e6","R9" }
};

//��ʼ��Goto��
int Goto[20][4] = {
	{ 1,2,3 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ 10,2,3 },
	{ -1,-1,-1 },
	{ -1,12,3 },
	{ -1,13,3 },
	{ -1,-1,14 },
	{ -1,-1,15 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
};

//�������ʽ
void Input_P() {
	VN.push_back('E');
	VN.push_back('T');
	VN.push_back('F');
	VT.push_back("+");
	VT.push_back("-");
	VT.push_back("*");
	VT.push_back("/");
	VT.push_back("(");
	VT.push_back(")");
	VT.push_back(".");
	VT.push_back("num");
	P.push_back("E->E+T");
	P.push_back("E->E-T");
	P.push_back("E->T");
	P.push_back("T->T*F");
	P.push_back("T->T/F");
	P.push_back("T->F");
	P.push_back("F->(E)");
	P.push_back("F->num");
	P.push_back("F->num.num");
}

//��string��ת��Ϊint��
int SToI(string str) {
	int num;
	stringstream stream;
	stream << str;
	stream >> num;
	return num;
}

//��λVN���еķ��ս�����������±�
int findVN(vector<char> VN, char c) {
	vector<char>::iterator it;
	for (it = VN.begin(); it != VN.end(); it++)
	{
		if ((*it)==c)
		{
			return it - VN.begin();
		}
	}
	return -1;
}

//��λVT���е��ս�����������±�
int findVT(vector<string>VT, string c) {
	vector<string>::iterator it;
	for ( it = VT.begin(); it !=VT.end(); it++)
	{
		if ((*it) == c) {
			return it - VT.begin();
		}
	}
	return -1;
}

//���뷽��
void Translate(int number) {
	int i; double j;//��ʱ����
	switch (number)
	{
	case 1://E->E+T
		Value[top - 2] = Value[top - 2] + Value[top];
		if (Type[top]==2||Type[top-2]==2)
		{
			Type[top - 2] = 2;
		}
		else {
			Type[top - 2] = 1;
		}
		break;
	case 2://E->E-T
		Value[top - 2] = Value[top - 2] - Value[top];
		if (Type[top] == 2 || Type[top - 2] == 2)
		{
			Type[top - 2] = 2;
		}
		else {
			Type[top - 2] = 1;
		}
		break;
	case 3://E->T
		Value[top] = Value[top];
		Type[top] = Type[top];
		break;
	case 4://T->T*F
		Value[top - 2] = Value[top - 2] * Value[top];
		if (Type[top] == 2 || Type[top - 2] == 2)
		{
			Type[top - 2] = 2;
		}
		else {
			Type[top - 2] = 1;
		}
		break;
	case 5://T->T/F
		if (Type[top] == 2 || Type[top - 2] == 2 || (int)Value[top - 2] % (int)Value[top] != 0) {
			//���ܹ�������Ϊreal
			Type[top - 2] = 2;
		}
		else
		{
			Type[top - 2] = 1;
		}
		Value[top - 2] = Value[top - 2] / Value[top];
		break;
	case 6://T->F
		Value[top] = Value[top];
		Type[top] = Type[top];
		break;
	case 7://F->(E)
		Value[top - 2] = Value[top - 1];
		Type[top - 2] = Type[top - 1];
		break;
	case 8://F->num
		Value[top] = Value[top];
		Type[top] = 1;
		break;
	case 9://F->num.num
		j = Value[top];
		//����С���м�λ
		for ( i = 0;j>=1 ; i++)
		{
			j = j / 10;
		}
		Value[top - 2] = Value[top - 2] + Value[top] * pow(0.1, i);
		Type[top - 2] = 2;
		break;
	default:
		break;
	}
}

//���״̬ջ0����ֵջ1
void output(int number) {
	int i;
	if (number==0)
	{
		for ( i = 0; i <= top; i++)
		{
			cout << State[i] << " ";
		}
	}
	else
	{
		for ( i = 0; i <= top; i++)
		{
			cout << Value[i] << " ";
		}
	}
}

void LR() {
	int i, j, k;	//��ʱ����
	int p = 0;		//���봮�±�
	int length = 0; //�����ַ�����
	int S;			//״̬ջջ������
	char a;			//���η������봮�����ַ�
	string temp;
	VT.push_back("$");
	string input;
	cout << "������Ҫ�����ķ��Ŵ���" << endl;
	cin >> input;
	cout << endl;
	input += "$";
	State[top] = 0;//��״̬ջջ��Ϊ0
	cout << "��ʼ������" << endl;
	while (1)
	{
		S = State[top];
		a = input[p];
		temp.clear();
		cout << "State: ";
		output(0);
		cout << endl;
		cout << "  Val: ";
		output(1);
		cout << endl << "���룺" << input.substr(p) <<"\t"<< "����������";
		if (a == '+'|| a == '-' || a == '*' || a == '/' || 
			a == '('|| a == ')' || a == '.' || a == '$')
		{
			length = 1;
		}
		else
		{
			j = p;
			i = 0;
			length = 0;
			while (isdigit(a))
			{
				i = i * 10 + a - '0';
				length++;
				j++;
				a = input[j];
			}
			a = 'n';
		}
		if (a == 'n')
		{
			temp = "num";
		}
		else
		{
			temp += a;
		}
		k = findVT(VT, temp);
		if (Action[S][k][0]=='S')//�ƽ�
		{
			top++;
			if (temp=="num")
			{
				Value[top] = i;
			}
			else
			{
				Value[top] = 0;
			}
			State[top] = SToI(Action[S][k].substr(1));
			p += length;
			cout << "Shift " << Action[S][k].substr(1) << endl << endl;
			temp.clear();
		}
		else if (Action[S][k][0]=='R')//��Լ
		{
			int r = SToI(Action[S][k].substr(1));//�����Ҫ��Լ�Ĳ���ʽ���
			int count = 0;//���ڼ���A->|b|��|b|�ĳ���
			//��Լǰ���з��붯��
			Translate(r);
			string str;
			str = P[r - 1].substr(3);//��ȡ����ʽ��ͷ�Ҳ�����
			int sp=0;
			string temp1;
			while (sp<str.length())
			{
				temp1 += str[sp];
				if (findVN(VN,str[sp])>=0||findVT(VT,temp1)>=0)
				{
					count++;
					temp1.clear();
				}
				sp++;
			}
			temp1.clear();
			//ջ������|b|��Ԫ��
			for (int m = 0; m < count; m++)
			{
				top--;
			}
			//_SΪ��ǰջ������
			int _S = State[top];
			string A = P[r - 1].substr(0, 1);
			//��Goto[_S,A]ѹ��Stateջ��
			int vn = findVN(VN, A[0]);
			top++;
			State[top] = Goto[_S][vn];
			//���A->b
			cout << "reduce by: " << P[r - 1] << "\tval:" << Value[top];
			cout << "\ttype: ";
			Type[top] == 1? cout << "integer" : cout << "real";
			cout << endl << endl;
		}
		else if (Action[S][k][0]=='A')
		{
			cout << "ACC" << "\t���ʽ��ֵ��" << Value[top];
			cout << "\t���ʽ�����ͣ�";
			Type[top] == 1 ? cout << "integer" : cout << "real";
			cout << endl;
			break;
		}
		else if (Action[S][k][0]=='e')
		{
			cout << "error :" << endl;
			if (Action[S][k][1]=='1')
			{//״̬ 0 4 6 7 8 9�ڴ�(����num��ȴ����Ϊ+ - * /��$
		     //ȱ��������󣬰Ѽ����numѹ��ջ����ת�Ƶ�״̬5
				top++;
				Value[top] = 0;
				State[top] = 5;
				cout << "ȱ��������󣬽�numѹ��ջ" << endl;
			}
			else if (Action[S][k][1] == '2')
			{//״̬0 1 2 4 6 7 8 9�ڴ�����������ַ���������ţ�ȴ����Ϊ)
			 //���Ų�ƥ�䣬ֱ���Թ�
				p++;
				cout << "���Ų�ƥ�䣬ָ����һ������" << endl;
			}
			else if (Action[S][k][1] == '3')
			{//״̬ 1 2 10 11 12�ڴ��������Ϊ������Ż�)ȴ���루����num
			 //ȱ����������������"+"ѹ��ջ����ת�Ƶ�״̬6
				Value[top] = '+';
				State[top] = 6;
				cout << "ȱ��������ţ���+ѹ��ջ" << endl;
			}
			else if (Action[S][k][1] == '4')
			{//״̬10�ڴ��������Ϊ������Ż�)ȴ����$
			 //ȱ�������ţ��������")"ѹ��ջ����ת�Ƶ�״̬16
				Value[top] = ')';
				State[top] = 16;
				cout << "ȱ�������ţ���)ѹ��ջ" << endl;
			}
			else if (Action[S][k][1] == '5')
			{//״̬1 10�ڴ�����+��-ȴ����*��/
			 //������Ŵ��󣬽������"+"ѹ��ջ����ת�Ƶ�״̬6
				Value[top] = '+';
				State[top] = 6;
				cout << "ȱ��+/-����+ѹ��ջ" << endl;
			}
			else if (Action[S][k][1] == '6')
			{//��Լʱ����Ϊ(����num��ֱ������
				p++;
				cout << "��Լʱ����������ţ�ֱ������" << endl;
			}
			else
			{//С�����ȱ���֣���0���õ�С����󣬲�ת�Ƶ�״̬17
				top++;
				Value[top] = 0;
				State[top] = 17;
				cout << "С�����ȱ����" << endl;
			}
			cout << endl;
		}
	}
	return;
}
void main() {
	Input_P();
	LR();
	system("pause");
}