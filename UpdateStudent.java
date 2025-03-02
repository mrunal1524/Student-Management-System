import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UpdateStudent extends JFrame {

    private JPanel contentPane;
    private JTextField updateEntry, nameU, entryU, emailU, contactU, homeU;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UpdateStudent frame = new UpdateStudent();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UpdateStudent() {
        setTitle("Update Student");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 600);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Update Student Details");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitle.setBounds(140, 20, 300, 30);
        contentPane.add(lblTitle);

        JLabel lblEntry = new JLabel("Enter Roll Number:");
        lblEntry.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblEntry.setBounds(50, 80, 200, 30);
        contentPane.add(lblEntry);

        updateEntry = new JTextField();
        updateEntry.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        updateEntry.setBounds(200, 80, 220, 30);
        contentPane.add(updateEntry);

        JButton searchBtn = createStyledButton("Search", Color.BLUE, Color.WHITE);
        searchBtn.setBounds(170, 130, 150, 40);
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });
        contentPane.add(searchBtn);

        JLabel lblName = new JLabel("Student Name:");
        lblName.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblName.setBounds(50, 190, 150, 30);
        contentPane.add(lblName);

        nameU = new JTextField();
        nameU.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        nameU.setBounds(200, 190, 220, 30);
        contentPane.add(nameU);

        JLabel lblEntryNo = new JLabel("Entry Number:");
        lblEntryNo.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblEntryNo.setBounds(50, 230, 150, 30);
        contentPane.add(lblEntryNo);

        entryU = new JTextField();
        entryU.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        entryU.setBounds(200, 230, 220, 30);
        contentPane.add(entryU);

        JLabel lblEmail = new JLabel("Email Address:");
        lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblEmail.setBounds(50, 270, 150, 30);
        contentPane.add(lblEmail);

        emailU = new JTextField();
        emailU.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        emailU.setBounds(200, 270, 220, 30);
        contentPane.add(emailU);

        JLabel lblContact = new JLabel("Contact Number:");
        lblContact.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblContact.setBounds(50, 310, 150, 30);
        contentPane.add(lblContact);

        contactU = new JTextField();
        contactU.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        contactU.setBounds(200, 310, 220, 30);
        contentPane.add(contactU);

        JLabel lblHome = new JLabel("Home City:");
        lblHome.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblHome.setBounds(50, 350, 150, 30);
        contentPane.add(lblHome);

        homeU = new JTextField();
        homeU.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        homeU.setBounds(200, 350, 220, 30);
        contentPane.add(homeU);

        JButton updateBtn = createStyledButton("Update", Color.GREEN, Color.WHITE);
        updateBtn.setBounds(75, 420, 150, 40);
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
        contentPane.add(updateBtn);

        JButton cancelBtn = createStyledButton("Cancel", Color.RED, Color.WHITE);
        cancelBtn.setBounds(250, 420, 150, 40);
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Menu().setVisible(true);
                dispose();
            }
        });
        contentPane.add(cancelBtn);
    }

    private void searchStudent() {
        String entry = updateEntry.getText().trim();
        if (entry.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Roll Number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("SELECT * FROM student WHERE entrynumber = ?")) {

            pst.setString(1, entry);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nameU.setText(rs.getString("name"));
                entryU.setText(rs.getString("entrynumber"));
                emailU.setText(rs.getString("email"));
                contactU.setText(rs.getString("contactnumber"));
                homeU.setText(rs.getString("homecity"));
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("UPDATE student SET name=?, entrynumber=?, email=?, contactnumber=?, homecity=? WHERE entrynumber=?")) {

            String pid = updateEntry.getText();
            if (nameU.getText().isEmpty() || entryU.getText().isEmpty() || emailU.getText().isEmpty() || contactU.getText().isEmpty() || homeU.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all the details!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pst.setString(1, nameU.getText());
            pst.setString(2, entryU.getText());
            pst.setString(3, emailU.getText());
            pst.setString(4, contactU.getText());
            pst.setString(5, homeU.getText());
            pst.setString(6, pid);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
