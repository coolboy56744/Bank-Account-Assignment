import java.io.Serializable;

class Chequings extends Account implements Serializable {
    Chequings() {
        super();


    }

    Chequings(String owner, int balance, double interestRate) {
        super();
        this.setOwner(owner);
        this.setBalance(balance);
        this.setInterestRate(interestRate);

    }

}