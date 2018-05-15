#include<windows.h>
#include<stdio.h>
#include<sql.h>
#include<stdlib.h>
#include<sqlext.h>
#include<string.h>
#include<iostream>
#include<assert.h>
using namespace std;

struct student {
	char *sno = new char[10];
	char *sname = new char[30];
	char *sex = new char[30];
	char *bdate = new char[30];
	char *dept = new char[30];
	char *classno = new char[30];
};
struct course{
	char *cno = new char[5];
	char *cname = new char[20];
	char *lhour = new char[5];
	char *credit = new char[5];
	char *semester = new char[5];
};
struct sc{
	char *sno = new char[10];
	char *cno = new char[5];
	char *grade = new char[5];
};
SQLHENV henv;			//初始化环境句柄
SQLHDBC hdbc;			//初始化数据源
SQLHSTMT hstmt;			//初始化语句句柄
SQLRETURN retcode;		//初始化返回错误代码
char input[100];		//用户输入
bool flag;				//操作正确与否标志

void select_course() {
	retcode = SQLAllocHandle(SQL_HANDLE_STMT, hdbc, &hstmt);
	retcode = SQLSetStmtAttr(hstmt, SQL_ATTR_ROW_BIND_TYPE, (SQLPOINTER)SQL_BIND_BY_COLUMN, SQL_IS_INTEGER);
	retcode = SQLExecDirect(hstmt, (SQLCHAR *)"select * from course;", SQL_NTS);
	if (retcode == SQL_SUCCESS || retcode == SQL_SUCCESS_WITH_INFO)
	{
		cout << "Query OK(0.00sec)" << endl;
	}
	struct course course;
	cout << "================================================" << endl;
	cout << "cno\tcname\t\tlhour\tcredit\tsemester" << endl;
	long columnlen;
	SQLBindCol(hstmt, 1, SQL_CHAR, course.cno, 5, &columnlen);
	SQLBindCol(hstmt, 2, SQL_CHAR, course.cname, 20, &columnlen);
	SQLBindCol(hstmt, 3, SQL_CHAR, course.lhour, 5, &columnlen);
	SQLBindCol(hstmt, 4, SQL_CHAR, course.credit, 5, &columnlen);
	SQLBindCol(hstmt, 5, SQL_CHAR, course.semester, 5, &columnlen);
	if (retcode<0)
	{
		cout << "没有执行语句" << endl;
	}
	retcode = SQLFetch(hstmt);
	while (retcode == SQL_ROW_SUCCESS || retcode == SQL_ROW_SUCCESS_WITH_INFO)
	{
		if (retcode == SQL_ROW_SUCCESS || retcode == SQL_ROW_SUCCESS_WITH_INFO) {
			printf("%s\t%s\t%s\t%s\t%s\n", course.cno, course.cname, course.lhour, course.credit, course.semester);
			retcode = SQLFetch(hstmt);
		}
	}
	cout << "================================================" << endl;
}

void select_student() {
	retcode = SQLAllocHandle(SQL_HANDLE_STMT, hdbc, &hstmt);
	retcode = SQLSetStmtAttr(hstmt, SQL_ATTR_ROW_BIND_TYPE, (SQLPOINTER)SQL_BIND_BY_COLUMN, SQL_IS_INTEGER);
	retcode = SQLExecDirect(hstmt, (SQLCHAR *)"select * from student;", SQL_NTS);
	if (retcode == SQL_SUCCESS || retcode == SQL_SUCCESS_WITH_INFO)
	{
		cout << "Query OK(0.00sec)" << endl;
	}
	struct student student;
	cout << "=============================================================" << endl;
	cout << "sno\tsname\tsex\tbdate\t\t\tdept\tclassno" << endl;
	long columnlen;
	SQLBindCol(hstmt, 1, SQL_CHAR, student.sno, 10, &columnlen);
	SQLBindCol(hstmt, 2, SQL_CHAR, student.sname, 30, &columnlen);
	SQLBindCol(hstmt, 3, SQL_CHAR, student.sex, 30, &columnlen);
	SQLBindCol(hstmt, 4, SQL_CHAR, student.bdate, 30, &columnlen);
	SQLBindCol(hstmt, 5, SQL_CHAR, student.dept, 30, &columnlen);
	SQLBindCol(hstmt, 6, SQL_CHAR, student.classno, 30, &columnlen);
	if (retcode<0)
	{
		cout << "没有执行语句" << endl;
	}
	retcode = SQLFetch(hstmt);
	while (retcode == SQL_ROW_SUCCESS || retcode == SQL_ROW_SUCCESS_WITH_INFO)
	{
		if (retcode == SQL_ROW_SUCCESS || retcode == SQL_ROW_SUCCESS_WITH_INFO) {
			printf("%s\t%s\t%s\t%s\t%s\t%s\n", student.sno, student.sname, student.sex, student.bdate, student.dept, student.classno);
			retcode = SQLFetch(hstmt);
		}
	}
	cout << "=============================================================" << endl;
}

