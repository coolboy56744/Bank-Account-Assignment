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
            menuSelection = input.next();
            if (menuSelection.equalsIgnoreCase("C")) {
                createAccount();
            } else if (menuSelection.equalsIgnoreCase("M")) {
                modifyAccount();
            } else if (menuSelection.equalsIgnoreCase("D")) {
                deleteAccount();
            } else if (menuSelection.equalsIgnoreCase("X")) {
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }

    }

    public static void createAccount() {
        while (true) {
            System.out.println("Would you like to create a new Chequing or a Savings account (C / S)? (X to exit)");
            String choice = input.next();
            if (choice.equalsIgnoreCase("C")) {
                String Owner = name;
                System.out.println("What is the name of this account? ");
                String accountName = input.next();
                System.out.println("What is the balance of this account? ");
                double balance = Integer.parseInt(input.next());
                System.out.println("What is the interest rate of this account? ");
                double interestRate = Double.parseDouble(input.next());

                applicableChequings.add(new Chequings(Owner, accountName, balance, interestRate));
                FileIO.getChequingsAccount().add(applicableChequings.get(applicableChequings.size() - 1));

                System.out.println("Successfully created Chequing account");

                System.out.println("You have " + applicableChequings.size() + " chequing(s) accounts and " + applicableSavings.size() + " saving(s) accounts");
            } else if (choice.equalsIgnoreCase("S")) {
                String Owner = name;
                System.out.println("What is the name of this account? ");
                String accountName = input.next();
                System.out.println("What is the balance of this account? ");
                double balance = Integer.parseInt(input.next());
                System.out.println("What is the interest rate of this account? ");
                double interestRate = Double.parseDouble(input.next());
                System.out.println("how many transfers can this account do per session? ");
                int transferLimit = Integer.parseInt(input.next());
                int transferCount = 0;
                System.out.println("Does this account compound? (true / false)");
                boolean compounding = Boolean.parseBoolean(input.next());

                applicableSavings.add(new Savings(Owner, accountName, balance, interestRate, transferLimit, transferCount, compounding));
                FileIO.getSavingsAccount().add(applicableSavings.get(applicableSavings.size() - 1));

                System.out.println("Successfully created Savings account");

                System.out.println("You have " + applicableChequings.size() + " chequing(s) accounts and " + applicableSavings.size() + " saving(s) accounts");
            } else if (choice.equalsIgnoreCase("X")) {
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    public static void modifyAccount() {
        while (true) {
            System.out.println("Would you like to modify a Chequing or a Savings account (C / S)? (X to exit)");
            String choice = input.next();
            if (choice.equalsIgnoreCase("C")) {
                if (applicableChequings.isEmpty()) {
                    System.out.println("You don't have a Chequing account");
                } else {
                    System.out.println("Available Accounts\n enter account number you would like to modify");
                    for (int i = 1; i <= applicableChequings.size(); i++) {
                        System.out.println(i + ". " + applicableChequings.get(i - 1).getName());
                    }
                    int selection = input.nextInt();
                    while (true) {
                        if (selection > applicableChequings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = input.nextInt();
                        } else {
                            break;
                        }
                    }

                    selection--;

                    // start modification

                    int finalSelection = selection;
                    String temp;
                    while (true) {
                        System.out.println("\n1. Name of Account: " + applicableSavings.get(selection).getName());
                        System.out.println("2. Balance: " + applicableSavings.get(selection).getBalance());
                        System.out.println("3. Stop modifying \n");

                        System.out.println("Enter number of item you want to modify");
                        int modificationSelection = Integer.parseInt(input.next());

                        if (modificationSelection == 1) {
                            System.out.println("Enter new name");
                            temp = input.next();


                            String finalTemp = temp;
                            FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setName(finalTemp));
                            applicableSavings.get(selection).setName(temp);

                            System.out.println("Name Change complete");
                        } else if (modificationSelection == 2) {
                            if (applicableSavings.get(selection).getTransferCount() < applicableSavings.get(selection).getTransferLimit()) {
                                applicableSavings.get(selection).setTransferCount(applicableSavings.get(selection).getTransferCount() + 1);

                                while (true) {
                                    System.out.println("Withdraw, Deposit, or exit? (W / D / X)");
                                    String transactionInput = input.next();
                                    double valueInput;
                                    if (transactionInput.equalsIgnoreCase("W")) {
                                        System.out.println("How much would you like to withdraw");
                                        valueInput = Double.parseDouble(input.next());
                                        if (valueInput > applicableSavings.get(selection).getBalance()) {
                                            System.out.println("Withdrawal amount is too large");
                                        } else {
                                            FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(p.getBalance() - valueInput));
                                            applicableSavings.get(selection).setBalance(applicableSavings.get(selection).getBalance() - valueInput);
                                        }
                                    } else if (transactionInput.equalsIgnoreCase("D")) {
                                        System.out.println("How much would you like to deposit");
                                        valueInput = Double.parseDouble(input.next());

                                        FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(p.getBalance() + valueInput));
                                        applicableSavings.get(selection).setBalance(applicableSavings.get(selection).getBalance() + valueInput);
                                    } else if (transactionInput.equalsIgnoreCase("X")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid Input");
                                    }
                                }
                            }
                        } else if (modificationSelection == 3) {
                            break;
                        } else {
                            System.out.println("Out of bounds");
                        }
                    }

                }
//                end modification
            } else if (choice.equalsIgnoreCase("S")) {
                if (applicableSavings.isEmpty()) {
                    System.out.println("You don't have a Savings account");
                } else {
                    System.out.println("Available Accounts\n enter account number you would like to modify");
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

                    selection--;

                    int finalSelection = selection;
                    String temp;
                    while (true) {
                        System.out.println("\n1. Name of Account: " + applicableSavings.get(selection).getName());
                        System.out.println("2. Balance: " + applicableSavings.get(selection).getBalance());
                        System.out.println("3. Stop modifying \n");

                        System.out.println("Enter number of item you want to modify");
                        int modificationSelection = Integer.parseInt(input.next());

                        if (modificationSelection == 1) {
                            System.out.println("Enter new name");
                            temp = input.next();


                            String finalTemp = temp;
                            FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setName(finalTemp));
                            applicableSavings.get(selection).setName(temp);

                            System.out.println("Name Change complete");
                        } else if (modificationSelection == 2) {
                            if (applicableSavings.get(selection).getTransferCount() < applicableSavings.get(selection).getTransferLimit()) {
                                applicableSavings.get(selection).setTransferCount(applicableSavings.get(selection).getTransferCount() + 1);

                                while (true) {
                                    System.out.println("Withdraw, Deposit, or exit? (W / D / X)");
                                    String transactionInput = input.next();
                                    double valueInput;
                                    if (transactionInput.equalsIgnoreCase("W")) {
                                        System.out.println("How much would you like to withdraw");
                                        valueInput = Double.parseDouble(input.next());
                                        if (valueInput > applicableSavings.get(selection).getBalance()) {
                                            System.out.println("Withdrawal amount is too large");
                                        } else {
                                            FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(p.getBalance() - valueInput));
                                            applicableSavings.get(selection).setBalance(applicableSavings.get(selection).getBalance() - valueInput);
                                        }
                                    } else if (transactionInput.equalsIgnoreCase("D")) {
                                        System.out.println("How much would you like to deposit");
                                        valueInput = Double.parseDouble(input.next());

                                        FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(p.getBalance() + valueInput));
                                        applicableSavings.get(selection).setBalance(applicableSavings.get(selection).getBalance() + valueInput);
                                    } else if (transactionInput.equalsIgnoreCase("X")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid Input");
                                    }
                                }
                            }
                        } else if (modificationSelection == 3) {
                            break;
                        } else {
                            System.out.println("Out of bounds");
                        }
                    }

                }
            } else if (choice.equalsIgnoreCase("x")) {
                break;
            } else {
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
                    System.out.println("Available Accounts\n enter account number you would like to delete");
                    for (int i = 1; i <= applicableChequings.size(); i++) {
                        System.out.println(i + ". " + applicableChequings.get(i - 1).getName());
                    }
                    int selection = input.nextInt();
                    while (true) {
                        if (selection > applicableChequings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = input.nextInt();
                        } else {
                            break;
                        }
                    }
                    System.out.println("Deleting option " + selection );
                    selection--;

                    int finalSelection = selection;
                    FileIO.getChequingsAccount().removeIf(p -> p.getName().equals(applicableChequings.get(finalSelection).getName()));
                    applicableChequings.remove(selection);

                    System.out.println("You have " + applicableChequings.size() + " chequing(s) accounts and " + applicableSavings.size() + " saving(s) accounts");
                }
            } else if (choice.equalsIgnoreCase("S")) {
                if (applicableSavings.isEmpty()) {
                    System.out.println("You don't have a Savings account");
                } else {
                    System.out.println("Available Accounts\n enter account number you would like to delete");
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