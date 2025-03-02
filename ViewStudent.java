import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

public class ViewStudent extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField searchField;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewStudent frame = new ViewStudent();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ViewStudent() {
        setTitle("View Students");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // **Title Label**
        JLabel lblTitle = new JLabel("Student Details", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // **Search Panel**
        JPanel searchPanel = new JPanel();
        JLabel lblSearch = new JLabel("Search: ");
        lblSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                searchStudent(searchField.getText());
            }
        });
        searchPanel.add(lblSearch);
        searchPanel.add(searchField);
        contentPane.add(searchPanel, BorderLayout.SOUTH);

        // **Table Model**
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"Roll No", "Name", "Email", "Contact", "Home City"});

        // **Student Table**
        table = new JTable(tableModel);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // **Back Button**
        JPanel bottomPanel = new JPanel();
        JButton btnBack = createStyledButton("Go Back", Color.RED, Color.WHITE);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Menu().setVisible(true);
                dispose();
            }
        });
        bottomPanel.add(btnBack);
        contentPane.add(bottomPanel, BorderLayout.NORTH);

        // Load Student Data from Database
        loadStudentData();
    }

    // **Method to Load Student Data from Database**
    private void loadStudentData() {
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

            tableModel.setRowCount(0);  // Clear Table before loading new data
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("rollno"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("contactnumber"),
                    rs.getString("homecity")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // **Method to Filter Students Dynamically**
    private void searchStudent(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));  // Case-insensitive search
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
