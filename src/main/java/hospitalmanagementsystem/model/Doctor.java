
package hospitalmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private int id;
    private String NAME;
    private String SPEC;
    private String USERNAME;
    private String PASSWORD;

    private List<Integer> assignedPatientIds;

    // Creates a doctor
    public Doctor(int id, String NAME, String SPEC) {

        this.id = id;
        this.NAME = NAME;
        this.SPEC = SPEC;
        this.assignedPatientIds = new ArrayList<>();
    }

    // Creates a doctor with login credentials
    public Doctor(int id, String NAME, String SPEC,
            String USERNAME, String PASSWORD) {

        this(id, NAME, SPEC);

        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    // Gets doctor information
    public int getId() {
        return id;
    }

    public String getName() {
        return NAME;
    }

    public String getSpecialty() {
        return SPEC;
    }

    public String getUsername() {
        return USERNAME;
    }

    // Checks the doctor's login credentials
    public boolean checkLogin(String user, String pass) {

        return USERNAME != null && PASSWORD != null
                && USERNAME.equals(user)
                && PASSWORD.equals(pass);
    }

    // Assigns a patient to the doctor
    public void assignPatient(int id) {

        if (id > 0 && !assignedPatientIds.contains(id)) {
            assignedPatientIds.add(id);
        }
    }

    // Gets the assigned patients
    public List<Integer> getAssignedPatientIds() {

        return new ArrayList<>(assignedPatientIds);
    }

    // Displays doctor information
    @Override
    public String toString() {

        return "Dr. " + NAME + " (" + SPEC + ")";
    }
}
