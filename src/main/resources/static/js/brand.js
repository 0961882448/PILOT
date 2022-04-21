$(document).ready(function() {
	var $brandInfoForm = $('#brandInfoForm');
	var $brandInfoModal = $('#brandInfoModal');
	var $brandTable;
	// Show brands list when opening page
	searchBrand();

	// Show brands list when clicking pagination button
	//$('.pagination').on('click', '.page-link', function() {
	//	var pagerNumber = $(this).attr("data-index");
	//	findAllBrands(pagerNumber);
	//})



	// Show add brand modal
	$('#addBrandInfoModal').on('click', function() {
		resetFormModal($brandInfoForm);
		showModalWithCustomizedTitle($brandInfoModal, "Add Brand");
		$('#logoImg img').attr('src', '/images/image-demo.png');
		$('#brandId').closest(".form-group").addClass("d-none");
		$("#brandLogo .required-mask").removeClass("d-none");
	});

	// Show update brand modal
	$("#brandInfoTable").on('click', 'tbody tr .edit-btn', function() {
		$("#brandLogo .required-mask").addClass("d-none");

		resetFormModal($brandInfoForm);
		showModalWithCustomizedTitle($brandInfoModal, "Edit Brand");

		var $tr = $(this).closest('tr');
		var dataRow = $brandTable.row($tr).data();
		console.log(dataRow);

		$('#brandId').val(brandInfo.brandId);
		$('#brandName').val(brandInfo.brandName);
		$('#description').val(brandInfo.description);

		var brandLogo = brandInfo.logo;
		if (brandLogo == null || brandLogo == "") {
			brandLogo = "/images/image-demo.png";
		}
		$("#logoImg img").attr("src", brandLogo);
		$("#logo").val(brandLogo);
	});

	//Show img brand modal
	$("brandInfoTable").on('click', 'img', function() {
		$('#img01').attr('src', this.src);
		$('#imgModal').modal('show');

	})

	// Show delete brand confirmation modal
	$("#brandInfoTable").on('click', 'tbody tr .delete-btn', function() {


		var $tr = $(this).closest('tr');
		var dataRow = $brandTable.row($tr).data();
		console.log(dataRow);
		$("#deletedBrandName").text(dataRow.brandName);
		$("#deleteSubmitBtn").attr('data-id', dataRow.brandId);
		$('#confirmDeleteModal').modal('show');
	});

	// Submit delete brand
	$("#deleteSubmitBtn").on('click', function() {
		$.ajax({
			url: "/brand/api/delete/" + $(this).attr("data-id"),
			type: 'DELETE',
			dataType: 'json',
			contentType: 'application/json',
			success: function(responseData) {
				$('#confirmDeleteModal').modal('hide');
				showNotification(responseData.responseCode == 100, responseData.responseMsg);
				$brandTable.destroy();
				searchBrand();
			}
		});
	});

	// Submit add and update brand

	$('#saveBrandBtn').on('click', function(event) {

		event.preventDefault();
		var formData = new FormData($brandInfoForm[0]);
		var brandId = formData.get("brandId");
		var isAddAction = brandId == undefined || brandId == "";

		$brandInfoForm.validate({
			ignore: [],
			rules: {
				brandName: {
					required: true,
					maxlength: 100
				},
				logoFiles: {
					required: isAddAction,
				}
			},
			messages: {
				brandName: {
					required: "Please input Brand Name",
					maxlength: "The Brand Name must be less than 100 characters",
				},
				logoFiles: {
					required: "Please upload Brand Logo",
				}
			},
			errorElement: "div",
			errorClass: "error-message-invalid"
		});

		if ($brandInfoForm.valid()) {

			// POST data to server-side by AJAX
			$.ajax({
				url: "/brand/api/" + (isAddAction ? "add" : "update"),
				type: 'POST',
				enctype: 'multipart/form-data',
				processData: false,
				contentType: false,
				cache: false,
				timeout: 10000,
				data: formData,
				success: function(responseData) {

					// Hide modal and show success message when save successfully
					// Else show error message in modal
					if (responseData.responseCode == 100) {
						$brandInfoModal.modal('hide');
						findAllBrands(1);
						showNotification(true, responseData.responseMsg);
					} else {
						showMsgOnField($brandInfoForm.find("#brandName"), responseData.responseMsg);
					}
				}
			});
		}
	});
	function searchBrand() {
		$brandTable = $('#brandInfoTable').DataTable({
			"processing": true,
			"serverSide": true,
			"searching": false,
			"ajax": {
				url: 'brand/api/brandList',
				dataType: 'json',
				contentType: 'application/json; charset=uft-8',
				type: "POST",
				data: function(setting) {
					var data = {
						'length': setting.length,
						'start': setting.start,
						'sSortDir': setting.order[0].dir,
						'draw': setting.draw,
						'iSortColumn': setting.columns[0].name,
						'iSortNum': setting.order[0].column,
						//'searchCondition': searchCondition
					}
					return JSON.stringify(data);
				},
			},
			columnDefs: [
				{ "searchable": false },
				{
					data: 'brandId',
					targets: 0,
				},
				{
					data: 'brandName',
					targets: 1,
				},
				{
					data: 'logo',
					targets: 2,
					orderable: false,
					render: function(data, type, row, meta) {
						return '<img src="' + data + '"id ="imgbrand">'
					}
				},
				{
					data: 'description',
					targets: 3,
				},
				{
					targets: 4,
					orderable: false,
					render: function(type, row) {
						return "<a class='edit-btn btn'><i class='fas fa-edit'></i></a> | <a class='delete-btn btn' ><i class='fas fa-trash-alt'></i></a>";
					}
				}
			]
		})

	}
});

function findAllBrands(pagerNumber) {
	$.ajax({
		url: "/brand/api/findAll/" + pagerNumber,
		type: 'GET',
		dataType: 'json',
		contentType: 'application/json',
		success: function(responseData) {
			if (responseData.responseCode == 100) {
				renderBrandsTable(responseData.data.brandsList);
				renderPagination(responseData.data.paginationInfo);
			}
		}
	});
}
