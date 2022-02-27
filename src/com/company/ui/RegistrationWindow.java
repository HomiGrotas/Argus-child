package com.company.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class RegistrationWindow extends JFrame implements ActionListener {
    private final int WIDTH = 900;
    private final int HEIGHT = 600;

    private final JPanel photoPanel;

    // Components of the Form
    private final Container container;
    private final JLabel title;

    private final JLabel parentTokenT;
    private final JTextField parentTokenF;

    private final JLabel nicknameT;
    private final JTextField nicknameF;

    private final JButton register;

    private final Font font1 = new Font("Arial", Font.PLAIN, 40);
    private final Font font2 = new Font("Arial", Font.PLAIN, 30);


    // constructor, to initialize the components
    // with default values.
    public RegistrationWindow()
    {
        setTitle("Argus Child Registration");
        setBounds(0, 0, WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        // argus photo
        photoPanel = new JPanel();
        try {
            File picF = new File(Path.of("com/company/assets/background.png").normalize().toUri());
            System.out.println(picF.getAbsolutePath());
            System.out.println(picF.exists());

            BufferedImage backgroundImg = ImageIO.read(picF);
            JLabel pic = new JLabel(new ImageIcon(backgroundImg));
            container.add(pic);

        }
        catch (IOException ignored){
            photoPanel.setBackground(new Color(0));
        }

        photoPanel.setBounds(WIDTH/2, 0, WIDTH/2, HEIGHT);


        photoPanel.setVisible(true);
        container.add(photoPanel);

        // Registration Form
        title = new JLabel("Registration Form");
        title.setFont(font1);
        title.setSize(WIDTH, 50);
        title.setLocation(300, 30);
        container.add(title);


        // Parent token
        parentTokenT = new JLabel("Parent Token:");
        parentTokenT.setFont(font2);
        parentTokenT.setBounds(50, 150, 250, 30);

        parentTokenF = new JTextField();
        parentTokenF.setFont(font2);
        parentTokenF.setBounds(270, 150, 250, 30);

        container.add(parentTokenT);
        container.add(parentTokenF);

        // nickname
        nicknameT = new JLabel("Nickname:");
        nicknameT.setFont(font2);
        nicknameT.setBounds(50, 220, 250, 30);

        nicknameF = new JTextField();
        nicknameF.setFont(font2);
        nicknameF.setBounds(270, 220, 250, 30);

        container.add(nicknameT);
        container.add(nicknameF);


        // register button
        register = new JButton("Register");
        register.setBounds(250, 300, 200, 50);

        container.add(register);


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