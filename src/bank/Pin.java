package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener{

    JPasswordField newPinField, reEnterPinField;
    JButton changeButton, backButton;
    JLabel titleLabel, newPinLabel, reEnterPinLabel;
    String currentPin;

    Pin(String pin){
        this.currentPin = pin;
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("bank/icons/atm.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        add(backgroundLabel);

        titleLabel = new JLabel("CHANGE YOUR PIN");
        titleLabel.setFont(new Font("System", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        newPinLabel = new JLabel("New PIN:");
        newPinLabel.setFont(new Font("System", Font.BOLD, 16));
        newPinLabel.setForeground(Color.WHITE);

        reEnterPinLabel = new JLabel("Re-Enter New PIN:");
        reEnterPinLabel.setFont(new Font("System", Font.BOLD, 16));
        reEnterPinLabel.setForeground(Color.WHITE);

        newPinField = new JPasswordField();
        newPinField.setFont(new Font("Raleway", Font.BOLD, 25));

        reEnterPinField = new JPasswordField();
        reEnterPinField.setFont(new Font("Raleway", Font.BOLD, 25));

        changeButton = new JButton("CHANGE");
        backButton = new JButton("BACK");

        changeButton.addActionListener(this);
        backButton.addActionListener(this);

        setLayout(null);

        titleLabel.setBounds(280,330,800,35);
        backgroundLabel.add(titleLabel);

        newPinLabel.setBounds(180,390,150,35);
        backgroundLabel.add(newPinLabel);

        reEnterPinLabel.setBounds(180,440,200,35);
        backgroundLabel.add(reEnterPinLabel);

        newPinField.setBounds(350,390,180,25);
        backgroundLabel.add(newPinField);

        reEnterPinField.setBounds(350,440,180,25);
        backgroundLabel.add(reEnterPinField);

        changeButton.setBounds(390,588,150,35);
        backgroundLabel.add(changeButton);

        backButton.setBounds(390,633,150,35);
        backgroundLabel.add(backButton);

        setSize(960,1080);
        setLocation(500,0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try{
            String newPin = newPinField.getText();
            String reEnteredPin = reEnterPinField.getText();

            if(!newPin.equals(reEnteredPin)){
                JOptionPane.showMessageDialog(null, "Entered PINs do not match");
                return;
            }

            if(ae.getSource()==changeButton){
                if (newPin.equals("") || reEnteredPin.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter both New PIN and Re-entered PIN");
                    return;
                }

                Conn connection = new Conn();
                String updateBankQuery = "update bank set pin = '"+reEnteredPin+"' where pin = '"+currentPin+"' ";
                String updateLoginQuery = "update login set pin = '"+reEnteredPin+"' where pin = '"+currentPin+"' ";
                String updateSignupQuery = "update signup3 set pin = '"+reEnteredPin+"' where pin = '"+currentPin+"' ";

                connection.s.executeUpdate(updateBankQuery);
                connection.s.executeUpdate(updateLoginQuery);
                connection.s.executeUpdate(updateSignupQuery);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transactions(reEnteredPin).setVisible(true);

            } else if(ae.getSource()==backButton){
                new Transactions(currentPin).setVisible(true);
                setVisible(false);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Pin("").setVisible(true);
    }
}
