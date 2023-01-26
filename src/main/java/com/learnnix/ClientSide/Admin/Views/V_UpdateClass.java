package com.learnnix.ClientSide.Admin.Views;

import com.learnnix.ClientSide.Admin.Admin;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class V_UpdateClass {
    private int classId;
    private Admin admin;
    private ClassInfos classInfos;
    private JFrame mainFrame;
    private V_AdminDashboard dashboard;

    public V_UpdateClass(int classId, Admin admin, V_AdminDashboard dashboard) {
        this.dashboard = dashboard;
        this.classId = classId;
        this.admin = admin;
        this.classInfos = this.admin.getClass(classId);
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
        JLabel infoLabel = new JLabel("Update Class");
        StylingFunctions.initiateLabels(mainFrame,infoLabel,150,50,300,40, Font.BOLD,32);
        JLabel idLabel = new JLabel("Class ID: "+classId);
        JTextField className = new JTextField();
        className.setText(classInfos.getClassName());
        JTextField classSubject = new JTextField();
        classSubject.setText(classInfos.getClassSubject());
        JComboBox<String> profsList = initiateComboBox();
        profsList.setSelectedItem(classInfos.getProfInCharge());
        JTextArea classDescription = new JTextArea();
        classDescription.setText(classInfos.getClassDescription());
        JLabel classNameLabel = new JLabel("Class Name");
        JLabel classSubLabel = new JLabel("Class Subject");
        JLabel classResponsible = new JLabel("Professor in Charge");
        JLabel classDescLabel = new JLabel("Class Description");
        StylingFunctions.initiateTextFields(mainFrame,className,200,200,300,40);
        StylingFunctions.initiateTextFields(mainFrame,classSubject,200,250,300,40);
        StylingFunctions.comboBoxStyling(mainFrame,profsList,200,300,300,40);
        StylingFunctions.styleTextArea(mainFrame,classDescription,200,350,300,100);
        StylingFunctions.initiateLabels(mainFrame,classNameLabel,20,200,150,40,Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,classSubLabel,20,250,150,40,Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,classResponsible,20,300,150,40,Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,classDescLabel,20,350,150,40,Font.PLAIN,16);
        AtomicReference<String> profName = new AtomicReference<String>();
        profsList.addActionListener((ae)->{
            profName.set((String) profsList.getSelectedItem());
        });
        JButton updateButton = new JButton("Update");
        StylingFunctions.buttonStyling(mainFrame,updateButton,200,500,100,50);
        updateButton.addActionListener((ae)->updateButtonListener(className,classSubject,classDescription,profName.toString()));
        JButton cancelButton = new JButton("Cancel");
        StylingFunctions.buttonStyling(mainFrame,cancelButton,350,450,100,50);
        cancelButton.addActionListener((ae)->{mainFrame.dispose();});
    }

    private void updateButtonListener(JTextField className,JTextField classSubject,JTextArea classDescription,String profName){
        if(className.getText().equals("")||classSubject.getText().equals("")||classDescription.getText().equals("")||profName.equals("")){
            JOptionPane.showMessageDialog(mainFrame,"All the fields are Required","Update Failed",JOptionPane.ERROR_MESSAGE);
        }else{
            ClassInfos updatedClass = new ClassInfos(classId,className.getText(),classSubject.getText(),classDescription.getText(),profName);
            if(admin.updateClassInfo(updatedClass)){
                dashboard.onClassAdded();
                JOptionPane.showMessageDialog(mainFrame,"Class Info updated successfully");
                mainFrame.dispose();
            }else{
                JOptionPane.showMessageDialog(mainFrame,"Class with similar Name already exist! Please try another one","Update Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private JComboBox<String> initiateComboBox(){
        String[] options = admin.getAllProfs().stream().map(ProfessorInfos::getProf_username).toArray(String[]::new);
        return new JComboBox<String>(options);
    }
}
