import java.util.Scanner;

public class Atm {
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Bank thebank = new Bank("State Bank of India");
        User auser = thebank.addUser("Raghuram", "Aluru", "724142");

        Account newaccount = new Account("Checking", auser, thebank);

        auser.addAccount(newaccount);
        thebank.addAccount(newaccount);

        User curuser;

        while(true){
            curuser = Atm.mainMenuPrompt(thebank,sc);
            Atm.printUserMenu(curuser,sc);
        }
    }

    

    public static User mainMenuPrompt(Bank theBank,Scanner sc){
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s \n\n", theBank.getName());
            System.out.printf("Enter User ID:  ");
            userID = sc.nextLine();
            System.out.printf("Enter the pin:  ");
            pin = sc.nextLine();
            authUser = theBank.userlogin(userID, pin);
            if(authUser == null){
                System.out.printf("Incorrect User ID or pin.  " + "Please try again. ");
            }
        } while (authUser == null);//continue looping until valid user is found

        return authUser;
    }

    public static void printUserMenu(User curuser,Scanner sc){

        curuser.printAccountSummary();

        int choice;

        do{
            System.out.printf("Welcome %s, what would you like to do?" , curuser.getFirstname());

            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Make Withdrawal");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Exit");
            System.out.println("\n");
            System.out.println("Enter choice");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5){
                System.out.println("Invalid choice please choose from the above choices");
            }

        }while(choice < 1 || choice > 5);

        switch(choice){
            case 1:
                Atm.showTransactionHistory(curuser,sc);
                break;
            case 2:
                Atm.WithdrawalFunds(curuser,sc);
                break;
            case 3:
                Atm.depositFunds(curuser,sc);
                break;
            case 4:
                Atm.transferFunds(curuser,sc);
                break;

            
        }

        if(choice != 5){
            Atm.printUserMenu(curuser, sc);
        }


    }

    public static void showTransactionHistory(User curuser,Scanner sc){
        int theAcc;

        do {
            System.out.printf("Enter the account number (1-%d) of the account\n whose transactions you want to see : ",curuser.numAccounts());
            theAcc = sc.nextInt()-1;
            if(theAcc<0 && theAcc >= curuser.numAccounts()){
                System.out.println("Invalid Account try again");
            }
        } while (theAcc < 0 || theAcc >= curuser.numAccounts());

        curuser.printAcctTransHistory(theAcc);
    }

    public static void transferFunds(User theuser , Scanner sc){
         int fromAcct;
         int toAcct;
         double amount;
         double acctBal;

         do {
            System.out.printf("Enter the account number (1-%d) of the account\n" + "to transfer from :  ", theuser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theuser.numAccounts()){
                System.out.println("Invalid Account try again");
            }
         } while (fromAcct < 0 || fromAcct >= theuser.numAccounts());
         acctBal = theuser.getAcctBalance(fromAcct);

         do {
            System.out.printf("Enter the account number (1-%d) of the account\n" + "to transfer to :  " ,theuser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theuser.numAccounts()){
                System.out.println("Invalid Account try again");
            }
         } while (toAcct < 0 || toAcct >= theuser.numAccounts());

         do {
            System.out.println("Enter the amount you want to transfer");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }
            else if(amount > acctBal){
                System.out.println("Amount is Greater than Account balance");
            }
         } while (amount < 0 || amount > acctBal);

         theuser.addAcctTransaction(fromAcct,-1*amount,String.format(" Transfer to account %s" , theuser.getAcctUUID(toAcct)));
         theuser.addAcctTransaction(toAcct,-1*amount,String.format(" Transfer to account %s" , theuser.getAcctUUID(fromAcct)));
    }

    public static void WithdrawalFunds(User theuser,Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the account number (1-%d) of the account\n" + "to transfer from :  ",theuser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theuser.numAccounts()){
                System.out.println("Invalid Account try again");
            }
        } while (fromAcct < 0 || fromAcct >= theuser.numAccounts());
        acctBal = theuser.getAcctBalance(fromAcct);

        do {
            System.out.println("Enter the amount you want to transfer");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }
            else if(amount > acctBal){
                System.out.println("Amount is Greater than Account balance");
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter the memo");
        String memo = sc.nextLine();

        theuser.addAcctTransaction(fromAcct, -1*amount, memo);


    }

    public static void depositFunds(User theuser, Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the account number (1-%d) of the account\n" + "to transfer to :  ",theuser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theuser.numAccounts()){
                System.out.println("Invalid Account try again");
            }
        } while (toAcct < 0 || toAcct >= theuser.numAccounts());
        acctBal = theuser.getAcctBalance(toAcct);

        do {
            System.out.println("Enter the amount you want to transfer");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }
        } while (amount < 0);

        sc.nextLine();

        System.out.println("Enter the memo");
        String memo = sc.nextLine();

        theuser.addAcctTransaction(toAcct, amount, memo);
    }
    


    



    
}

    
    

    

