package teamthree.twodo.logic.parser;

import static org.junit.Assert.assertEquals;
import static teamthree.twodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static teamthree.twodo.testutil.TypicalTask.INDEX_FIRST_TASK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import teamthree.twodo.logic.commands.MarkCommand;
import teamthree.twodo.logic.parser.exceptions.ParseException;

//@@author A0139267W
public class MarkCommandParserTest {
    private static String VALID_FIRST_INDEX = "1";
    private static String INVALID_INDEX = "a";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parseValidArgsReturnsMarkCommand() throws ParseException {
        MarkCommand command = parser.parse(VALID_FIRST_INDEX);
        assertEquals(INDEX_FIRST_TASK, command.targetIndex);
    }

    @Test
    public void parseInvalidArgsThrowsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));

        parser.parse(INVALID_INDEX);
    }
}
