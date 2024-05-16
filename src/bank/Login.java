package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel welcomeLabel, cardLabel, pinLabel;
    JTextField cardField;
    JPasswordField pinField;
    JButton signInButton, clearButton, signUpButton;

    Login() {
        setTitle("Login");

        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("bank/icons/logo.jpg"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setBounds(70, 10, 100, 100);
        add(logoLabel);

        welcomeLabel = new JLabel("Banque Epargne");
        welcomeLabel.setFont(new Font("Osward", Font.BOLD, 38));
        welcomeLabel.setBounds(200, 40, 450, 40);
        add(welcomeLabel);

        cardLabel = new JLabel("Carte No:");
        cardLabel.setFont(new Font("Raleway", Font.BOLD, 28));
        cardLabel.setBounds(125, 150, 375, 30);
        add(cardLabel);

        cardField = new JTextField(15);
        cardField.setBounds(300, 150, 230, 30);
        cardField.setFont(new Font("Arial", Font.BOLD, 14));
        add(cardField);

        pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Raleway", Font.BOLD, 28));
        pinLabel.setBounds(125, 220, 375, 30);
        add(pinLabel);

        pinField = new JPasswordField(15);
        pinField.setFont(new Font("Arial", Font.BOLD, 14));
        pinField.setBounds(300, 220, 230, 30);
        add(pinField);

        signInButton = new JButton("SE CONNECTER");
        signInButton.setBackground(Color.BLACK);
        signInButton.setForeground(Color.WHITE);

        clearButton = new JButton("EFFACER");
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);

        signUpButton = new JButton("S'INSCRIRE");
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setForeground(Color.WHITE);

        setLayout(null);

        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.setBounds(300, 300, 100, 30);
        add(signInButton);

        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBounds(430, 300, 100, 30);
        add(clearButton);

        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBounds(300, 350, 230, 30);
        add(signUpButton);

        signInButton.addActionListener(this);
        clearButton.addActionListener(this);
        signUpButton.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(800, 480);
        setLocation(550, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == signInButton) {
                Conn connection = new Conn();
                String cardNumber = cardField.getText();
                String pin = new String(pinField.getPassword());
                String query = "select * from login where cardno = '" + cardNumber + "' and pin = '" + pin + "'";

                ResultSet resultSet = connection.s.executeQuery(query);
                if (resultSet.next()) {
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
                }
            } else if (ae.getSource() == clearButton) {
                cardField.setText("");
                pinField.setText("");
            } else if (ae.getSource() == signUpButton) {
                setVisible(false);
                new Signup().setVisible(true);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
