package gitlet;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/** Commit Class.
 *  @author Sung Min Shin
 */
public class Commit implements Serializable {
    /** Default constructor. */
    Commit() {
        message = null;
        date = dateFormat.format(new Date(0)) + " -0800";
        parentCommit = null;
        shaVal = Utils.sha1(Utils.serialize(this));
        files = new HashMap<String, String>();
        merged = false;
        parent1 = null;
        parent2 = null;
    }
    /** When given log message and parent commit.
     * @param logMessage the message
     * @param parent the commit*/
    Commit(String logMessage, Commit parent) {
        message = logMessage;
        date = dateFormat.format(new Date()) + " -0800";
        parentCommit = parent;
        files = parent.getFiles();
        shaVal = Utils.sha1(Utils.serialize(this));
        merged = false;
        parent1 = null;
        parent2 = null;
    }
    /** When initial commit.
     * @param  logMessage the log message
     * @param isInitial is initial*/
    Commit(String logMessage, boolean isInitial) {
        message = logMessage;
        date = dateFormat.format(new Date()) + " -0800";
        parentCommit = null;
        shaVal = Utils.sha1(Utils.serialize(this));
        merged = false;
        parent1 = null;
        parent2 = null;
        files = new HashMap<String, String>();

    }
    /** Helper method to get the time.
     * @return the date*/
    private Date getCurrentTimestamp() {
        return new Date();
    }

    /** Adds files to hashmap.
     * @param filename the file name
     * @param sha the id*/
    public void addfiles(String filename, String sha) {
        if (files == null) {
            files = new HashMap<String, String>();
        }
        files.put(filename, sha);
    }

    /** True if commit is a result of merge.
     * @return merged*/
    public boolean isMerged() {
        return merged;
    }

    /** Sets whether commit is merged or not.
     * @param bool the boolean*/
    public void setMerged(boolean bool) {
        merged = bool;
    }

    /** Returns true if commit contains given version.
     * @param value  contains version*/
    public boolean containsVersion(String value) {
        if (files == null) {
            return false;
        }
        return files.containsValue(value);
    }

    /** Returns true if commit contains given file.
     * @param filename the filename*/
    public boolean containsFile(String filename) {
        if (files == null) {
            return false;
        }
        return files.containsKey(filename);
    }

    /** Gets the file version of the given file.
     * @param fileName file name
     * @return file*/
    public String getFileVersion(String fileName) {
        return files.get(fileName);
    }

    /** Replaces the old file with new file and new version.
     * @param newfilename new file name
     * @param newVersion  new version
     * @param oldfilname old file name*/
    public void replace(String oldfilname, String newfilename,
                        String newVersion) {
        files.remove(oldfilname);
        files.put(newfilename, newVersion);
    }

    /** Removes the mapping of specified key.
     * @param key remove this mapping */
    public void removeMapping(String key) {
        files.remove(key);
    }

    /** If merge, gets the first parent.
     * @return parent 1*/
    public String getParent1() {
        return parent1;
    }

    /** If merge, gets the second parent.
     * @return parent 2*/
    public String getParent2() {
        return parent2;
    }

    /** If merge, sets the first parent.
     * @param p the parent name
     */
    public void setParent1(String p) {
        parent1 = p;
    }

    /**v If merge, sets the second parent.
     * @param p  second parent
     * */
    public void setParent2(String p) {
        parent2 = p;
    }

    /** Returns the parent commit.*/
    public Commit getParentCommit() {
        return parentCommit;
    }

    /** Returns the commit message. */
    public String getMessage() {
        return message;
    }

    /** Returns date. */
    public String getDate() {
        return date;
    }

    /** Returns the hashmap of the files and versions. */
    public HashMap<String, String> getFiles() {
        return files;
    }

    /** Returns the id. */
    public String getID() {
        return shaVal;
    }


    /** Proper Date formatting. */
    private DateFormat dateFormat =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");

    /** The serial uid needed for compilation. */
    private static final long serialVersionUID = -2314365000903341785L;

    /** If merge, the first parent commit. */
    private String parent1;

    /** If merge, then the second parent commit. */
    private String parent2;

    /** True if commit is a result of merge. */
    private boolean merged;


    /** The ID of the commit. */
    private String shaVal;

    /** The Hashmap storing all file names and version numbers. */
    private HashMap<String, String> files;

    /** The commit message. */
    private String message;

    /** The date.*/
    private String date;

    /** The parent commit. */
    private Commit parentCommit;

}
