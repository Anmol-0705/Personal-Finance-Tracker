import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage {
    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test"; // Update with your database name
    private static final String DB_USERNAME = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "baloney"; // Update with your MySQL password

    public static void main(String[] args) {
        
        // Colors
        Color FrameColor = new Color(49, 51, 56);
        Color TextColor = new Color(255, 255, 255);
        Color InputBoxColor = new Color(30, 31, 34); // Background color for textboxes
        Color ButtonColor = new Color(88, 101, 242); // Background color for buttons

        // Setting Frame
        JFrame frame = new JFrame("Personal Finance Tracker");
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.getContentPane().setBackground(FrameColor);

        // Setting Fonts
        Font Heading = new Font("Gg Sans", Font.BOLD, 25);
        Font font1 = new Font("Gg Sans", Font.BOLD, 20);

        // Labels
        JLabel label1 = new JLabel("Welcome to Personal Finance Tracker Application");
        JLabel label2 = new JLabel("Username *");
        JLabel label3 = new JLabel("Password *");
        
        label1.setFont(Heading);
        label2.setFont(font1);
        label3.setFont(font1);

        label1.setForeground(TextColor);
        label2.setForeground(TextColor);
        label3.setForeground(TextColor);

        label1.setBounds(55, 10, 600, 30);
        label2.setBounds(75, 100, 150, 30);
        label3.setBounds(75, 200, 150, 30);

        // Textboxes
        JTextField Userbox = new JTextField();
        JPasswordField Passbox = new JPasswordField();
        Userbox.setFont(font1);
        Passbox.setFont(font1);
        Userbox.setBounds(75, 130, 300, 40);
        Passbox.setBounds(75, 230, 300, 40);
        
        // Set background and text color for textboxes
        Userbox.setBackground(InputBoxColor);
        Userbox.setForeground(TextColor);
        Userbox.setOpaque(true); // Ensure background is visible
        Userbox.setBorder(null); // Remove border

        Passbox.setBackground(InputBoxColor);
        Passbox.setForeground(TextColor);
        Passbox.setOpaque(true); // Ensure background is visible
        Passbox.setBorder(null); // Remove border

        // Login Button
        JButton Login = new JButton("Login");
        Login.setFont(font1);
        Login.setFocusable(false);
        Login.setBounds(150, 300, 150, 30);
        Login.setBackground(ButtonColor); // Set button background color
        Login.setForeground(TextColor); // Set button text color
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = Userbox.getText();
                String password = new String(Passbox.getPassword());
                if (checkCredentials(username, password)) {
                    System.out.println("Login successful!");
                    new HomePage(username); // Open the Home Page and pass the username
                    frame.dispose(); // Close the login page window
                } else {
                    System.out.println("Invalid username or password.");
                    label1.setText("Username or Password Incorrect!");
                }
            }
        });

        // Register Button
        JButton Register = new JButton("Register");
        Register.setFont(font1);
        Register.setFocusable(false);
        Register.setBounds(310, 300, 150, 30);
        Register.setBackground(ButtonColor); // Set button background color
        Register.setForeground(TextColor); // Set button text color
        Register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = Userbox.getText();
                String password = new String(Passbox.getPassword());
                if (registerUser(username, password)) {
                    System.out.println("Registration successful!");
                    label1.setText("Registration Successful!");
                } else {
                    System.out.println("Registration failed.");
                }
            }
        });

        // Adding components to the frame
        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(Login);
        frame.add(Register);
        frame.add(Userbox);
        frame.add(Passbox);

        // Setting Frame to visible
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    // Method to check the username and password against the database
    private static boolean checkCredentials(String username, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?"; // Use parameterized query for security
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            isValid = resultSet.next(); // If a row is returned, the credentials are valid
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return isValid;
    }

    // Method to register a new user
    private static boolean registerUser(String username, String password) {
        boolean isRegistered = false;
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            int rowsAffected = preparedStatement.executeUpdate();
            isRegistered = rowsAffected > 0; // If a row is inserted, registration is successful
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return isRegistered;
    }
}
