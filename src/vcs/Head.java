package vcs;

public final class Head {
    private static Head instance = null;
    private Commit commit;

    private Head() {

    }

    public static Head getInstance() {
        if (instance == null) {
            instance = new Head();
        }

        return instance;
    }

    public Commit getCommit() {
        return this.commit;
    }

    public void setHead(Commit headCommit) {
        this.commit = headCommit;
    }
}
