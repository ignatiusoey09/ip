package main.java.commands;

import main.java.tasks.TaskList;
import main.java.util.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui);

    public abstract boolean isExit();
}
