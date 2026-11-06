package hospitalmanagementsystem.GUI;

import hospitalmanagementsystem.database.AppointmentDAO;
import hospitalmanagementsystem.dsa.SystemDataStructures;
import hospitalmanagementsystem.logic.DoctorManager;
import hospitalmanagementsystem.model.Appointments;
import hospitalmanagementsystem.model.Doctor;
import hospitalmanagementsystem.model.Patient;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mikeyks
 */
public class Patienttab extends JFrame implements ActionListener {
    private JLabel txtTitle, patname, patage, patgenlab, patillness, medhist, apptsched, docappt, urgencylab;
    private JComboBox<String> patgender, avaibdocs, urgencyBox;
    private JTextField patnameF, patageF, apptschedF;
    private JTextArea patillnessF, medhistF;
    private DefaultTableModel patres;
    private JTable tab;
    private JButton btnBack, btnAdd, btnRemove;
    private JPanel backgroundPanel;
    private ImageIcon image;
    private String[] gend = {"male", "female", "trans(male/female)"};
    private String[] urgencyLevels = {"1 - Low", "2 - Medium", "3 - High", "4 - Critical"};
    private String[] avaib = {};

    // Standard font so label widths are predictable on every machine
    private static final String FONT_NAME = "Arial";

    private SystemDataStructures systemDataStructures;

    public Patienttab() {
        this(new SystemDataStructures());
    }

    public Patienttab(SystemDataStructures sharedDataStructures) {
        this.systemDataStructures = (sharedDataStructures != null) ? sharedDataStructures : new SystemDataStructures();

        setTitle("HOSPITAL MANAGEMENT APP");
        setSize(870, 520);
        setLayout(null);

        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBackground(new Color(153, 197, 144));
        setContentPane(backgroundPanel);

        image = new ImageIcon("src/main/java/hospitalmanagementsystem/GUI/Logo.png");
        Image scaledImage = image.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        setIconImage(scaledImage);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        txtTitle = new JLabel("Book an Appointment");
        txtTitle.setBounds(10, 5, 280, 50);
        txtTitle.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        backgroundPanel.add(txtTitle);

        // ---------- LEFT COLUMN (labels x=10 w=125, fields x=140 w=230) ----------
        patname = new JLabel("Patient's Name:");
        patname.setBounds(10, 40, 125, 50);
        patname.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patname);

