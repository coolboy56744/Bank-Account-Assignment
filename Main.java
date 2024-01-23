class Main {

    public static void main(String[] args) {

        FileIO.sAccountDeserialization();
        FileIO.cAccountDeserialization();

        FileIO.read();

        boolean successful = Bank.login();

        if (successful) {
            Bank.menu();
        }

        FileIO.sAccountSerialization();
        FileIO.cAccountSerialization();

        FileIO.write();

    }

    
}
