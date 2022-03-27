package com.company.ui;

import com.company.local.Auth;
import com.company.utils.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class RegistrationWindow extends JFrame implements ActionListener {
    private final JTextField nicknameF;
    private final JTextField parentTokenF;
    private final JLabel infoMessage;

    // constructor, to initialize the components
    // with default values.
    public RegistrationWindow()
    {
        setTitle("Argus Child Registration");
        int WIDTH = 900;
        int HEIGHT = 600;
        Font font1 = new Font("Arial", Font.PLAIN, 40);
        Font font2 = new Font("Arial", Font.PLAIN, 30);



        setBounds(0, 0, WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Components of the Form
        Container container = getContentPane();
        container.setBackground(new Color(0x4371C3));
        container.setLayout(null);

        // argus photo
        JPanel photoPanel = new JPanel();
        try {
            URL url = RegistrationWindow.class.getResource("/com/company/assets/background.png");
            if (url != null)
            {
                BufferedImage backgroundImg = ImageIO.read(url);
                Image resizedImage = backgroundImg.getScaledInstance(800, HEIGHT, Image.SCALE_DEFAULT);

                JLabel pic = new JLabel(new ImageIcon(resizedImage));
                pic.setBounds(200, 0, WIDTH, HEIGHT);
                container.add(pic);
            }
        }
        catch (IOException ignored){
            photoPanel.setBackground(new Color(0));
        }
        photoPanel.setBounds(WIDTH /2, 0, WIDTH /2, HEIGHT);
        photoPanel.setVisible(true);

        // Registration Form
        JLabel title = new JLabel("Registration Form");
        title.setFont(font1);
        title.setSize(WIDTH, 50);
        title.setLocation(230, 30);

        // Parent token
        JLabel parentTokenT = new JLabel("Parent Token:");
        parentTokenT.setFont(font2);
        parentTokenT.setBounds(20, 150, 250, 30);

        parentTokenF = new JTextField();
        parentTokenF.setFont(font2);
        parentTokenF.setBounds(230, 150, 250, 30);

        container.add(parentTokenT);
        container.add(parentTokenF);

        // nickname
        JLabel nicknameT = new JLabel("Nickname:");
        nicknameT.setFont(font2);
        nicknameT.setBounds(20, 220, 250, 30);

        nicknameF = new JTextField();
        nicknameF.setFont(font2);
        nicknameF.setBounds(230, 220, 250, 30);

        container.add(nicknameT);
        container.add(nicknameF);

        // infoMessage message
        infoMessage = new JLabel();
        infoMessage.setBounds(50, 420, WIDTH, 100);
        infoMessage.setFont(font2);

        // register button
        JButton register = new JButton("Register");
        register.setBounds(250, 300, 200, 50);
        register.addActionListener(this);

        container.add(register, 0);
        container.add(photoPanel, 2);
        container.add(infoMessage, 0);
        container.add(title, 0);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String parentToken = parentTokenF.getText();
        String childNickname = nicknameF.getText();
        Pair<Boolean, String> success_and_msg = Auth.register(childNickname, parentToken);
        if (!success_and_msg.a)
        {
            infoMessage.setText("Error: "+success_and_msg.b);
            infoMessage.setForeground(new Color(218, 13, 13));
        }
        else {
            System.out.println(success_and_msg.b);
            infoMessage.setText("Successfully registered. You can close the window.");
            infoMessage.setForeground(Color.GREEN);
        }
    }

    public static void create()
    {
        new RegistrationWindow();
    }
}