package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.ApplicationStatus;

/**
 * Represents the in-memory model of the TrackAScholar tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TrackAScholar trackAScholar;
    private final UserPrefs userPrefs;
    private final FilteredList<Applicant> filteredApplicants;

    /**
     * Initializes a ModelManager with the given trackAScholar and userPrefs.
     */
    public ModelManager(ReadOnlyTrackAScholar trackAScholar, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(trackAScholar, userPrefs);

        logger.fine("Initializing with address book: " + trackAScholar + " and user prefs " + userPrefs);

        this.trackAScholar = new TrackAScholar(trackAScholar);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApplicants = new FilteredList<>(this.trackAScholar.getApplicantList());
    }

    public ModelManager() {
        this(new TrackAScholar(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTrackAScholarFilePath() {
        return userPrefs.getTrackAScholarFilePath();
    }

    @Override
    public void setTrackAScholarFilePath(Path trackAScholarFilePath) {
        requireNonNull(trackAScholarFilePath);
        userPrefs.setTrackAScholarFilePath(trackAScholarFilePath);
    }

    //=========== TrackAScholar ================================================================================

    @Override
    public void setTrackAScholar(ReadOnlyTrackAScholar trackAScholar) {
        this.trackAScholar.resetData(trackAScholar);
    }

    @Override
    public ReadOnlyTrackAScholar getTrackAScholar() {
        return trackAScholar;
    }

    @Override
    public boolean hasApplicant(Applicant applicant) {
        requireNonNull(applicant);
        return trackAScholar.hasApplicant(applicant);
    }

    @Override
    public void deleteApplicant(Applicant target) {
        trackAScholar.removeApplicant(target);
    }

    @Override
    public void removeApplicant(ApplicationStatus applicationStatus) {
        trackAScholar.removeApplicantByStatus(applicationStatus);
    }

    @Override
    public void addApplicant(Applicant applicant) {
        trackAScholar.addApplicant(applicant);
        updateFilteredApplicantList(PREDICATE_SHOW_ALL_APPLICANTS);
    }

    @Override
    public void setApplicant(Applicant target, Applicant editedApplicant) {
        requireAllNonNull(target, editedApplicant);

        trackAScholar.setApplicant(target, editedApplicant);
    }

    @Override
    public void sortApplicants(Comparator<Applicant> comparator) {
        requireNonNull(comparator);
        trackAScholar.sortApplicants(comparator);
    }

    //=========== Filtered Applicant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Applicant} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Applicant> getFilteredApplicantList() {
        return filteredApplicants;
    }

    @Override
    public void updateFilteredApplicantList(Predicate<Applicant> predicate) {
        requireNonNull(predicate);
        filteredApplicants.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return trackAScholar.equals(other.trackAScholar)
                && userPrefs.equals(other.userPrefs)
                && filteredApplicants.equals(other.filteredApplicants);
    }

}
