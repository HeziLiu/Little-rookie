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
						
							<div class="form-two">
								<form action="ChAddressServlet" method="post">
									<input type="text" name="zip" placeholder="Zip *">
									<select type="text" name="province" placeholder="province *">
										<option>-- Country --</option>
										<option>United States</option>
										<option>Bangladesh</option>
										<option>UK</option>
										<option>India</option>
										<option>Pakistan</option>
										<option>Ucrane</option>
										<option>Canada</option>
										<option>Dubai</option>
									</select>
									<select type="text" name="city" placeholder="city">
										<option>-- State / Province / Region --</option>
										<option>United States</option>
										<option>Bangladesh</option>
										<option>UK</option>
										<option>India</option>
										<option>Pakistan</option>
										<option>Ucrane</option>
										<option>Canada</option>
										<option>Dubai</option>
									</select>
									<input type="text" name="detail" placeholder="detail">
										<button type="submit" class="btn btn-default" value="" >submit</button>		
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
