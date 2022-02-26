package com.company.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationWindow extends JFrame implements ActionListener {

    // Components of the Form
    private final Container container;
    private final JLabel title;


    // constructor, to initialize the components
    // with default values.
    public RegistrationWindow()
    {
        setTitle("Argus Child Registration");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 35);
        title.setLocation(300, 30);
        container.add(title);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void create()
    {
        RegistrationWindow f = new RegistrationWindow();
    }
}