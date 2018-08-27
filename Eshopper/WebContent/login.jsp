<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<%@include file="./include/head.jsp"  %>

<body>
	<%@include file="./include/header.jsp"  %>

<section id="form"><!--form-->
		<div class="container">
			<div class="row">
				<div class="col-sm-4 col-sm-offset-1">
					<div class="login-form"><!--login form-->
						<h2>Login to your account</h2>	
				<form  id="loginform" name="loginform" action="loginservlet1" method="post">
						<fieldset>
						    <div class="form-group">
							<input  class="form-control" placeholder="Name" name="username" id="username"/>
						    </div>
						    
						    <div class="form-group">
						  	<input type="password" class="form-control" placeholder="Password" name="upassword"/>
							</div>							
							<div>
							<div class="form-group">
							<button type="submit" class="btn " value="提交">login</button>
							</div>
							</div>
						</fieldset>
						</form>		
			
					</div><!--/login form-->
				</div>
				<div class="col-sm-1">
					<h2 class="or">OR</h2>
				</div>
				<div class="col-sm-4">
					<div class="signup-form"><!--sign up form-->
						<h2>New User Signup!</h2>
					<form  id="signform" name="signform" action="registerservlet" method="post">
						<fieldset>
						    <div class="form-group">
							<input type="text" class="form-control" placeholder="Name" name="uname"/>
						    </div>
						    
						    <div class="form-group">
						  	<input type="password" class="form-control" placeholder="Password" name="upwd"/>
							</div>
							
							<div class="form-group">
							<input type="password"  class="form-control" placeholder="resurePassword" name="reupwd"/>
							</div>
							
							<div class="form-group">
							<input type="email"  class="form-control" placeholder="Email Address" name="uemail"/>
							</div>
							
							<div>
							<div class="form-group">
							<button type="submit" class="btn " value="提交">Signup</button>
							</div>
							</div>
						</fieldset>
						</form>
					</div><!--/sign up form-->
				</div>
			</div>
		</div>
	</section><!--/form-->


	<%@include file="./include/footer.jsp"  %>
	<%@include file="./include/script.jsp"  %>
    <!--表单验证-->
<script type="text/javascript">
$(document).ready(function(){
		$('#signform').bootstrapValidator({
			message:'This value is not valid',
			feedbackIcons:{
				valid:'glyphicon glyphicon-ok',
				invalid:'glyphicon glyphicon-remove',
				validating:'glyphicon glyphicon-refresh'
			},
			fields:{
				uname:{
					validators:{
						notEmpty:{
							message:'用户名不能为空'
						},
						stringLength:{
							min:3,
							max:22,
							message:'用户名长度为3-22'
						},
						regexp:{
							regexp:/^[0-9A-Za-z_]+$/,
							message:'用户名由字母，数字及_组成'
						}, 
				
						different:{
							field:'upwd',
							message:'用户名和密码不能相同'
							}
					}
				},
				upwd:{
					validators:{
						notEmpty:{
							message:'密码不能为空'
						},
						stringLength:{
							min:5,
							max:22,
							message:'密码长度为5-22'
						},
						 regexp:{
							regexp:/^[0-9A-Za-z_]+$/,
							message:'密码由字母，数字及_组成'
						}, 
						identical:{
							field:'reupwd',
							message:'密码输入不一致'
						},
						different:{
							field:'uname',
							message:'密码不能与用户名相同'
						}
					}
				},
				reupwd:{
					validators:{
						notEmpty:{
							message:'确认密码不能为空'
						},
						identical:{
							field:'upwd',
							message:'密码输入不一致'
						},
						different:{
							field:'uname',
							message:'密码不能与用户名相同'
						}
					}
				},
			
			}
		});
		$('#loginform').bootstrapValidator({
			message:'This value is not valid',
			feedbackIcons:{
				valid:'glyphicon glyphicon-ok',
				invalid:'glyphicon glyphicon-remove',
				validating:'glyphicon glyphicon-refresh'
			},
			fields:{
				username:{
					validators:{
						notEmpty:{
							message:'用户名不能为空'
						},
						stringLength:{
							min:3,
							max:22,
							message:'用户名长度为3-22'
						},
						regexp:{
							regexp:/^[0-9A-Za-z_]+$/,
							message:'用户名由字母，数字及_组成'
						}, 
				
						different:{
							field:'upassword',
							message:'用户名和密码不能相同'
							}
					}
				},
				upassword:{
					validators:{
						notEmpty:{
							message:'密码不能为空'
						},
						stringLength:{
							min:5,
							max:22,
							message:'密码长度为5-22'
						},
						 regexp:{
							regexp:/^[0-9A-Za-z_]+$/,
							message:'密码由字母，数字及_组成'
						}, 
						
					
						different:{
							field:'username',
							message:'密码不能与用户名相同'
						}
					}
				},
		
			
			}
		});
	});
</script>
<script type="text/javascript">
</script>


<%
String errorInfo = (String)request.getAttribute("loginError");         // 获取错误属性
if(errorInfo != null) {
%>
<script type="text/javascript" >
alert("<%=errorInfo%>");                                            // 弹出错误信息                           // 跳转到登录界面
</script> 
<%
}

%>

<%
String registererrorInfo = (String)request.getAttribute("registerError");         // 获取错误属性
if( registererrorInfo != null) {
%>
<script type="text/javascript" >
alert("<%= registererrorInfo%>");                                            // 弹出错误信息                           // 跳转到登录界面
</script> 
<%
}

%>
</body>
</html>
