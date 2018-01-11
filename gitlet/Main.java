package gitlet;
import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Sung Min Shin
 */
public class Main {
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        switch (args[0]) {
        case "init":
            callInit();
            /* fall through */
        case "add":
            callAdd(args);
        /* fall through */
        case "commit":
            callCommit(args);
        /* fall through */
        case "log":
            callLog();
        /* fall through */
        case "find":
            callFind(args);
            /* fall through */
        case "global-log":
            callGlobalLog(args);
        /* fall through */
        case  "branch":
            callBranch(args);
            /* fall through */
        case "status":
            callStatus(args);
        /* fall through */
        case "checkout":
            callCheckout(args);
        /* fall through */
        case "rm":
            callRm(args);
        /* fall through */
        case "rm-branch":
            callRmBranch(args);
        /* fall through */
        case "reset":
            callReset(args);
        /* fall through */
        case "merge":
            callMerge(args);
        /* fall through */
        default:
            System.out.println("No command with that name exists.");
            System.exit(0);
        }
    }

    /** Calls init.
     * */
    private static void callInit() {
        initStatement();
        System.exit(0);
    }
    /** Calls add.
     * @param args call function*/
    private static void callAdd(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        addStatement(args[1]);
        System.exit(0);
    }
    /** Calls commit.
     * @param args call function*/
    private static void callCommit(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        boolean first = true;
        if (args[1].equals("")) {
            System.out.println("Please enter commit message.");
            System.exit(0);
        }
        String commitMessage = args[1];
        commitStatement(commitMessage);
        System.exit(0);
    }

    /** Calls log.*/
    private static void callLog() {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        logStatement();
        System.exit(0);
    }
    /** Calls find.
     * @param args call function*/
    private static void callFind(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        findStatement(args[1]);
        System.exit(0);
    }
    /** Calls global log.
     * @param args call function*/
    private static void callGlobalLog(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        globalLogStatement();
        System.exit(0);
    }
    /** Calls branch.
     * @param args call function*/
    private static void callBranch(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        branchStatement(args[1]);
        System.exit(0);
    }
    /** Calls status.
     * @param args call function*/
    private static void callStatus(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        statusStatement();
        System.exit(0);
    }
    /** Calls checkout.
     * @param args call function*/
    private static void callCheckout(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length == 2) {
            checkoutOption1(args[1]);
            System.exit(0);
        } else if (args.length == 3) {
            if (!args[1].equals("--")) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            checkoutOption2(args[2]);
            System.exit(0);
        } else if (args.length == 4) {
            if (!args[2].equals("--")) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            checkoutOption3(args[1], args[3]);
            System.exit(0);
        } else {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
    /** Calls Rm.
     * @param args call function */
    private static void callRm(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        rmStatement(args[1]);
        System.exit(0);
    }
    /** Calls rm branch.
     * @param args call function*/
    private static void callRmBranch(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        rmBranchStatement(args[1]);
        System.exit(0);
    }
    /** Calls reset.
     * @param args call function*/
    private static void callReset(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        resetStatement(args[1]);
        System.exit(0);
    }
    /** Calls merge.
     * @param args call function*/
    private static void callMerge(String... args) {
        if (!new File(parentdir).exists()) {
            System.out.println("Not in an initialized "
                    + "Gitlet directory.");
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        mergeStatement(args[1]);
        System.exit(0);
    }



    /** When command is init.*/
    static void initStatement() {
        boolean validInit;
        validInit = new File(parentdir).mkdir();
        if (!validInit) {
            System.out.println("A Gitlet version-control system already "
                    + "exists in the current directory.");
            System.exit(0);
        } else {
            new File(parentdir + separator + "commits").mkdir();
            new File(parentdir + separator + "blobs").mkdir();
            new File(parentdir + separator + "heads").mkdir();
            new File(parentdir + separator  + "heads" + separator
                    + "master").mkdir();
            new File(parentdir + separator + "staging").mkdir();
            new File(parentdir + separator + "rm").mkdir();
            new File(parentdir + separator + "merged").mkdir();

            Utils.writeContents(new File(parentdir + separator + "heads"
                    + separator + "currentBranch"), "master");

            Commit initialCommit = new Commit("initial commit", true);
            byte[] serialized = Utils.serialize(initialCommit);
            String shaOne = initialCommit.getID();
            File fileInCommits =
                    new File(parentdir + separator + "commits" + separator
                            + shaOne);
            Utils.writeContents(fileInCommits, serialized);

            String masterpath = parentdir + separator + "heads" + separator
                    + "master" + separator + shaOne;
            File fileInHeads = new File(masterpath);
            Utils.writeContents(fileInHeads, serialized);

            String headpath = parentdir + separator + "HEAD.txt";
            File headfile = new File(headpath);
            Utils.writeContents(headfile, parentdir + separator + "heads"
                    + separator + "master" + separator + shaOne);
        }
    }
    /** When command is add.
     * @param filename  add filename */
    static void addStatement(String filename) {
        String path = System.getProperty("user.dir")  + separator + filename;
        File addedFile = new File(path);
        if (!addedFile.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        } else {
            Blob newBlob = new Blob(addedFile, filename);
            String sha = newBlob.getHash();
            byte[] serialized = Utils.serialize(newBlob);

            String headPath = Utils.readContentsAsString(headTxtFile);
            File headCommitFile = new File(headPath);
            Commit headCommit = Utils.readObject(headCommitFile, Commit.class);

            if (headCommit.getFiles() == null || headCommit.getFiles().isEmpty()
                    || !headCommit.containsVersion(sha)) {
                File fileInStaging = new File(parentdir + separator
                        + "staging" + separator + filename);
                Utils.writeContents(fileInStaging, serialized);
                File fileInBlobs = new File(parentdir + separator + "blobs"
                        + separator + sha);
                Utils.writeContents(fileInBlobs, serialized);
            }
        }
        new File(parentdir + separator + "rm" + separator + filename).delete();
    }



    /** When command is commit.
     * @param commitMessage commitMessage
     * @return the new commit*/
    static Commit commitStatement(String commitMessage) {
        commitError(commitMessage);
        File stagingArea = new File(parentdir + separator + "staging");
        File rm = new File(parentdir + separator + "rm");
        if (stagingArea.listFiles().length == 0 && rm.listFiles().length == 0) {
            System.out.println("No changes added to the commit");
            System.exit(0);
        }
        String currentHeadPath = Utils.readContentsAsString(headTxtFile);
        File currentHead = new File(currentHeadPath);
        Commit parentCommit = Utils.readObject(currentHead, Commit.class);
        Commit newCommit = new Commit(commitMessage, parentCommit);
        for (String fileToRemove : new File(parentdir + separator
                + "rm").list()) {
            newCommit.removeMapping(fileToRemove);
        }
        File[] filesToCommit = new File(parentdir + separator
                + "staging").listFiles();
        Blob blobInStaging = null;
        for (File file : filesToCommit) {
            try {
                blobInStaging = Utils.readObject(file, Blob.class);
            } catch (IllegalArgumentException e) {
                continue;
            }
            String filename = blobInStaging.getName();
            if (parentCommit.getFiles() != null
                    && parentCommit.containsFile(filename)) {
                newCommit.replace(filename, filename, blobInStaging.getHash());
            } else if (new File(parentdir + separator + "rm" + separator
                    + filename).exists()) {
                continue;
            } else {
                newCommit.addfiles(filename, blobInStaging.getHash());
            }
        }
        byte[] serialized = Utils.serialize(newCommit);
        String sha = newCommit.getID();
        File fileInCommitsDir = new File(parentdir + separator + "commits"
                + separator + sha);
        Utils.writeContents(fileInCommitsDir, serialized);
        new File(currentHeadPath).delete();
        String newHeadPath = parentdir + separator + "heads" + separator
                + Utils.readContentsAsString(new File(parentdir + separator
                + "heads" + separator
                + "currentBranch")) + separator + sha;
        File fileInHeads = new File(newHeadPath);
        Utils.writeContents(fileInHeads, serialized);
        Utils.writeContents(headTxtFile, newHeadPath);
        File[] filesInStaging = new File(parentdir + separator
                + "staging").listFiles();
        for (File file : filesInStaging) {
            file.delete();
        }
        File[] filesInRm = new File(parentdir + separator + "rm").listFiles();
        for (File file : filesInRm) {
            file.delete();
        }
        return newCommit;
    }

    /** Reports error for commit.
     *
     * @param commitMessage message
     */
    static void commitError(String commitMessage) {
        if (commitMessage == null) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
    }

    /** When command is log. */
    static void logStatement() {
        String headCommitPath = Utils.readContentsAsString(headTxtFile);
        Commit commit = Utils.readObject
                (new File(headCommitPath), Commit.class);

        while (commit != null) {
            System.out.println("===");
            System.out.println("commit " + commit.getID());
            if (commit.isMerged()) {
                System.out.println("Merge: " + commit.getParent1() + " "
                        + commit.getParent2());
            }
            System.out.println("Date: " + commit.getDate());
            System.out.println(commit.getMessage());
            System.out.println();
            commit = commit.getParentCommit();
        }
    }

    /** When command is find.
     * @param message find message*/
    static void findStatement(String message) {
        Commit commit = null;
        boolean messageFound = false;
        for (File file : new File(parentdir + separator
                + "commits").listFiles()) {
            commit = Utils.readObject(file, Commit.class);
            if (commit.getMessage().equals(message)) {
                System.out.println(commit.getID());
                messageFound = true;
            }
        }
        if (!messageFound) {
            System.out.println("Found no commit with that message.");
        }
    }
    /** When command is globallog.*/
    static void globalLogStatement() {
        File commitsFolder = new File(parentdir + separator + "commits");
        File[] allCommits = commitsFolder.listFiles();
        for (File commit : allCommits) {
            Commit commitClass = Utils.readObject(commit, Commit.class);
            System.out.println("===");
            System.out.println("commit " + commitClass.getID());
            System.out.println("Date: " + commitClass.getDate());
            System.out.println(commitClass.getMessage());
            System.out.println();
        }
    }
    /** When command is branch.
     * @param branchName branch branchName*/
    static void branchStatement(String branchName) {
        File newBranch = new File(parentdir + separator + "heads"
                + separator + branchName);
        boolean nonExistingBranch = newBranch.mkdir();
        if (!nonExistingBranch) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        String headCommitPath = Utils.readContentsAsString(headTxtFile);
        Commit currentCommit = Utils.readObject(new File(headCommitPath),
                Commit.class);
        File newBranchedCommit = new File(parentdir + separator
                + "heads" + separator + branchName + separator
                + currentCommit.getID());

        Utils.writeContents(newBranchedCommit, Utils.serialize(currentCommit));

    }
    /** When command is status.*/
    static void statusStatement() {
        System.out.println("=== Branches ===");
        File[] branches = new File(parentdir + separator + "heads").listFiles();
        Arrays.sort(branches);
        for (File branch : branches) {
            if (branch.isHidden() || !branch.isDirectory()) {
                continue;
            } else if (branch.getName().equals(Utils.
                    readContentsAsString(new File(parentdir + separator
                            + "heads" + separator + "currentBranch")))) {
                System.out.println("*" + branch.getName());
            } else {
                System.out.println(branch.getName());
            }
        }
        System.out.println();

        System.out.println("=== Staged Files ===");
        for (String file : Utils.plainFilenamesIn(parentdir + separator
                + "staging")) {
            System.out.println(file);
        }

        System.out.println();

        System.out.println("=== Removed Files ===");
        for (String file : Utils.plainFilenamesIn(parentdir + separator
                + "rm")) {
            System.out.println(file);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    /** Checkout error catching.
     *
     * @param branchName branch name
     * @return the commit
     */
    static Commit checkoutError(String branchName) {
        if (!new File(parentdir + separator + "heads" + separator
                + branchName).exists()) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (Utils.readContentsAsString(new File(parentdir + separator
                + "heads" + separator + "currentBranch")).equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
        return Utils.readObject(new File(Utils.
                readContentsAsString(headTxtFile)), Commit.class);
    }


    /** When command is checkout branchname.
     * @param branchName checkout branchname*/
    static void checkoutOption1(String branchName) {
        Commit currentCommitObject = checkoutError(branchName);
        File currentBranchIndicator = new File(parentdir + separator
                + "heads" + separator + "currentBranch");
        String newHeadPath = parentdir + separator + "heads" + separator
                + branchName;
        String[] headCommitID = new File(newHeadPath).list();
        newHeadPath += separator + headCommitID[0];
        Commit headCommitObject = Utils.readObject(new File(newHeadPath),
                Commit.class);
        List<String> filesInWorkingDir = Utils.
                plainFilenamesIn(System.getProperty("user.dir"));
        for (String filename : filesInWorkingDir) {
            if (new File(parentdir + separator + filename).isHidden()
                    || filename.equals("Makefile")
                    || filename.equals("proj3.iml")) {
                continue;
            }
            if (headCommitObject.getFiles() != null
                    && (currentCommitObject.getFiles() == null
                    || !currentCommitObject.getFiles().containsKey(filename))
                    && headCommitObject.getFiles().containsKey(filename)) {
                System.out.println("There is an untracked file in the way;"
                        + " delete it or add it first.");
                System.exit(0);
            }
        }
        Utils.writeContents(currentBranchIndicator, branchName);
        if (headCommitObject.getFiles() != null) {
            for (String id : headCommitObject.getFiles().values()) {
                File blob = new File(parentdir + separator + "blobs"
                        + separator + id);
                Blob blobObject = Utils.readObject(blob, Blob.class);
                File workingDirectory = new File(
                        System.getProperty("user.dir") + separator
                                + blobObject.getName());
                Utils.writeContents(workingDirectory, blobObject.getContents());
            }
        }
        if (currentCommitObject.getFiles() != null) {
            for (String filename : currentCommitObject.getFiles().keySet()) {
                if (headCommitObject.getFiles() == null
                        || !headCommitObject.getFiles().containsKey(filename)) {
                    new File(System.getProperty("user.dir")
                            + separator + filename).delete();
                }
            }
        }
        List<String> filesInStaging = Utils.plainFilenamesIn(parentdir
                + separator + "staging");
        for (String filename : filesInStaging) {
            new File(parentdir + separator + "staging" + separator
                    + filename).delete();
        }
        Utils.writeContents(headTxtFile, parentdir + separator + "heads"
                + separator + branchName + separator + headCommitID[0]);
        Utils.writeContents(new File(parentdir + separator + "heads"
                + separator + "currentBranch"), branchName);
    }

    /** When command is checkout filename.
     * @param filename checkout filename*/
    static void checkoutOption2(String filename) {
        File headCommit = new File(Utils.readContentsAsString(headTxtFile));
        Commit headCommitObject = Utils.readObject(headCommit, Commit.class);
        String currentFileVersion = headCommitObject.getFiles().get(filename);

        if (currentFileVersion == null) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }

        Blob currentFileBlob = Utils.readObject(new File(parentdir + separator
                + "blobs" + separator + currentFileVersion), Blob.class);
        File inWorkingDir = new File(System.getProperty("user.dir")
                + separator + filename);
        Utils.writeContents(inWorkingDir, currentFileBlob.getContents());
    }

    /** When command is checkout commit , filename.
     * @param filename checkout commitID
     * @param commitID checkout filename*/
    static void checkoutOption3(String commitID, String filename) {
        String[] commits = new File(parentdir + separator + "commits").list();
        String chosenCommit = commitID;

        for (String id : commits) {
            if (id.startsWith(commitID)) {
                chosenCommit = id;
                break;
            }
        }
        File commitFile = new File(parentdir + separator + "commits"
                + separator + chosenCommit);

        Commit commitVersion = null;
        try {
            commitVersion = Utils.readObject(commitFile, Commit.class);
        } catch (IllegalArgumentException e) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        if (!commitVersion.containsFile(filename)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        String sha = commitVersion.getFiles().get(filename);
        Blob newBlob = Utils.readObject(new File(parentdir + separator
                + "blobs" + separator + sha), Blob.class);
        Utils.writeContents(new File(System.getProperty("user.dir")
                + separator + filename), newBlob.getContents());
    }

    /** When command is rm.
     * @param filename rm filename*/
    static void rmStatement(String filename) {
        boolean noerror = false;
        if (new File(parentdir + separator + "staging").
                listFiles().length == 0) {
            noerror = false;
        }
        noerror = new File(parentdir + separator + "staging"
                + separator + filename).delete();
        Commit currentCommit = Utils.readObject(new File(Utils.
                readContentsAsString(headTxtFile)), Commit.class);
        if (currentCommit.containsFile(filename)) {
            noerror = true;
            File addToRm = new File(parentdir + separator + "rm"
                    + separator + filename);
            Utils.writeContents(addToRm, currentCommit.getFiles()
                    .get(filename));
            File fileToDelete = new File(System.getProperty("user.dir")
                    + separator + filename);
            fileToDelete.delete();
        }
        if (!noerror) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
    }

    /** When command is rmbranch.
     * @param branchName remove branch*/
    static void rmBranchStatement(String branchName) {
        File branch = new File(parentdir + separator + "heads"
                + separator + branchName);
        if (!branch.exists()) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if (Utils.readContentsAsString(new File(parentdir + separator
                + "heads" + separator + "currentBranch")).equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
        }
        for (File s : branch.listFiles()) {
            s.delete();
        }
        branch.delete();
    }

    /** Error handling.
     *
     * @param commitID commit Id
     * @return commit
     */
    static Commit resetError(String commitID) {
        if (!new File(parentdir + separator + "commits" + separator
                + commitID).exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        return Utils.readObject(new File(Utils.
                readContentsAsString(headTxtFile)), Commit.class);
    }

    /** Do the reset action.
     *
     * @param currentCommit current commit
     * @param newCommit new commit
     */
    static void resetAction(Commit currentCommit, Commit newCommit) {
        for (String filename : Utils.
                plainFilenamesIn(System.getProperty("user.dir"))) {
            String fileContents = Utils.
                    readContentsAsString(new File(System.getProperty
                            ("user.dir") + separator + filename));
            if (new File(parentdir + separator + filename).isHidden()
                    || filename.equals("Makefile")
                    || filename.equals("proj3.iml")) {
                continue;
            } else if (!currentCommit.containsFile(filename)
                    && newCommit.containsFile(filename)) {
                String sha = newCommit.getFileVersion(filename);
                Blob blob = Utils.readObject(new File(parentdir + separator
                        + "blobs" + separator + sha), Blob.class);
                String blobContents = blob.getContents();
                if (!fileContents.equals(blobContents)) {
                    System.out.println("There is an untracked file in the "
                            + "way; delete it or add it first.");
                    System.exit(0);
                }
            } else {
                if (!newCommit.containsFile(filename)) {
                    new File(parentdir + separator + filename).delete();
                }
            }
        }
    }


    /** When command is reset.
     * @param commitID reset to id*/
    static void resetStatement(String commitID) {
        Commit currentCommit = resetError(commitID);
        Commit newCommit = Utils.readObject(new File(parentdir + separator
                + "commits" + separator + commitID), Commit.class);

        resetAction(currentCommit, newCommit);

        for (String key : newCommit.getFiles().keySet()) {
            Blob blob = null;
            try {
                blob = Utils.readObject(new File(parentdir + separator
                                + "blobs" + separator
                                + newCommit.getFileVersion(key)),
                        Blob.class);
            } catch (IllegalArgumentException e) {
                continue;
            }
            String contents = blob.getContents().toString();
            Utils.writeContents(new File(parentdir + separator + key),
                    contents);
        }
        List<String> workingFiles = Utils.plainFilenamesIn(".");
        for (String file : workingFiles) {
            if (!currentCommit.getFiles().containsKey(file)) {
                Utils.restrictedDelete(file);
            }
        }
        for (File file : new File(parentdir + separator + "staging").
                listFiles()) {
            file.delete();
        }
        String headPath = Utils.readContentsAsString(headTxtFile);
        new File(headPath).delete();
        String currentBranch = Utils.readContentsAsString(new File(parentdir
                + separator + "heads" + separator + "currentBranch"));
        Utils.writeContents(headTxtFile, parentdir + separator + "heads"
                + separator + currentBranch + separator + commitID);
        headPath = Utils.readContentsAsString(headTxtFile);
        Utils.writeContents(new File(headPath),
                Utils.readContents(new File(parentdir + separator + "commits"
                        + separator + commitID)));
    }


    /** Returns error.
     *
     * @param branchName branch name
     */
    static void mergeError(String branchName) {
        String[] stagedFiles = new File(parentdir + separator + "staging").
                list();
        String[] rmFiles = new File(parentdir + separator + "rm").list();
        String branch = Utils.readContentsAsString(new File(parentdir
                + separator + "heads" + separator + "currentBranch"));
        if (branch.equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }
        if (stagedFiles.length != 0 || rmFiles.length != 0) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }
    }

    /** Finds the split point of given and current commits.
     *
     * @param givenBranchCommit given branch commit
     * @param currentCommit current commit
     * @return the split
     */
    static String findSplitPoint(Commit givenBranchCommit,
                                 Commit currentCommit) {
        HashSet<String> allBranchCommits = new HashSet<>();
        Commit commit = givenBranchCommit;
        while (commit != null) {
            allBranchCommits.add(commit.getID());
            commit = commit.getParentCommit();
        }

        String splitPointID = null;
        commit = currentCommit;
        while (commit != null) {
            if (allBranchCommits.contains(commit.getID())) {
                splitPointID = commit.getID();
                break;
            }
            commit = commit.getParentCommit();
        }
        return splitPointID;
    }

    /** Does the merge action.
     *
     * @param combinedList combined list of commits
     * @param splitPointCommit split point's commit
     * @param givenBranchCommit the given branch's commit
     * @param currentCommit the current branch's commit
     * @return conflict
     */
    static boolean mergeAction(ArrayList<String> combinedList,
                               Commit splitPointCommit,
                               Commit givenBranchCommit,
                               Commit currentCommit) {
        boolean conflict = false;

        for (String filename : combinedList) {
            String s = splitPointCommit.getFileVersion(filename);
            String m = givenBranchCommit.getFileVersion(filename);
            String c = currentCommit.getFileVersion(filename);
            if (equals(s, c) && !equals(s, m) && m != null) {
                checkoutOption3(givenBranchCommit.getID(), filename);
                addStatement(filename);
            } else if (equals(s, c) && m == null) {
                rmStatement(filename);
            } else if ((equals(s, m) && !equals(s, c)) || (equals(c, m)
                    && !equals(s, c))) {
                continue;
            } else {
                conflict = true;
                mergeConflict(filename, currentCommit, givenBranchCommit);
                addStatement(filename);
            }
        }
        return conflict;
    }

    /** Sees if its ancestor or fast forwarded.
     *
     * @param branchName branch name
     * @param splitPointID split point
     * @param givenBranchCommit given branch's commit
     * @param currentCommit the current commit
     */
    static void ancestorOrFastForward(String branchName, String splitPointID,
                                      Commit givenBranchCommit,
                                      Commit currentCommit) {
        if (splitPointID.equals(givenBranchCommit.getID())) {
            System.out.println("Given branch is an ancestor of the "
                    + "current branch.");
            System.exit(0);
        }
        if (splitPointID.equals(currentCommit.getID())) {
            Utils.writeContents(new File(parentdir + separator
                    + "currentBranch"), branchName);
            Utils.writeContents(headTxtFile, parentdir + separator
                    + "heads" + separator + branchName + separator
                    + givenBranchCommit.getID());
            System.out.println("Current branch fast-forwarded.");
            System.exit(0);
        }
    }

    /** When command is merge.
     * @param branchName merge to branchName*/
    static void mergeStatement(String branchName) {
        mergeError(branchName);
        String currentBranch = Utils.readContentsAsString(new File(parentdir
                + separator + "heads" + separator + "currentBranch"));
        String givenBranchPath = parentdir + separator + "heads" + separator
                + branchName + separator;
        if (!new File(givenBranchPath).exists()) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        File givenFile = new File(givenBranchPath
                + Utils.plainFilenamesIn(givenBranchPath).get(0));
        File currentBranchFile = new File(Utils.
                readContentsAsString(headTxtFile));
        Commit givenBranchCommit = Utils.readObject(givenFile, Commit.class);
        Commit currentCommit = Utils.readObject(currentBranchFile,
                Commit.class);
        List<String> workingFiles = Utils.plainFilenamesIn(".");
        for (String file : workingFiles) {
            if (!currentCommit.getFiles().containsKey(file)) {
                System.out.println("There is an untracked file in the way:"
                        + " delete it or add it first.");
                System.exit(0);
            }
        }
        String splitPointID = findSplitPoint(givenBranchCommit, currentCommit);
        ancestorOrFastForward(branchName, splitPointID,
                givenBranchCommit, currentCommit);
        Commit splitPointCommit = Utils.readObject(new File(parentdir
                        + separator + "commits" + separator + splitPointID),
                Commit.class);
        ArrayList<String> combinedList = new ArrayList<>();
        for (String filename : givenBranchCommit.getFiles().keySet()) {
            if (!combinedList.contains(filename)) {
                combinedList.add(filename);
            }
        }
        for (String filename : currentCommit.getFiles().keySet()) {
            if (!combinedList.contains(filename)) {
                combinedList.add(filename);
            }
        }
        for (String filename : splitPointCommit.getFiles().keySet()) {
            if (!combinedList.contains(filename)) {
                combinedList.add(filename);
            }
        }
        boolean conflict = mergeAction(combinedList, splitPointCommit,
                givenBranchCommit, currentCommit);
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
        }
        Commit mergedCommit = commitStatement("Merged " + branchName
                + " into " + currentBranch + ".");
        mergedCommit.setMerged(true);
        mergedCommit.setParent1(currentCommit.getID().substring(0, 6));
        mergedCommit.setParent2(givenBranchCommit.getID().substring(0, 6));
    }

    /** Checks to see if two booleans are equal, takes into account nulls.
     *
     * @param a string a
     * @param b string b
     * @return boolean
     */
    static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }

    /** Function runs when there is a conflict in the merge.
     * @param fileName merge conflict with filename
     * @param currentCommit merge conflict in current commit
     * @param givenBranchCommit merge conflict with gen branch commit*/
    static void mergeConflict(String fileName, Commit currentCommit,
                              Commit givenBranchCommit) {
        File currentBlobFile = new File(parentdir + separator + "blobs"
                + separator + currentCommit.getFileVersion(fileName));
        File givenBlobFile = new File(parentdir + separator + "blobs"
                + separator + givenBranchCommit.getFileVersion(fileName));

        String currentBlobContents = "";
        if (currentBlobFile.exists()) {
            Blob currentBlob = Utils.readObject(currentBlobFile, Blob.class);
            currentBlobContents = currentBlob.getContents();

        }

        String givenBlobContents = "";
        if (givenBlobFile.exists()) {
            Blob givenBlob = Utils.readObject(givenBlobFile, Blob.class);
            givenBlobContents = givenBlob.getContents();
        }
        String replacementContent = "<<<<<<< HEAD\n" + currentBlobContents
                + "=======\n" + givenBlobContents + ">>>>>>>\n";
        Utils.writeContents(new File(System.getProperty("user.dir")
                + separator + fileName), replacementContent);
    }

    /** The separator.*/
    private static String separator = File.separator;

    /** The working .gitlet directory. */
    private static String parentdir = System.getProperty("user.dir")
            + separator + ".gitlet";

    /** The HEAD txt file directory. */
    private static File headTxtFile = new File(parentdir
            + separator + "HEAD.txt");
}
