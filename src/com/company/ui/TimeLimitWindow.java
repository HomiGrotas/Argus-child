package com.company.ui;

import com.company.local.timeLimit.MonitorTime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class TimeLimitWindow extends JFrame implements ActionListener {
    final String dayOpened;

    public TimeLimitWindow(){
        setTitle("Argus Child Registration");
        Font font1 = new Font("Arial", Font.PLAIN, 40);
        Font font2 = new Font("Arial", Font.PLAIN, 30);
        dayOpened = MonitorTime.getCurrentDay();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        final int WIDTH = (int) size.getWidth();
        final int HEIGHT = (int) size.getHeight();

        setUndecorated(true);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // prevent window closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            }
        });

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
                Image resizedImage = backgroundImg.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

                JLabel pic = new JLabel(new ImageIcon(resizedImage));
                pic.setBounds((int) (WIDTH/4), 0, WIDTH, HEIGHT);
                container.add(pic);
            }
        }
        catch (IOException ignored){
            photoPanel.setBackground(new Color(0));
        }
        photoPanel.setBounds(0, 0, (int)(WIDTH /3.5), HEIGHT);
        photoPanel.setVisible(true);

        // Title
        JLabel title = new JLabel("Oops!");
        title.setFont(font1);
        title.setSize(WIDTH, 50);
        title.setLocation(50, 100);
        title.setForeground(new Color(0x930E0E));
        container.add(title, 0);

        JLabel desc = new JLabel("You reached your time limit");
        desc.setFont(font1);
        desc.setSize(WIDTH, 50);
        desc.setLocation(50, 200);
        desc.setForeground(new Color(0x930E0E));
        container.add(desc, 0);

        // Instructions
        JLabel instructions = new JLabel("You need to refresh if your parent changed your limits");
        instructions.setFont(font2);
        instructions.setSize(WIDTH, 50);
        instructions.setLocation(50, 300);
        instructions.setForeground(new Color(0x930E0E));
        container.add(instructions, 0);


        // Refresh button
        Button refreshButton = new Button("Refresh");
        refreshButton.setFont(font2);
        refreshButton.setBounds(150, 450, 150, 50);
        refreshButton.addActionListener(this);
        container.add(refreshButton, 1);


        setVisible(true);
    }


    public static void create()
    {
        new TimeLimitWindow();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed for refresh");
        if (!MonitorTime.getCurrentDay().equals(dayOpened))
        {
            // todo: check for change in server
            System.out.println("can count again");
        }
    }
}
