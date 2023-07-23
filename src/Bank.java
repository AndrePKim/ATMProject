import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID() {
        //init
        String uuid;
        Random random = new Random();
        int length = 6;
        boolean nonUnique;
        //continue generating uuid until unique
        do {
            //generate uuid
            uuid = "";
            for (int i=0; i<length; i++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }
            //check if unique
            nonUnique = false;
            for (User u: this.users) {
                if (uuid.equals(u.getUUID())) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique = true);
        //return unique uuid
        return uuid;
    }
    public String getNewAccountUUID() {
        //init
        String uuid;
        Random random = new Random();
        int length = 6;
        boolean nonUnique;
        //continue generating uuid until unique
        do {
            //generate uuid
            uuid = "";
            for (int i=0; i<length; i++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }
            //check if unique
            nonUnique = false;
            for (Account a: this.accounts) {
                if (uuid.equals(a.getUUID())) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique = true);
        //return unique uuid
        return uuid;
    }
    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }
    public User addUser(String firstName, String lastName, String pin) {
        //create new user object and add to bank list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);
        //create savings account and add to both holder and bank lists
        Account newAccount = new Account("Savings",newUser,this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);
        return newUser;
    }
    public User userLogin(String userID, String pin) {
        //search list of users
        for (User u: this.users) {
            if(u.getUUID().equals(userID) && u.validatePin(pin)) {
                return u;
            }
        }
        //if user not found or incorrect pin
        return null;
    }
    public String getName() {
        return this.name;
    }
}