void select_sc() {
	retcode = SQLAllocHandle(SQL_HANDLE_STMT, hdbc, &hstmt);
	retcode = SQLSetStmtAttr(hstmt, SQL_ATTR_ROW_BIND_TYPE, (SQLPOINTER)SQL_BIND_BY_COLUMN, SQL_IS_INTEGER);
	retcode = SQLExecDirect(hstmt, (SQLCHAR *)"select * from sc;", SQL_NTS);
	if (retcode == SQL_SUCCESS || retcode == SQL_SUCCESS_WITH_INFO)
	{
		cout << "Query OK(0.00sec)" << endl;
	}
	struct sc sc;
	cout << "=====================" << endl;
	cout << "sno\tcno\tgrade" << endl;
	long columnlen;
	SQLBindCol(hstmt, 1, SQL_CHAR, sc.sno, 10, &columnlen);
	SQLBindCol(hstmt, 2, SQL_CHAR, sc.cno, 5, &columnlen);
	SQLBindCol(hstmt, 3, SQL_CHAR, sc.grade, 5, &columnlen);
	if (retcode<0)
	{
		cout << "没有执行语句" << endl;
	}
	retcode = SQLFetch(hstmt);
	while (retcode == SQL_ROW_SUCCESS || retcode == SQL_ROW_SUCCESS_WITH_INFO)
	{
		if (retcode == SQL_ROW_SUCCESS || retcode == SQL_ROW_SUCCESS_WITH_INFO) {
			printf("%s\t%s\t%s\n", sc.sno, sc.cno, sc.grade);
			retcode = SQLFetch(hstmt);
		}
	}
	cout << "==================" << endl;
}

void operation() {
	retcode = SQLAllocHandle(SQL_HANDLE_STMT, hdbc, &hstmt);
	retcode = SQLSetStmtAttr(hstmt, SQL_ATTR_ROW_BIND_TYPE, (SQLPOINTER)SQL_BIND_BY_COLUMN, SQL_IS_INTEGER);
	retcode = SQLPrepare(hstmt, (SQLCHAR*)input, SQL_NTS);
	retcode = SQLExecute(hstmt);
	if (retcode == SQL_SUCCESS || retcode == SQL_SUCCESS_WITH_INFO)
		cout << "operation succeed!" << endl;
	else
		cout << "operation failed!" << endl;
}

void check_result(char c) {
	switch (c)
	{
	case't':
		select_student();
		break;
	case'o':
		select_course();
		break;
	case'c':
		select_sc();
		break;
	default:
		break;
	}
}

int main() {
	retcode = SQLAllocHandle(SQL_HANDLE_ENV, NULL, &henv);//分配环境句柄
	//retcode<0分配失败
	assert(retcode == SQL_SUCCESS || retcode == SQL_SUCCESS_WITH_INFO);	
	//设置将使用的ODBC版本
	retcode = SQLSetEnvAttr(henv, SQL_ATTR_ODBC_VERSION, (void*)SQL_OV_ODBC3, SQL_IS_INTEGER);
	//分配数据源句柄
	retcode = SQLAllocHandle(SQL_HANDLE_DBC, henv, &hdbc);
	//建立连接
	retcode = SQLConnect(hdbc, (SQLCHAR *)"myodbc", SQL_NTS, (SQLCHAR *)"root", SQL_NTS, (SQLCHAR *)"962464", SQL_NTSL);
	//判断连接是否成功
	if (retcode == SQL_SUCCESS || retcode == SQL_SUCCESS_WITH_INFO)
	{
		cout << "success connection" << endl;
	}
	else
	{
		cout << "fail connection" << endl;
	}
	while (1) {
		cout << "mysql>";
		cin.getline(input, 100);
		switch (input[0])
		{
		case's'://查询
			switch (input[15])//from 之后第二个字母 sc(c) student(t) course(o)
			{
			case't'://查询student表
				select_student();
				break;
			case'o'://查询course表
				select_course();
				break;
			case'c'://查询sc表
				select_sc();
				break;
			default:
				cout << "Error: table does not exist! " << endl;
				break;
			}
			break;
		case 'i'://插入
			operation();
			if(retcode==0||retcode==1)
				check_result(input[13]);
			break;
		case 'u'://修改
			operation();
			if (retcode == 0 || retcode == 1)
				check_result(input[8]);
			break;
		case 'd'://删除
			operation();
			if(retcode == 0 || retcode == 1)
				check_result(input[13]);
			break;
		case 'e'://退出
			cout << "Bye~" << endl;
			goto out;
			break;
		default:
			break;
		}
	}
	out:SQLFreeHandle(SQL_HANDLE_STMT, hstmt);
	SQLDisconnect(hdbc);
	SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
	SQLFreeHandle(SQL_HANDLE_ENV, henv);
	system("pause");
	return 0;
}