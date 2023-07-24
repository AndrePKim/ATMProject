import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        //init
        Scanner scan = new Scanner(System.in);
        Bank bank = new Bank("Banco De Banco");
        //add user, also create savings account
        User user = bank.addUser("Andre","Kim","1234");
        //add checking account for user
        Account nAccount = new Account("Checking",user,bank);
        user.addAccount(nAccount);
        bank.addAccount(nAccount);
        User curUser;
        while (true) {
            curUser = ATM.mainMenuPrompt(bank, scan);
            ATM.printUserMenu(curUser,scan);
        }
    }
    public static User mainMenuPrompt(Bank bank, Scanner scan) {
        //init
        String userID;
        String pin;
        User authUser;
        //prompt the user for user ID/pin combo until correct
        do {
            System.out.println("\nWelcome to " + bank.getName());
            System.out.print("Enter the ID: ");
            userID = scan.nextLine();
            System.out.print("Enter pin: ");
            pin = scan.nextLine();
            authUser = bank.userLogin(userID,pin);
            if (authUser==null) {
                System.out.println("Incorrect user ID/pin combination. Please try again.");
            }
        } while (authUser==null);
        return authUser;
    }
    public static void printUserMenu(User user, Scanner scan) {
        //print summary of user's account
        user.printAccountsSummary();
        // init
        int choice;
        //user menu
        do {
            System.out.println("Welcome " + user.getFirstName() + ", what would you like to do?");
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Withdrawal");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Change PIN");
            System.out.println("    6) Quit");
            System.out.print("Enter choice: ");
            choice = scan.nextInt();
            if (choice<1 || choice>6) {
                System.out.println("Invalid choice. Please choose 1-6");
            }
        } while (choice<1 || choice>6);
        //process choice
        switch (choice) {
            case 1 -> ATM.showTransHistory(user, scan);
            case 2 -> ATM.withdrawFunds(user, scan);
            case 3 -> ATM.depositFunds(user, scan);
            case 4 -> ATM.transferFunds(user, scan);
            case 5 -> ATM.changePIN(user, scan);
        }
        //recurse user menu if cancel
        if (choice!=6) {
            ATM.printUserMenu(user,scan);
        }
    }
    public static void showTransHistory(User user, Scanner scan) {
        int account;
        //get account history of chosen account
        do {
            System.out.println("\nEnter the number (1-" + user.numAccounts() + ") of the account\n" +
                    "whose transactions you want to see:");
            account = scan.nextInt()-1;
            if (account<0 || account>=user.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (account<0 || account>=user.numAccounts());
        //print transaction history
        user.printAcctTransHistory(account);
    }
    public static void withdrawFunds(User user, Scanner scan) {
        //init
        int fromAcct;
        double amount;
        double acctBal;
        String note;
        //get account to withdraw from
        do {
            System.out.println("\nEnter the number (1-" + user.numAccounts() + ") of the account\n" +
                    "to withdraw from: ");
            fromAcct = scan.nextInt()-1;
            if (fromAcct<0 || fromAcct>=user.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct<0 || fromAcct>=user.numAccounts());
        //get maximum balance of account
        acctBal = user.getAcctBalance(fromAcct);
        //get amount to withdraw
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
            amount = scan.nextDouble();
            if (amount<0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount>acctBal) {
                System.out.printf("Amount must not be greater than balance of $%.02f.\n", acctBal);
            }
        } while (amount<0 || amount>acctBal);
        //gobble up rest of previous input
        scan.nextLine();
        //get the note
        System.out.print("Enter a note: ");
        note = scan.nextLine();
        //do the withdrawal
        user.addAcctTransaction(fromAcct,-1*amount,note);
    }
    public static void depositFunds(User user, Scanner scan) {
        //init
        int toAcct;
        double amount;
        String note;
        //get account to deposit to
        do {
            System.out.println("\nEnter the number (1-" + user.numAccounts() + ") of the account\n" +
                    "to deposit to: ");
            toAcct = scan.nextInt()-1;
            if (toAcct<0 || toAcct>=user.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct<0 || toAcct>=user.numAccounts());
        //get amount to deposit
        do {
            System.out.print("Enter the amount to deposit: $");
            amount = scan.nextDouble();
            if (amount<0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount<0);
        //gobble up rest of previous input
        scan.nextLine();
        //get the note
        System.out.print("Enter a note: ");
        note = scan.nextLine();
        //do the deposit
        user.addAcctTransaction(toAcct,amount,note);
    }
    public static void transferFunds(User user, Scanner scan) {
        //init
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        //get account to transfer from
        do {
            System.out.println("\nEnter the number (1-" + user.numAccounts() + ") of the account\n" +
                    "to transfer from: ");
            fromAcct = scan.nextInt()-1;
            if (fromAcct<0 || fromAcct>=user.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct<0 || fromAcct>=user.numAccounts());
        //get maximum balance of account
        acctBal = user.getAcctBalance(fromAcct);
        //get account to transfer to
        do {
            System.out.println("Enter the number (1-" + user.numAccounts() + ") of the account\n" +
                    "to transfer to: ");
            toAcct = scan.nextInt()-1;
            if (toAcct<0 || toAcct>=user.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct<0 || toAcct>=user.numAccounts());
        //get amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = scan.nextDouble();
            if (amount<0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount>acctBal) {
                System.out.printf("Amount must not be greater than balance of $%.02f.\n", acctBal);
            }
        } while (amount<0 || amount>acctBal);
        //do the transfer
        user.addAcctTransaction(fromAcct,-1*amount,String.format("Transfer to account %s",user.getAcctUUID(toAcct)));
        user.addAcctTransaction(toAcct,amount,String.format("Transfer from account %s",user.getAcctUUID(fromAcct)));
    }
    public static void changePIN(User user, Scanner scan) {
        //init
        String currPIN;
        String newPIN;
        String checkNewPIN;
        boolean authPIN;
        //gobble up rest of previous input
        scan.nextLine();
        //reenter pin for checking
        do {
            System.out.print("\nEnter your current PIN: ");
            currPIN = scan.nextLine();
            authPIN = false;
            authPIN = user.validatePin(currPIN);
            if (!authPIN) {
                System.out.println("Invalid PIN. Please try again.");
            }
        } while (!authPIN);
        //enter new pin
        do {
            System.out.print("Enter your new PIN: ");
            newPIN = scan.nextLine();
            System.out.print("Reenter your new PIN to confirm: ");
            checkNewPIN = scan.nextLine();
            if (newPIN.equals(checkNewPIN)) {
                //do the changePIN
                user.changePIN(newPIN);
            } else if (newPIN.equals(currPIN)) {
                System.out.println("Entered PIN is the same as previous PIN. Please try again.");
                authPIN = false;
            } else {
                System.out.println("Entered PIN does not match new PIN. Please try again.");
                authPIN = false;
            }
        } while (authPIN==false);
    }
}