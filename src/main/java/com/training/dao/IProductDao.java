package com.training.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import com.training.entity.ProductEntity;

@Repository
public interface IProductDao extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
	ProductEntity findByProductId(Long productId);

	ProductEntity findByProductName(String productName);

	List<ProductEntity> findAllByOrderByProductId();
	
}
