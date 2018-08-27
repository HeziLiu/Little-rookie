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
	<header id="header"><!--header-->
		<div class="header_top"><!--header_top-->
			<div class="container">
				<div class="row">
					<div class="col-sm-6">
						<div class="contactinfo">
							<ul class="nav nav-pills">
								<li><a href=""><i class="fa fa-phone"></i> +2 95 01 88 821</a></li>
								<li><a href=""><i class="fa fa-envelope"></i> info@domain.com</a></li>
							</ul>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="social-icons pull-right">
							<ul class="nav navbar-nav">
								<li><a href=""><i class="fa fa-facebook"></i></a></li>
								<li><a href=""><i class="fa fa-twitter"></i></a></li>
								<li><a href=""><i class="fa fa-linkedin"></i></a></li>
								<li><a href=""><i class="fa fa-dribbble"></i></a></li>
								<li><a href=""><i class="fa fa-google-plus"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div><!--/header_top-->

		<div class="header-middle"><!--header-middle-->
			<div class="container">
				<div class="row">
					<div class="col-sm-8">
						<div class="shop-menu pull-right">
							<ul class="nav navbar-nav">
								<li><a href="modifyPersonalInfo.jsp"><i class="fa fa-user"></i> Modify basic information</a></li>
								<li><a href="basicAddInfo.jsp"><i class="fa fa-star"></i>Modify address information</a></li>
								<li><a href="password.jsp" class="active"><i class="fa fa-crosshairs"></i> Change the password</a></li>
								<!--li><a href="cart.jsp"><i class="fa fa-shopping-cart"></i> Cart</a></li>
								<li><a href="login.jsp"><i class="fa fa-lock"></i> Login</a></li-->
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div><!--/header-middle-->

		<div class="header-bottom"><!--header-bottom-->
			<div class="container">
				<div class="row">
					<div class="col-sm-9">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
						</div>
						<div class="mainmenu pull-left">
							<ul class="nav navbar-nav collapse navbar-collapse">
								<li><a href="index.jsp">Home</a></li>
								<li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                    <ul role="menu" class="sub-menu">
                                        <li><a href="shop.jsp">Products</a></li>
										<li><a href="product-details.jsp">Product Details</a></li>
										<li><a href="checkout.jsp" class="active">Checkout</a></li>
										<li><a href="cart.jsp">Cart</a></li>
										<li><a href="login.jsp">Login</a></li>
                                    </ul>
                                </li>
								
								<li><a href="404.jsp">404</a></li>
								<li><a href="contact-us.jsp">Contact</a></li>
							</ul>
						</div>
					</div>
					
				</div>
			</div>
		</div><!--/header-bottom-->
	</header><!--/header-->

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
					<div class="col-sm-13">
						<div class="shopper-info">
							<p>Shopper Information</p>
							
							<form class="form-horizontal" action="InfoServlet" method="post">
     <div class="form-group">
        <label for="" class="col-sm-2 control-label">username:</label>
        <div class="col-sm-10">
        <label class="form-control" name="uesrname" >${user.username}</label>
        </div>
    </div>
    <div class="form-group">
        <label for="" class="col-sm-2 control-label">password:</label>
        <div class="col-sm-10">
                <label class="form-control" name="password" >${user.email}</label>
        
        </div>
    </div>
    <div class="form-group">
        <label for="" class="col-sm-2 control-label">telephone:</label>
        <div class="col-sm-10">
        <label class="form-control" name="telephone" >${user.telephone}</label>
        </div>
    </div>
 
    <div class="form-group">
        <label for="" class="col-sm-2 control-label">sex:</label>
        <div class="col-sm-10">
        <label class="form-control" name="birthday"  >${user.sex}</label>
        </div>
    </div>
    <div class="form-group">
        <label for="" class="col-sm-2 control-label name="password">email:</label>
        <div class="col-sm-10" >
        <label class="form-control" >${user.password}</label>
        </div>
    </div>
    <div class="form-group">
        <label for="" class="col-sm-2 control-label">birthday:</label>
        <div class="col-sm-10">
                <label class="form-control" name="sex"  >${user.birthday}</label>
        </div>
    </div> 

      

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
