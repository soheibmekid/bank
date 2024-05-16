package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel instructionLabel;
    JButton euros100Button, euros500Button, euros1000Button, euros2000Button, euros5000Button, euros10000Button, backButton;
    String pin;

    FastCash(String pin) {
        this.pin = pin;
        ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("bank/icons/atm.jpg"));
        Image backgroundImg = backgroundImageIcon.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImg);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        add(backgroundLabel);

        instructionLabel = new JLabel("SELECT WITHDRAWAL AMOUNT");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 16));
        instructionLabel.setBounds(235, 400, 700, 35);
        backgroundLabel.add(instructionLabel);

        euros100Button = new JButton("Euros 100");
        euros500Button = new JButton("Euros 500");
        euros1000Button = new JButton("Euros 1000");
        euros2000Button = new JButton("Euros 2000");
        euros5000Button = new JButton("Euros 5000");
        euros10000Button = new JButton("Euros 10000");
        backButton = new JButton("BACK");

        euros100Button.setBounds(170, 499, 150, 35);
        euros500Button.setBounds(390, 499, 150, 35);
        euros1000Button.setBounds(170, 543, 150, 35);
        euros2000Button.setBounds(390, 543, 150, 35);
        euros5000Button.setBounds(170, 588, 150, 35);
        euros10000Button.setBounds(390, 588, 150, 35);
        backButton.setBounds(390, 633, 150, 35);

        backgroundLabel.add(euros100Button);
        backgroundLabel.add(euros500Button);
        backgroundLabel.add(euros1000Button);
        backgroundLabel.add(euros2000Button);
        backgroundLabel.add(euros5000Button);
        backgroundLabel.add(euros10000Button);
        backgroundLabel.add(backButton);

        euros100Button.addActionListener(this);
        euros500Button.addActionListener(this);
        euros1000Button.addActionListener(this);
        euros2000Button.addActionListener(this);
        euros5000Button.addActionListener(this);
        euros10000Button.addActionListener(this);
        backButton.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = ((JButton)ae.getSource()).getText().substring(7); // Extracting amount from button text
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");
            int balance = 0;
            while (rs.next()) {
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }

            if (ae.getSource() != backButton && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }

            if (ae.getSource() == backButton) {
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
            } else {
                Date date = new Date();
                c.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawal', '"+amount+"')");
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");

                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}
