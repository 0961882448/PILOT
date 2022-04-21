$(document).ready(function(){
	// Show products list when opening page
findProducts(1);
brandList();


//Show products list when clicking button search

$('#keySearch').on('click', function(){
	findProducts(1);
		
});
// Show products list when clicking pagination button
$('.pagination').on('click', '.page-link', function() {
		var pagerNumber = $(this).attr("data-index");
		findProducts(pagerNumber);
	})

})


function brandList(){
	$.ajax({
		url : "/product/api/brandList",
		dataType : 'json',
		contentType : 'application/json',
		success : function(responseData) {
			if (responseData.responseCode == 100) {
				renderBrandName(responseData.data);
			}
		}
	})
}
/**
find  products
 */
function findProducts(pagerNumber) {
	var data = {
		"keyword" : $('#keyword').val(),
	 	"priceFrom" : $('#price option:selected').attr('data-priceFrom'),
	 	"priceTo" : $('#price option:selected').attr('data-priceTo'),
	 	"brandName": $('#brandName').val() 
	}
	
	console.log(data);
	$.ajax({
		url : "/api/search/" + pagerNumber,
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json',
		data: JSON.stringify(data) ,
		success : function(responseData) {
			if (responseData.responseCode == 100) {
				renderProductsTable(responseData.data.productList);
				renderPagination(responseData.data.paginationInfo);
			}
		}
	});
}
/**
 * Render HTML for brand table
 */
function renderProductsTable(productList){
	var rowHtml = "";
	
	$("#listproduct").empty();
	$.each(productList, function(key, value){
		var price = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value.price);
		rowHtml =  '<li class="item">'+
                '<a href="#">'+
                    '<div class="item-lable">'+
                        '<span>Trả góp 0% </span>'+
                    '</div>'+
                   ' <div class="item-img">'+
                       ' <img src="'+value.image+'">'+
                    '</div>'+
                    '<h3>'+value.productName+'</h3>'+
                    '<strong class="price">'+price+'</strong>'+
                    '<div class="item-rating">'+
                       ' <p>'+
                            '<i class="fa-solid fa-star"></i>'+
                            '<i class="fa-solid fa-star"></i>'+
                            '<i class="fa-solid fa-star"></i>'+
                            '<i class="fa-solid fa-star"></i>'+
                            '<i class="fa-solid fa-star"></i>'+
                        '</p>'+
                    '</div>'+
                '</a>'+
            '</li>';
		$("#listproduct").append(rowHtml);
	});
}
 
 /**
 * Render HTML for pagination bar
 * 
 * @param paginationInfo
 */
function renderPagination(paginationInfo) {

	var paginationInnerHtml = "";
	if (paginationInfo.pageNumberList.length > 0) {
		$("ul.pagination").empty();
		paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.firstPage == 0 ? 'disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.firstPage + '">First</a></li>'
		paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.previousPage == 0 ? 'disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.previousPage + '"> < </a></li>'
		$.each(paginationInfo.pageNumberList, function(key, value) {
			paginationInnerHtml += '<li class="page-item"><a class="page-link '+ (value == paginationInfo.currentPage ? 'active' : '') +'" href="javascript:void(0)" data-index="' + value +'">' + value + '</a></li>';
		});
		paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.nextPage == 0 ? 'disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.nextPage + '"> > </a></li>'
		paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.lastPage == 0 ? 'disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.lastPage + '">Last</a></li>'
		$("ul.pagination").append(paginationInnerHtml);
	}

}

function renderBrandName(brandList){
	var brandNameHtml = "";
	$.each(brandList,function(key, value) {
		brandNameHtml = "<option value='" + value.brandName +"'>"+ value.brandName +"</option>";
		$("#brandName").append(brandNameHtml);
	});
}