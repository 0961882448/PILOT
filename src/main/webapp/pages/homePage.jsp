<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<jsp:include page="../common/head.jsp" />
<link rel="stylesheet" href="<c:url value='/css/homePage.css'/>">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>
<body>
	<nav class="navbar navbar-light ">
		<div class="container-fluid">
			<a class="navbar-brand ">PILOT</a>
			<form class="d-flex input-group w-auto">
				<input type="search" class="form-control rounded"
					placeholder="Search" aria-label="Search"
					aria-describedby="search-addon" /> <span
					class="input-group-text border-0" id="search-addon"> <i
					class="fas fa-search"></i>
				</span>
			</form>
		</div>
	</nav>

	<div class="row">
		<div class="col1 side"></div>
		<div class="col1 brand">

			<select type="button" class="btn  aria-expanded="false" id="brandName" name = "brandName"">
				 <option selected="selected" disabled="disabled" value = "" >Hãng</option>

			</select>
		</div>
		<div class="col1 price">

			<select type="button" class="btn   aria-expanded="false" id="price">
				<option selected="selected" disabled="disabled" value = "">Giá</option>
				<option  data-priceFrom=""
					data-priceTo="2000000">Dưới 2 triệu</option>
				<option  data-priceFrom="2000000"
					data-priceTo="4000000">Từ 2 -4 triệu</option>
				<option  data-priceFrom="4000000"
					data-priceTo="7000000">Từ 4 - 7 triệu</option>
				<option  data-priceFrom="7000000"
					data-priceTo="13000000">Từ 7 - 13 triệu</option>
				<option  data-priceFrom="13000000"
					data-priceTo="20000000">Từ 13 - 20 triệu</option>
				<option  data-priceFrom="20000000"
					data-priceTo="">trên 20tr</option>

			</select>
		</div>
		<div class="col1 keyword">

			<input class="form-control rounded" type="text" id = "keyword"
				placeholder=" Tên Sản Phẩm" name="keyword">
		</div>
		<div class="col1 search">
			<button type="button" id="keySearch">Search</button>
		</div>

		<div class="col1 side"></div>


	</div>

	<div class="row">
		<div class="col1 side"></div>
		<div class="container-productbox col1 middle">
			<ul class="listproduct" id = "listproduct">


			</ul>
		</div>
		<div class="col1 side"></div>
	</div>

	<div class="d-flex justify-content-center">
		<ul class="pagination">

		</ul>
	</div>


	






	<jsp:include page="../common/footer.jsp" />

	<script src="<c:url value='/js/homePage.js'/>"></script>
</body>
</html>