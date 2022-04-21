package com.training.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.training.common.constant.Constants;
import com.training.common.util.FileHelper;
import com.training.dao.IBrandDao;
import com.training.dao.IProductDao;
import com.training.dao.jpaspec.ProductJpaSpecification;
import com.training.entity.ProductEntity;
import com.training.model.PagerModel;
import com.training.model.ResponseDataModel;
import com.training.service.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Value("${parent.folder.images.product}")
	private String productImageFolderPath;

	@Autowired
	IProductDao productDao;

	@Autowired
	IBrandDao brandDao;

	@Override
	public ProductEntity add(ProductEntity productEntity) {

		try {
			String imagePath = FileHelper.addNewFile(productImageFolderPath, productEntity.getImageFiles());
			// Date saleDate = CommonUtil.cvStringToDate(productEntity.getDateString(),
			// "yyyyy-mm-dd");
			// productEntity.setSaleDate(saleDate);
			productEntity.setImage(imagePath);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return productDao.saveAndFlush(productEntity);
	}

	@Override
	public ProductEntity update(ProductEntity productEntity) {

		try {
			if (productEntity.getImageFiles()[0].getSize() > 0) {
				String imagePath = FileHelper.editFile(productImageFolderPath, productEntity.getImageFiles(),
						productEntity.getImage());
				// Date saleDate = CommonUtil.cvStringToDate(productEntity.getDateString(),
				// "yyyyy-mm-dd ");
				// productEntity.setSaleDate(saleDate);
				productEntity.setImage(imagePath);
				System.out.println(imagePath);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return productDao.saveAndFlush(productEntity);
	}

	@Override
	public ResponseDataModel searchWithConditions(Map<String, Object> searchConditions, int pageNumber) {

		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		Map<String, Object> responseMap = new HashMap<>();

		try {

			Sort sortInfo = Sort.by(Sort.Direction.DESC, "productId");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sortInfo);
			Page<ProductEntity> productEntitiesPage = productDao
					.findAll(ProductJpaSpecification.getSearchCriteria(searchConditions), pageable);
			responseMap.put("productList", productEntitiesPage.getContent());
			responseMap.put("paginationInfo", new PagerModel(pageNumber, productEntitiesPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			responseMsg = e.getMessage();
			LOGGER.error("Error when get all brand: ", e);
		}

		return new ResponseDataModel(responseCode, responseMsg, responseMap);
	}

	@Override
	public ResponseDataModel searchWithConditionsWeb(Map<String, Object> searchConditions, int pageNumber) {

		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		Map<String, Object> responseMap = new HashMap<>();

		try {

			Sort sortInfo = Sort.by(Sort.Direction.DESC, "productId");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_WEB, sortInfo);
			Page<ProductEntity> productEntitiesPage = productDao
					.findAll(ProductJpaSpecification.getSearchCriteria(searchConditions), pageable);
			responseMap.put("productList", productEntitiesPage.getContent());
			responseMap.put("paginationInfo", new PagerModel(pageNumber, productEntitiesPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			responseMsg = e.getMessage();
			LOGGER.error("Error when get all brand: ", e);
		}

		return new ResponseDataModel(responseCode, responseMsg, responseMap);
	}

	@Override
	public ResponseDataModel addApi(ProductEntity productEntity) {

		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;

		try {
			if (findByProductName(productEntity.getProductName()) != null) {
				responseMsg = "Product Name is duplicated";
				responseCode = Constants.RESULT_CD_DUPL;
			} else {
				MultipartFile[] imageFiles = productEntity.getImageFiles();
				if (imageFiles != null && imageFiles[0].getSize() > 0) {

					String imagePath = FileHelper.addNewFile(productImageFolderPath, productEntity.getImageFiles());
					productEntity.setImage(imagePath);
				}
				productDao.saveAndFlush(productEntity);
				responseMsg = "Product is added Successfully";
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (IOException e) {

			responseMsg = " Error when adding product";
			LOGGER.error("Error when adding product : ", e);
		}
		// Date saleDate = CommonUtil.cvStringToDate(productEntity.getDateString(),
		// "yyyyy-mm-dd");
		// productEntity.setSaleDate(saleDate);;

		return new ResponseDataModel(responseCode, responseMsg);
	}

	@Override
	public ResponseDataModel updateApi(ProductEntity productEntity) {

		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;

		try {
			// ProductEntity duplicatedProduct = productDao.fin

			MultipartFile[] imageFiles = productEntity.getImageFiles();
			if (imageFiles != null && imageFiles[0].getSize() > 0) {

				String imagePath = FileHelper.addNewFile(productImageFolderPath, productEntity.getImageFiles());
				productEntity.setImage(imagePath);
			}
			productDao.saveAndFlush(productEntity);
			responseMsg = "Product is update Successfully";
			responseCode = Constants.RESULT_CD_SUCCESS;

		} catch (IOException e) {

			responseMsg = " Error when adding product";
			LOGGER.error("Error when adding product : ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg);
	}

	@Override
	public ResponseDataModel deleteApi(Long productId) {
		
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		ProductEntity productEntity = productDao.findByProductId(productId);

		try {
			if (productEntity != null) {
				productDao.deleteById(productId);
				productDao.flush();
				FileHelper.deleteFile(productEntity.getImage());
				responseMsg = " Product is deleted successfully";
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (IOException e) {
			
			responseMsg = "Error when deleting product";
			LOGGER.error("Error when deteted product : ", e);

		}

		return new ResponseDataModel(responseCode, responseMsg);
	}

	@Override
	public List<ProductEntity> getAll() {
		
		return productDao.findAllByOrderByProductId();
	}

	@Override
	public ResponseDataModel findByProductIdApi(Long productId) {
		
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		Map<String, Object> responseMap = new HashMap();
		ProductEntity productEntity = null;

		try {
			productEntity = productDao.findByProductId(productId);
			if (productEntity != null) {
				responseCode = Constants.RESULT_CD_SUCCESS;
				responseMap.put("product", productEntity);

			}
		} catch (Exception e) {
			
			responseMsg = "Error when finding product by ID";
			LOGGER.error("Error when finding product by ID : ", e);

		}
		return new ResponseDataModel(responseCode, responseMsg, responseMap);

	}

	@Override
	public ProductEntity findByProductName(String productName) {
		
		return productDao.findByProductName(productName);
	}

	@Override
	public ResponseDataModel findAllWithPagerApi(int pageNumber) {
		
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		Map<String, Object> responseMap = new HashMap();
		try {
			Sort sortInfo = Sort.by(Sort.Direction.DESC, "productId");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sortInfo);
			Page<ProductEntity> productEntitiesPage = productDao.findAll(pageable);
			responseMap.put("productList", productEntitiesPage.getContent());
			responseMap.put("paginationInfo", new PagerModel(pageNumber, productEntitiesPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			
			responseMsg = e.getMessage();
			LOGGER.error("Error when get all product: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg, responseMap);
	}
	@Override
	public Map<String, Object> findAllApi(Map<String, Object> conditionMap) {
		// TODO Auto-generated method stub

		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		Map<String, Object> responseMap = new HashMap<>();
		int pageSize = (int) conditionMap.get("length");
		int pageNumber = (int) conditionMap.get("start");
		if(pageNumber != 0) {
			pageNumber = pageNumber /pageSize  ;
		}
		String sSortDir = (String) conditionMap.get("sSortDir");
		int iSortNum = (int) conditionMap.get("iSortNum");
		String iSortColumn = "productId";
		switch (iSortNum) {
		case 0:
			break;
		case 1:
			iSortColumn = "productName";
			break;
		case 2:
			iSortColumn = "quantity";
			break;
		case 3:
			iSortColumn = "price";
			break;
		case 4:
			iSortColumn = "saleDate";
			break;
		}
		
		
		List<ProductEntity> productEntities = productDao
				.findAll(ProductJpaSpecification.getSearchCriteria(conditionMap));
		try {

			Sort sortInfo =  Sort.by(Sort.Direction.DESC, iSortColumn);
			if(sSortDir.equals("asc")) {
				sortInfo =  Sort.by(Sort.Direction.ASC, iSortColumn) ;
			}
			Pageable pageable = PageRequest.of(pageNumber , pageSize, sortInfo);
			Page<ProductEntity> productEntitiesPage = productDao
					.findAll(ProductJpaSpecification.getSearchCriteria(conditionMap), pageable);
			responseMap.put("data", productEntitiesPage.getContent());
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			responseMsg = e.getMessage();
			LOGGER.error("Error when get all brand: ", e);
		}

		responseMap.put("responseCode", responseCode);
		responseMap.put("responseMsg", responseMsg);
		responseMap.put("recordsFiltered", productEntities.size());
		responseMap.put("recordsTotal", productEntities.size());
		responseMap.put("draw", (int) conditionMap.get("draw"));
		return responseMap;
	}
}