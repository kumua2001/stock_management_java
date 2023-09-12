package com.stockmanagement3.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.stockmanagement3.bean.Customer;
import com.stockmanagement3.bean.Product;
import com.stockmanagement3.bean.SuperMarket;
import com.stockmanagement3.bean.Transaction;
import com.stockmanagement3.utils.Constants;

public class StockManagementApplication3 {

	public enum Command {
		CREATE, WRITE, ADD, UPDATE, REMOVE, STATUS, HISTORY, TOTAL, CUSTOMER, CUSTOMERS, INVALID_COMMAND;

		public static Command toStr(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return INVALID_COMMAND;
			}
		}

	}

	public static int position;

	public static void main(String[] args) {

		Product product = new Product();
		SuperMarket superMarket = new SuperMarket();

		List<Customer> activeCustomer = new ArrayList<Customer>();

		Product apple = new Product("apple", "app", 10, 100);
		Product orange = new Product("orange", "ora", 20, 100);
		SuperMarket.getProductList().add(apple);
		SuperMarket.getProductList().add(orange);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		String query = "";
		String[] input;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate currentDate = LocalDate.now();

		System.out.println("Create a new customer using the command ' create [Name] [Mobile Number] ' ");

		while (true) {
			try {
				query = br.readLine();
				if (query.equalsIgnoreCase("quit"))
					break;
			} catch (IOException e) {
				e.printStackTrace();
			}

			st = new StringTokenizer(query);
			input = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				input[i] = token;
				i++;
			}

			switch (Command.toStr(input[0].toUpperCase())) {
			case CREATE: {
				// Creating Customer account
				if (input.length == 3) {
					String customerName = input[1];
					long mobileNumber = Long.parseLong(input[2]);
					activeCustomer.clear();
					int isValid = superMarket.addCustomer(customerName, mobileNumber, currentDate, activeCustomer);
					if (isValid == Constants.CUSTOMER_ADDED)
						System.out.println("Customer account created");
					else if (isValid == Constants.CUSTOMER_ALDREADY_ADDED)
						System.out.println("Customer account aldready created");
					else if (isValid == Constants.INVALID_CUSTOMER_NAME)
						System.out.println("Invalid Customer name");

				} else
					System.out.println("Command Error: Please use the Command ' create [name] [Mobile Number]' to create a new user");
				break;
			}
			case WRITE: {
				// Buying Products
				if (input.length == 3) {
					String productKey = input[1];
					int productCount = Integer.parseInt(input[2]);
					if (activeCustomer.isEmpty()) {
						System.out.println("Please Create customer account ' create [name] [Mobile Number]' ");
						break;
					}

					int isValid = superMarket.writingProduct(activeCustomer.get(0), product, productKey, productCount,
							currentDate);
					switch (isValid) {
					case Constants.WRITTEN_SUCCESSFUL:
						superMarket.total = superMarket.total
								+ SuperMarket.getProductList().get(position).getProductAmount() * productCount;
						System.out.println("  " + SuperMarket.getProductList().get(position).getProductName() + "  ->  "
								+ SuperMarket.getProductList().get(position).getProductAmount() + " * " + productCount
								+ " = "
								+ (SuperMarket.getProductList().get(position).getProductAmount() * productCount));
						break;
					case Constants.NOT_ENOUGH_PRODUCT:
						System.out.println("You do not have sufficient product ");
						break;
					case Constants.INVALID_PRODUCT_NAME:
						System.out.println("Please enter valid product key");
						break;
					case Constants.INVALID_COUNT:
						System.out.println("Please enter a valid count");
						break;
					}
				} else
					System.out.println("Command Error: Please use the command 'write [Product Name] [Count]' to buy the product");
				break;
			}

			case ADD: {
				// Adding Products
				if (input.length == 5) {
					try {
						String productName = input[1];
						String productKey = input[2];
						double productAmount = Double.parseDouble(input[3]);
						int productCount = Integer.parseInt(input[4]);
						int isValid = superMarket.addProduct(productName, productKey, productAmount, productCount);
						if (isValid == Constants.PRODUCT_ADDED)
							System.out.println("Product added $ " + input[1] + " on " + currentDate);
						else if (isValid == Constants.INVALID_PRODUCT_NAME) {
							System.out.println("Product name or Product key aldready in stock . Please check status ");
						} else if (isValid == Constants.INVALID_COUNT)
							System.out.println("invalid number : please check product's count and amount ");
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number");
					}

				} else
					System.out.println(
							"Command Error: Please use the command 'add [product name] [product key] [amount] [count] ' to add a new product in product list ");
				break;
			}

			case UPDATE: {
				// Updating Products
				if (input.length == 5) {
					try {
						String productName = input[1];
						String productKey = input[2];
						double productAmount = Double.parseDouble(input[3]);
						int productCount = Integer.parseInt(input[4]);
						int isValid = superMarket.updateProduct(productName, productKey, productAmount, productCount);
						if (isValid == Constants.PRODUCT_UPDATED)
							System.out.println("Product Updated $ " + input[1] + " on " + currentDate);
						else if (isValid == Constants.INVALID_PRODUCT_NAME) {
							System.out.println(
									"Product name not in stock . Please check status and change product name ");
						} else if (isValid == Constants.INVALID_COUNT)
							System.out.println("invalid number : please check product's count and amount ");
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number");
					}

				} else
					System.out.println(
							" Command Error: Please use the command 'update [product name] [product key] [amount] [count]' to modify product details");
				break;
			}

			case REMOVE: {
				// Removing Products
				if (input.length == 2) {
					String productName = input[1];
					int isValid = superMarket.removeProduct(productName);
					if (isValid == Constants.PRODUCT_REMOVED)
						System.out.println("Product removed $ " + input[1] + " on " + currentDate);
					else if (isValid == Constants.INVALID_PRODUCT_NAME) {
						System.out.println(" Product name not in stock . Please check status and change product name ");
					}
				} else
					System.out.println("Command Error: Please use the command ' remove [product name] ' to remove a product from Product list");
				break;
			}

			case HISTORY: {
				// Viewing all transaction history
				if (input.length == 1) {
					Iterator<Transaction> it = SuperMarket.getTransactionList().iterator();
					if (SuperMarket.getTransactionList().isEmpty())
						System.out.println(" No shopping");
					else {
						System.out.println(
								"----------------+-----------------+------------------+------------+--------------+-----------------+");
						System.out.println(
								" Date\t\t|  Customer name  |   Product name   |   count    |    Amount    |   total amount  |");
						System.out.println(
								"----------------+-----------------+------------------+------------+--------------+-----------------+");
						while (it.hasNext()) {
							Transaction c = it.next();
							String customerName = c.getCustomerName();
							String productName = c.getProductName();
							int productCount = c.getProductCount();
							double productAmount = c.getProductAmount();
							LocalDate date = c.getTransactionDate();

							System.out.format("%11s", date);
							System.out.print("     |  ");
							System.out.format("%12s", customerName);
							System.out.print("   |  ");
							System.out.format("%12s", productName);
							System.out.print("    |  ");
							System.out.format("%6d", productCount);
							System.out.print("    |  ");
							System.out.format("%8s", superMarket.toString(productAmount));
							System.out.print("    |  ");
							System.out.format("%12s", superMarket.toString(productCount * productAmount));
							System.out.print("   |  ");
							System.out.println(
									"\n----------------+-----------------+------------------+------------+--------------+-----------------+");

						}

					}
				} else
					System.out.println("Command Error: Please use the command ;' [history]' to get all transactions");
				break;
			}

			case STATUS: {
				// Viewing Product Details
				if (input.length == 1) {
					System.out.println(" ---------------+---------------+------------+--------------+");
					System.out.println(" Product name   |  Product Key  |   count    |    Amount    |");
					System.out.println(" ---------------+---------------+------------+--------------+");
					for (int j = 0; j < SuperMarket.getProductList().size(); j++) {
						System.out.format("%12s", SuperMarket.getProductList().get(j).getProductName());
						System.out.print("    |  ");
						System.out.format("%9s", SuperMarket.getProductList().get(j).getProductKey());
						System.out.print("    |  ");
						System.out.format("%6d", SuperMarket.getProductList().get(j).getProductCount());
						System.out.print("    |  ");
						System.out.format("%8s",
								superMarket.toString(SuperMarket.getProductList().get(j).getProductAmount()));
						System.out.print("    |  ");
						System.out.println("\n ---------------+---------------+------------+--------------+");

					}
				} else
					System.out.println("Command Error: Please use the command ' [status] ' to get Product details");
				break;
			}

			case TOTAL: {
				// Viewing current total
				if (input.length == 1) {
					try {
						System.out.println("Total = " + superMarket.total);
					} catch (Exception e) {
						System.out.println("Customer not created");
					}
				} else
					System.out.println("Command Error: Please use the command ' [total] ' to get total amount");

				break;
			}

			case CUSTOMER: {
				// Viewing particular Customer transactions
				if (input.length == 2) {
					String customerName = input[1];
					for (int j = 0; j < SuperMarket.getCustomersList().size(); j++) {
						if (SuperMarket.getCustomersList().get(j).getCustomerName().equals(customerName)) {

							Iterator<Transaction> it = SuperMarket.getCustomersList().get(j)
									.getCustomerTransactionList().iterator();
							if (SuperMarket.getCustomersList().get(j).getCustomerTransactionList().isEmpty()) {
								System.out.println(" No shopping");
								break;
							} else {
								System.out.println(
										"----------------+-----------------+------------------+------------+--------------+-----------------+");
								System.out.println(
										" Date\t\t|  Customer name  |   Product name   |   count    |    Amount    |   total amount  |");
								System.out.println(
										"----------------+-----------------+------------------+------------+--------------+-----------------+");
								while (it.hasNext()) {
									Transaction c = it.next();

									String customName = c.getCustomerName();
									String productName = c.getProductName();
									int productCount = c.getProductCount();
									double productAmount = c.getProductAmount();
									LocalDate date = c.getTransactionDate();

									System.out.format("%11s", date);
									System.out.print("     |  ");
									System.out.format("%12s", customName);
									System.out.print("   |  ");
									System.out.format("%12s", productName);
									System.out.print("    |  ");
									System.out.format("%6d", productCount);
									System.out.print("    |  ");
									System.out.format("%8s", superMarket.toString(productAmount));
									System.out.print("    |  ");
									System.out.format("%12s", superMarket.toString(productCount * productAmount));
									System.out.print("   |  ");
									System.out.println(
											"\n----------------+-----------------+------------------+------------+--------------+-----------------+");
								}
								break;
							}
						} else {
							if (j == (SuperMarket.getCustomersList().size() - 1))
								System.out.println("Customer not found, please check customer list");

						}
					}
				}

				else
					System.out.println("Command Error: Please use the command ' customer [customer name] ' to get transaction history for particular customer");
				break;
			}

			case CUSTOMERS: {
				// Viewing all customer Details
				if (input.length == 1) {
					System.out.println(" ---------------+-----------------+---------------+");
					System.out.println(" CUSTOMER NAME  |  MOBILE NUMBER  |      DATE     |");
					System.out.println(" ---------------+-----------------+---------------+");
					for (int j = 0; j < SuperMarket.getCustomersList().size(); j++) {
						System.out.format("%12s", SuperMarket.getCustomersList().get(j).getCustomerName());
						System.out.print("    |  ");
						System.out.format("%11d", SuperMarket.getCustomersList().get(j).getMobileNumber());
						System.out.print("    |  ");
						System.out.format("%8s", SuperMarket.getCustomersList().get(j).getJoinDate());
						System.out.print("   |  ");
						System.out.println("\n ---------------+-----------------+---------------+");

					}
				} else
					System.out.println("Command Error: Please use the command ' [customers] ' to get all customer details");
				break;
			}

			case INVALID_COMMAND: {
				System.out.println(
						"Invalid Command: commands - CREATE, WRITE, ADD, UPDATE, REMOVE, STATUS, HISTORY, TOTAL, CUSTOMER, CUSTOMERS ");
				break;
			}

			}

		}

	}

}
