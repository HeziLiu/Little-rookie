<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="db.DBConn" %>
<%@ page import="db.Member" %>
  <%
 
  request.setCharacterEncoding("UTF-8");
  String name=request.getParameter("username");
  String password=request.getParameter("password");
  String sex=request.getParameter("sex");
  String grade=request.getParameter("grade");
  String tel=request.getParameter("tel");
  String comment=request.getParameter("comment");
  String interest[]=request.getParameterValues("chk");
  String ins="";
  for(int i=0;i<interest.length;i++){
  	ins=ins+interest[i]+" ";
  }
  
  DBConn db=new DBConn();
  //验证用户是否已经被注册
  /* if(db.checkUsername(name)==true){
  	out.println("用户名已存在，请重新输入");
  	return;
  }else{ */
  	db.exeSQL("insert into member(username,password,sex,grade,tel,interest,comment)values('"+name+"','"+password+"','"+sex+"','"+grade+"','"+tel+"','"+ins+"','"+comment+"')");
 //插入用户数据
   %>
   <body onLoad="back()">
   <br>
   <p style="text-align:center">注册成功，返回：<a href=index.jsp>主页</a></p>
   </body>
<script type="text/javascript">
function back(){
	setTimeout("window.location='index.jsp'",2000);
}
</script>