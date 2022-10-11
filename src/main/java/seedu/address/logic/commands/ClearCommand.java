package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.TrackAScholar;

/**
 * Clears all applicants from TrackAScholar.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TrackAScholar has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTrackAScholar(new TrackAScholar());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
