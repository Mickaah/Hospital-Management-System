package hospitalmanagementsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    // Changed doctor_accs to hospital_db here:
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean initialize() {
        String patients = "CREATE TABLE IF NOT EXISTS patients ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "age INT NOT NULL, "
                + "gender VARCHAR(20), "
                + "medical_history TEXT)";

        String appointments = "CREATE TABLE IF NOT EXISTS appointments ("
                + "appointment_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "patient_id INT NOT NULL, "
                + "patient_name VARCHAR(100), "
                + "patient_age INT, "
                + "patient_gender VARCHAR(20), "
                + "doctor_name VARCHAR(100), "
                + "specialty VARCHAR(100), "
                + "medical_history TEXT, "
                + "concern TEXT, "
                + "appointment_date VARCHAR(50), "
                + "urgency_level VARCHAR(50))";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            st.execute(patients);
            st.execute(appointments);

            System.out.println("Connected to new 'hospital_db' database successfully!");
            System.out.println("Tables initialized successfully!");

            return true;

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            return false;
        }
    }
}