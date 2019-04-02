package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;

import java.util.ArrayList;
import java.util.List;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private List<Branch> branches = new ArrayList<Branch>();
    private Head head;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);

        // Create branch "master" and add it to the branches list
        Branch b = new Branch("master");
        this.branches.add(b);

        // Create first commit
        Commit c = new Commit("First commit", b, this.activeSnapshot.cloneFileSystem(), null);

        b.addCommit(c);

        // Set HEAD on the current commit
        this.head = Head.getInstance();
        this.head.setHead(c);
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        return vcsOperation.execute(this);
    }

    public Head getHead() {
        return this.head;
    }

    public List<Branch> getBranches() {
        return this.branches;
    }

    public void addBranch(Branch b) {
        this.branches.add(b);
    }

    public FileSystemSnapshot getActiveSnapshot() {
        return this.activeSnapshot;
    }

    public void setActiveSnapshot(FileSystemSnapshot fss) {
        this.activeSnapshot = fss;
    }
}
