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
            choice = sc.nextLine();

        }
    }
    }

    
    

    
}
