package com.learnnix.ClientSide.Admin.Views;

import com.learnnix.ClientSide.Admin.Admin;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;

public class V_UpdateProf {
    private int profId;
    private  Admin admin;
    private ProfessorInfos professorData;
    private JFrame mainFrame;
    private V_AdminDashboard dashboard;

    public V_UpdateProf(int profId, Admin admin, V_AdminDashboard dashboard) {
        this.dashboard = dashboard;
        this.profId = profId;
        this.admin = admin;
        this.professorData = this.admin.getProfessor(profId);
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
        JLabel infoLabel = new JLabel("Update Professor");
        StylingFunctions.initiateLabels(mainFrame,infoLabel,150,50,300,40, Font.BOLD,32);
        JLabel idLabel = new JLabel("Professor ID: "+profId);
        StylingFunctions.initiateLabels(mainFrame,idLabel,200,100,300,40,Font.BOLD,26);
        JTextField profNameField = new JTextField();
        profNameField.setText(professorData.getProf_username());
        JTextField profPasswordField = new JTextField();
        profPasswordField.setText(professorData.getProfPassword());
        JTextField profSpecialityField = new JTextField();
        profSpecialityField.setText(professorData.getProf_speciality());
        addFields(profNameField, profPasswordField, profSpecialityField, mainFrame);
        JButton updateButton = new JButton("Update");
        StylingFunctions.buttonStyling(mainFrame,updateButton,200,350,100,50);
        updateButton.addActionListener((ae)->updateButtonListener(profNameField,profPasswordField,profSpecialityField));
        JButton cancelButton = new JButton("Cancel");
        StylingFunctions.buttonStyling(mainFrame,cancelButton,350,350,100,50);
        cancelButton.addActionListener((ae)->{mainFrame.dispose();});
    }

    static void addFields(JTextField profNameField, JTextField profPasswordField, JTextField profSpecialityField, JFrame mainFrame) {
        JLabel profNameLabel = new JLabel("Professor Name");
        JLabel profPasswordLabel = new JLabel("Professor Password");
        JLabel profSpecialityLabel = new JLabel("Professor Speciality");
        StylingFunctions.initiateTextFields(mainFrame,profNameField,200,200,300,40);
        StylingFunctions.initiateTextFields(mainFrame,profPasswordField,200,250,300,40);
        StylingFunctions.initiateTextFields(mainFrame,profSpecialityField,200,300,300,40);
        StylingFunctions.initiateLabels(mainFrame,profNameLabel,20,200,150,40, Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,profPasswordLabel,20,250,150,40, Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,profSpecialityLabel,20,300,150,40, Font.PLAIN,16);
    }

    private void updateButtonListener(JTextField nameField,JTextField passwordField,JTextField specialityField){
        if(nameField.getText().equals("")||passwordField.getText().equals("")||specialityField.getText().equals("")){
            JOptionPane.showMessageDialog(mainFrame,"All the fields are Required","Update Failed",JOptionPane.ERROR_MESSAGE);
        }else{
            if(admin.updateProfInfo(profId,nameField.getText(),passwordField.getText(),specialityField.getText())){
                dashboard.onProfAdded();
                JOptionPane.showMessageDialog(mainFrame,"Professor Info updated successfully");
                mainFrame.dispose();
            }else{
                JOptionPane.showMessageDialog(mainFrame,"Something went wrong! Please try again later","Update Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
