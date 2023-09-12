package com.stockmanagement3.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.stockmanagement3.main.StockManagementApplication3;
import com.stockmanagement3.utils.Constants;

public class SuperMarket {

	private String superMarketName;
	private long phoneNumber;

	private static List<Customer> customersList = new ArrayList<Customer>();
	private static List<Product> productList = new ArrayList<Product>();
	private static List<Transaction> transactionList = new ArrayList<Transaction>();

	public double total;
	Transaction transaction = new Transaction();

	public String getSuperMarketName() {
		return superMarketName;
	}

	public void setSuperMarketName(String superMarketName) {
		this.superMarketName = superMarketName;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static List<Customer> getCustomersList() {
		return customersList;
	}

	public static void setCustomersList(List<Customer> customersList) {
		SuperMarket.customersList = customersList;
	}

	public static List<Product> getProductList() {
		return productList;
	}

	public static void setProductList(List<Product> productList) {
		SuperMarket.productList = productList;
	}

	public static List<Transaction> getTransactionList() {
		return transactionList;
	}

	public static void setTransactionList(List<Transaction> transactionList) {
		SuperMarket.transactionList = transactionList;
	}

	//  adding Customer Method

	public int addCustomer(String customName, long mobileNumber, LocalDate currentDate, List<Customer> activeCustomer) {

		total = 0;
		for (int k = 0; k < SuperMarket.getCustomersList().size(); k++) {
			if (SuperMarket.getCustomersList().get(k).getCustomerName().equals(customName)) {
				Customer c = SuperMarket.getCustomersList().get(k);
				activeCustomer.add(c);
				return Constants.CUSTOMER_ALDREADY_ADDED;
			}
		}
		if (activeCustomer.isEmpty()) {
			Customer c = new Customer(customName, mobileNumber, currentDate);
			SuperMarket.getCustomersList().add(c);
			activeCustomer.add(c);
			return Constants.CUSTOMER_ADDED;
		}
		return Constants.INVALID_CUSTOMER_NAME;
	}

	//  adding Products Method
	public int addProduct(String proName, String proKey, double proAmount, int proCount) {

		if (proAmount < 0 | proCount < 0) {
			return Constants.INVALID_COUNT;
		}
		Product p = new Product(proName, proKey, proAmount, proCount);
		if ((getProductList().contains(p))) {
			return Constants.INVALID_PRODUCT_NAME;

		} else {
			getProductList().add(p);
			return Constants.PRODUCT_ADDED;
		}

	}

	//  Updating products method
	public int updateProduct(String proName, String proKey, double proAmount, int proCount) {

		if (proAmount < 0 | proCount < 0) {
			return Constants.INVALID_COUNT;
		}
		Product p = new Product(proName, proKey, proAmount, proCount);
		if (getProductList().contains(p)) {
			int position = getProductList().indexOf(p);
			getProductList().get(position).setProductName(proName);
			getProductList().get(position).setProductKey(proKey);
			getProductList().get(position).setProductAmount(proAmount);
			getProductList().get(position).setProductCount(proCount);
			return Constants.PRODUCT_UPDATED;
		}

		return Constants.INVALID_PRODUCT_NAME;

	}

	//  Removing product method
	public int removeProduct(String proName) {
		for (int i = 0; i < getProductList().size(); i++) {
			if (getProductList().get(i).getProductName().equals(proName)) {
				getProductList().remove(i);
				return Constants.PRODUCT_REMOVED;
			}
		}

		return Constants.INVALID_PRODUCT_NAME;
	}

	//  Buying products Method
	public int writingProduct(Customer custom, Product product, String proKey, int proCount, LocalDate currentDate) {
		for (int i = 0; i < SuperMarket.getProductList().size(); i++) {
			if (SuperMarket.getProductList().get(i).getProductKey().equals(proKey)) {
				int isValid = transaction.writeTransaction(custom, SuperMarket.getProductList().get(i), proKey,
						proCount, currentDate);
				StockManagementApplication3.position = i;
				return isValid;

			} else {
				if (i == (SuperMarket.getProductList().size() - 1))
					return Constants.INVALID_PRODUCT_NAME;
			}

		}
		return Constants.INVALID_PRODUCT_NAME;
	}

	public void toString(List<Product> li) {

		for (int i = 0; i < li.size(); i++) {
			System.out.println(li.get(i).getProductName());
		}
	}

	public String toString(double amount) {
		return String.valueOf(amount);

	}

}
