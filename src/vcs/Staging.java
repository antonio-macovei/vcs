package vcs;

import filesystem.FileSystemOperation;

import java.util.ArrayList;
import java.util.List;

public final class Staging {
    private static Staging instance = null;
    private List<FileSystemOperation> stagedChanges = new ArrayList<>();

    private Staging() {

    }

    public static Staging getInstance() {
        if (instance == null) {
            instance = new Staging();
        }

        return instance;
    }

    public List<FileSystemOperation> getStagedChanges() {
        return this.stagedChanges;
    }

    public void clearStagedChanges() {
        this.stagedChanges.clear();
    }

    public void addChange(FileSystemOperation fso) {
        this.stagedChanges.add(fso);
    }
}
