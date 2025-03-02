import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu frame = new Menu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Menu() {
        setTitle("Student Management - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 600);

        contentPane = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(34, 193, 195);
                Color color2 = new Color(253, 187, 45);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("STUDENT MANAGEMENT");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(120, 20, 300, 40);
        contentPane.add(lblTitle);

        JButton btnAddStudent = createStyledButton("Add Student");
        btnAddStudent.setBounds(75, 90, 350, 50);
        btnAddStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Student().setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnAddStudent);

        JButton btnRemoveStudent = createStyledButton("Remove Student");
        btnRemoveStudent.setBounds(75, 160, 350, 50);
        btnRemoveStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RemoveStudent().setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnRemoveStudent);

        JButton btnViewStudents = createStyledButton("View Students");
        btnViewStudents.setBounds(75, 230, 350, 50);
        btnViewStudents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewStudent().setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnViewStudents);

        JButton btnUpdateStudent = createStyledButton("Update Student");
        btnUpdateStudent.setBounds(75, 300, 350, 50);
        btnUpdateStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateStudent().setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnUpdateStudent);

        JButton btnLogout = createStyledButton("Logout");
        btnLogout.setBounds(75, 370, 350, 50);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
                JOptionPane.showMessageDialog(null, "Successfully logged out.");
            }
        });
        contentPane.add(btnLogout);
    }

    // Helper method to create styled buttons with Times New Roman font
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(0, 51, 102));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }
}
