package com.training.dao.jpaspec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.training.entity.BrandEntity;
import com.training.entity.ProductEntity;

public class ProductJpaSpecification {

	/**
	 * Get search criteria for query to search products
	 * 
	 * @param searchConditionsMap
	 * @return Specification<ProductEntity>
	 */
	public static Specification<ProductEntity> getSearchCriteria(Map<String, Object> searchConditionsMap) {

		return new Specification<ProductEntity>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<ProductEntity> productRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if (searchConditionsMap != null) {

					String keyword = (String) searchConditionsMap.get("keyword");
					String priceFrom = (String) searchConditionsMap.get("priceFrom");
					String priceTo = (String) searchConditionsMap.get("priceTo");
					List<String> brandIds = (List<String>) searchConditionsMap.get("brandIds");
					Join<ProductEntity, BrandEntity> brandRoot = productRoot.join("brandEntity");
					String brandName = (String) searchConditionsMap.get("brandName");
					// Keyword Predicate
					if (StringUtils.isNotEmpty(keyword)) {
						predicates.add(criteriaBuilder.or(
								criteriaBuilder.like(productRoot.get("productName"), "%" + keyword + "%"),
								criteriaBuilder.like(brandRoot.get("brandName"), "%" + keyword + "%"),
								criteriaBuilder.like(productRoot.get("description"), "%" + keyword + "%"),
								criteriaBuilder.like(brandRoot.get("description"), "%" + keyword + "%")
						));
					}

					// Price From Predicate
					if (StringUtils.isNotEmpty(priceFrom)) {
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(productRoot.get("price"), Double.parseDouble(priceFrom)));
					}

					// Price To Predicate
					if (StringUtils.isNotEmpty(priceTo)) {
						predicates.add(criteriaBuilder.lessThanOrEqualTo(productRoot.get("price"), Double.parseDouble(priceTo)));
					}
					// Brand Name Predicate
					if(StringUtils.isNoneEmpty(brandName)) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.like(brandRoot.get("brandName"), "%"+brandName + "%")));
					}
					// Brand Predicate
					if (!CollectionUtils.isEmpty(brandIds) ) {
						List<Predicate> brandIdPredicateList = new ArrayList<>();
						for (String brandId : brandIds) {
							brandIdPredicateList.add(criteriaBuilder.equal(brandRoot.get("brandId"), Long.parseLong(brandId)));
						}
						predicates.add(criteriaBuilder.or(brandIdPredicateList.toArray(new Predicate[brandIdPredicateList.size()])));
					}
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
