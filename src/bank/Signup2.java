package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup2 extends JFrame implements ActionListener{

    JLabel l1, l2, l5, l8, l9, l12, l13;
    JButton b;
    JRadioButton r1, r2;
    JComboBox c4;
    JTextField t2;
    String formno;

    Signup2(String formno){

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("bank/icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l14 = new JLabel(i3);
        l14.setBounds(150, 0, 100, 100);
        add(l14);

        this.formno = formno;
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");

        l1 = new JLabel("Page 2: Additonal Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));

        l2 = new JLabel("Educational Qualification:");
        l2.setFont(new Font("Raleway", Font.BOLD, 18));

        l5 = new JLabel("Senior Citizen:");
        l5.setFont(new Font("Raleway", Font.BOLD, 18));

        l8 = new JLabel("Form No:");
        l8.setFont(new Font("Raleway", Font.BOLD, 13));

        l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD, 13));

        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);

        t2 = new JTextField();
        t2.setFont(new Font("Raleway", Font.BOLD, 14));

        r1 = new JRadioButton("Yes");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);

        r2 = new JRadioButton("No");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);

        String education[] = {"Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Others"};
        c4 = new JComboBox(education);
        c4.setBackground(Color.WHITE);
        c4.setFont(new Font("Raleway", Font.BOLD, 14));

        setLayout(null);

        l8.setBounds(700,10,60,30);
        add(l8);

        l13.setBounds(760,10,60,30);
        add(l13);

        l1.setBounds(280,30,600,40);
        add(l1);

        l2.setBounds(100,120,250,30);
        add(l2);

        c4.setBounds(350,120,320,30);
        add(c4);

        l5.setBounds(100,170,150,30);
        add(l5);

        r1.setBounds(350,170,100,30);
        add(r1);

        r2.setBounds(460,170,100,30);
        add(r2);

        b.setBounds(570,240,100,30);
        add(b);

        b.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 320);
        setLocation(500,120);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        String education = (String)c4.getSelectedItem();

        String scitizen = "";
        if(r1.isSelected()){
            scitizen = "Yes";
        }
        else if(r2.isSelected()){
            scitizen = "No";
        }

        try{
            if(t2.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Fill all the required fields");
            }else{
                Conn c1 = new Conn();
                String q1 = "insert into signup2 values('"+formno+"','"+education+"','"+scitizen+"')";
                c1.s.executeUpdate(q1);

                new Signup3(formno).setVisible(true);
                setVisible(false);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Signup2("").setVisible(true);
    }
}
