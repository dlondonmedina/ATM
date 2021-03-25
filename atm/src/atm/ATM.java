package atm;

import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args) {
		
		// init Scanner
		Scanner sc = new Scanner(System.in);
		
		// init Bank 
		Bank theBank = new Bank("Bruno's Bank of Banks.");
		
		// add a user
		User aUser = theBank.addUser("John", "Doe", "1234");
		
		// add a checking account for our user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		
		while(true) {
			
			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			// stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
		
	}

	/**
	 * Print the user main menu
	 * @param curUser	Current logged in user
	 * @param sc		Scanner from main
	 */
	private static void printUserMenu(User theUser, Scanner sc) {
		
		// print a summary of the user's accounts
		theUser.printAccountsSummary();
		
		// init 
		int choice;
		
		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", 
					theUser.getFirstName());
			System.out.println("  1) Show account transaction history");
			System.out.println("  2) Withdrawal");
			System.out.println("  3) Deposit");
			System.out.println("  4) Transfer");
			System.out.println("  5) Quit");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}
		} while( choice < 1 || choice > 5);
		
		// process the choice
		switch (choice) {
			case 1:
				ATM.showTransHistory(theUser, sc);
				break;
			case 2:
				ATM.withdrawFunds(theUser, sc);
				break;
			case 3:
				ATM.depositFunds(theUser, sc);
				break;
			case 4:
				ATM.transferFunds(theUser, sc);
				break;
		}
		
		// redisplay this menu until the user wants to quit
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
			
		
	}

	/**
	 * Transfer funds from one account to another
	 * @param theUser	user who's making transfer	
	 * @param sc		scanner from main
	 */
	private static void transferFunds(User theUser, Scanner sc) {

		// inits
		int fromAcct;
		int toAcct;
		int acctsCount;
		double amount;
		double acctBal;
		
		// Get number of accounts from user
		acctsCount = theUser.numAccounts();
		
		// get the account to transfer from. 
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"
					+ "to transfer FROM: ", acctsCount);
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= acctsCount) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(fromAcct < 0 || fromAcct >= acctsCount);
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get the account to transfer to.
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"
					+ "to transfer TO: ", acctsCount);
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= acctsCount) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(toAcct < 0 || toAcct >= acctsCount);
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max: $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than account balance of $%.02f.\n", acctBal);
			}
			
		} while (amount < 0 || amount > acctBal);
		
		// do transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, 
				String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, 
				String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct)));
		
	}

	/**
	 * Deposit funds to user account.
	 * @param theUser	the user to deposit funds
	 * @param sc		scanner from main
	 */
	private static void depositFunds(User theUser, Scanner sc) {

		// inits
		int theAcct;
		int acctsCount;
		double amount;
		String memo;
		
		// get accounts count
		acctsCount = theUser.numAccounts();
		
		// get the account to transfer from. 
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"
					+ "to deposit TO: ", acctsCount);
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= acctsCount) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAcct < 0 || theAcct >= acctsCount);
		
		// get the amount to transfer
		do {
			System.out.println("Enter the amount to deposit $");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 			
		} while (amount < 0);
		
		// gobble up the rest of the previous input
		sc.nextLine();
				
		// get a memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		// do deposit
		theUser.addAcctTransaction(theAcct, amount, memo);
		
	}

	/**
	 * Withdraw funds from account
	 * @param theUser	the user to withdraw funds
	 * @param sc		scanner from main
	 */
	private static void withdrawFunds(User theUser, Scanner sc) {
		
		// inits
		int theAcct;
		int acctsCount;
		String memo;
		double amount;
		double acctBal;
		
		// Get number of accounts
		acctsCount = theUser.numAccounts();
		
		// get the account to transfer from. 
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"
					+ "to withdraw FROM: ", acctsCount);
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= acctsCount) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAcct < 0 || theAcct >= acctsCount);
		
		acctBal = theUser.getAcctBalance(theAcct);
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max: $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than account balance of $%.02f.\n", acctBal);
			}
			
		} while (amount < 0 || amount > acctBal);
		
		// gobble up the rest of the previous input
		sc.nextLine();
		
		// get a memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		// do withdrawal
		theUser.addAcctTransaction(theAcct, -1*amount, memo);
		
	}

	/**
	 * Show the transaction history.
	 * @param theUser 	The user whose account transactions are shown.
	 * @param sc		Scanner from main.
	 */
	private static void showTransHistory(User theUser, Scanner sc) {
		
		int theAcct;
		int acctsCount;
		
		// get accounts count
		acctsCount = theUser.numAccounts();
		
		// get account whose transaction history to look at.
		do {
			System.out.printf("Enter the number of account (1-%d) to\n" +
					"view transactions.", acctsCount);
			theAcct = sc.nextInt() - 1;
			
			if (theAcct < 0 || theAcct >= acctsCount) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAcct < 0 || theAcct >= acctsCount);
		
		theUser.printAcctTransHistory(theAcct);
		
		
	}

	/**
	 * Main menu prompt to login
	 * @param theBank	Bank object that has the user
	 * @param sc		Scanner from main
	 * @return			Logged in user
	 */
	private static User mainMenuPrompt(Bank theBank, Scanner sc) {
		// inits
		String userID;
		String pin;
		User authUser;
		
		// Prompt user until correct uuid and pin is reached.
		do {
			System.out.printf("\n\n Welcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();
			
			// try to get user object
			authUser = theBank.userLogin(userID, pin);
			
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + "Please try again.");
			}
			
		} while( authUser == null ); // continue looping until successful login
		
		return authUser;
	}

}
