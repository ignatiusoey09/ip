import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.Deadline;
import main.java.Event;
import main.java.Task;
import main.java.Todo;

import main.java.util.Storage;

public class Karen {
    private static List<Task> tasks = new ArrayList<>();

    private static enum Keywords {
        MARK,
        UNMARK,
        LIST,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        BYE,
        UNKNOWN;
    }

    public static void main(String[] args) {
        final String LINE = "_______________________\n";

        //Print greeting
        String output = LINE +
                "Hi! I'm Karen\n" +
                "What can I do for you?\n" +
                LINE;
        System.out.print(output);

        //Initiate file and retrieve saved data
        Storage.initFile();
        tasks = Storage.readFile();

        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        boolean hasPendingChanges = false;
        while (loop) {
            String input = scanner.nextLine();
            String[] command = input.split(" ", 2); //[keyword, parameters;
            Keywords keyword;
            try {
                keyword = Keywords.valueOf(command[0].toUpperCase());
            } catch (IllegalArgumentException IAE) {
                keyword = Keywords.UNKNOWN;
            }

            output = "";
            Task task = null;
            switch (keyword) {
            case BYE:
                //End program
                System.out.print(
                        LINE + "Bye! Hope to see you again!\n" + LINE
                );
                loop = false;
                break;
            case LIST:
                //List tasks
                output += LINE;
                if (tasks.isEmpty()) {
                    output += "No tasks yet!\n";
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        Task t = tasks.get(i);
                        output += String.format("%d. %s\n", i + 1, t);
                    }
                }
                output += LINE;
                break;
            case MARK:
                //Mark as done
                try {
                    int n = Integer.parseInt(command[1]) - 1;
                    Task marked_task = tasks.get(n);
                    marked_task.mark();
                    output += LINE +
                            "Nice! I've marked this task as done:\n\t" +
                            marked_task + "\n" +
                            LINE;
                    hasPendingChanges = true;
                } catch (NumberFormatException NFE) {
                    output += LINE
                            + "Error! You must input a number after the 'mark' command\n"
                            + LINE;
                } catch (IndexOutOfBoundsException IOB) {
                    output += LINE
                            + "Invalid index! Use 'list' to see the tasks and their respective indices!\n"
                            + LINE;
                }
                break;
            case UNMARK:
                //Unmark as done
                try {
                    int n = Integer.parseInt(command[1]) - 1;
                    Task unmarked_task = tasks.get(n);
                    unmarked_task.unmark();
                    output += LINE +
                            "Ok! This task is now marked undone:\n\t" +
                            unmarked_task + "\n" +
                            LINE;
                    hasPendingChanges = true;
                } catch (NumberFormatException NFE) {
                    output += LINE
                            + "Error! You must input a number after the 'unmark' command\n"
                            + LINE;
                } catch (IndexOutOfBoundsException IOB) {
                    output += LINE
                            + "Invalid index! Use 'list' to see the tasks and their respective indices!\n"
                            + LINE;
                }
                break;
            case TODO:
                try {
                    task = new Todo(command[1]);
                    hasPendingChanges = true;
                } catch (IndexOutOfBoundsException e) {
                    output += LINE
                            + "Please enter a name for your todo!\n"
                            + LINE;
                }
                break;
            case DEADLINE:
                try {
                    String[] params = command[1].split("/by ", 2);
                    task = new Deadline(params[0], params[1]);
                    hasPendingChanges = true;
                } catch (DateTimeParseException e) {
                    output += LINE
                            + "Invalid format! Datetime must be in this form: E.g. 2024-10-11 1200\n"
                            + LINE;
                } catch (Exception e) {
                    output += LINE
                            + "Invalid input! Deadlines must follow this syntax: deadline <name> /by <due date>\n"
                            + LINE;
                }
                break;
            case EVENT:
                try {
                    String[] params = command[1].split(" /from ", 2);
                    String name = params[0];
                    String[] fromTo = params[1].split(" /to ", 2);
                    task = new Event(name, fromTo[0], fromTo[1]);
                    hasPendingChanges = true;
                } catch (DateTimeParseException e) {
                    output += LINE
                            + "Invalid format! Datetime must be in this form: E.g. 2024-10-11 1200\n"
                            + LINE;
                } catch (Exception e) {
                    output += LINE
                            + "Error! Events must follow this syntax: " +
                            "event <name> /from <start time> /to <end time>\n"
                            + LINE;
                }
                break;
            case DELETE:
                try {
                    int n = Integer.parseInt(command[1]) - 1;
                    Task t = tasks.get(n);
                    tasks.remove(n);
                    output += LINE
                            + "Alright! I've removed this task from your list:\n\t"
                            + t.toString() + "\n"
                            + LINE;
                    hasPendingChanges = true;
                } catch (NumberFormatException NFE) {
                    output += LINE
                            + "Error! Please enter a valid number after the `delete` command!\n"
                            + LINE;
                } catch (IndexOutOfBoundsException IOB) {
                    output += LINE
                            + "Invalid index! Use 'list' to see the current tasks and their respective indices\n"
                            + LINE;
                }
                break;
            default:
                output += LINE
                        + "Sorry! I don't understand :(\n"
                        + LINE;
                break;
            }

            if (task != null) {
                tasks.add(task);
                output += LINE +
                        "Got it! Added this task:\n\t" +
                        task + "\n" +
                        String.format("Now you have %d tasks in the list.\n", tasks.size()) +
                        LINE;
            }

            if (hasPendingChanges) {
                Storage.saveToFile(tasks);
                hasPendingChanges = false;
            }

            System.out.print(output);
        }

    }
}
