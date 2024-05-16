package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Deposit extends JFrame implements ActionListener {

    JTextField amountField;
    JButton depositButton, backButton;
    JLabel instructionLabel, backgroundLabel;
    String pin;

    Deposit(String pin) {
        this.pin = pin;
        ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("bank/icons/atm.jpg"));
        Image backgroundImg = backgroundImageIcon.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImg);
        backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        add(backgroundLabel);

        instructionLabel = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 16));
        instructionLabel.setBounds(190, 350, 400, 35);
        backgroundLabel.add(instructionLabel);

        amountField = new JTextField();
        amountField.setFont(new Font("Raleway", Font.BOLD, 22));
        amountField.setBounds(190, 420, 320, 25);
        backgroundLabel.add(amountField);

        depositButton = new JButton("DEPOSIT");
        depositButton.setBounds(390, 588, 150, 35);
        backgroundLabel.add(depositButton);

        backButton = new JButton("BACK");
        backButton.setBounds(390, 633, 150, 35);
        backgroundLabel.add(backButton);

        setLayout(null);

        depositButton.addActionListener(this);
        backButton.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true); // Removing frame decorations like title bar
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == depositButton) {
                String amount = amountField.getText();
                Date date = new Date();
                if (amount.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the amount you want to deposit");
                } else {
                    Conn connection = new Conn();
                    connection.s.executeUpdate("insert into bank values('" + pin + "', '" + date + "', 'Deposit', '" + amount + "')");
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " deposited successfully");
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
            } else if (ae.getSource() == backButton) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
