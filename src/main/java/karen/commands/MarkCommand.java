package karen.commands;

import karen.tasks.Task;
import karen.tasks.TaskList;
import karen.util.Storage;
import karen.util.Ui;

/**
 * Handles marking a <code>Task</code> in a <code>TaskList</code> and prints the appropriate message
 */
public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int i) {
        this.index = i;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.markTask(this.index);
        Task t = taskList.getTask(this.index);
        ui.showMarkMessage(t);
        Storage.saveToFile(taskList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
