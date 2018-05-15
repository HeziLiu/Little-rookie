#include<iostream>
#include<cmath>
#include<vector>
#include<sstream>
#include<string>
using namespace std;

vector<char> VN;	//非终结符集合
vector<string> VT;	//终结符集合
vector<string>P;	//产生式集合
int State[50];		//状态栈
double Value[50];   //数值栈
int Type[50];       //数值类型，为1则为integer,为2则为real
int top = 0;        //栈顶指针

//初始化Action表
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

//初始化Goto表
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

//输入产生式
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

//将string型转换为int型
int SToI(string str) {
	int num;
	stringstream stream;
	stream << str;
	stream >> num;
	return num;
}

//定位VN集中的非终结符，返回其下标
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

//定位VT集中的终结符，返回其下标
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

//翻译方案
void Translate(int number) {
	int i; double j;//临时变量
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
			//不能够整除则为real
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
		//计算小数有几位
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

//输出状态栈0和数值栈1
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
	int i, j, k;	//临时变量
	int p = 0;		//输入串下标
	int length = 0; //输入字符长度
	int S;			//状态栈栈顶内容
	char a;			//依次分析输入串各个字符
	string temp;
	VT.push_back("$");
	string input;
	cout << "请输入要分析的符号串：" << endl;
	cin >> input;
	cout << endl;
	input += "$";
	State[top] = 0;//置状态栈栈顶为0
	cout << "开始分析：" << endl;
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
		cout << endl << "输入：" << input.substr(p) <<"\t"<< "分析动作：";
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
		if (Action[S][k][0]=='S')//移进
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
		else if (Action[S][k][0]=='R')//归约
		{
			int r = SToI(Action[S][k].substr(1));//获得需要归约的产生式序号
			int count = 0;//用于计算A->|b|中|b|的长度
			//归约前进行翻译动作
			Translate(r);
			string str;
			str = P[r - 1].substr(3);//提取产生式箭头右部部分
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
			//栈顶弹出|b|个元素
			for (int m = 0; m < count; m++)
			{
				top--;
			}
			//_S为当前栈顶内容
			int _S = State[top];
			string A = P[r - 1].substr(0, 1);
			//将Goto[_S,A]压入State栈中
			int vn = findVN(VN, A[0]);
			top++;
			State[top] = Goto[_S][vn];
			//输出A->b
			cout << "reduce by: " << P[r - 1] << "\tval:" << Value[top];
			cout << "\ttype: ";
			Type[top] == 1? cout << "integer" : cout << "real";
			cout << endl << endl;
		}
		else if (Action[S][k][0]=='A')
		{
			cout << "ACC" << "\t表达式的值：" << Value[top];
			cout << "\t表达式的类型：";
			Type[top] == 1 ? cout << "integer" : cout << "real";
			cout << endl;
			break;
		}
		else if (Action[S][k][0]=='e')
		{
			cout << "error :" << endl;
			if (Action[S][k][1]=='1')
			{//状态 0 4 6 7 8 9期待(或者num，却输入为+ - * /或$
		     //缺少运算对象，把假想的num压入栈，并转移到状态5
				top++;
				Value[top] = 0;
				State[top] = 5;
				cout << "缺少运算对象，将num压入栈" << endl;
			}
			else if (Action[S][k][1] == '2')
			{//状态0 1 2 4 6 7 8 9期待运算对象首字符或运算符号，却输入为)
			 //括号不匹配，直接略过
				p++;
				cout << "括号不匹配，指向下一个符号" << endl;
			}
			else if (Action[S][k][1] == '3')
			{//状态 1 2 10 11 12期待输入符号为运算符号或)却输入（或者num
			 //缺少运算符，将假想的"+"压入栈，并转移到状态6
				Value[top] = '+';
				State[top] = 6;
				cout << "缺少运算符号，将+压入栈" << endl;
			}
			else if (Action[S][k][1] == '4')
			{//状态10期待输入符号为运算符号或)却输入$
			 //缺少右括号，将假想的")"压入栈，并转移到状态16
				Value[top] = ')';
				State[top] = 16;
				cout << "缺少右括号，将)压入栈" << endl;
			}
			else if (Action[S][k][1] == '5')
			{//状态1 10期待输入+或-却输入*或/
			 //运算符号错误，将假想的"+"压入栈，并转移到状态6
				Value[top] = '+';
				State[top] = 6;
				cout << "缺少+/-，将+压入栈" << endl;
			}
			else if (Action[S][k][1] == '6')
			{//归约时输入为(或者num，直接跳过
				p++;
				cout << "归约时产生多余符号，直接跳过" << endl;
			}
			else
			{//小数点后缺数字，将0放置到小数点后，并转移到状态17
				top++;
				Value[top] = 0;
				State[top] = 17;
				cout << "小数点后缺数字" << endl;
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