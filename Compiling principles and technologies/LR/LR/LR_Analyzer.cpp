#include<iostream>
#include<vector>
#include<sstream>
#include<stack>
#include<string>
using namespace std;

vector<char> VN;	//非终结符表
vector<string> VT;	//终结符表
vector<string> P;	//产生式表
stack<int> State;	//状态栈
stack<string> Symbol;//符号栈
//Action表
string Action[16][8] = {
	{ "e1","e1","e1","e1","S4","e2","S5","e1" },
	{ "S6","S7","e5","e5","e3","e2","e3","ACC" },
	{ "R3","R3","S8","S9","e3","R3","e3","R3" },
	{ "R6","R6","R6","R6","e6","R6","e6","R6" },
	{ "e1","e1","e1","e1","S4","e2","S5","e1" },
	{ "R8","R8","R8","R8","e6","R8","e6","R8" },
	{ "e1","e1","e1","e1","S4","e2","S5","e1" },
	{ "e1","e1","e1","e1","S4","e2","S5","e1" },
	{ "e1","e1","e1","e1","S4","e2","S5","e1" },
	{ "e1","e1","e1","e1","S4","e2","S5","e1" },
	{ "S6","S7","e5","e5","e3","S15","e3","e4" },
	{ "R1","R1","S8","S9","e3","R1","e3","R1" },
	{ "R2","R2","S8","S9","e3","R2","e3","R2" },
	{ "R4","R4","R4","R4","e6","R4","e6","R4" },
	{ "R5","R5","R5","R5","e6","R5","e6","R5" },
	{ "R7","R7","R7","R7","e6","R7","e6","R7" }
};
//goto表
int Goto[16][3] = {
	{ 1,2,3 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ 10,2,3 },
	{ -1,-1,-1 },
	{ -1,11,3 },
	{ -1,12,3 },
	{ -1,-1,13 },
	{ -1,-1,14 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
	{ -1,-1,-1 },
};
//字符类型转换为字符串类型
string charToString(char c) {
	string s;
	stringstream stream;
	stream << c;
	s = stream.str();
	return s;
}
//定位非终结符
int findVN(vector<char> _VN, char c) {
	vector<char>::iterator it;
	for ( it = _VN.begin(); it != _VN.end(); it++)
	{
		if ((*it)==c)
		{
			return it - _VN.begin();
		}
	}
	return -1;
}
//定位终结符
int findVT(vector<string> _VT, string s) {
	vector<string>::iterator it;
	for ( it = _VT.begin(); it != _VT.end(); it++)
	{
		if ((*it)==s)
		{
			return it - _VT.begin();
		}
	}
	return -1;
}
//加入终结符表
void addToVT(string s) {
	if (s.length() > 0) {
		if (s=="num")
		{
			if (findVT(VT, s) < 0) {
				VT.push_back(s);
			}
		}
		else
		{
			for (int j = 0; j < s.length(); j++)
			{
				if (findVT(VT,charToString(s[j]))<0)
				{
					VT.push_back(charToString(s[j]));
				}
			}
		}
	}
}
//输入产生式
void inputP() {
	string s;
	string temp;//临时字符串变量
	string vt;	//终结符字符串
	cout << "输入产生式：（键入ok表示输入结束）" << endl;
	getline(cin, s);
	while (s!="ok")
	{
		temp.clear();
		vt.clear();
		if (s.length()>1)
		{
			for (int i = 0; i < s.length(); i++) {
				if (s[i]>='A'&&s[i]<='Z')
				{
					if (findVN(VN, s[i]) < 0) {
						VN.push_back(s[i]);
					}
					if (i!=0)
					{
						temp += s[i];
						addToVT(vt);
						vt.clear();
					}
				}
				else if ((s[i]=='-'&&s[i+1]=='>')||(s[i]=='>'&&s[i-1]=='-'))
				{
					continue;
				}
				else if (s[i]=='|')
				{
					addToVT(vt);
					vt.clear();
					string j = "->";
					P.push_back(s[0] + j + temp);
					temp.clear();
				}
				else
				{
					vt += s[i];
					temp += s[i];
				}
				if (i==s.length()-1)
				{
					addToVT(vt);
					vt.clear();
					string j = "->";
					P.push_back(s[0] + j + temp);
					temp.clear();
				}
			}
		}
		cout << "输入产生式：（键入ok表示输入结束）" << endl;
		getline(cin, s);
	}
	vector<char>::iterator it_n;	//输出非终结符迭代器
	vector<string>::iterator it;  //输出终结符和产生式迭代器
	cout << "VN:";
	for ( it_n = VN.begin(); it_n != VN.end(); it_n++)
	{
		cout << *(it_n) << " ";
	}
	cout << endl;
	cout << "VT:";
	for ( it = VT.begin(); it !=VT.end() ; it++)
	{
		cout << *it << " ";
	}
	cout << endl;
	for ( it = P.begin(); it != P.end(); it++)
	{
		cout << *it << endl;
	}
	cout << endl;
}
//输出栈中内容(flag为0输出状态栈，1输出符号栈)
void output(int flag) {
	//利用两个栈反转输出栈中的内容
	stack<int> _state;
	stack<string> _symbol;
	int t_state;
	string t_symbol;
	if (flag==0)
	{
		while (!State.empty())
		{
			t_state = State.top();
			State.pop();
			_state.push(t_state);
		}
		while (!_state.empty())
		{
			t_state = _state.top();
			cout << t_state << " ";
			_state.pop();
			State.push(t_state);
		}
	}
	else
	{
		while (!Symbol.empty())
		{
			t_symbol = Symbol.top();
			Symbol.pop();
			_symbol.push(t_symbol);
		}
		while (!_symbol.empty())
		{
			t_symbol = _symbol.top();
			cout << t_symbol << " ";
			_symbol.pop();
			Symbol.push(t_symbol);
		}
	}
}

void function() {
	string input;
	cout << "输入符号串：" << endl;
	getline(cin, input);
	cout << endl;
	input += "$";
	State.push(0);//初始化状态栈
	int i = 0;
	int j;
	int S;       //S为状态栈栈顶元素
	string temp;
	VT.push_back("$");
	cout << "开始分析..." << endl;
	while (true)
	{
		S = State.top();
		j = i;
		while (findVT(VT,temp)<0)
		{
			temp += input[j];
			j++;
		}
		cout << "State: ";
		output(0);
		cout << endl;
		cout << "Symbol: ";
		output(1);
		cout << '\t' << "输入：" << input.substr(i) << '\t' << "分析动作：";
		//移进
		if (Action[S][findVT(VT,temp)][0]=='S')
		{
			State.push(Action[S][findVT(VT, temp)][1]-48);
			Symbol.push(temp);//移进符号
			cout << "Shift " << Action[S][findVT(VT, temp)][1] << endl << endl;
			temp.clear();
			i = j;//指向输入串的下一个符号
		}
		//归约
		else if (Action[S][findVT(VT,temp)][0]=='R')
		{
			int r = Action[S][findVT(VT, temp)][1] - 48;//取得归约产生式的下标
			int p_length = 0;							//用于存取产生式箭头后的长度
			string str;
			str = P[r - 1].substr(3);					//从箭头之后开始计数
			int c = 0;
			string t1;									//计算A->β中 |β|长度
			while (c < str.length())
			{
				t1 += str[c];
				if (findVN(VN,str[c])>=0||findVT(VT,t1)>=0)
				{
					p_length++;
					t1.clear();
				}
				c++;
			}
			t1.clear();
			for (int i = 0; i < p_length; i++)			//从栈顶弹出|β|（即这里的p_length）个符号
			{
				State.pop();
				Symbol.pop();
			}
			int n_S = State.top();			//n_S为新的栈顶
			string A = P[r - 1].substr(0, 1);//归约后的A压入Symbol栈中
			Symbol.push(A);
			State.push(Goto[n_S][findVN(VN, A[0])]);//定位A在非终结符表中的位置进而查GOTO表将下一状态压栈
			cout << "reduce by " << P[r - 1] << endl << endl;
		}
		//接受
		else if (Action[S][findVT(VT,temp)][0]=='A')
		{
			cout << "ACC" << endl;
			break;
		}
		//错误处理
		else if (Action[S][findVT(VT, temp)][0] == 'e')
		{
			cout << "error: " << endl;
			if (Action[S][findVT(VT, temp)][1]=='1')
			{//状态 0 6 7 8 9期待输入符号为运算对象的首字符却输入+、-、*、/和$
			 //缺少运算对象，把假想的num压入栈，并转移到状态3
				Symbol.push("num");
				State.push(5);
				cout << "缺少运算对象，将num压入栈" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '2')
			{//状态0 1 4 6 7 8 9期待输入符号为运算符号却输入)
			 //括号不匹配，直接略过
				i++;
				cout << "括号不匹配，指向下一个符号" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '3')
			{//状态 1 2 10 11 12期待输入符号为运算符号或)却输入（或者num
			 //缺少运算符，将假想的"+"压入栈，并转移到状态6
				Symbol.push("+");
				State.push(6);
				cout << "缺少运算符号，将+压入栈" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '4')
			{//状态10期待输入符号为运算符号或)却输入$
			 //缺少右括号，将假想的")"压入栈，并转移到状态15
				Symbol.push(")");
				State.push(15);
				cout << "缺少有括号，将）压入栈" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '5')
			{//状态1 10期待输入+或-却输入*或/
			 //运算符号错误，将假想的"+"压入栈，并转移到状态6
				Symbol.push("+");
				State.push(6);
				cout << "缺少+/-，将+压入栈" << endl;
			}
			else
			{//归约产生式时输入符号为（或者num,直接跳过相关符号
				if (temp == "(")
					i++;
				else
					i = i + 3;
				cout << "归约产生多余符号，直接跳过" << endl;
			}
		}
		temp.clear();
	}
	cout << endl;
	cout << "...分析结束..." << endl;
}
void main() {
	inputP();
	function();
	system("pause");
	return;
}