import java.io.Serializable;

class Chequings extends Account implements Serializable {
    Chequings() {
        super();


    }

    Chequings(String owner, String name, int balance, double interestRate) {
        super();
        this.setOwner(owner);
        this.setName(name);
        this.setBalance(balance);
        this.setInterestRate(interestRate);

    }

}
