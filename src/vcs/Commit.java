package vcs;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

public final class Commit {
    private int id;
    private String description;
    private Branch branch;
    private Commit parent;
    private FileSystemSnapshot fss;

    public Commit(final String description, final Branch branch,
                  FileSystemSnapshot fss, final Commit parent) {
        this.id = IDGenerator.generateCommitID();
        this.description = description;
        this.branch = branch;
        this.parent = parent;
        this.fss = fss;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public Commit getParent() {
        return this.parent;
    }

    public FileSystemSnapshot getFileSystemSnapshot() {
        return this.fss;
    }
}
