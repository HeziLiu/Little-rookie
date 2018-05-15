#include<iostream>
#include<vector>
#include<sstream>
#include<stack>
#include<string>
using namespace std;

vector<char> VN;	//���ս����
vector<string> VT;	//�ս����
vector<string> P;	//����ʽ��
stack<int> State;	//״̬ջ
stack<string> Symbol;//����ջ
//Action��
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
//goto��
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
//�ַ�����ת��Ϊ�ַ�������
string charToString(char c) {
	string s;
	stringstream stream;
	stream << c;
	s = stream.str();
	return s;
}
//��λ���ս��
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
//��λ�ս��
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
//�����ս����
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
//�������ʽ
void inputP() {
	string s;
	string temp;//��ʱ�ַ�������
	string vt;	//�ս���ַ���
	cout << "�������ʽ��������ok��ʾ���������" << endl;
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
		cout << "�������ʽ��������ok��ʾ���������" << endl;
		getline(cin, s);
	}
	vector<char>::iterator it_n;	//������ս��������
	vector<string>::iterator it;  //����ս���Ͳ���ʽ������
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
//���ջ������(flagΪ0���״̬ջ��1�������ջ)
void output(int flag) {
	//��������ջ��ת���ջ�е�����
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
	cout << "������Ŵ���" << endl;
	getline(cin, input);
	cout << endl;
	input += "$";
	State.push(0);//��ʼ��״̬ջ
	int i = 0;
	int j;
	int S;       //SΪ״̬ջջ��Ԫ��
	string temp;
	VT.push_back("$");
	cout << "��ʼ����..." << endl;
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
		cout << '\t' << "���룺" << input.substr(i) << '\t' << "����������";
		//�ƽ�
		if (Action[S][findVT(VT,temp)][0]=='S')
		{
			State.push(Action[S][findVT(VT, temp)][1]-48);
			Symbol.push(temp);//�ƽ�����
			cout << "Shift " << Action[S][findVT(VT, temp)][1] << endl << endl;
			temp.clear();
			i = j;//ָ�����봮����һ������
		}
		//��Լ
		else if (Action[S][findVT(VT,temp)][0]=='R')
		{
			int r = Action[S][findVT(VT, temp)][1] - 48;//ȡ�ù�Լ����ʽ���±�
			int p_length = 0;							//���ڴ�ȡ����ʽ��ͷ��ĳ���
			string str;
			str = P[r - 1].substr(3);					//�Ӽ�ͷ֮��ʼ����
			int c = 0;
			string t1;									//����A->���� |��|����
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
			for (int i = 0; i < p_length; i++)			//��ջ������|��|���������p_length��������
			{
				State.pop();
				Symbol.pop();
			}
			int n_S = State.top();			//n_SΪ�µ�ջ��
			string A = P[r - 1].substr(0, 1);//��Լ���Aѹ��Symbolջ��
			Symbol.push(A);
			State.push(Goto[n_S][findVN(VN, A[0])]);//��λA�ڷ��ս�����е�λ�ý�����GOTO����һ״̬ѹջ
			cout << "reduce by " << P[r - 1] << endl << endl;
		}
		//����
		else if (Action[S][findVT(VT,temp)][0]=='A')
		{
			cout << "ACC" << endl;
			break;
		}
		//������
		else if (Action[S][findVT(VT, temp)][0] == 'e')
		{
			cout << "error: " << endl;
			if (Action[S][findVT(VT, temp)][1]=='1')
			{//״̬ 0 6 7 8 9�ڴ��������Ϊ�����������ַ�ȴ����+��-��*��/��$
			 //ȱ��������󣬰Ѽ����numѹ��ջ����ת�Ƶ�״̬3
				Symbol.push("num");
				State.push(5);
				cout << "ȱ��������󣬽�numѹ��ջ" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '2')
			{//״̬0 1 4 6 7 8 9�ڴ��������Ϊ�������ȴ����)
			 //���Ų�ƥ�䣬ֱ���Թ�
				i++;
				cout << "���Ų�ƥ�䣬ָ����һ������" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '3')
			{//״̬ 1 2 10 11 12�ڴ��������Ϊ������Ż�)ȴ���루����num
			 //ȱ����������������"+"ѹ��ջ����ת�Ƶ�״̬6
				Symbol.push("+");
				State.push(6);
				cout << "ȱ��������ţ���+ѹ��ջ" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '4')
			{//״̬10�ڴ��������Ϊ������Ż�)ȴ����$
			 //ȱ�������ţ��������")"ѹ��ջ����ת�Ƶ�״̬15
				Symbol.push(")");
				State.push(15);
				cout << "ȱ�������ţ�����ѹ��ջ" << endl;
			}
			else if (Action[S][findVT(VT, temp)][1] == '5')
			{//״̬1 10�ڴ�����+��-ȴ����*��/
			 //������Ŵ��󣬽������"+"ѹ��ջ����ת�Ƶ�״̬6
				Symbol.push("+");
				State.push(6);
				cout << "ȱ��+/-����+ѹ��ջ" << endl;
			}
			else
			{//��Լ����ʽʱ�������Ϊ������num,ֱ��������ط���
				if (temp == "(")
					i++;
				else
					i = i + 3;
				cout << "��Լ����������ţ�ֱ������" << endl;
			}
		}
		temp.clear();
	}
	cout << endl;
	cout << "...��������..." << endl;
}
void main() {
	inputP();
	function();
	system("pause");
	return;
}