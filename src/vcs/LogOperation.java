package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class LogOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
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

        List<Commit> commits = vcs.getHead().getCommit().getBranch().getCommits();
        String outputString;
        int i = commits.size();
        for (Commit c : commits) {
            outputString = "Commit id: " + c.getId() + "\n";
            outputString += "Message: " + c.getDescription() + "\n";

            // Check if item it's not the last and add another newline
            i--;
            if (i > 0) {
                outputString += "\n";
            }

            vcs.getActiveSnapshot().getOutputWriter().write(outputString);
        }
        return ErrorCodeManager.OK;
    }
}
