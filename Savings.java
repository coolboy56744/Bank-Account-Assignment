import java.io.Serializable;

class Savings extends Account implements Serializable {
    private int transferLimit;
    private int transferCount;
    private boolean compounding;

    Savings() {
        super();

    }

    Savings(String owner, String name, double balance, double interestRate, int transferLimit, int transferCount, boolean compounding) {
        this.setOwner(owner);
        this.setName(name);
        this.setBalance(balance);
        this.setInterestRate(interestRate);
        this.transferLimit = transferLimit;
        this.transferCount = transferCount;
        this.compounding = compounding;
    }

    public int getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(int transferLimit) {
        this.transferLimit = transferLimit;
    }

    public int getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(int transferCount) {
        this.transferCount = transferCount;
    }

    public boolean getCompounding() {
        return compounding;
    }

    public void setCompounding(boolean compounding) {
        this.compounding = compounding;
    }


}
