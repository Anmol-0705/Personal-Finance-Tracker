import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddExp {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test"; // Update with your database name
    private static final String DB_USERNAME = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "baloney"; // Update with your MySQL password

    // Constructor
    public AddExp() {

        // Colors
        Color FrameColor = new Color(49, 51, 56);
        Color TextColor = new Color(255, 255, 255);
        Color InputBoxColor = new Color(30, 31, 34); // Background color for textboxes
        Color ButtonColor = new Color(88, 101, 242); // Background color for buttons

        // Setting Frame
        JFrame frame = new JFrame("Add Expenditure");
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        frame.getContentPane().setBackground(FrameColor);

        // Setting Fonts
        Font headingFont = new Font("Gg Sans", Font.BOLD, 25);

        // Setting Labels
        JLabel label1 = new JLabel("Add your expenditure details here!");
        label1.setFont(headingFont);
        label1.setBounds(120, 0, 500, 50);
        

        JLabel label2 = new JLabel("Title:");
        label2.setFont(headingFont);
        label2.setBounds(75, 100, 100, 50);

        JLabel label3 = new JLabel("Date:");
        label3.setFont(headingFont);
        label3.setBounds(75, 150, 250, 50);

        JLabel label4 = new JLabel("Category:");
        label4.setFont(headingFont);
        label4.setBounds(30, 200, 200, 50);

        JLabel label5 = new JLabel("Amount:");
        label5.setFont(headingFont);
        label5.setBounds(40, 250, 100, 50);

        label1.setForeground(TextColor);
        label2.setForeground(TextColor);
        label3.setForeground(TextColor);
        label4.setForeground(TextColor);
        label5.setForeground(TextColor);
        

        // Setting Textboxes
        JTextField box1 = new JTextField();
        box1.setFont(headingFont);
        box1.setBounds(150, 105, 400, 40);
        box1.setBackground(InputBoxColor);
        box1.setForeground(TextColor);
        box1.setBorder(null);

        // Creating separate JTextFields for date
        JTextField dayField = new JTextField();
        dayField.setFont(headingFont);
        dayField.setBounds(150, 155, 100, 40);
        dayField.setBackground(InputBoxColor);
        dayField.setForeground(TextColor);
        dayField.setBorder(null);

        JTextField monthField = new JTextField();
        monthField.setFont(headingFont);
        monthField.setBounds(260, 155, 100, 40);
        monthField.setBackground(InputBoxColor);
        monthField.setForeground(TextColor);
        monthField.setBorder(null);


        JTextField yearField = new JTextField();
        yearField.setFont(headingFont);
        yearField.setBounds(370, 155, 100, 40);
        yearField.setBackground(InputBoxColor);
        yearField.setForeground(TextColor);
        yearField.setBorder(null);

        JTextField box3 = new JTextField();
        box3.setFont(headingFont);
        box3.setBounds(150, 205, 400, 40);
        box3.setBackground(InputBoxColor);
        box3.setForeground(TextColor);
        box3.setBorder(null);

        JTextField box4 = new JTextField();
        box4.setFont(headingFont);
        box4.setBounds(150, 255, 400, 40);
        box4.setBackground(InputBoxColor);
        box4.setForeground(TextColor);
        box4.setBorder(null);

        // Setting Buttons
        JButton Submit = new JButton("Submit");
        Submit.setFont(headingFont);
        Submit.setFocusable(false);
        Submit.setBounds(250, 300, 200, 50);
        Submit.setBackground(ButtonColor);
        Submit.setForeground(TextColor);
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = box1.getText();
                
                // Get and validate date input as integers
                int day = 0, month = 0, year = 0;
                try {
                    day = Integer.parseInt(dayField.getText());
                    month = Integer.parseInt(monthField.getText());
                    year = Integer.parseInt(yearField.getText());
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid date entered. Please enter valid integer values for day, month, and year.");
                    return; // Exit if date is invalid
                }

                String category = box3.getText();
                String amountText = box4.getText();

                try {
                    double amount = Double.parseDouble(amountText); // Convert amount to double

                    // Prepare SQL INSERT statement
                    String query = "INSERT INTO transactions (title, day, month, year, category, amount) VALUES (?, ?, ?, ?, ?, ?)";
                    
                    // Establish database connection and execute the query
                    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
                         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                         
                        // Set the values for the prepared statement
                        preparedStatement.setString(1, title);
                        preparedStatement.setInt(2, day);
                        preparedStatement.setInt(3, month);
                        preparedStatement.setInt(4, year);
                        preparedStatement.setString(5, category);
                        preparedStatement.setDouble(6, amount);

                        // Execute the INSERT statement
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Expenditure added successfully.");
                            label1.setText("Expenditure Successfully Added!");
                        } else {
                            System.out.println("Failed to add expenditure.");
                        }
                    } catch (SQLException sqlEx) {
                        System.out.println("Database error: " + sqlEx.getMessage());
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid amount entered. Please enter a valid number.");
                }
            }
        });

        // Adding components to the frame
        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(label4);
        frame.add(label5);
        frame.add(box1);
        frame.add(dayField);
        frame.add(monthField);
        frame.add(yearField);
        frame.add(box3);
        frame.add(box4);
        frame.add(Submit);

        // Setting Frame to visible
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

        // Debug message to check if the window is shown
        System.out.println("Add Expenditure window opened.");
    }

    // Main method (optional) to test this class separately
    public static void main(String[] args) {
        new AddExp();
    }
}
