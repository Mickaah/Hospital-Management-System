
package hospitalmanagementsystem.database;

import hospitalmanagementsystem.model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Saves a patient to the database
    public boolean insert(Patient p) {

        if (p == null || p.getId() <= 0
                || p.getName() == null
                || p.getName().trim().isEmpty()
                || p.getAge() <= 0) {
            return false;
        }

        String sql = "INSERT INTO patients "
                + "(id, name, age, gender, medical_history) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getGender());
            ps.setString(5, p.getMedicalHistory());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Could not save patient: "
                    + e.getMessage());

            return false;
        }
    }

    // Loads all patients from the database
    public List<Patient> getAll() {

        List<Patient> patients = new ArrayList<>();

        String sql = "SELECT id, name, age, gender, medical_history "
                + "FROM patients ORDER BY id";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("medical_history")
                );

                patients.add(p);
            }

        } catch (SQLException e) {

            System.err.println("Could not load patients: "
                    + e.getMessage());
        }

        return patients;
    }

    // Deletes a patient using their ID
    public boolean delete(int id) {

        String sql = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Could not delete patient: "
                    + e.getMessage());

            return false;
        }
    }
}
