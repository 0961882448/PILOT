<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Product</title>
	
	<jsp:include page="../common/head.jsp" />
	<link rel="stylesheet" href="<c:url value='/css/product.css'/>">
</head>
<body>
<jsp:include page="../common/header.jsp" />
	<div class="container">
		<div class="sub-header">
			<div class="float-left sub-title">Update Product</div>
		</div>
		<div class="w-50 mx-auto">
			<c:if test="${not empty errorMessage}">
				<div class="message-area">
					<div class="error-message-invalid">${errorMessage}</div>
				</div>
			</c:if>
			<form id="productInfoForm" role="form" enctype="multipart/form-data"
				action="update" method="POST">
				<div class="modal-body">
				<input type = "hidden" value="${product.productId}" name = "productId">
					<div class="form-group">
						<label for="productName">Product Name</label> 
						<input type="text" class="form-control" id="productName" name="productName"
							value= "${product.productName}">
					</div>
					<div class="form-group">
						<label for="quantity">Quantity</label> <input type="number"
							class="form-control" id="quantity" name="quantity" value= "${product.quantity}">
					</div>
					<div class="form-group">
						<label for="price">Price </label> <input type="number"
							class="form-control" id="price" name="price" value= "${product.price}">
					</div>
					<div class="form-group">
						<label for="brandId">Brand Name </label>
						 <select name="brandId" id="brandId">
							<c:forEach var="brand" items="${brandList}">
								<option value="${brand.brandId}">${brand.brandName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="dateString">Opening For Sale</label> 
						<input type="date" value="2017-06-01" class="form-control" id="dateString" name="dateString" value= "${product.saleDate}">
					</div>
					<div class="form-group">
						<label for="description">Description</label>
						<textarea name="description" id="description" cols="30" rows="3"
							class="form-control" placeholder="Description" >${product.description}</textarea>
					</div>
					<div class="form-group">
						<label for="image">Image </label>
						<div class="preview-image-upload" id="image">
							<img src="<c:url value='${product.image}'/>" alt="image">
						</div>
						<input type="file" class="form-control upload-image" name="imageFiles"
							accept="image/*" /> <input type="hidden" class="old-img"
							id="image" name="image">
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Cancel</button>
					<button type="submit" class="btn btn-primary" id="saveBtn">Save</button>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>