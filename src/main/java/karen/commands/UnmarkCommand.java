package karen.commands;

import karen.tasks.Task;
import karen.tasks.TaskList;
import karen.util.Storage;
import karen.util.Ui;

/**
 * Handles unmarking a <code>Task</code> in a <code>TaskList</code> and prints the appropriate message
 */
public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int i) {
        this.index = i;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.unmarkTask(this.index);
        Task t = taskList.getTask(this.index);
        ui.showUnmarkMessage(t);
        Storage.saveToFile(taskList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
