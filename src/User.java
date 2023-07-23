import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[]; //MD5 hash of user's pin number
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank bank) {
        //set user's name
        this.firstName = firstName;
        this.lastName = lastName;
        //store pin's MD5 hash for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        //get new and unique universal ID for user
        this.uuid = bank.getNewUserUUID();
        //create empty list of accounts
        this.accounts = new ArrayList<Account>();
        //print log message
        System.out.println("Success");

    }
    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }
    public String getUUID() {
        return this.uuid;
    }
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public int numAccounts() {
        return this.accounts.size();
    }
    public void printAccountsSummary() {
        System.out.println(this.firstName + "'s accounts summary");
        for (int i=0; i<this.accounts.size(); i++) {
            System.out.println(i+1 + ")" + this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }
    public void printAcctTransHistory(int acc) {
        this.accounts.get(acc).printTransHistory();
    }
    public double getAcctBalance(int acc) {
        return this.accounts.get(acc).getBalance();
    }
    public String getAcctUUID(int acc) {
        return this.accounts.get(acc).getUUID();
    }
    public void addAcctTransaction(int acc,double amount,String note) {
        this.accounts.get(acc).addTransaction(amount,note);
    }
}
