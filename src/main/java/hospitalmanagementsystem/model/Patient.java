
package hospitalmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    private int id;
    private String name;
    private int age;
    private String gender;
    private String medicalHistory;
    private String status;

    private List<Appointments> appointments;

    // Creates a patient without gender
    public Patient(int id, String name, int age,
            String medicalHistory) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.medicalHistory = medicalHistory;
        this.status = "Active";

        appointments = new ArrayList<>();
    }

    // Creates a patient with gender
    public Patient(int id, String name, int age,
            String gender, String medicalHistory) {

        this(id, name, age, medicalHistory);

        this.gender = gender;
    }

    // Gets patient information
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getStatus() {
        return status;
    }

    public List<Appointments> getAppointments() {
        return appointments;
    }

    // Changes patient information
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMedicalHistory(String history) {
        this.medicalHistory = history;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }
}
