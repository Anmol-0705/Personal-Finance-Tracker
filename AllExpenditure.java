import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent; // Ensure ActionEvent is imported
import java.awt.event.ActionListener; // Ensure ActionListener is imported
import java.sql.*;

public class AllExpenditure {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test"; // Update with your database name
    private static final String DB_USERNAME = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "baloney"; // Update with your MySQL password

    // Constructor
    public AllExpenditure() {

        // Colors
        Color FrameColor = new Color(49, 51, 56);
        Color TextColor = new Color(255, 255, 255);
        Color ButtonColor = new Color(88, 101, 242); // Background color for buttons

        // Setting Frame
        JFrame frame = new JFrame("All Expenditures");
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        frame.getContentPane().setBackground(FrameColor);

        // Setting Fonts
        Font headingFont = new Font("Gg Sans", Font.BOLD, 25);

        // Setting Labels
        JLabel label1 = new JLabel("All Expenditures");
        label1.setFont(headingFont);
        label1.setBounds(200, 10, 300, 50);
        label1.setForeground(TextColor);

        // Creating Table
        String[] columnNames = {"Title", "Day", "Month", "Year", "Category", "Amount"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setBounds(30, 80, 540, 250);

        // Button to View PieChart
        JButton button1 = new JButton("View Monthly Expenditure Bar Graph");
        button1.setFont(headingFont);
        button1.setFocusable(false);
        button1.setBounds(75, 50, 500, 25);
        button1.setBackground(ButtonColor);
        button1.setForeground(TextColor);

        // Action Listener for the button
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("View Bar Graph button clicked!");
                // Open the PieChartDisplay window (Make sure this class is defined)
                new BarGraphDisplay();  
            }
        });

        // Fetching all data from the database
        fetchDataAndDisplayTable(model);

        // Add JScrollPane to allow scrolling in case the data exceeds table size
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 540, 250);
        frame.add(scrollPane);

        // Adding components to the frame
        frame.add(label1);
        frame.add(button1); // Add the button to the frame

        // Setting frame properties
        frame.setVisible(true);
    }

    // Fetch all data and display it in the table
    private void fetchDataAndDisplayTable(DefaultTableModel model) {
        try {
            // Database connection
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
            String query = "SELECT title, day, month, year, category, amount FROM transactions";
            Statement statement = connection.createStatement();

            // Execute the query and populate the table
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int day = resultSet.getInt("day");
                int month = resultSet.getInt("month");
                int year = resultSet.getInt("year");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");

                // Add row to the table model
                model.addRow(new Object[]{title, day, month, year, category, amount});
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AllExpenditure::new);
    }
}
