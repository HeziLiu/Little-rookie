<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="db.DBConn" %>
<%@ page import="db.Member" %>
<%!//声明函数check()
	public static boolean check(String id,String value){
		boolean b=false;
		String regex="";	//匹配模式字符串：regex
		if(id.equals("username")){//如果是用户名，设置regex的值
			regex="[0-9A-Za-z_]{3,22}";
		}else if(id.equals("password")){//如果是口令，设置regex的值
			regex="[0-9A-Za-z_]{5,22}";
		}
		b=java.util.regex.Pattern.compile(regex).matches(regex,value);//value匹配regex的结果
		return b;
	}
%>
<%
	//获取各项参数值
	String username=request.getParameter("u");//用户名
	String password=request.getParameter("p");//用户密码
	//验证用户名，三种情况：NULL;空;模式不匹配，需要重新填写
	if(username==null||"".equals(username)||check("username",username)==false){
		out.println("用户名错误，请重新填写！");return;
	}
	//验证密码
	if(password==null||"".equals(password)||check("password",password)==false){
		out.println("密码错误，请重新填写！");return;
	}
	DBConn db=new DBConn();
	//验证用户名是否已经被注册
	if(db.checkUsername(username)!=true){
		out.println("用户名不存在，请重试！");return;
	}
	boolean b=db.checkPassword(username, password);
	if(b==true){
		session.setAttribute("uname",username);System.out.println("AAAAAAAAAAAAAA");
		out.print("ac");
	}else{
		out.print("登录失败，请重试！");
	}
 %>
