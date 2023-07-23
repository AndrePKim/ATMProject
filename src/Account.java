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
    public String getSummaryLine() {
        //get balance
        double balance = this.getBalance();
        //format summary line based on sign
        if (balance>=0) {
            return String.format("%s : $%0.2f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%0.2f) : %s", this.uuid, balance, this.name);
        }
    }
    public double getBalance() {
        double balance = 0;
        for (Transaction t: this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }
    public void printTransHistory() {
        System.out.println("Transaction history for account " + this.uuid);
        for (int i=this.transactions.size()-1; i>=0; i--) {
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }
    public void addTransaction(double amount,String note) {
        Transaction newTrans = new Transaction(amount,note,this);
        this.transactions.add(newTrans);
    }
}
