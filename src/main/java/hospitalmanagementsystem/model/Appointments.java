
package hospitalmanagementsystem.model;

public class Appointments implements Comparable<Appointments> {

    private int appointmentId;
    private String patientId;
    private String patientName;
    private int patientAge;
    private String patientGender;
    private String doctorName;
    private String specialty;
    private String medicalHistory;
    private String concern;
    private String appointmentDate;
    private String urgencyLevel;

    // Creates a new appointment
    public Appointments(String patientName, int patientAge,
            String patientGender, String patientId,
            String doctorName, String specialty,
            String medicalHistory, String concern,
            String appointmentDate, String urgencyLevel) {

        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.medicalHistory = medicalHistory;
        this.concern = concern;
        this.appointmentDate = appointmentDate;
        this.urgencyLevel = urgencyLevel;
    }

    // Gets and sets appointment information
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    // Extracts the urgency number from values such as "4 - Critical"
    public int getNumericUrgency() {

        if (urgencyLevel == null) {
            return 1;
        }

        String number = urgencyLevel.trim().split("\\s*[-:]\\s*")[0];

        try {
            int urgency = Integer.parseInt(number);

            if (urgency >= 1 && urgency <= 5) {
                return urgency;
            }

        } catch (NumberFormatException e) {
            return 1;
        }

        return 1;
    }

    // Sorts appointments from highest to lowest urgency
    @Override
    public int compareTo(Appointments other) {

        return Integer.compare(
                other.getNumericUrgency(),
                this.getNumericUrgency()
        );
    }
}
