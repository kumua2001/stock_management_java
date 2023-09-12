package com.stockmanagement3.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {

	private String customerName;
	private long mobileNumber;
	private LocalDate joinDate;
	private List<Transaction> customerTransactionList;

	public Customer() {
		super();
	}

	public Customer(String customerName, long mobileNumber, LocalDate joinDate) {

		this.customerName = customerName;
		this.mobileNumber = mobileNumber;
		this.joinDate = joinDate;
		this.setCustomerTransactionList(new ArrayList<Transaction>());

	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public List<Transaction> getCustomerTransactionList() {
		return customerTransactionList;
	}

	public void setCustomerTransactionList(List<Transaction> customerTransactionList) {
		this.customerTransactionList = customerTransactionList;
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
		Customer other = (Customer) obj;
		return Objects.equals(customerName, other.customerName);
	}

}
