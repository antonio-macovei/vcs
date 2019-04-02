package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class CheckoutOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
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
        if (operationArgs.size() > 2 || operationArgs.size() < 1) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        if (Staging.getInstance().getStagedChanges().size() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }
        String firstArg = operationArgs.get(0);
        if (firstArg.equals("-c")) {
            return checkoutCommit(vcs);
        } else {
            return checkoutBranch(vcs);
        }
    }

    private int checkoutCommit(Vcs vcs) {
        int commitId = Integer.parseInt(operationArgs.get(1));
        Branch currentBranch = vcs.getHead().getCommit().getBranch();
        List<Commit> currentCommits = currentBranch.getCommits();

        Commit moveToCommit = null;
        for (Commit c : currentCommits) {
            if (c.getId() == commitId) {
                 moveToCommit = c;
            }
        }

        if (moveToCommit == null) {
            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }

        // Remove commits made after the one we are moving to
        List<Commit> toBeRemoved = new ArrayList<Commit>();
        for (Commit c : currentCommits) {
            if (c.getId() > moveToCommit.getId()) {
                toBeRemoved.add(c);
            }
        }
        currentCommits.removeAll(toBeRemoved);

        // Set HEAD to the new commit
        vcs.getHead().setHead(moveToCommit);

        vcs.setActiveSnapshot(moveToCommit.getFileSystemSnapshot());

        return ErrorCodeManager.OK;
    }

    private int checkoutBranch(Vcs vcs) {
        String branchName = operationArgs.get(0);

        // Search the requested branch
        Branch moveToBranch = null;
        for (Branch b : vcs.getBranches()) {
            if (b.getName().equals(branchName)) {
                moveToBranch = b;
                break;
            }
        }

        // If the branch doesn't exist, return error
        if (moveToBranch == null) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        List<Commit> branchCommits = moveToBranch.getCommits();
        int lastCommit = branchCommits.size() - 1;

        // Move the HEAD to the last commit in the new branch
        Commit x = branchCommits.get(lastCommit);
        vcs.getHead().setHead(x);

        return ErrorCodeManager.OK;
    }
}
