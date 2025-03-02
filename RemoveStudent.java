import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class RemoveStudent extends JFrame {

    private JPanel contentPane;
    private JTextField deleteEntry;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RemoveStudent frame = new RemoveStudent();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RemoveStudent() {
        setTitle("Remove Student");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Enter Student Roll Number:");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblTitle.setBounds(50, 30, 300, 30);
        contentPane.add(lblTitle);

        deleteEntry = new JTextField();
        deleteEntry.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        deleteEntry.setBounds(50, 70, 300, 30);
        contentPane.add(deleteEntry);

        JButton deleteData = createStyledButton("Delete", Color.RED, Color.WHITE);
        deleteData.setBounds(50, 120, 140, 40);
        deleteData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        contentPane.add(deleteData);

        JButton cancelBtn = createStyledButton("Cancel", Color.DARK_GRAY, Color.WHITE);
        cancelBtn.setBounds(210, 120, 140, 40);
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Menu().setVisible(true);
                dispose();
            }
        });
        contentPane.add(cancelBtn);
    }

    private void deleteStudent() {
        String entryNumber = deleteEntry.getText().trim();
        if (entryNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Roll Number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete student " + entryNumber + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("DELETE FROM students WHERE rollno=?")) {

            pst.setString(1, entryNumber);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Menu().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No student found with Roll Number: " + entryNumber, "Not Found", JOptionPane.WARNING_MESSAGE);
            }
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
