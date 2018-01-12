import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String selection = null;

        while (true) {
            Database data = new Database();
            System.out.println("\nTo store a new set of user name and password, type \"newstore\"" +
                    "\nTo retrieve information, write the title for the data." +
                    "\nFor all list of all available information, type \"showlist\"." +
                    "\nTo delete an item from the list, type \"delete\"." +
                    "\nTo exit, type \"q\".");


            selection = reader.next();

            if (selection.equalsIgnoreCase("newstore")) {
                newstore(reader, data);
            } else if (selection.equalsIgnoreCase("showlist")) {
                System.out.println("-----------------------------");
                System.out.println("LIST OF ALL SAVED TITLES: ");
                for (String title : data.getStorage().keySet()) {
                    System.out.println(title);
                }
                System.out.println("-----------------------------");
            } else if (selection.equalsIgnoreCase("delete")) {
                System.out.println("What is the title you are trying to delete?");
                String title = reader.next();
                delete(title, data);
            } else if (selection.equalsIgnoreCase("q")) {
                System.exit(0);
            } else {
                retrieve(selection, data);
            }
        }

    }

    public static void delete(String title, Database data) {
        if (!data.getStorage().containsKey(title)) {
            System.out.println("That title is not on the list.");
        } else {
            for (File file : new File("/Users/PeterShin/passwords_program/Passwords/" + title).listFiles()) {
                file.delete();
            }
            new File("/Users/PeterShin/passwords_program/Passwords/" + title).delete();
            System.out.println(title + " has been deleted.\n\n");
        }
    }


    public static void newstore(Scanner reader, Database data) {
            boolean finished = false;
            String title = null;
            String user = null;
            String password = null;

            while (!finished) {
                System.out.println("Enter the title: ");
                title = reader.next();

                System.out.println();

                System.out.println("Enter the user name: ");
                user = reader.next();

                System.out.println();

                System.out.println("Enter the password: ");
                password = reader.next();

                System.out.println("\n\n-------------------------------------------");
                System.out.println("Here is the information inputed: \n\n" +
                        title +
                        "\nUSERNAME: " + user +
                        "\nPASSWORD: " + password +
                        "\n\nIs the information correct? (Y/N)");

                String yesOrno = reader.next();
                while (!yesOrno.equalsIgnoreCase("Y") && !yesOrno.equalsIgnoreCase("N")) {
                    System.out.println("Invalid selection. Input \"Y\" for yes and \"N\" for no: ");
                    yesOrno = reader.next();
                }

                if (yesOrno.equalsIgnoreCase("Y")) {
                    finished = true;
                }
            }
            data.addToDatabase(title, user, password);
        }

    public static void retrieve(String title, Database data) {
        if (!data.hasTitle(title)) {
            System.out.println("That title does not exist.");
        } else {
            System.out.println("\n-------------------------------------------");
            System.out.println("Info for " + title + ":");
            System.out.println("USERNAME: " + data.returnUser(title));
            System.out.println("\nPASSWORD: " + data.returnPassword(title));
            System.out.println("-------------------------------------------\n\n");
        }
    }

}
