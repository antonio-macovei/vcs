package vcs;

import java.util.ArrayList;
import java.util.List;

public final class Branch {
    private String name;
    private List<Commit> commits = new ArrayList<>();

    public Branch(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Commit> getCommits() {
        return this.commits;
    }

    public void addCommit(Commit commit) {
        this.commits.add(commit);
    }
}
