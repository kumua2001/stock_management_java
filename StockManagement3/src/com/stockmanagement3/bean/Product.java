package com.stockmanagement3.bean;

import java.util.Objects;

public class Product {
	private String productName;
	private String productKey;
	private double productAmount;
	private int productCount;

	public Product() {
		super();
	}

	public Product(String productName, String productKey, double productAmount, int productCount) {
		this.productName = productName;
		this.productKey = productKey;
		this.productAmount = productAmount;
		this.productCount = productCount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(productName, other.productName);
	}

}
