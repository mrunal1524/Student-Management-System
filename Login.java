import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 460, 450);

        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        loginLabel.setBounds(190, 20, 100, 30);
        contentPane.add(loginLabel);

        JLabel loginUsername = new JLabel("Username:");
        loginUsername.setFont(new Font("Times New Roman", Font.BOLD, 16));
        loginUsername.setBounds(50, 90, 100, 30);
        contentPane.add(loginUsername);

        username = new JTextField();
        username.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        username.setBounds(170, 90, 200, 30);
        contentPane.add(username);

        JLabel loginPassword = new JLabel("Password:");
        loginPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
        loginPassword.setBounds(50, 140, 100, 30);
        contentPane.add(loginPassword);

        password = new JPasswordField();
        password.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        password.setBounds(170, 140, 200, 30);
        contentPane.add(password);

        JButton loginButton = createStyledButton("Login");
        loginButton.setBounds(150, 220, 150, 40);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
        contentPane.add(loginButton);
    }

    private void authenticateUser() {
        String user = username.getText();
        String pass = new String(password.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the Username or Password.");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                Menu menuPage = new Menu();  
                menuPage.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Connection Error!");
        }
    }

    // Styled button with hover effect
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
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
