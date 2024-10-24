import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
    
    JFrame frame;
    JLabel label;

    // Colors
    Color FrameColor = new Color(49, 51, 56);
    Color TextColor = new Color(255, 255, 255);
    Color InputBoxColor = new Color(30, 31, 34); // Background color for textboxes
    Color ButtonColor = new Color(88, 101, 242); // Background color for buttons
    
    // Constructor
    public HomePage(String username) {
        frame = new JFrame("Home Page");
        label = new JLabel("Hello, " + username);
        frame.getContentPane().setBackground(FrameColor);
        
        
        // Set label properties
        label.setBounds(230, 10, 300, 50);
        label.setFont(new Font("Gg Sans", Font.BOLD, 25));
        label.setForeground(TextColor);
        

        // Buttons
        JButton Button1 = new JButton("Add Expenditure");
        Button1.setFont(new Font("Gg Sans", Font.BOLD, 25));
        Button1.setFocusable(false);
        Button1.setBounds(70, 100, 500, 50);
        Button1.setBackground(ButtonColor);
        Button1.setForeground(TextColor);
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add Expenditure button clicked!");
                new AddExp();  // Open the AddExp window (separate Java file)
            }
        });

        JButton Button2 = new JButton("View Expenses");
        Button2.setFont(new Font("Gg Sans", Font.BOLD, 25));
        Button2.setFocusable(false);
        Button2.setBounds(70, 200, 500, 50);
        Button2.setBackground(ButtonColor);
        Button2.setForeground(TextColor);
        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("View Expenditure button clicked!");
                new ViewExp();  // Open the AddExp window (separate Java file)
            }
        });

        // Setting frame properties
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Adding components to frame
        frame.add(label);
        frame.add(Button1);
        frame.add(Button2);
    }
    
    // Main method to run the HomePage
    public static void main(String[] args) {
        new HomePage("User");  // Replace "User" with the actual username
    }
} 
