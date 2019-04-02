package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class RollbackOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
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
        if (operationArgs.size() != 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        Staging.getInstance().clearStagedChanges();
        vcs.setActiveSnapshot(vcs.getHead().getCommit().getFileSystemSnapshot().cloneFileSystem());

        return ErrorCodeManager.OK;
    }
}
