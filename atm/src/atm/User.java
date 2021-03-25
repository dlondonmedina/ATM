package atm;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	/*
	 * The first name of the user.
	 */
	private String firstName;
	
	/*
	 * The last name of the user.
	 */
	private String lastName;
	
	/*
	 * The ID number of the user.
	 */
	private String uuid;
	
	/*
	 * The MD5 hash of the user's pin number.
	 */
	private byte pinHash[];
	
	/*
	 * The list of accounts for this user.
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new user.
	 * @param firstName the user's first name.
	 * @param lastName	the user's last name.
	 * @param pin		the user's account pin number.
	 * @param theBank	the Bank object that the user is customer of.
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pin's MD5 hash.
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException.");			
			e.printStackTrace();
			System.exit(1);
		}
		
		// get a new UUID for the user.
		this.uuid = theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message.
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);

	}

	/**
	 * Add an account held by the user.
	 * @param account	Account to be added.
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	/**
	 * Return the user's id.
	 * @return 	the UUID.
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Check pins
	 * @param aPin	the pin to test for this user.
	 * @return		true if the pin matches, false if not.
	 */
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException.");			
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}

	/**
	 * Print summaries for the accounts of this user.
	 */
	public void printAccountsSummary() {

		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
		
	}

	/**
	 * Get the User's first name.
	 * @return 	return user's first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Get the number of the user's accounts.
	 * @return	number of accounts
	 */
	public Integer numAccounts() {
		
		return this.accounts.size();
	}

	/**
	 * Print transaction history for a particular account.
	 * @param acctIdx	the index of the account to print.
	 */
	public void printAcctTransHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}

	/**
	 * Get the balance from an account.
	 * @param fromAcct		the account
	 * @return				the balance
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	/**
	 * Get the account uuid
	 * @param acctIdx	Account to get id for.
	 * @return			the uuid
	 */
	public Object getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	/**
	 * Add an account transaction.
	 * @param acctIdx	account where transaction occurs 
	 * @param amount	amount to transact
	 * @param memo		memo for transaction
	 */
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
		
	}
}
