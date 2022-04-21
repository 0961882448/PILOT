<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Management</title>
<jsp:include page="../common/head.jsp" />
<link rel="stylesheet" href="<c:url value='/css/product.css'/>">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<div class="container">
		<div class="sub-header">
			<div class="float-left sub-title">Product Management</div>
			<div class="float-right">
				<a class="btn btn-success add-btn" id = "addProductInfoModal"><i
					class="fas fa-plus-square"></i> Add Brand</a>
			</div>
		</div>
		<div class = "container">
			<div class = "row">
				<div class = "col " >
				<label for = "keyword"> Product Name</label>
				<input type = "text" class = "form-control" id = "keyword" name = "keyword" />
				</div>
				<div class = "col">
					<lable for = "priceFrom">Price From</lable>
					<input type = "number"class = "form-control" id = "priceFrom" name = "priceFrom"  />
				</div>
				<div class = "col">
					<lable for = "priceTo">Price To</lable>
					<input type = "number" class = "form-control"id = "priceTo" name = "priceTo"  />
				</div>
				<button type = "button" class="btn btn-primary" id = "keySearch"> Search</button>
			</div>
		</div>
		<table class="table table-bordered" id="productInfoTable">
			<thead>
				<tr class="text-center">
					<th scope="col">ID</th>
					<th scope="col">Product</th>
					<th scope="col">Quanity</th>
					<th scope="col">Price</th>
					<th scope="col">Brand Name</th>
					<th scope="col">Opening For Sale</th>
					<th scope="col">Image</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		
			<div class="d-flex justify-content-center">
				<ul class="pagination">

				</ul>
			</div>
		
	</div>
	<!-- Modal Add and Edit Brand -->
	<div class="modal fade" id="productInfoModal">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<form id="productInfoForm" role="form" enctype="multipart/form-data">
					<div class="modal-header">
						<h5 class="modal-title">Add Product</h5>
						<button type="button" class="close" data-bs-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group d-none">							
							<input type="hidden" class="form-control" name="productId" id="productId">
						</div>
						<div class="form-group">
							<label for="productName">Product Name <span class="required-mask">(*)</span></label> <input type="text"
								class="form-control" id="productName" name="productName"
								placeholder="Product Name">
						</div>
						<div class="form-group">
							<label for="quantity">Quantity</label> <input type="number"
								class="form-control" id="quantity" name="quantity">
						</div>
						<div class="form-group">
							<label for="price">Price </label> <input type="number"
								class="form-control" id="price" name="price">
						</div>
						<div class="form-group">
							<label for="brandId">Brand Name </label> 
							<select name="brandEntity.brandId" id="brandId" class="form-control">
								
							</select>
						</div>
						<div class="form-group">
							<label for="saleDate">Opening For Sale</label> <input
								type="date" value="2021-06-01" class="form-control"
								id="saleDate" name="saleDate">
						</div>
						<div class="form-group">
							<label for="description">Description</label>
							<textarea name="description" id="description" cols="30" rows="3"
								class="form-control" placeholder="Description"></textarea>
						</div>
						<div class="form-group" id= "productImage">
							<label for="image">Image <span class="required-mask">(*)</span> </label>
							<div class="preview-image-upload" id="image">
								<img src="<c:url value='/images/image-demo.png'/>" alt="image">
							</div>
							<input type="file" class="form-control upload-image"
								name="imageFiles" accept="image/*" /> <input type="hidden"
								class="old-img" id="image" name="image">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancel</button>
						<button type="button" class="btn btn-primary" id="saveProductBtn">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Modal Confirm Deleting Brand -->
	<div class="modal fade" id="confirmDeleteModal">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Delete product</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						Do you want to delete <b id="deleteProductName"></b>?
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary#" data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="deleteSubmitBtn">Save</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />

<script src="<c:url value='/js/product.js'/>"></script>


</body>
</html>