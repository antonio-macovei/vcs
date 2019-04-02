package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class BranchOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the status operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() != 1) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        Commit currentCommit = vcs.getHead().getCommit();

        Branch newBranch = new Branch(operationArgs.get(0));

        // Check if branch name already exists
        List<Branch> currentBranches = vcs.getBranches();
        for (Branch b : currentBranches) {
            if (b.getName().equals(newBranch.getName())) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        }

        // Initialize the new branch with the current commit
        Commit firstBranchCommit = new Commit(currentCommit.getDescription(), newBranch,
                currentCommit.getFileSystemSnapshot().cloneFileSystem(), currentCommit);
        newBranch.addCommit(firstBranchCommit);

        // Add new branch
        vcs.addBranch(newBranch);
        return ErrorCodeManager.OK;
    }
}
