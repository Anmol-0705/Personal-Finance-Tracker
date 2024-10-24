import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BarGraphDisplay {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test"; // Update with your database name
    private static final String DB_USERNAME = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "baloney"; // Update with your MySQL password

    public BarGraphDisplay() {
        // Fetch expenditures and create a bar chart
        HashMap<Integer, Double> expenditures = fetchExpenditures();
        createBarChart(expenditures);
    }

    // Fetch expenditures from the database and return a map of month to total expenditures
    private HashMap<Integer, Double> fetchExpenditures() {
        HashMap<Integer, Double> expenditures = new HashMap<>();

        try {
            // Database connection
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
            String query = "SELECT month, SUM(amount) AS total_amount FROM transactions GROUP BY month";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                double totalAmount = resultSet.getDouble("total_amount");
                expenditures.put(month, totalAmount);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenditures;
    }

    // Create and display the bar chart
    private void createBarChart(HashMap<Integer, Double> expenditures) {
        // Prepare data for the bar chart
        List<String> months = new ArrayList<>();
        List<Double> totals = new ArrayList<>();

        for (int month : expenditures.keySet()) {
            months.add(getMonthName(month)); // Convert month number to name
            totals.add(expenditures.get(month));
        }

        // Create the chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Monthly Expenditures")
                .xAxisTitle("Month")
                .yAxisTitle("Total Amount")
                .build();

        // Add data to the chart
        chart.addSeries("Expenditure", months, totals);

        // Create a JFrame to display the chart
        JFrame frame = new JFrame("Monthly Expenditures");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Use XChartPanel instead of SwingWrapper
        XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
        frame.add(chartPanel); // Add the chart panel to the frame
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true); // Make frame visible
    }

    // Convert month number to month name
    private String getMonthName(int month) {
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month - 1]; // Month is 1-based
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BarGraphDisplay::new); // Ensure the GUI is created on the Event Dispatch Thread
    }
}
