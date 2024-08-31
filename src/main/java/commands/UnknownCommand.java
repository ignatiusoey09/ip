package main.java.commands;

import main.java.TaskList;
import main.java.util.Storage;
import main.java.util.Ui;

public class UnknownCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.showUnknownInputError();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
