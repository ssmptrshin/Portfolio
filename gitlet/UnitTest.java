package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/** The suite of all JUnit tests for the gitlet package.
 *  @author
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    @Test
    public void testInit() {
        Main.initStatement();
        File gitletFile = new File(System.getProperty("user.dir")
                + File.separator
                + ".gitlet");
        assertTrue(gitletFile.exists());
        recursiveDelete(gitletFile);
    }
    @Test
    public void testAdd() {
        Main.initStatement();
        File wug = new File(System.getProperty("user.dir")
                + File.separator + "wug.txt");
        Utils.writeContents(wug, "Hello world");
        File wugInStaging = new File(System.getProperty("user.dir")
                + File.separator + "staging" + File.separator + "wug.txt");
        assertTrue(wugInStaging.exists());

        wug.delete();
        recursiveDelete(new File(System.getProperty("user.dir")
                + File.separator
                + ".gitlet"));
    }
    @Test
    public void testCommit() {
        Main.initStatement();
        Main.commitStatement("hello");
        assertTrue(new File(System.getProperty("user.dir")
                + File.separator + "staging").list().length == 0);

        recursiveDelete(new File(System.getProperty("user.dir")
                + File.separator
                + ".gitlet"));
    }
    @Test
    public void testFind() {
        Main.initStatement();
        File wug = new File(System.getProperty("user.dir")
                + File.separator + "wug.txt");
        Utils.writeContents(wug, "Hello world");
        Main.commitStatement("hello");
        Main.findStatement("hello");
        recursiveDelete(new File(System.getProperty("user.dir")
                + File.separator
                + ".gitlet"));
    }

    @Test
    public void testgloballog() {
        Main.initStatement();
        File wug = new File(System.getProperty("user.dir")
                + File.separator + "wug.txt");
        Utils.writeContents(wug, "Hello world");
        Main.commitStatement("hello world");
        Main.globalLogStatement();
        recursiveDelete(new File(System.getProperty("user.dir")
                + File.separator
                + ".gitlet"));
    }


    @Test
    public void testStatus() {
        Main.initStatement();
        File wug = new File(System.getProperty("user.dir")
                + File.separator + "wug.txt");
        Utils.writeContents(wug, "Hello world");
        Main.commitStatement("hello world");
        Main.statusStatement();
        recursiveDelete(new File(System.getProperty("user.dir")
                + File.separator
                + ".gitlet"));
    }


    /** A dummy test to avoid complaint. */
    @Test
    public void placeholderTest() {
    }



    public static void recursiveDelete(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
        System.out.println("Deleted file/folder: " + file.getAbsolutePath());
    }
}


