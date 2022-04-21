package com.training.entity;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.training.common.util.CommonUtil;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "PRODUCT_ID")
	private Long productId;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "SALE_DATE")
	private Date saleDate;

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "DESCRIPTION")
	private String description;

	@JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private BrandEntity brandEntity;

	@Transient
	private MultipartFile[] imageFiles;

	@Transient
	private String saleDateFormat;

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}

	/**
	 * @param saleDate the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the brandEntity
	 */
	public BrandEntity getBrandEntity() {
		return brandEntity;
	}

	/**
	 * @param brandEntity the brandEntity to set
	 */
	public void setBrandEntity(BrandEntity brandEntity) {
		this.brandEntity = brandEntity;
	}

	/**
	 * @return the imageFiles
	 */
	public MultipartFile[] getImageFiles() {
		return imageFiles;
	}

	/**
	 * @param imageFiles the imageFiles to set
	 */
	public void setImageFiles(MultipartFile[] imageFiles) {
		this.imageFiles = imageFiles;
	}

	public String getSaleDateFormat() {
		return CommonUtil.cvDateToString(saleDate, "dd-MM-yyyy");
	}

}