import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Main {
    private static Scanner input = new Scanner(System.in);
    private static boolean successful = false;
    private static List<Savings> applicableSavings = new ArrayList<Savings>();
    private static List<Chequings> applicableChequings = new ArrayList<Chequings>();
    private static String name = "";

    public static void main(String[] args) {

        FileIO.sAccountDeserialization();

        FileIO.read();

        login();

        if (successful) {
            menu();
        }


//        String Owner = "Arjun";
//        String name = "Savings 2";
//        double balance = 1000;
//        double interestRate = 0.05;
//        int transferLimit = 5;
//        int transferCount = 0;
//        boolean compounding = true;
//
//        for (int i = 0; i < 10; i++) {
//            FileIO.addSavings(new Savings(Owner, "Savings " + i, balance, interestRate, transferLimit, transferCount, compounding));
//        }



        FileIO.sAccountSerialization();

        FileIO.write();

    }

    public static void login() {
        while (true) {
            System.out.println("Enter L to login, R to register, or X to exit ");
            String choice = input.next();
            if (choice.equalsIgnoreCase("L")) {
                System.out.println("Enter username");
                String username = input.next();
                System.out.println("Enter password");
                String password = input.next();
                if (FileIO.getUsernames().contains(username)) {
                    int index = FileIO.getUsernames().indexOf(username);
                    if (FileIO.getPasswords().get(index).equals(password)) {
                        System.out.println("Login successful");

                        name = username;
                        successful = true;
                        break;
                    } else {
                        System.out.println("Login failed");
                    }
                }
                else {
                    System.out.println("Login failed");
                }
            } else if (choice.equalsIgnoreCase("R")) {
                System.out.println("Enter username");
                String username = input.next();
                System.out.println("Enter password");
                String password = input.next();

                FileIO.getUsernames().add(username);
                FileIO.getPasswords().add(password);
            } else if (choice.equalsIgnoreCase("X")) {
                System.out.println("Goodbye");
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    public static void menu() {
        applicableSavings = FileIO.getSavingsAccount().stream().filter(p -> p.getOwner().equals(name)).collect(Collectors.toList());
        applicableChequings = FileIO.getChequingsAccount().stream().filter(p -> p.getOwner().equals(name)).collect(Collectors.toList());

        System.out.println("You have " + applicableChequings.size() + " chequing(s) accounts and " + applicableSavings.size() + " saving(s) accounts");

        String menuSelection = "_";
        while (true) {
            System.out.println("Would you like to create a new account (C), modify an existing account (M), delete an account (D)? (X to exit) ");
            System.out.println(menuSelection);
            menuSelection = input.next();
            if (menuSelection.equalsIgnoreCase("C")) {

            } else if (menuSelection.equalsIgnoreCase("M")) {

            } else if (menuSelection.equalsIgnoreCase("D")) {
                deleteAccount();
                System.out.println("1");
            } else if (menuSelection.equalsIgnoreCase("X")) {
                System.out.println("Goodbye");
                break;
            } else {
                System.out.println("2");
                System.out.println("Invalid Input");
            }
        }

    }

    public static void deleteAccount() {
        while (true) {
            System.out.println("Would you like to delete a Chequing or a Savings account (C / S)? (X to exit)");
            String choice = input.next();
            if (choice.equalsIgnoreCase("C")) {
                if (applicableChequings.isEmpty()) {
                    System.out.println("You don't have a Chequing account");
                } else {

                }
            } else if (choice.equalsIgnoreCase("S")) {
                if (applicableSavings.isEmpty()) {
                    System.out.println("You don't have a Savings account");
                } else {
                    System.out.println("Available Accounts, enter account number you would like to delete");
                    for (int i = 1; i <= applicableSavings.size(); i++) {
                        System.out.println(i + ". " + applicableSavings.get(i - 1).getName());
                    }
                    int selection = input.nextInt();
                    while (true) {
                        if (selection > applicableSavings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = input.nextInt();
                        } else {
                            break;
                        }
                    }
                    System.out.println("Deleting option " + selection );
                    selection--;

                    int finalSelection = selection;
                    FileIO.getSavingsAccount().removeIf(p -> p.getName().equals(applicableSavings.get(finalSelection).getName()));
                    applicableSavings.remove(selection);

                    System.out.println("You have " + applicableChequings.size() + " chequing(s) accounts and " + applicableSavings.size() + " saving(s) accounts");
                }
            } else if (choice.equalsIgnoreCase("x")) {
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}
