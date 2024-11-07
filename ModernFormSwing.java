import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class ModernFormSwing extends JFrame implements ActionListener {
    private JTextField firstNameField, lastNameField, mobileField, addressField;
    private JComboBox<String> genderComboBox, categoryComboBox;
    private JButton submitButton;

    public ModernFormSwing() {
        setTitle("User Information Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(new Color(25, 25, 112)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(firstNameField, gbc);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameLabel, gbc);

        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lastNameField, gbc);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(mobileLabel, gbc);

        mobileField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(mobileField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(genderLabel, gbc);

        String[] genders = {"Select Gender", "Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(genderComboBox, gbc);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(addressLabel, gbc);

        addressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(addressField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(categoryLabel, gbc);

        String[] categories = {"Select Category", "Technology", "Education", "Healthcare"};
        categoryComboBox = new JComboBox<>(categories);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(categoryComboBox, gbc);

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); // Dark blue button
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.addActionListener(this);
        submitButton.addMouseListener(new HoverEffect());
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String mobile = mobileField.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            String address = addressField.getText();
            String category = (String) categoryComboBox.getSelectedItem();

            if (insertData(firstName, lastName, mobile, gender, address, category)) {
                JOptionPane.showMessageDialog(this, "Data submitted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit data.");
            }
        }
    }

    private boolean insertData(String firstName, String lastName, String mobile, String gender, String address, String category) {
        String url = "jdbc:mysql://localhost:3306/UserInformation";
        String username = "root"; // Replace with your MySQL username
        String password = "Suyash@1230"; // Replace with your MySQL password

        String query = "INSERT INTO user_info (first_name, last_name, mobile, gender, address, category) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, mobile);
            pstmt.setString(4, gender);
            pstmt.setString(5, address);
            pstmt.setString(6, category);

            int count = pstmt.executeUpdate();
            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    class HoverEffect extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof JButton) {
                component.setBackground(new Color(100, 149, 237)); // Lighter blue on hover
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof JButton) {
                component.setBackground(new Color(70, 130, 180));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ModernFormSwing::new);
    }
}
