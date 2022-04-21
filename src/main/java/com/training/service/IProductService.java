package com.training.service;

import java.util.List;
import java.util.Map;

import com.training.entity.ProductEntity;
import com.training.model.ResponseDataModel;

public interface IProductService {
	
	ProductEntity add(ProductEntity productEntity);

	ProductEntity update(ProductEntity productEntity);

	ResponseDataModel searchWithConditions(Map<String, Object> searchConditions, int pageNumber);

	ResponseDataModel searchWithConditionsWeb(Map<String, Object> searchConditions, int pageNumber);

	ResponseDataModel addApi(ProductEntity productEntity);

	ResponseDataModel updateApi(ProductEntity productEntity);

	ResponseDataModel deleteApi(Long productId);

	List<ProductEntity> getAll();

	ResponseDataModel findByProductIdApi(Long productId);

	ProductEntity findByProductName(String productName);

	ResponseDataModel findAllWithPagerApi(int pageNumber);

	Map<String, Object> findAllApi(Map<String, Object> conditionsMap);
}