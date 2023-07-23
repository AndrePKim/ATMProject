import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank bank) {
        //set account name and holder
        this.name = name;
        this.holder = holder;
        //get account universal ID
        this.uuid = bank.getNewAccountUUID();
        //init transactions
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;
    }
}
