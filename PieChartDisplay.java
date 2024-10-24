import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PieChartDisplay {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test"; // Update with your database name
    private static final String DB_USERNAME = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "baloney"; // Update with your MySQL password

    public PieChartDisplay() {
        // Start a new thread to create and display the pie chart
        new Thread(() -> {
            PieChart pieChart = createPieChart();
            // Directly display the chart without invokeLater
            SwingWrapper<PieChart> swingWrapper = new SwingWrapper<>(pieChart);
            swingWrapper.displayChart(); // This runs on the current thread (which is NOT the EDT)
        }).start();
    }

    // Method to create the pie chart and fetch data
    private PieChart createPieChart() {
        Map<String, Double> categoryMap = new HashMap<>();

        // Fetch data from the database for the current month
        try {
            // Get the current month and year
            LocalDate today = LocalDate.now();
            int currentMonth = today.getMonthValue();
            int currentYear = today.getYear();

            // Database connection
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
            String query = "SELECT category, SUM(amount) AS totalAmount FROM transactions WHERE month = ? AND year = ? GROUP BY category";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentMonth); // Set current month
            preparedStatement.setInt(2, currentYear);  // Set current year

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                double totalAmount = resultSet.getDouble("totalAmount");

                // Store data in the map
                categoryMap.put(category, totalAmount);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create Pie Chart
        PieChart pieChart = new PieChart(800, 600);
        pieChart.setTitle("Expenditure by Category");
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            pieChart.addSeries(entry.getKey(), entry.getValue());
        }

        return pieChart;
    }
}
