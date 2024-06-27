import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private String firstName;

    private String lastName;

    private String uuid;

    private byte pinHash[];

    private ArrayList<Account> accounts;

    public User(String firstname,String lastname,String pin,Bank theBank){

        this.firstName = firstname;

        this.lastName = lastname;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error no such algorithmn");
            e.printStackTrace();
            System.exit(1);
        }

        this.uuid =  theBank.getNewUserUUID();

        this.accounts = new ArrayList<Account>();

        System.out.printf("New User %s, %s with ID %s created.\n",firstName,lastName,this.uuid);

    }

    public void addAccount(Account acc){
        this.accounts.add(acc);
    }

    public String getUUID(){
        return uuid;
    }

    public boolean validatepin(String apin){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(apin.getBytes()),this.pinHash);
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("error no such algorithmn");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public String getFirstname(){
        return firstName;
    }


}