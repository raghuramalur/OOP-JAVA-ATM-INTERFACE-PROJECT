import java.util.Scanner;

public class Atm {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("State Bank of India");

        User aUser = theBank.addUser("Raghuram", "Aluru", "724142");
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

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
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.printf("Enter User ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter PIN: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.printf("Incorrect User ID or PIN. Please try again.\n");
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
            System.out.println("  2) Make a withdrawal");
            System.out.println("  3) Make a deposit");
            System.out.println("  4) Transfer funds");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.printf("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5.");
            }
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                Atm.showTransactionHistory(curUser, sc);
                break;
            case 2:
                Atm.withdrawFunds(curUser, sc);
                break;
            case 3:
                Atm.depositFunds(curUser, sc);
                break;
            case 4:
                Atm.transferFunds(curUser, sc);
                break;
            case 5:
                sc.nextLine();  // Consume newline
                System.out.println("Exiting. Have a nice day!");
                System.exit(0);
                break;
        }

        if (choice != 5) {
            Atm.printUserMenu(curUser, sc);
        }
    }

    public static void showTransactionHistory(User curUser, Scanner sc) {
        int theAcct;

        do {
            System.out.printf("Enter the account number (1-%d) of the account\nwhose transactions you want to see: ", curUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= curUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= curUser.numAccounts());

        curUser.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User curUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the account number (1-%d) of the account to transfer from: ", curUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= curUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= curUser.numAccounts());

        acctBal = curUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the account number (1-%d) of the account to transfer to: ", curUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= curUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= curUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max %.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0.");
            } else if (amount > acctBal) {
                System.out.println("Amount exceeds account balance.");
            }
        } while (amount < 0 || amount > acctBal);

        curUser.addAcctTransaction(fromAcct, -amount, String.format("Transfer to account %s", curUser.getAcctUUID(toAcct)));
        curUser.addAcctTransaction(toAcct, amount, String.format("Transfer from account %s", curUser.getAcctUUID(fromAcct)));
    }

    public static void withdrawFunds(User curUser, Scanner sc) {
        int fromAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the account number (1-%d) of the account to withdraw from: ", curUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= curUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= curUser.numAccounts());

        acctBal = curUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the amount to withdraw (max %.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0.");
            } else if (amount > acctBal) {
                System.out.println("Amount exceeds account balance.");
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();  // Consume newline

        System.out.printf("Enter a memo: ");
        String memo = sc.nextLine();

        curUser.addAcctTransaction(fromAcct, -amount, memo);
    }

    public static void depositFunds(User curUser, Scanner sc) {
        int toAcct;
        double amount;

        do {
            System.out.printf("Enter the account number (1-%d) of the account to deposit to: ", curUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= curUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= curUser.numAccounts());

        do {
            System.out.printf("Enter the amount to deposit: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0.");
            }
        } while (amount < 0);

        sc.nextLine();  // Consume newline

        System.out.printf("Enter a memo: ");
        String memo = sc.nextLine();

        curUser.addAcctTransaction(toAcct, amount, memo);
    }
}
