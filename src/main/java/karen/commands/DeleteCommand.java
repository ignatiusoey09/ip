package karen.commands;

import karen.tasks.Task;
import karen.tasks.TaskList;
import karen.util.Storage;
import karen.util.Ui;

/**
 * Handles deleting a <code>Task</code> from the <code>TaskList</code> and prints the appropriate message
 */
public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int i) {
        this.index = i;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        Task t = taskList.getTask(this.index);
        taskList.deleteTask(this.index);
        ui.showDeleteMessage(t);
        Storage.saveToFile(taskList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
