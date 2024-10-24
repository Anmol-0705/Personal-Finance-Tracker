import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewExp {
    
    // Constructor
    public ViewExp() {

        // Colors
        Color FrameColor = new Color(49, 51, 56);
        Color TextColor = new Color(255, 255, 255);
        Color ButtonColor = new Color(88, 101, 242); // Background color for buttons

        // Setting Frame
        JFrame frame = new JFrame("View Expenditure");
        frame.setSize(700, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        frame.getContentPane().setBackground(FrameColor);

        // Setting Fonts
        Font headingFont = new Font("Gg Sans", Font.BOLD, 25);

        // Setting Labels
        JLabel label1 = new JLabel("Expenditure Viewer");
        label1.setFont(headingFont);
        label1.setBounds(200, 0, 500, 50);
        label1.setForeground(TextColor);

        // Setting Buttons
        JButton button1 = new JButton("View Current Month's Expenditure");
        button1.setFont(headingFont);
        button1.setFocusable(false);
        button1.setBounds(70, 100, 500, 50);
        button1.setBackground(ButtonColor);
        button1.setForeground(TextColor);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                new CurrentMonth();
            }
        });


        JButton button2 = new JButton("View All Expenditure");
        button2.setFont(headingFont);
        button2.setFocusable(false);
        button2.setBounds(70, 200, 500, 50);
        button2.setBackground(ButtonColor);
        button2.setForeground(TextColor);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                new AllExpenditure();
            }
        });
        


        // Setting Frame to visible
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

        // Adding components to the frame
        frame.add(label1);
        frame.add(button1);
        frame.add(button2);
    }
}