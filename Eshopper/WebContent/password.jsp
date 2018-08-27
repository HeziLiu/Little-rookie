<%--
  Created by IntelliJ IDEA.
  User: Zhan
  Date: 2018/7/27
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html lang="en">
<%@include file="./include/head.jsp" %>

<body>
	<%@include file="./include/header.jsp" %>

	<section id="cart_items">
		<div class="container">
			<div class="breadcrumbs">
				<ol class="breadcrumb">
				  <li><a href="#">Home</a></li>
				  <li class="active">Modifying personal information</li>
				</ol>
			</div><!--/breadcrums-->
			<div class="shopper-informations">
				<div class="row">
					<div class="col-sm-3">
						<div class="shopper-info">
							<p>Shopper Information</p>
							<form action="PasswordServlet" method="post">
							
							<input type="text" name="username" placeholder="username" value="${user.username }">
								<input type="password" name="oldpassword" placeholder="oldpassword" value="">
					           <input type="password" name="newpassword" placeholder=" newpassword" value="">
									<input type="password" name="firmpassword" placeholder="firm-password" value="">
					                <input type="submit" value="Submit" style="background-color:#FE980F">	
												
							
							</form>
						</div>
					</div>
			
				</div>
			</div>

		</div>
	</section> <!--/#cart_items-->

	<%@include file="./include/footer.jsp" %>
	<%@include file="./include/script.jsp" %>
</body>
</html>
