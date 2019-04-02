package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class CommitOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
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
        if (operationArgs.size() < 2) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        // If there are no changes in staging, return error
        if (Staging.getInstance().getStagedChanges().size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        // Check if the first argument is "-m"
        String option = operationArgs.remove(0);
        if (!option.equals("-m")) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        // Get description from arguments
        String description = "";
        while (operationArgs.size() != 0) {
            description += operationArgs.remove(0) + " ";
        }

        // Remove last space
        description = description.substring(0, description.length() - 1);


        Branch currentBranch = vcs.getHead().getCommit().getBranch();
        Commit currentCommit = vcs.getHead().getCommit();

        // Create a new commit
        Commit newCommit = new Commit(description, currentBranch,
                vcs.getActiveSnapshot().cloneFileSystem(), currentCommit);

        // Add the new commit to the current branch
        currentBranch.addCommit(newCommit);

        // Clear staging
        Staging.getInstance().clearStagedChanges();

        // Move the head on the new commit
        vcs.getHead().setHead(newCommit);

        return ErrorCodeManager.OK;
    }
}
