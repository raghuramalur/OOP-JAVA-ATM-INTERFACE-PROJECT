import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> customers;
    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.customers = new ArrayList<User>();
    }

    public String getNewUserUUID() {
        String uuid;
        boolean unique;
        Random rng = new Random();

        do {
            uuid = String.format("%06d", rng.nextInt(1000000));
            unique = true;
            for (User u : this.customers) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    unique = false;
                    break;
                }
            }
        } while (!unique);

        return uuid;
    }

    public String getNewAccountUUID() {
        String uuid;
        boolean unique;
        Random rng = new Random();

        do {
            uuid = String.format("%06d", rng.nextInt(1000000));
            unique = true;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    unique = false;
                    break;
                }
            }
        } while (!unique);

        return uuid;
    }

    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }

    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.customers.add(newUser);
        return newUser;
    }

    public User userLogin(String userID, String pin) {
        for (User u : this.customers) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
