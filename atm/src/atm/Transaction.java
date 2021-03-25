package atm;

import java.util.Date;

public class Transaction {
	
	/*
	 * Transaction amount.
	 */
	private double amount;
	
	/*
	 * Timestamp of the transaction.
	 */
	private Date timestamp;
	
	/*
	 * A note describing the nature of the transaction.
	 */
	private String memo;
	
	/*
	 * Account in which this transaction was performed.
	 */
	private Account inAccount;
	
	/**
	 * Create a new transaction
	 * @param amount		the amount transacted.
	 * @param inAccount		the account the transaction belongs to.
	 */
	public Transaction(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	
	/**
	 * Create a new transaction
	 * @param amount		the amount transacted.
	 * @param inAccount		the account the transaction belongs to.
	 * @param memo 			the memo for the transaction.
	 */
	public Transaction(double amount, Account inAccount, String memo) {
		// call the two arg constructor first
		this(amount, inAccount);
		
		// set the memo
		this.memo = memo;
	}

	/**
	 * Get the amount of the transaction
	 * @return	the amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * Get the summary of the transaction
	 * @return	the summary
	 */
	public String getSummaryLine() {
		
		if (this.amount >= 0) {
			return String.format("%s : $%.02f : %s", 
					this.timestamp.toString(), this.amount, this.memo);
		} else {
			return String.format("%s : $(%.02f) : %s", 
					this.timestamp.toString(), this.amount, this.memo);
		}
	}
}
