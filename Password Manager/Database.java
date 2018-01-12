import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Database {
    Database() {
        File inPasswords = new File(mainPath);
        String[] passwordsArray = inPasswords.list();

        if (passwordsArray != null) {
            try {
                for (String name : passwordsArray) {
                    if (new File(mainPath + "/" + name).isHidden()) {
                        continue;
                    }
                    BufferedReader userReader = new BufferedReader(new FileReader(mainPath + "/" + name + "/user.txt"));
                    BufferedReader passReader = new BufferedReader(new FileReader(mainPath + "/" + name + "/password.txt"));

                    String username = userReader.readLine();
                    String password = passReader.readLine();

                    storage.put(name, new Data(username, password));
                }
            } catch (IOException e) {
                System.out.println("error line 25");
            }
        }
    }

    public void addToDatabase(String title, String user, String password) {
        String domainPath = mainPath + "/" + title;
        File newFileCreated = new File(domainPath);
        boolean test = newFileCreated.mkdir();

        try {
            File userFile = new File(domainPath + "/user.txt");
            File passFile = new File( domainPath + "/password.txt");
            FileOutputStream userStream = new FileOutputStream(userFile);
            FileOutputStream passStream = new FileOutputStream(passFile);
            userStream.write(user.getBytes());
            passStream.write(password.getBytes());

        } catch (Exception e) {
            System.out.println("error line 40");
        }
    }
    public HashMap<String, Data> getStorage() {
        return storage;
    }

    public boolean hasTitle(String title) {
        return storage.containsKey(title);
    }

    public String returnUser(String title) {
        return storage.get(title).get_user();
    }

    public String returnPassword(String title) {
        return storage.get(title).get_password();
    }

    private HashMap<String, Data> storage = new HashMap<String, Data>();

    private static String mainPath = "/Users/PeterShin/passwords_program/Passwords";

}
