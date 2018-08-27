<%--
  Created by IntelliJ IDEA.
  User: Zhan
  Date: 2018/7/27
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!-- 引入jstl标签库 -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<%@include file="./include/head.jsp"  %>
<body>
	<%@include file="./include/header.jsp" %>
	<%@include file="./include/slider.jsp" %>
	<section>
		<div class="container">
			<!-- Product page -->
		<div class="container">
			<div class="thumb p-30">
				<div class="row filter-bar">
					<div class="col-sm-3 col-md-5 cat-search-input">
						<div class="form-group input-group input-group-sm">
							<label class="input-group-addon" for="input-sort"  style="color:orange">CATEGORY</label>
							<select id="input-sort" class="form-control" onchange="window.location.href='/Eshopper/ProductListTypeServlet?typeid='+this.value;">
								<option value="">Default</option>
								<c:forEach items="${types }" var="type">
									<option 
									<c:if test="${type.category_id eq param.typeid }">selected</c:if>
									value="${type.category_id }"
									>${type.category_name }</option>								
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row product-bor">
					<h2 class="title text-center">Features Items</h2>
					<c:forEach items="${list }" var="goods">
				
					<div class="col-sm-3">
									<div class="product-image-wrapper">
										<div class="single-products">
											<div class="productinfo text-center">
											<a href="/Eshopper/ProductDetailServlet?productid=${goods.product_id}">
												<img src="images/home/${goods.product_name}" alt="" /></a>
												<h2>$${goods.price }</h2>
												<h5>${goods.description }</h5>
												<p>Easy Polo Black Edition</p>
												<!--a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a-->
												<button class="btn btn-sefault add-to-cart" onclick="add_to_cart(${goods.product_id})"><i class="fa fa-shopping-cart"></i>Add to cart</button>
											</div>

										</div>
									</div>
								</div>
					
					</c:forEach>
					
				</div>
				
			</div>
		</div>
		</div>
		
		<div class="row">
		<div class="col-md-7 text-right">
		<nav aria-label="Page navigation">
					<ul class="pagination ht-pagination m-t-30">
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
					</ul>
				</nav>
		</div>
		</div>
		<div class="recommended_items"><!--recommended_items-->
						<h2 class="title text-center">recommended items</h2>

						<div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
								<div class="item active">
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="images/home/recommend1.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="images/home/recommend2.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="images/home/recommend3.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="images/home/recommend1.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="images/home/recommend2.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="images/home/recommend3.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							 <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
								<i class="fa fa-angle-left"></i>
							  </a>
							  <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
								<i class="fa fa-angle-right"></i>
							  </a>
						</div>
					</div><!--/recommended_items-->
	</section>


	<%@include file="./include/footer.jsp" %>
	<%@include file="./include/script.jsp" %>
	<script>
	 function add_to_cart(product_id) {
		 //var user_id = '${userid}';
		/* alert('${userid}');
		 if(user_id == null || user_id == ""){
			 alert("Please Login first!");
		 }
		 else{
			 var req = newXMLHttpRequest();
			 //req.onreadystatechange = getReadyStateHandler(req, updateCart);
			 
			 req.open("POST", "/Eshopper/AjaxAddToCartServlet", true);
			 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			 req.send("user_id=" + user_id + "&product_id=" + product_id);
			 alert("OK!")
		}*/
		
		 $.ajax({
             type: "POST",//规定传输方式
             url: "/Eshopper/AjaxAddToCartServlet",//提交URL
             data: {
            	 'user_id':'${userid}',
            	 'product_id':product_id
            	 },//提交的数据
             success: function(data){
                      alert(data);
                }
          });
		 
	}
	</script>
	
</body>
</html>
