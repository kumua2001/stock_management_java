package com.stockmanagement3.bean;

import java.time.LocalDate;
import java.util.Objects;

import com.stockmanagement3.utils.Constants;

public class Transaction {

	private String productName;
	private int productCount;
	private double productAmount;
	private LocalDate transactionDate;
	private String customerName;

	public Transaction() {
		super();
	}

	public Transaction(Customer customer, String productName, int productCount, double productAmount, LocalDate date) {
		this.customerName = customer.getCustomerName();
		this.productName = productName;
		this.productCount = productCount;
		this.productAmount = productAmount;
		this.transactionDate = date;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int writeTransaction(Customer custom, Product product, String proKey, int proCount, LocalDate currentDate) {
		if (proCount > 0) {
			if (SuperMarket.getProductList().contains(product)) {
				int i = SuperMarket.getProductList().indexOf(product);
				if (proCount <= SuperMarket.getProductList().get(i).getProductCount()) {
					int isAdded = addList(custom, product, proCount, product.getProductAmount(), currentDate);
					if (isAdded == Constants.WRITTEN_SUCCESSFUL) {
						SuperMarket.getProductList().get(i).setProductCount(product.getProductCount() - proCount);
						return Constants.WRITTEN_SUCCESSFUL;
					} else
						return isAdded;
				}
				return Constants.NOT_ENOUGH_PRODUCT;
			}
			return Constants.INVALID_PRODUCT_NAME;

		}
		return Constants.INVALID_COUNT;

	}

	public int addList(Customer custom, Product product, int count, double amount, LocalDate currentDate) {
		if (SuperMarket.getProductList().contains(product)) {
			Transaction c = new Transaction(custom, product.getProductName(), count, amount, currentDate);
			custom.getCustomerTransactionList().add(c);
			SuperMarket.getTransactionList().add(c);
			return Constants.WRITTEN_SUCCESSFUL;
		} else
			return Constants.INVALID_PRODUCT_NAME;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(customerName, other.customerName);
	}

	public String toString(double amount) {
		return String.valueOf(amount);

	}
}
