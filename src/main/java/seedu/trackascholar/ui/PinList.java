package seedu.trackascholar.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.trackascholar.commons.core.LogsCenter;
import seedu.trackascholar.model.applicant.Applicant;

/**
 * Panel containing the list of persons.
 */
public class PinList extends UiPart<Region> {
    private static final String FXML = "PinList.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Applicant> hehe;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PinList(ObservableList<Applicant> applicantList) {
        super(FXML);
        hehe.setItems(applicantList);
        hehe.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Applicant} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Applicant> {
        @Override
        protected void updateItem(Applicant applicant, boolean empty) {
            super.updateItem(applicant, empty);

            if (empty || applicant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PinCard(applicant, getIndex() + 1).getRoot());
            }
        }
    }

}
