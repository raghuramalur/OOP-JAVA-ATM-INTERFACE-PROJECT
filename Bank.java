import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

public class Bank {
    private String name;

    private ArrayList<User> customers;

    //added accounts separately even though users have list of accounts but it is convinient

    private ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.customers = new ArrayList<User>();
    }

    public String getNewUserUUID(){
        String uuid;
        boolean unique = true;
        Random rg = new Random();
        while (unique) {
            
            uuid = String.format("%06d", rg.nextInt(1000000));
            boolean check = true;
            for(Account acc : accounts){
                if(acc.getUUID().equals(uuid)){
                    check = false;
                    break;
                }
            }

            if(check){
                unique = false;
                return uuid;
            }


        }

        return "all 6 digit uuid's are used up";

    }
    public String getNewAccountUUID(){
        String uuid;
        boolean unique = true;
        Random rg = new Random();
        while (unique) {
            
            uuid = String.format("%06d", rg.nextInt(1000000));
            boolean check = true;
            for(User cst : customers){
                if(cst.getUUID().equals(uuid)){
                    check = false;
                    break;
                }
            }

            if(check){
                unique = false;
                return uuid;
            }


        }

        return "all 6 digit uuid's are used up";
    }

    public void addAccount(Account acc){//could have removed this as now account wiull be added in back inly for new user
        this.accounts.add(acc);
    }

    public User addUser(String firstname,String lastname,String pin){
        User newUser = new User(firstname,lastname,pin,this);
        this.customers.add(newUser);

        Account newaccount = new Account(lastname, newUser, this);
        newUser.addAccount(newaccount);
        this.addAccount(newaccount);
        return newUser;
    }

    public User userlogin(String userID,String pin){
        for(User u : customers){
            if(u.getUUID().compareTo(userID) == 0 && (u.validatepin(pin))){
                return u;
            }
        }   

        return null;
    }

    


}
