package teamthree.twodo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import teamthree.twodo.logic.CommandHistory;
import teamthree.twodo.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Entered commands (from earliest to most recent):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public CommandResult execute() {
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
    }

    @Override
    public void setData(Model model, CommandHistory history) {
        requireNonNull(history);
        this.history = history;
    }
}