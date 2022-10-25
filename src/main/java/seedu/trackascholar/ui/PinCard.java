package seedu.trackascholar.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.trackascholar.model.applicant.Applicant;

/**
 * An UI component that displays information of a {@code Applicant}.
 */
public class PinCard extends UiPart<Region> {

    private static final String FXML = "PinCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on TrackAScholar level 4</a>
     */

    public final Applicant applicant;

    @FXML
    private HBox pinCardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label scholarship;
    @FXML
    private Label applicationStatus;
    @FXML
    private Label email;
    @FXML
    private FlowPane majors;

    /**
     * Creates a {@code PersonCode} with the given {@code Applicant} and index to display.
     */
    public PinCard(Applicant applicant) {
        super(FXML);
        this.applicant = applicant;
        name.setText(applicant.getName().fullName);
        phone.setText(applicant.getPhone().value);
        scholarship.setText(applicant.getScholarship().scholarship);
        applicationStatus.setText(applicant.getApplicationStatus().applicationStatus);
        setApplicationStatusStyling(applicant.getApplicationStatus().applicationStatus);
        //Ensure only pinned applicants are here
        assert applicant.getPin().getHasPinned();
        email.setText(applicant.getEmail().value);
        applicant.getMajors().stream()
                .sorted(Comparator.comparing(major -> major.major))
                .forEach(major -> majors.getChildren().add(new Label(major.major)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PinCard card = (PinCard) other;
        return applicant.equals(card.applicant);
    }

    public void setApplicationStatusStyling(String status) {
        if (status.equals("accepted")) {
            applicationStatus.getStyleClass().remove("cell_pending_label");
            applicationStatus.getStyleClass().add("cell_accepted_label");
        } else if (status.equals("rejected")) {
            applicationStatus.getStyleClass().remove("cell_pending_label");
            applicationStatus.getStyleClass().add("cell_rejected_label");
        }
    }


}
