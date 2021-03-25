package atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	
	
	public Bank(String name) {
		
		// set name
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/**
	 * Generate a new UUID for a user
	 * @return	the uuid
	 */
	public String getNewUserUUID() {
		
		// inits
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique = false;
		
		// continue looping until we get a unique ID
		do {
			// generate the number
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to make sure unique
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);
		
		return uuid;
	}
	
	/**
	 * Generate a new UUID for an account
	 * @return 	the uuid
	 */
	public String getNewAccountUUID() {
		// inits
				String uuid;
				Random rng = new Random();
				int len = 6;
				boolean nonUnique = false;
				
				// continue looping until we get a unique ID
				do {
					// generate the number
					uuid = "";
					for (int c = 0; c < len; c++) {
						uuid += ((Integer)rng.nextInt(10)).toString();
					}
					
					// check to make sure unique
					for (Account a : this.accounts) {
						if (uuid.compareTo(a.getUUID()) == 0) {
							nonUnique = true;
							break;
						}
					}
				} while (nonUnique);
				
				return uuid;
	}
	
	/**
	 * Add an account held by the user.
	 * @param account	Account to be added.
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	/**
	 * Create a new user of the bank
	 * @param firstName		the user's first name
	 * @param lastName		the user's last name
	 * @param pin			the user's pin
	 * @return				the new User object
	 */
	public User addUser(String firstName, String lastName, String pin) {
		
		// Create a new User
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// Create a new account for savings.
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
		
	}
	
	/**
	 * Get the User object associated with id and pin if valid.
	 * @param uuid	the UUID of the user to log in
	 * @param pin	the pin of the user
	 * @return		the User object if successful or null. 
	 */
	public User userLogin(String uuid, String pin) {
		
		// Find the userid
		for (User u : this.users) {
			// check user id.
			if (u.getUUID().compareTo(uuid) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		return null;
	}

	public String getName() {

		return this.name;
	}
}
