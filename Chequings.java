import java.io.Serializable;

class Chequings extends Account implements Serializable {
    private static final long serialVersionUID = -8633355382431018442L;

    Chequings() {
        super();


    }

    Chequings(String owner, String name, double balance, double interestRate) {
        super();
        this.setOwner(owner);
        this.setName(name);
        this.setBalance(balance);
        this.setInterestRate(interestRate);

    }

}