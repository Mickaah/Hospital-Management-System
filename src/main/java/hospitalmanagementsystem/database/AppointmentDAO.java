
package hospitalmanagementsystem.database;

import hospitalmanagementsystem.model.Appointments;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Gets the doctor's specialty from the appointment
    public static String resolveSpecialty(Appointments a) {

        String specialty = a.getSpecialty();

        if (specialty != null && !specialty.trim().isEmpty()) {
            return specialty.trim();
        }

        String doctor = a.getDoctorName();

        if (doctor != null) {

            int open = doctor.lastIndexOf('(');
            int close = doctor.lastIndexOf(')');

            if (open >= 0 && close > open) {
                return doctor.substring(open + 1, close).trim();
            }
        }

        return "";
    }

    // Saves an appointment to the database
    public boolean insert(Appointments a) {

        if (a == null || a.getPatientId() == null
                || a.getPatientName() == null
                || a.getPatientName().trim().isEmpty()) {
            return false;
        }

        int patientId;

        try {
            patientId = Integer.parseInt(a.getPatientId());

            if (patientId <= 0) {
                return false;
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid patient ID.");
            return false;
        }

        String sql = "INSERT INTO appointments "
                + "(patient_id, patient_name, patient_age, patient_gender, "
                + "doctor_name, specialty, medical_history, concern, "
                + "appointment_date, urgency_level) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, patientId);
            ps.setString(2, a.getPatientName());
            ps.setInt(3, a.getPatientAge());
            ps.setString(4, a.getPatientGender());
            ps.setString(5, a.getDoctorName());
            ps.setString(6, resolveSpecialty(a));
            ps.setString(7, a.getMedicalHistory());
            ps.setString(8, a.getConcern());
            ps.setString(9, a.getAppointmentDate());
            ps.setString(10, a.getUrgencyLevel());

            int result = ps.executeUpdate();

            if (result > 0) {

                try (ResultSet keys = ps.getGeneratedKeys()) {

                    if (keys.next()) {
                        a.setAppointmentId(keys.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {

            System.err.println("Could not save appointment: "
                    + e.getMessage());
        }

        return false;
    }

    // Loads all appointments from the database
    public List<Appointments> getAll() {

        List<Appointments> list = new ArrayList<>();

        String sql = "SELECT * FROM appointments ORDER BY appointment_id";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Appointments a = new Appointments(
                        rs.getString("patient_name"),
                        rs.getInt("patient_age"),
                        rs.getString("patient_gender"),
                        String.valueOf(rs.getInt("patient_id")),
                        rs.getString("doctor_name"),
                        rs.getString("specialty"),
                        rs.getString("medical_history"),
                        rs.getString("concern"),
                        rs.getString("appointment_date"),
                        rs.getString("urgency_level")
                );

                a.setAppointmentId(rs.getInt("appointment_id"));

                list.add(a);
            }

        } catch (SQLException e) {

            System.err.println("Could not load appointments: "
                    + e.getMessage());
        }

        return list;
    }

    // Deletes an appointment using its ID
    public boolean delete(int appointmentId) {

        String sql = "DELETE FROM appointments WHERE appointment_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Could not delete appointment: "
                    + e.getMessage());

            return false;
        }
    }
}
