import java.io.Serializable;

class Account implements Serializable {
    private String owner;
    private double balance;
    private double interestRate;
    private String name;

    public Account() {
        this.owner = "";
        this.name = "";
        this.balance = 0;
        this.interestRate = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
