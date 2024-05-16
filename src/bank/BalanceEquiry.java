package bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import java.util.*;

class BalanceEquiry extends JFrame implements ActionListener {

    JTextField balanceField, dummyField;
    JButton backButton;
    JLabel balanceLabel, backgroundLabel;
    String pin;

    BalanceEquiry(String pin) {
        this.pin = pin;

        // Setting up background image
        ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("bank/icons/atm.jpg"));
        Image backgroundImg = backgroundImageIcon.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImg);
        backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        add(backgroundLabel);

        balanceLabel = new JLabel();
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.BOLD, 16));
        balanceLabel.setBounds(190, 350, 400, 35);
        backgroundLabel.add(balanceLabel);

        backButton = new JButton("BACK");
        backButton.setBounds(390, 633, 150, 35);
        backgroundLabel.add(backButton);

        setLayout(null);

        int balance = 0;
        try {
            Conn connection = new Conn();
            ResultSet resultSet = connection.s.executeQuery("select * from bank where pin = '" + pin + "'");
            while (resultSet.next()) {
                if (resultSet.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(resultSet.getString("amount"));
                } else {
                    balance -= Integer.parseInt(resultSet.getString("amount"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        balanceLabel.setText("Your Current Account Balance is Euros " + balance);

        // Adding ActionListener to back button
        backButton.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEquiry("").setVisible(true);
    }
}
