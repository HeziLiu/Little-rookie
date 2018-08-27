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
							<form action="ChInfoServlet" method="post">
							
							<input type="text" name="username" disabled="disabled" placeholder="username" value="${user.username }">
								
					           <input type="text" name="telephone" placeholder="Mobile Phone" value="">
									<input type="text" name="email" placeholder="Email*" value="">
								
									<input type="date" name="birthday" placeholder="birthday*" value="">
										<select name="sex" value="">
										<option>-- sex --</option>
										<option>Male</option>
										<option>Female</option>
										
									</select>							
					                <input type="submit" value="Submit" style="background-color:#FE980F">	
									<input type="submit" value="cancel" style="background-color:#FE980F">			
							
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
