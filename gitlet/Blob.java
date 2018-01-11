package gitlet;
import java.io.File;
import java.io.Serializable;

/** Blob Class.
 *  @author Sung Min Shin
 */
public class Blob implements Serializable {

    /** Creates a new blob instance.
     * @param file the file instance
     * @param filename the file name
     */
    Blob(File file, String filename) {
        name = filename;
        contents = Utils.readContentsAsString(file);
        byte[] serialized = Utils.serialize(this);
        sha = Utils.sha1(serialized);
    }

    /** Returns the name of the blob.*/
    public String getName() {
        return name;
    }

    /** Returns the contents of the blob. */
    public String getContents() {
        return contents;
    }

    /** Returns the id of the blob. */
    public String getHash() {
        return sha;
    }

    /** The name of the file. */
    private String name;

    /** The contents of the file. */
    private String contents;

    /** The SHA1 value of the file. */
    private String sha;

    /** The serial version id needed for compilation. */
    private static final long serialVersionUID = -4340565754044999502L;
}
