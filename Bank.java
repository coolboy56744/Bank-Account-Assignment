import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class Bank extends Main {
    private static Scanner input = new Scanner(System.in);
    private static boolean successful = false;
    private static List<Savings> applicableSavings = new ArrayList<Savings>();
    private static List<Chequings> applicableChequings = new ArrayList<Chequings>();
    private static String name = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static boolean login() {
        while (true) {
            System.out.println("Enter L to login, R to register, or X to exit ");
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
                        return successful;
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
                successful = false;
                return successful;
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
            menuSelection = input.nextLine();
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

    private static void createAccount() {
        while (true) {
            System.out.println("Would you like to create a new Chequing or a Savings account (C / S)? (X to exit)");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("C")) {
                String Owner = name;
                System.out.println("What is the name of this account? ");
                String accountName = input.nextLine();
                System.out.println("What is the balance of this account? ");
                double balance = 0;
                balance = rounding(doubleValidation(balance));
                System.out.println("What is the interest rate of this account? (between 0.2 and 0)");
                double interestRate = 0;
                while (validInterestRate(interestRate, "Chequings")) {
                    interestRate = doubleValidation(interestRate);
                }
                applicableChequings.add(new Chequings(Owner, accountName, balance, interestRate));
                FileIO.getChequingsAccount().add(applicableChequings.get(applicableChequings.size() - 1));

                System.out.println("Successfully created Chequing account");

                System.out.println("You have " + applicableChequings.size() + " chequing(s) accounts and " + applicableSavings.size() + " saving(s) accounts");
            } else if (choice.equalsIgnoreCase("S")) {
                String Owner = name;
                System.out.println("What is the name of this account? ");
                String accountName = input.nextLine();
                System.out.println("What is the balance of this account? ");
                double balance = 0;
                balance = rounding(doubleValidation(balance));
                System.out.println("What is the interest rate of this account? (between 5 and 2)");
                double interestRate = 0;
                while (validInterestRate(interestRate, "Savings")) {
                    interestRate = doubleValidation(interestRate);
                }
                System.out.println("how many transfers can this account do per session? ");
                int transferLimit = 0;
                transferLimit = intValidation(transferLimit);
                int transferCount = 0;
                System.out.println("Does this account compound? (true / false)");
                boolean compounding;
                compounding = booleanValidation();

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

    private static void modifyAccount() {
        while (true) {
            System.out.println("Would you like to modify a Chequing or a Savings account (C / S)? (X to exit)");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("C")) {
                if (applicableChequings.isEmpty()) {
                    System.out.println("You don't have a Chequing account");
                } else {
                    System.out.println("Available Accounts\nEnter account number you would like to modify");
                    for (int i = 1; i <= applicableChequings.size(); i++) {
                        System.out.println(i + ". " + applicableChequings.get(i - 1).getName());
                    }
                    int selection = 0;
                    selection = intValidation(selection);
                    while (true) {
                        if (selection > applicableChequings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = intValidation(selection);
                        } else {
                            break;
                        }
                    }

                    selection--;

                    int finalSelection = selection;
                    String temp;
                    while (true) {
                        System.out.println("\n1. Name of Account: " + applicableChequings.get(selection).getName());
                        System.out.println("2. Balance: " + df.format(applicableChequings.get(selection).getBalance()));
                        System.out.println("3. Stop modifying \n");

                        System.out.println("Enter number of item you want to modify");
                        int modificationSelection = 0;
                        modificationSelection = intValidation(modificationSelection);

                        if (modificationSelection == 1) {
                            System.out.println("Enter new name");
                            temp = input.nextLine();

                            String finalTemp = temp;
                            FileIO.getChequingsAccount().stream().filter(p -> p.getName().equals(applicableChequings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setName(finalTemp));
                            applicableChequings.get(selection).setName(temp);

                            System.out.println("Name Change complete");
                        } else if (modificationSelection == 2) {
                            while (true) {
                                System.out.println("Withdraw, Deposit, Transfer or exit? (W / D / T / X)");
                                String transactionInput = input.nextLine();
                                double valueInput = 0;
                                if (transactionInput.equalsIgnoreCase("W")) {
                                    System.out.println("How much would you like to withdraw");
                                    valueInput = doubleValidation(valueInput);
                                    if (valueInput > applicableChequings.get(selection).getBalance()) {
                                        System.out.println("Withdrawal amount is too large");
                                    } else {
                                        double finalValueInput = valueInput;
                                        FileIO.getChequingsAccount().stream().filter(p -> p.getName().equals(applicableChequings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(rounding(p.getBalance() - finalValueInput)));
                                    }
                                } else if (transactionInput.equalsIgnoreCase("D")) {
                                    System.out.println("How much would you like to deposit");
                                    valueInput = doubleValidation(valueInput);

                                    double finalValueInput1 = valueInput;
                                    FileIO.getChequingsAccount().stream().filter(p -> p.getName().equals(applicableChequings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(rounding(p.getBalance() + finalValueInput1)));
                                } else if (transactionInput.equalsIgnoreCase("T")) {
                                    System.out.println("To which number of account?");

                                    for (int i = 0; i < FileIO.getUsernames().size(); i++) {
                                        System.out.println((i + 1) + ". " + FileIO.getUsernames().get(i));
                                    }

                                    int accountSelection = 0;
                                    boolean escape = false;
                                    while (true) {
                                        while (true) {
                                            accountSelection = intValidation(accountSelection);
                                            accountSelection--;
                                            if (accountSelection < 0 || accountSelection > FileIO.getUsernames().size()) {
                                                System.out.println("Out of bounds");
                                            } else {
                                                break;
                                            }
                                        }
                                        while (true) {
                                            if (FileIO.getUsernames().get(accountSelection).equals(name)) {
                                                System.out.println("You can't transfer to yourself");
                                                break;
                                            } else {
                                                escape = true;
                                                break;
                                            }
                                        }
                                        if (escape) {
                                            break;
                                        }
                                    }

                                    int finalAccountSelection = accountSelection;
                                    List<Chequings> applicableChequingsTransfer = FileIO.getChequingsAccount().stream().filter(p -> p.getOwner().equals(FileIO.getUsernames().get(finalAccountSelection))).collect(Collectors.toList());

                                    if (applicableChequingsTransfer.isEmpty()) {
                                        System.out.println("This user has no chequing accounts");
                                    } else {
                                        System.out.println("Which account would you like to transfer to?");
                                        for (int i = 1; i <= applicableChequingsTransfer.size(); i++) {
                                            System.out.println(i + ". " + applicableChequingsTransfer.get(i - 1).getName());
                                        }
                                        int transferSelection = 0;
                                        transferSelection = intValidation(transferSelection);
                                        while (true) {
                                            if (transferSelection > applicableChequingsTransfer.size() || transferSelection < 0) {
                                                System.out.println("Out of bounds");
                                                modificationSelection = intValidation(modificationSelection);
                                            } else {
                                                break;
                                            }
                                        }

                                        transferSelection--;

                                        double transferAmount = 0;
                                        while (true) {
                                            System.out.println("How much would you like to transfer?");
                                            transferAmount = doubleValidation(transferAmount);
                                            if (transferAmount > applicableChequings.get(selection).getBalance()) {

                                                System.out.println(applicableChequings.get(selection).getName() + " doesn't have enough money to transfer");
                                            } else {
                                                break;
                                            }
                                        }
                                        int finalTransferSelection = transferSelection;
                                        double finalTransferAmount = transferAmount;
                                        FileIO.getChequingsAccount().stream().filter(p -> p.getName().equals(applicableChequings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(rounding(p.getBalance() - finalTransferAmount)));
                                        FileIO.getChequingsAccount().stream().filter(p -> p.getName().equals(applicableChequingsTransfer.get(finalTransferSelection).getName()) && p.getOwner().equals(applicableChequingsTransfer.get(finalTransferSelection).getOwner())).findFirst().ifPresent(p -> p.setBalance(rounding(p.getBalance() + finalTransferAmount)));

                                        System.out.println("Transfer complete");
                                    }

                                } else if (transactionInput.equalsIgnoreCase("X")) {
                                    break;
                                } else {
                                    System.out.println("Invalid Input");
                                }
                            }

                        } else if (modificationSelection == 3) {
                            break;
                        } else {
                            System.out.println("Out of bounds");
                        }
                    }
                }
            } else if (choice.equalsIgnoreCase("S")) {
                if (applicableSavings.isEmpty()) {
                    System.out.println("You don't have a Savings account");
                } else {
                    System.out.println("Available Accounts\nenter account number you would like to modify");
                    for (int i = 1; i <= applicableSavings.size(); i++) {
                        System.out.println(i + ". " + applicableSavings.get(i - 1).getName());
                    }
                    int selection = 0;
                    selection = intValidation(selection);
                    while (true) {
                        if (selection > applicableSavings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = intValidation(selection);
                        } else {
                            break;
                        }
                    }

                    selection--;

                    int finalSelection = selection;
                    String temp;
                    while (true) {
                        System.out.println("\n1. Name of Account: " + applicableSavings.get(selection).getName());
                        System.out.println("2. Balance: " + df.format(applicableSavings.get(selection).getBalance()));
                        System.out.println("3. Stop modifying \n");

                        System.out.println("Enter number of item you want to modify");
                        int modificationSelection = 0;
                        modificationSelection = intValidation(modificationSelection);

                        if (modificationSelection == 1) {
                            System.out.println("Enter new name");
                            temp = input.nextLine();


                            String finalTemp = temp;
                            FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setName(finalTemp));
                            applicableSavings.get(selection).setName(temp);

                            System.out.println("Name Change complete");
                        } else if (modificationSelection == 2) {
                            if (applicableSavings.get(selection).getTransferCount() < applicableSavings.get(selection).getTransferLimit()) {
                                applicableSavings.get(selection).setTransferCount(applicableSavings.get(selection).getTransferCount() + 1);

                                while (true) {
                                    System.out.println("Withdraw, Deposit, or exit? (W / D / X)");
                                    String transactionInput = input.nextLine();
                                    double valueInput = 0;
                                    if (transactionInput.equalsIgnoreCase("W")) {
                                        System.out.println("How much would you like to withdraw");
                                        valueInput = doubleValidation(valueInput);
                                        if (valueInput > applicableSavings.get(selection).getBalance()) {
                                            System.out.println("Withdrawal amount is too large");
                                        } else {
                                            double finalValueInput = valueInput;
                                            FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(rounding(p.getBalance() - finalValueInput)));

                                        }
                                    } else if (transactionInput.equalsIgnoreCase("D")) {
                                        System.out.println("How much would you like to deposit");
                                        valueInput = doubleValidation(valueInput);

                                        double finalValueInput1 = valueInput;
                                        FileIO.getSavingsAccount().stream().filter(p -> p.getName().equals(applicableSavings.get(finalSelection).getName())).findFirst().ifPresent(p -> p.setBalance(rounding(p.getBalance() + finalValueInput1)));
                                    } else if (transactionInput.equalsIgnoreCase("X")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid Input");
                                    }
                                }
                            } else {
                                System.out.println("You have reached your transfer limit");
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

    private static void deleteAccount() {
        while (true) {
            System.out.println("Would you like to delete a Chequing or a Savings account (C / S)? (X to exit)");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("C")) {
                if (applicableChequings.isEmpty()) {
                    System.out.println("You don't have a Chequing account");
                } else {
                    System.out.println("Available Accounts\n enter account number you would like to delete");
                    for (int i = 1; i <= applicableChequings.size(); i++) {
                        System.out.println(i + ". " + applicableChequings.get(i - 1).getName());
                    }
                    int selection = 0;
                    selection = intValidation(selection);
                    while (true) {
                        if (selection > applicableChequings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = intValidation(selection);
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
                    int selection = 0;
                    selection = intValidation(selection);
                    while (true) {
                        if (selection > applicableSavings.size() || selection < 0) {
                            System.out.println("Out of bounds");
                            selection = intValidation(selection);
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

    private static double doubleValidation(double validator) {
        while (true) {
            try {
                validator = Double.parseDouble(input.nextLine());



                return validator;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input");
            }
        }
    }

    private static int intValidation(int validator) {
        while (true) {
            try {
                validator = Integer.parseInt(input.nextLine());

                return validator;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input");
            }
        }
    }

    private static boolean booleanValidation() {
        String validator = null;
        boolean validatingItem = false;
        while (true) {
            try {
                validator = input.nextLine();
                if (validator.equalsIgnoreCase("true") || validator.equalsIgnoreCase("t")) {
                    validatingItem = true;
                } else if (validator.equalsIgnoreCase("false") || validator.equalsIgnoreCase("f")) {
                    validatingItem = false;
                } else {
                    validator = "breaker";
                    int breaker = Integer.parseInt(validator);
                }
                return validatingItem;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input");
            }
        }
    }

    private static double rounding(double item) {
        item *= 100;
        item = Math.round(item);
        item /= 100;
        return item;
    }

    private static boolean validInterestRate(double interestRate, String type) {
        if (type.equals("Savings")) {
            if (interestRate > 5 || interestRate < 2) {
                return true;
            } else {
                return false;
            }
        }
        if (type.equals("Chequings")) {
            if (interestRate > 0.2 || interestRate < 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}