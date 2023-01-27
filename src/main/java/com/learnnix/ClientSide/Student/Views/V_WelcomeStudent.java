package com.learnnix.ClientSide.Student.Views;

import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;

public class V_WelcomeStudent {
    private JFrame mainFrame;
    public V_WelcomeStudent(){
        initiateFrame();
    }

    private void initiateFrame(){
        this.mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500,600);
        GridBagConstraints panelConstraints = new GridBagConstraints();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(Color.white);
        mainFrame.setLocationRelativeTo(null);

        //panels
        JPanel welcomePanel = new JPanel(new FlowLayout());
        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        JLabel logo = new JLabel(new ImageIcon("src/main/resources/logo.png"));
        JLabel welcome = new JLabel("Welcome To LearnniX!");
        StylingFunctions.initiateLabels(welcomePanel,welcome,0,0,300,40,Font.BOLD,36);
        welcomePanel.add(logo);

        JButton login = new JButton("Login");
        StylingFunctions.buttonStyling(mainFrame,login,0,0,100,40);

        JButton register = new JButton("Register");
        StylingFunctions.buttonStyling(mainFrame,register,0,0,100,40);

        panelConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelConstraints.anchor = GridBagConstraints.LINE_START;
        panelConstraints.weightx = 1.0;
        panelConstraints.gridwidth = 2;
        panelConstraints.insets = new Insets(0,10,0,10);
        buttonsPanel.add(login,panelConstraints);
        panelConstraints.anchor = GridBagConstraints.LINE_END;
        buttonsPanel.add(register,panelConstraints);

        mainFrame.add(welcomePanel,BorderLayout.NORTH);
        mainFrame.add(buttonsPanel,BorderLayout.CENTER);

        login.addActionListener((ae)->{
            new V_LoginStudent();
            mainFrame.dispose();
        });
        register.addActionListener((ae)->{
            new V_RegisterStudent();
            mainFrame.dispose();
        });
    }

}
