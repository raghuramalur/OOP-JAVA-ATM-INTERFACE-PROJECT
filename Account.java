import java.util.ArrayList;

public class Account {
    private String name;

    private String uuid;

    private double balance;

    private User holder;

    private ArrayList<Transaction> transactions;

    public Account(String name,User holder,Bank theBank){
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID(){
        return uuid;
    }

    public String getSummaryLine(){
        double balance = this.getBalance();
        //format the summary line depending on balance is overdraft
        if(balance >= 0){
            return String.format("%s : $%.02f : %s" ,this.uuid , balance , this.name);
        }else{
            return String.format("%s : $(%.02f) : %s" ,this.uuid , balance , this.name);
        }
    }

    public double getBalance(){
        double balance = 0;
        for(Transaction t : transactions){
            balance+=t.getAmount();
        }

        return balance;
    }

    public void printTransHistory(){
        System.out.printf("Transaction history for account %s",this.uuid);
        for(int t = this.transactions.size()-1;t>=0;t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amt,String memo){
        Transaction trans = new Transaction(amt, this, memo);
        this.transactions.add(trans);
    }
}
