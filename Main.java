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

//
//        String Owner = "Arjun";
//        double balance = 1000;
//        double interestRate = 0.05;
//        int transferLimit = 5;
//        int transferCount = 0;
//        boolean compounding = true;
//
//        FileIO.addSavings(new Savings(Owner, balance, interestRate, transferLimit, transferCount, compounding));
//
//        FileIO.addSavings(new Savings(Owner, balance, interestRate, transferLimit, transferCount, compounding));
//
//        System.out.println(FileIO.getSavingsAccount());
//
//        FileIO.sAccountSerialization();
//        File.write();
    }

    public static void login() {
        while (true) {
            System.out.println("Enter L to login, R to register, or X to exit");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("L")) {
                System.out.println("Enter username");
                String username = input.nextLine();
                System.out.println("Enter password");
                String password = input.nextLine();
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
                String username = input.nextLine();
                System.out.println("Enter password");
                String password = input.nextLine();

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

        String choice;
        while (true) {
            System.out.println("Would you like to create a new account (C), modify an existing account (M), delete an account (D)? (X to exit)");
            choice = input.nextLine();
            if (choice.equalsIgnoreCase("C")) {
                
            } else if (choice.equalsIgnoreCase("M")) {

            } else if (choice.equalsIgnoreCase("D")) {

            } else if (choice.equalsIgnoreCase("X")) {
                System.out.println("Goodbye");
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }

    }
}