import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, Account inAccount, String memo) {
        this(amount, inAccount);  // Call the other constructor
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }

    public Account getInAccount() {
        return this.inAccount;
    }
}
