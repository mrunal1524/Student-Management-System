import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Student extends JFrame {

    private JPanel contentPane;
    private JTextField sname, sentry, semail, scontact, shome;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Student frame = new Student();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Student() {
        setTitle("Add Student");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 600);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel studentDetails = new JLabel("Student Details");
        studentDetails.setFont(new Font("Times New Roman", Font.BOLD, 22));
        studentDetails.setBounds(160, 20, 200, 30);
        contentPane.add(studentDetails);

        JLabel studentName = new JLabel("Student Name:");
        studentName.setFont(new Font("Times New Roman", Font.BOLD, 16));
        studentName.setBounds(50, 80, 150, 30);
        contentPane.add(studentName);

        sname = new JTextField();
        sname.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        sname.setBounds(200, 80, 220, 30);
        contentPane.add(sname);

        JLabel entryNumber = new JLabel("Entry Number:");
        entryNumber.setFont(new Font("Times New Roman", Font.BOLD, 16));
        entryNumber.setBounds(50, 130, 150, 30);
        contentPane.add(entryNumber);

        sentry = new JTextField();
        sentry.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        sentry.setBounds(200, 130, 220, 30);
        contentPane.add(sentry);

        JLabel emailAddress = new JLabel("Email Address:");
        emailAddress.setFont(new Font("Times New Roman", Font.BOLD, 16));
        emailAddress.setBounds(50, 180, 150, 30);
        contentPane.add(emailAddress);

        semail = new JTextField();
        semail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        semail.setBounds(200, 180, 220, 30);
        contentPane.add(semail);

        JLabel contactNumber = new JLabel("Contact Number:");
        contactNumber.setFont(new Font("Times New Roman", Font.BOLD, 16));
        contactNumber.setBounds(50, 230, 150, 30);
        contentPane.add(contactNumber);

        scontact = new JTextField();
        scontact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        scontact.setBounds(200, 230, 220, 30);
        contentPane.add(scontact);

        JLabel homeCity = new JLabel("Home City:");
        homeCity.setFont(new Font("Times New Roman", Font.BOLD, 16));
        homeCity.setBounds(50, 280, 150, 30);
        contentPane.add(homeCity);

        shome = new JTextField();
        shome.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        shome.setBounds(200, 280, 220, 30);
        contentPane.add(shome);

        JButton submit = createStyledButton("Submit", Color.BLUE, Color.WHITE);
        submit.setBounds(75, 350, 150, 40);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        contentPane.add(submit);

        JButton cancelBtn = createStyledButton("Cancel", Color.RED, Color.WHITE);
        cancelBtn.setBounds(250, 350, 150, 40);
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Menu().setVisible(true);
                dispose();
            }
        });
        contentPane.add(cancelBtn);
    }

    private void addStudent() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("INSERT INTO student (name, entrynumber, email, contactnumber, homecity) VALUES (?, ?, ?, ?, ?)")) {

            // Validate inputs
            if (sname.getText().isEmpty() || sentry.getText().isEmpty() || semail.getText().isEmpty()
                    || scontact.getText().isEmpty() || shome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all the details!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email format
            String email = semail.getText();
            if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                JOptionPane.showMessageDialog(this, "Enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate contact number (10 digits)
            String contact = scontact.getText();
            if (!contact.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Enter a valid 10-digit contact number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert data into database
            pst.setString(1, sname.getText());
            pst.setString(2, sentry.getText());
            pst.setString(3, semail.getText());
            pst.setString(4, scontact.getText());
            pst.setString(5, shome.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new Menu().setVisible(true);
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Styled button with hover effect
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
