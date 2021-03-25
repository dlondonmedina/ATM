package atm;

import java.util.ArrayList;

public class Account {
	/*
	 * Name of the account.
	 */
	private String name;
	
	/*
	 * The ID of the Account.
	 */
	private String uuid;
	
	/*
	 * The User owner of the account.
	 */
	private User holder;
	
	/*
	 * The list of transactions.
	 */
	private ArrayList<Transaction> transactions;
	
	/**
	 * Create a new account.
	 * @param name		the name of the account.
	 * @param holder	the User object that holds this account.
	 * @param theBank	the Bank object that issues the account.
	 */
	public Account(String name, User holder, Bank theBank) {
		
		// set the account name and holder
		this.name = name;
		this.holder = holder;
		
		// get UUID for account.
		this.uuid = theBank.getNewAccountUUID();
		
		// initialize transactions
		this.transactions = new ArrayList<Transaction>();
		
	}

	/**
	 * Get the account uuid.
	 * @return	returns the uuid.
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Get summary line for the account
	 * @return	summary line for account rounded to 2 dp
	 */
	public String getSummaryLine() {
		
		// get the account's balance
		double balance = this.getBalance();
		
		// format the summary line depending on positive or negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
		
	}

	/**
	 * Get the balance by running calculations...not best?
	 * @return 	the balance of the account
	 */
	public double getBalance() {
		// TODO: When we attach database change this to not run calculations each time.
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		
		return balance;
	}
	
	/**
	 * Print transaction history for this account.
	 */
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
		
		
	}

	/**
	 * Add a new transaction
	 * @param amount	amount to be transacted
	 * @param memo		memo of the transaction
	 */
	public void addTransaction(double amount, String memo) {

		// create new transaction object.
		Transaction newTrans = new Transaction(amount, this, memo);
		this.transactions.add(newTrans);
		
	}
}
