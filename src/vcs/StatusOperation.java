package vcs;

import filesystem.FileSystemOperation;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class StatusOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
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

        Staging stagedChanges = Staging.getInstance();
        List<FileSystemOperation> operations = new ArrayList<>(stagedChanges.getStagedChanges());
        String outputString;
        vcs.getActiveSnapshot().getOutputWriter().write("On branch: "
                + vcs.getHead().getCommit().getBranch().getName() + "\n");
        vcs.getActiveSnapshot().getOutputWriter().write("Staged changes:\n");
        for (FileSystemOperation fso : operations) {
            switch (fso.getType()) {
                case CHANGEDIR:
                    outputString = "\tChanged directory to " + fso.getOperationArgs().get(1);
                    break;
                case TOUCH:
                    outputString = "\tCreated file " + fso.getOperationArgs().get(1);
                    break;
                case MAKEDIR:
                    outputString = "\tCreated directory " + fso.getOperationArgs().get(1);
                    break;
                case REMOVE:
                    outputString = "\tRemoved " + fso.getOperationArgs().get(1);
                    break;
                case WRITETOFILE:
                    outputString = "\tAdded \"" + fso.getOperationArgs().get(1)
                            + "\" to file " + fso.getOperationArgs().get(0);
                    break;
                default:
                    outputString = "";
                    break;
            }
            vcs.getActiveSnapshot().getOutputWriter().write(outputString + "\n");
        }


        return ErrorCodeManager.OK;
    }
}

