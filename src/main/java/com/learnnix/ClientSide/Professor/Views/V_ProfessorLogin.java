package com.learnnix.ClientSide.Professor.Views;

import com.learnnix.ClientSide.Professor.Professor;
import com.learnnix.ClientSide.Student.Student;
import com.learnnix.ClientSide.Student.Views.V_StudentDashboard;
import com.learnnix.HelperClasses.StudentInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class V_ProfessorLogin {
    private JFrame frame;

    public V_ProfessorLogin(){
        initiateFrame();
    }

    //fonction qui initialise la FRAME
    private void initiateFrame(){
        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frameStyling();
    }
    //fonction qui s'occupe d'organiser les elements au sein de la FRAME
    private void frameStyling(){
        JLabel welcomeLabel = new JLabel("Welcome! Professor");
        JLabel logo = new JLabel(new ImageIcon("src/main/resources/logo.png"));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        StylingFunctions.initiateLabels(this.frame,welcomeLabel,250,10,350,50, Font.BOLD,32);
        logo.setBounds(270,50,300,300);
        frame.add(logo);
        StylingFunctions.initiateTextFields(this.frame,emailField,280,350,300,40);
        StylingFunctions.initiateTextFields(this.frame,passwordField,280,400,300,40);
        JLabel emailLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        StylingFunctions.initiateLabels(this.frame,emailLabel,180,350,100,40,Font.PLAIN,16);
        StylingFunctions.initiateLabels(this.frame,passwordLabel,180,400,100,40,Font.PLAIN,16);
        initiateCheckbox(passwordField);
        JButton loginButton = new JButton("Login");
        StylingFunctions.buttonStyling(this.frame,loginButton,280,480,100,40);
        loginButton.addActionListener((ae)->{
            loginListener(passwordField,emailField);
        });
    }

    //fonction pour le logique et le style du checkbox qui affiche le mot de passe
    private void initiateCheckbox(JPasswordField passwordField){
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener((ae)->{if(showPassword.isSelected()){
            passwordField.setEchoChar((char) 0);
        }else{
            passwordField.setEchoChar('*');
        }});
        showPassword.setBounds(280,450,300,20);
        showPassword.setForeground(Color.decode("#175676"));
        showPassword.setFont(new Font("Roboto",Font.PLAIN,16));
        frame.add(showPassword);
    }
    //fonction listener du click sur le bouton de login
    private void loginListener(JPasswordField passwordField,JTextField emailField){
        if(passwordField.getText().equals("")||emailField.getText().equals("")){
            JOptionPane.showMessageDialog(frame,"Username and password are required Fields","Login Failed",JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                Professor professor = new Professor(emailField.getText(), passwordField.getText());
                if(professor.professorLogin()){
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    new V_ProfessorDashboard(professor);
                    frame.dispose();
                }else JOptionPane.showMessageDialog(frame,"Login Failed! Wrong Credentials","Login Failed",JOptionPane.ERROR_MESSAGE);


            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