        patnameF = new JTextField();
        patnameF.setBounds(140, 55, 230, 20);
        patnameF.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patnameF);

        patage = new JLabel("Patient's Age:");
        patage.setBounds(10, 75, 125, 50);
        patage.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patage);

        patageF = new JTextField();
        patageF.setBounds(140, 90, 230, 20);
        patageF.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patageF);

        patgenlab = new JLabel("Patient's Gender:");
        patgenlab.setBounds(10, 110, 125, 50);
        patgenlab.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patgenlab);

        patgender = new JComboBox<>(gend);
        patgender.setBounds(140, 125, 230, 20);
        patgender.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patgender);

        // ---------- RIGHT COLUMN (labels x=400 w=135, fields x=540 w=270) ----------
        docappt = new JLabel("See Doctor:");
        docappt.setBounds(400, 40, 135, 50);
        docappt.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(docappt);

        avaibdocs = new JComboBox<>(avaib);
        avaibdocs.setBounds(540, 55, 270, 20);
        avaibdocs.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(avaibdocs);

        urgencylab = new JLabel("Urgency Level:");
        urgencylab.setBounds(400, 75, 135, 50);
        urgencylab.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(urgencylab);

        urgencyBox = new JComboBox<>(urgencyLevels);
        urgencyBox.setBounds(540, 90, 270, 20);
        urgencyBox.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(urgencyBox);

        apptsched = new JLabel("Appointment Date:");
        apptsched.setBounds(400, 110, 135, 50);
        apptsched.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(apptsched);

        apptschedF = new JTextField();
        apptschedF.setBounds(540, 125, 270, 20);
        apptschedF.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        apptschedF.setToolTipText("Format: YYYY-MM-DD (e.g. 2026-11-20)");
        backgroundPanel.add(apptschedF);

        // ---------- TEXT AREAS ----------
        medhist = new JLabel("Previous Medical History:");
        medhist.setBounds(10, 155, 220, 50);
        medhist.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(medhist);

        medhistF = new JTextArea();
        JScrollPane scrollMed = new JScrollPane(medhistF);
        scrollMed.setBounds(10, 195, 380, 60);
        medhistF.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(scrollMed);

        patillness = new JLabel("Patient Concern / Illness:");
        patillness.setBounds(410, 155, 220, 50);
        patillness.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(patillness);

        patillnessF = new JTextArea();
        JScrollPane scrollConcern = new JScrollPane(patillnessF);
        scrollConcern.setBounds(410, 195, 400, 60);
        patillnessF.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        backgroundPanel.add(scrollConcern);

        loadDoctorsIntoComboBox();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                loadDoctorsIntoComboBox();
            }
        });

        // ---------- TABLE ----------
        patres = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patres.addColumn("Patient Name");
        patres.addColumn("Age");
        patres.addColumn("Gender");
        patres.addColumn("Date");
        patres.addColumn("Urgency");
        patres.addColumn("Doctor");

        tab = new JTable(patres);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getTableHeader().setResizingAllowed(false);
        tab.getTableHeader().setBackground(Color.decode("#bdfdc5"));
        tab.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(tab);
        scroll.setBounds(10, 275, 810, 130);
        scroll.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        backgroundPanel.add(scroll);

        // ---------- BUTTONS ----------
        btnAdd = new JButton("Add");
        btnAdd.setBounds(500, 420, 100, 40);
        backgroundPanel.add(btnAdd);

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(610, 420, 100, 40);
        backgroundPanel.add(btnRemove);

        btnBack = new JButton("Back");
        btnBack.setBounds(720, 420, 100, 40);
        backgroundPanel.add(btnBack);

        btnBack.addActionListener(this);
        btnAdd.addActionListener(this);
        btnRemove.addActionListener(this);

        // Make window visible at the very END so all components render properly
        setVisible(true);
    }

    private void loadDoctorsIntoComboBox() {
        Object previousSelection = avaibdocs.getSelectedItem();
        avaibdocs.removeAllItems();
        for (Doctor doc : DoctorManager.getInstance().getAllDoctors()) {
            avaibdocs.addItem(doc.toString());
        }
        if (previousSelection != null) {
            avaibdocs.setSelectedItem(previousSelection);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            hospitalmanagementsystem.GUI.HospitalSysApp menu = new hospitalmanagementsystem.GUI.HospitalSysApp();
            this.setVisible(false);
            menu.setVisible(true);
        }
        else if (e.getSource() == btnRemove) {
            int selectedRow = tab.getSelectedRow();
            if (selectedRow != -1) {
                patres.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "No appointments to remove.");
            }
        }
        else if (e.getSource() == btnAdd) {
            String name = patnameF.getText().trim();
            String ageText = patageF.getText().trim();
            String gender = (String) patgender.getSelectedItem();
            String concern = patillnessF.getText().trim();
            String history = medhistF.getText().trim();
            String date = apptschedF.getText().trim();
            String doctor = (String) avaibdocs.getSelectedItem();
            String urgency = (String) urgencyBox.getSelectedItem();

            if (name.isEmpty() || ageText.isEmpty() || concern.isEmpty() || date.isEmpty() || gender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            // Validate date format (MySQL DATE expects YYYY-MM-DD)
            try {
                java.time.LocalDate.parse(date);
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                    "Date must be in YYYY-MM-DD format (e.g. 2026-11-20).",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int patientAge = Integer.parseInt(ageText);
                int dummyPatientId = (int)(Math.random() * 90000) + 10000;

                Patient newPatient = new Patient(dummyPatientId, name, patientAge, gender);
                systemDataStructures.addPatient(newPatient);

                Appointments newAppt = new Appointments(
                    name,
                    patientAge,
                    gender,
                    String.valueOf(dummyPatientId),
                    (doctor == null) ? "Not selected" : doctor,
                    "General",
                    history,
                    concern,
                    date,
                    urgency
                );

                AppointmentDAO dao = new AppointmentDAO();
                boolean dbSuccess = dao.insert(newAppt);

                if (!dbSuccess) {
                    JOptionPane.showMessageDialog(this, "Warning: Patient added locally, but failed to save to MySQL database.");
                }

                patres.addRow(new Object[]{
                    name,
                    ageText,
                    gender,
                    date,
                    urgency,
                    (doctor == null || doctor.isEmpty()) ? "Not selected" : doctor
                });

                patnameF.setText("");
                patageF.setText("");
                patillnessF.setText("");
                medhistF.setText("");
                apptschedF.setText("");

                if (dbSuccess) {
                    JOptionPane.showMessageDialog(this, "Appointment added and saved to database successfully!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Patient Age must be a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}