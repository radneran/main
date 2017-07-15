package teamthree.twodo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import teamthree.twodo.logic.commands.ListCommand.AttributeInputted;
import teamthree.twodo.testutil.TaskBookBuilder;
import teamthree.twodo.testutil.TypicalDeadline;
import teamthree.twodo.testutil.TypicalTask;

public class ModelManagerTest {

    private TypicalTask typicalTask = new TypicalTask();
    private TypicalDeadline typicalDeadline = new TypicalDeadline();

    @Test
    public void equals() throws Exception {
        TaskBook taskBook = new TaskBookBuilder().withTask(typicalTask.cs2103)
                .withTask(typicalTask.cs1020).withTask(typicalTask.partyCompleted).build();
        TaskBook differentTaskBook = new TaskBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(taskBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(taskBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTaskBook, userPrefs)));

        // different filteredList (key words) -> returns false
        modelManager.updateFilteredTaskList(new HashSet<>(
                Arrays.asList(typicalTask.cs2103.getName().fullName.split(" "))), true);
        assertFalse(modelManager.equals(new ModelManager(taskBook, userPrefs)));
        modelManager.updateFilteredListToShowAllIncomplete(); // resets modelManager to initial state for upcoming tests

        // different filteredList (completed) -> return false
        modelManager.updateFilteredListToShowAllComplete();
        assertFalse(modelManager.equals(new ModelManager(taskBook, userPrefs)));
        modelManager.updateFilteredListToShowAllIncomplete(); // resets modelManager to initial state for upcoming tests

        // different filteredList (period) -> return false
        modelManager.updateFilteredListToShowPeriod(typicalDeadline.getDeadline(), AttributeInputted.START, true);
        assertFalse(modelManager.equals(new ModelManager(taskBook, userPrefs)));
        modelManager.updateFilteredListToShowAllIncomplete(); // resets modelManager to initial state for upcoming tests

        // different sortedList -> returns false
        modelManager.sort();
        assertFalse(modelManager.equals(new ModelManager(taskBook, userPrefs)));
        modelManager.updateFilteredListToShowAllIncomplete(); // resets modelManager to initial state for upcoming tests

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTaskBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(taskBook, differentUserPrefs)));
    }
}
