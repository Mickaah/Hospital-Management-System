
package hospitalmanagementsystem.dsa;

import hospitalmanagementsystem.database.AppointmentDAO;
import hospitalmanagementsystem.database.DataBaseConnection;
import hospitalmanagementsystem.database.PatientDAO;
import hospitalmanagementsystem.model.Appointments;
import hospitalmanagementsystem.model.Doctor;
import hospitalmanagementsystem.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SystemDataStructures {

    private static SystemDataStructures instance;

    private PriorityQueue<Appointments> appointments;
    private List<Appointments> history;

    private PatientBST patientBST;

    private PatientDAO patientDAO;
    private AppointmentDAO appointmentDAO;

    // Initializes the system components
    public SystemDataStructures() {

        appointments = new PriorityQueue<>();
        history = new ArrayList<>();

        patientBST = new PatientBST();

        patientDAO = new PatientDAO();
        appointmentDAO = new AppointmentDAO();
    }

    // Returns the shared system instance
    public static synchronized SystemDataStructures getInstance() {

        if (instance == null) {

            instance = new SystemDataStructures();
            instance.loadFromDatabase();
        }

        return instance;
    }

    // Loads saved database records into the DSA structures
    private void loadFromDatabase() {

        if (!DataBaseConnection.initialize()) {
            System.err.println("Cannot load system data.");
            return;
        }

        // Loads patients into the BST
        for (Patient p : patientDAO.getAll()) {
            patientBST.insert(p);
        }

        // Loads appointments into the queue and history
        for (Appointments a : appointmentDAO.getAll()) {

            appointments.offer(a);
            history.add(a);

            // Links appointments to their patients
            try {

                int patientId = Integer.parseInt(a.getPatientId());

                Patient p = patientBST.search(patientId);

                if (p != null) {
                    p.getAppointments().add(a);
                }

            } catch (NumberFormatException e) {

                System.err.println(
                        "Invalid patient ID for appointment: "
                        + a.getAppointmentId()
                );
            }
        }
    }

    // Checks whether a patient exists
    public boolean verifyPatientExists(int patientId) {

        return patientBST.contains(patientId);
    }

    // Finds a patient using their ID
    public Patient findPatient(int patientId) {

        return patientBST.search(patientId);
    }

    // Adds a patient to the system and database
    public boolean addPatient(Patient patient) {

        if (patient == null || patientBST.contains(patient.getId())) {
            return false;
        }

        // Save to database first
        if (!patientDAO.insert(patient)) {
            return false;
        }

        // Add to BST after successful saving
        return patientBST.insert(patient);
    }

    // Removes a patient from the system
    public boolean removePatient(int patientId) {

        Patient patient = patientBST.search(patientId);

        if (patient == null) {
            return false;
        }

        // Do not remove a patient who still has appointments
        if (!patient.getAppointments().isEmpty()) {
            return false;
        }

        if (!patientDAO.delete(patientId)) {
            return false;
        }

        return patientBST.remove(patientId);
    }

    // Adds an appointment to the system
    public boolean addAppointment(Appointments a, Patient p, Doctor d) {

        if (a == null) {
            return false;
        }

        // Find the actual registered patient
        Patient registeredPatient = null;

        try {

            int patientId = Integer.parseInt(a.getPatientId());

            registeredPatient = patientBST.search(patientId);

        } catch (NumberFormatException e) {

            return false;
        }

        if (registeredPatient == null) {
            return false;
        }

        // Save the appointment first
        if (!appointmentDAO.insert(a)) {
            return false;
        }

        // Add appointment to the priority queue
        appointments.offer(a);

        // Add appointment to appointment history
        history.add(a);

        // Connect appointment to patient
        registeredPatient.getAppointments().add(a);

        // Assign patient to doctor
        if (d != null) {

            d.assignPatient(registeredPatient.getId());
        }

        return true;
    }

    // Removes an appointment from the system
    public boolean removeAppointment(Appointments a) {

        if (a == null) {
            return false;
        }

        // Delete from database first
        if (a.getAppointmentId() > 0) {

            if (!appointmentDAO.delete(a.getAppointmentId())) {
                return false;
            }
        }

        // Remove from the priority queue
        appointments.remove(a);

        // Remove from appointment history
        history.remove(a);

        // Remove appointment from the patient
        try {

            int patientId = Integer.parseInt(a.getPatientId());

            Patient p = patientBST.search(patientId);

            if (p != null) {
                p.getAppointments().remove(a);
            }

        } catch (NumberFormatException e) {

            System.err.println("Invalid patient ID.");
        }

        return true;
    }

    // Processes the next highest-priority appointment
    public Appointments processNextAppointment() {

        return appointments.poll();
    }

    // Gets all appointment records
    public List<Appointments> getAppointmentHistory() {

        return new ArrayList<>(history);
    }

    // Gets the appointment priority queue
    public PriorityQueue<Appointments> getAppointmentPriorityQueue() {

        return new PriorityQueue<>(appointments);
    }

    // Gets all registered patients
    public PatientBST getPatientBST() {

        return patientBST;
    }
}
