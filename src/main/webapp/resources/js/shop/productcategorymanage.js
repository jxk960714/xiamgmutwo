$(function() {
	var listUrl = '/Oto/shop/getproductcategorylist';
	var addUrl = '/Oto/shop/addproductcategorys';
	var deleteUrl = '/Oto/shop/removeproductcategory';
	var testUrl='/Oto/ajax/test';
	var testUrl2='/Oto/ajax/fileUpload';
	$('#jjj').click(function(){
	
		/*var formData = new FormData($("#form")[0]);*/
		var formData=new FromData();
		var shopImg = $("#file")[0].files[0];
		formData.append("file",shopImg);
		formData.append("type","jxk");
			$.ajax({
				url:  testUrl2,
				type : 'POST',
				data: formData,
				contentType : false,
				processData : false,
				cache : false,
		        async : false,
				dataType:'json',
				success:function(data){
					if(data.success){
					
					alert("成功");
					}else{
						
						alert("失败")
					}
				}
					
				});
		
	});
	function getdata(){
		$.getJSON(
				 testUrl,
				 function(data){
					 if(data.success){
						 var name=data.name;
						 $('#test').html(""+name+"&nbsp"+data.age+"");
						 
					 }
					 
				 }
		
		)
		
		
	}
	getdata();

	$
			.getJSON(
					listUrl,
					function(data) {
						if (data.success) {
							var dataList = data.data;
							$('.category-wrap').html('');
							var tempHtml = '';
							dataList
									.map(function(item, index) {
										tempHtml += ''
												+ '<div class="row row-product-category now">'
												+ '<div class="col-33 product-category-name">'
												+ item.productCategoryName
												+ '</div>'
												+ '<div class="col-33">'
												+ item.priority
												+ '</div>'
												+ '<div class="col-33"><a href="#" class="button delete" data-id="'
												+ item.productCategoryId
												+ '">删除</a></div>' + '</div>';
									});
							$('.category-wrap').append(tempHtml);
						}
					});

	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var dataList = data.data;
								
								$('.category-wrap').html('');
								var tempHtml = '';
								dataList
										.map(function(item, index) {
											tempHtml += ''
													+ '<div class="row row-product-category now">'
													+ '<div class="col-33 product-category-name">'
													+ item.productCategoryName
													+ '</div>'
													+ '<div class="col-33">'
													+ item.priority
													+ '</div>'
													+ '<div class="col-33"><a href="#" class="button delete" data-id="'
													+ item.productCategoryId
													+ '">删除</a></div>'
													+ '</div>';
										});
								$('.category-wrap').append(tempHtml);
							}
						});
	}
	getList();

	$('#submit').click(function() {
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addUrl,
			type : 'POST',
			data : JSON.stringify(productCategoryList),
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					getList();
				} else {
					$.toast('提交失败！');
				}
			}
		});
		
	});

	$('#new')
			.click(
					function() {
						var tempHtml = '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
								+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
								+ '</div>';
						$('.category-wrap').append(tempHtml);
					});

	$('.category-wrap').on('click', '.row-product-category.now .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('确定么?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							productCategoryId : target.dataset.id,
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('删除成功！');
								getList();
							} else {
								$.toast('删除失败！');
							}
						}
					});
				});
			});

	$('.category-wrap').on('click', '.row-product-category.temp .delete',
			function(e) {
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();

			});
	
	
	
	
	
});