import java.util.*;

class Bank {

    String accountNumber;
    String designation;
    String name;
    long phone;
    double balance;
    Scanner sc; // class-level scanner

    // constructor
    Bank(Scanner sc) {
        this.sc = sc;
    }

    // generate random account number
    String generateAccountNumber() {
        String uniqueID = UUID.randomUUID().toString();
        return "ACC" + uniqueID.substring(0, 6).toUpperCase();
    }

    // create account
    void accountCreate() {
        System.out.print("Enter your title (Mr/Mrs/Ms): ");
        designation = sc.nextLine();

        System.out.printf("Enter your name (%s): ", designation);
        name = sc.nextLine();

        System.out.print("Enter your phone number: ");
        phone = sc.nextLong();
        sc.nextLine(); // consume newline

        accountNumber = generateAccountNumber();

        String firstName = name.split(" ")[0];
        System.out.printf("Welcome %s %s!\n", designation, firstName);
        System.out.println("Your account number is " + accountNumber);
        System.out.println("Your account will be up and running within a minute!");
    }

    // deposit money
    void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline

        balance += amount;

        System.out.println("Amount deposited successfully!");
        System.out.println("Updated Balance: ₹" + balance);
    }

    // withdraw money
    void withdraw() {
        System.out.print("Enter withdraw amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Amount withdrawn successfully! Updated Balance: ₹" + balance);
        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    // display account details
    void display() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + designation + " " + name);
        System.out.println("Phone Number: " + phone);
        System.out.println("Balance: ₹" + balance);
    }

    // check account match
    boolean findAccount(String accNum) {
        return this.accountNumber != null && this.accountNumber.equals(accNum);
    }
}

public class Main {

    // helper function to find account
    static Bank findAccount(Vector<Bank> accounts, String accNo) {
        for (Bank b : accounts) {
            if (b.findAccount(accNo)) {
                return b;
            }
        }
        return null; // not found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vector<Bank> accounts = new Vector<>();

        while (true) {
            System.out.println("\n==== Banking System Menu ====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Find Account (Single Account Details)");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    Bank newAcc = new Bank(sc);
                    newAcc.accountCreate();
                    accounts.add(newAcc);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    String accNoDep = sc.nextLine();
                    Bank accDep = findAccount(accounts, accNoDep);
                    if (accDep != null) accDep.deposit();
                    else System.out.println("Account not found!");
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    String accNoW = sc.nextLine();
                    Bank accW = findAccount(accounts, accNoW);
                    if (accW != null) accW.withdraw();
                    else System.out.println("Account not found!");
                    break;

                case 4:
                    System.out.print("Enter Sender Account Number: ");
                    String senderNo = sc.nextLine();
                    Bank sender = findAccount(accounts, senderNo);

                    System.out.print("Enter Receiver Account Number: ");
                    String receiverNo = sc.nextLine();
                    Bank receiver = findAccount(accounts, receiverNo);

                    if (sender != null && receiver != null) {
                        System.out.print("Enter transfer amount: ");
                        double amt = sc.nextDouble();
                        sc.nextLine();

                        if (amt <= sender.balance) {
                            sender.balance -= amt;
                            receiver.balance += amt;
                            System.out.println("Transfer successful! New Balance: ₹" + sender.balance);
                        } else {
                            System.out.println("Insufficient funds in sender account!");
                        }
                    } else {
                        System.out.println("Invalid sender or receiver account!");
                    }
                    break;

                case 5:
                    System.out.println("\n--- All Accounts ---");
                    for (Bank b : accounts) {
                        b.display();
                    }
                    break;

                case 6:
                    System.out.print("Enter account number: ");
                    String searchAcc = sc.nextLine();
                    Bank searchAccount = findAccount(accounts, searchAcc);
                    if(searchAccount != null) {
                        searchAccount.display();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 7:
                    System.out.println("Thank you! Exiting...");
                    sc.close();
                    return; // exits main safely

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
