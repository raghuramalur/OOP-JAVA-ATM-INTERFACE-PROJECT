import java.util.Scanner;

public class Atm {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("State Bank of India");
        User aUser = theBank.addUser("Raghuram", "Aluru", "724142");

        // Automatically create the first account for the user
        aUser.createNewAccount();

        User curUser;

        while (true) {
            curUser = Atm.mainMenuPrompt(theBank, sc);
            Atm.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s \n\n", theBank.getName());
            System.out.printf("Enter User ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter the pin: ");
            pin = sc.nextLine();
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.printf("Incorrect User ID or pin. Please try again. ");
            }
        } while (authUser == null);

        return authUser;
    }

    public static void printUserMenu(User curUser, Scanner sc) {
        curUser.printAccountSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?\n", curUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Make Withdrawal");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Create a new account");
            System.out.println("  6) Exit");
            System.out.println("\n");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice < 1 || choice > 6) {
                System.out.println("Invalid choice, please choose from the above options");
            }

        } while (choice < 1 || choice > 6);

        switch (choice) {
            case 1:
                Atm.showTransactionHistory(curUser, sc);
                break;
            case 2:
                Atm.withdrawalFunds(curUser, sc);
                break;
            case 3:
                Atm.depositFunds(curUser, sc);
                break;
            case 4:
                Atm.transferFunds(curUser, sc);
                break;
            case 5:
                curUser.createNewAccount();
                break;
        }

        if (choice != 6) {
            Atm.printUserMenu(curUser, sc);
        }
    }

    public static void showTransactionHistory(User curUser, Scanner sc) {
        int theAcc;

        do {
            System.out.printf("Enter the account number (1-%d) of the account\n whose transactions you want to see: ", curUser.numAccounts());
            theAcc = sc.nextInt() - 1;
            if (theAcc < 0 || theAcc >= curUser.numAccounts()) {
                System.out.println("Invalid Account, try again");
            }
        } while (theAcc < 0 || theAcc >= curUser.numAccounts());

        curUser.printAcctTransHistory(theAcc);
    }

    public static void transferFunds(User theUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the account number (1-%d) of the account to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account, try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the account number (1-%d) of the account to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account, try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.println("Enter the amount you want to transfer: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0");
            } else if (amount > acctBal) {
                System.out.println("Amount is greater than account balance");
            }
        } while (amount < 0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct)));
    }

    public static void withdrawalFunds(User theUser, Scanner sc) {
        int fromAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the account number (1-%d) of the account to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account, try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.println("Enter the amount you want to withdraw: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0");
            } else if (amount > acctBal) {
                System.out.println("Amount is greater than account balance");
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter the memo: ");
        String memo = sc.nextLine();

        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    public static void depositFunds(User theUser, Scanner sc) {
        int toAcct;
        double amount;

        do {
            System.out.printf("Enter the account number (1-%d) of the account to deposit to: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account, try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.println("Enter the amount you want to deposit: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0");
            }
        } while (amount < 0);

        sc.nextLine();

        System.out.println("Enter the memo: ");
        String memo = sc.nextLine();

        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
