import java.util.Scanner;

public class Atm {
    Scanner sc = new Scanner(System.in);
    Bank thebank = new Bank("State Bank of India");
    User auser = thebank.addUser("Raghuram", "Aluru", "724142");
    
}
