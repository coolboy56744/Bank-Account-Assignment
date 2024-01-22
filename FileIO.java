import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

class FileIO {
    static String line;

    private static List<String> usernames = new ArrayList<String>();
    private static List<String> passwords = new ArrayList<String>();

    private static List<Savings> savingsAccount = new ArrayList<Savings>();
    private static List<Chequings> ChequingsAccount = new ArrayList<Chequings>();

    public static List<String> getUsernames() {
        return usernames;
    }

    public static List<String> getPasswords() {
        return passwords;
    }

    public static void addSavings(Savings account) {
        savingsAccount.add(account);
    }

    public static void addChequings(Chequings account) {
        ChequingsAccount.add(account);
    }

    public static List<Savings> getSavingsAccount() {
        return savingsAccount;
    }

    public static List<Chequings> getChequingsAccount() {
        return ChequingsAccount;
    }

    public static void read() {
        try {
            // read usernames
            FileReader fileReader = new FileReader("usernames.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                usernames.add(line);
            }

            fileReader.close();

            // read passwords
            fileReader = new FileReader("passwords.txt");
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                passwords.add(line);
            }

            bufferedReader.close();
            fileReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Username or Password File not found");
        }
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void write() {
        try {
            // write usernames
            FileWriter fileWriter = new FileWriter("usernames.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String item : usernames) {
                bufferedWriter.write(item);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();

            // write passwords
            FileWriter fileWriter2 = new FileWriter("passwords.txt");
            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
            for (String item : passwords) {
                bufferedWriter2.write(item);
                bufferedWriter2.newLine();
            }

            bufferedWriter2.close();
            fileWriter2.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        // This just has to be here in order for the file to read and write properly
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cAccountSerialization() {
        try {
            FileOutputStream fileOut = new FileOutputStream("ChequingsAccounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ChequingsAccount);
            System.out.println("Serialized data is saved in employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void sAccountSerialization() {
        try {
            FileOutputStream fileOut = new FileOutputStream("SavingAccounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(savingsAccount);
            System.out.println("Serialized data is saved in employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void sAccountDeserialization() {
        Savings temp = null;
        try {
            FileInputStream fileIn = new FileInputStream("SavingAccounts.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            savingsAccount = (List<Savings>) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Savings class not found");
            c.printStackTrace();
            return;
        }
    }

}
