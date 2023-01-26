package com.learnnix.ClientSide.Admin.Views;

import com.learnnix.ClientSide.Admin.Admin;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;

public class V_AddProfessor {
    private JFrame mainFrame;
    private Admin admin;
    private V_AdminDashboard adminDashboard;
    public V_AddProfessor(Admin admin, V_AdminDashboard v_adminDashboard){
        this.adminDashboard = v_adminDashboard;
        this.admin = admin;
        initiateFrame();
    }
    private void initiateFrame() {
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(600,600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        setFrameComponents();
    }
    //ajouter les components a la fenetre
    private void setFrameComponents(){
        JLabel infoLabel = new JLabel("New Professor");
        StylingFunctions.initiateLabels(mainFrame,infoLabel,150,50,300,40,Font.BOLD,32);
        JTextField profNameField = new JTextField();
        JPasswordField profPasswordField = new JPasswordField();
        JTextField profSpecialityField = new JTextField();
        V_UpdateProf.addFields(profNameField, profPasswordField, profSpecialityField, mainFrame);
        JButton addButton = new JButton("Save");
        StylingFunctions.buttonStyling(mainFrame,addButton,200,350,100,50);
        addButton.addActionListener((ae)->addProfButtonListener(profNameField,profPasswordField,profSpecialityField));
    }

    private void addProfButtonListener(JTextField nameField,JPasswordField passwordField,JTextField specialityField){
        if(nameField.getText().equals("")||passwordField.getText().equals("")||specialityField.getText().equals("")){
            JOptionPane.showMessageDialog(mainFrame,"All the fields are Required","Add Failed",JOptionPane.ERROR_MESSAGE);
        }else{
            ProfessorInfos professor = new ProfessorInfos(nameField.getText(), specialityField.getText(), passwordField.getText());
            if(admin.addProfessor(professor)){
                adminDashboard.onProfAdded();
                JOptionPane.showMessageDialog(mainFrame,"Professor Added successfully");
                mainFrame.dispose();
            }else{
                JOptionPane.showMessageDialog(mainFrame,"Something went wrong! Please try again later","Login Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
