package com.learnnix.ClientSide.Admin.Views;

import com.learnnix.ClientSide.Admin.Admin;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class V_AddClass {
    private JFrame mainFrame;
    private Admin admin;
    private V_AdminDashboard adminDashboard;
    public V_AddClass(Admin admin, V_AdminDashboard v_adminDashboard){
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
        JLabel infoLabel = new JLabel("New Class");
        StylingFunctions.initiateLabels(mainFrame,infoLabel,150,50,300,40, Font.BOLD,32);
        JTextField classSubject = new JTextField();
        JComboBox<String> profsList = initiateComboBox();
        JTextArea classDescription = new JTextArea();
        JLabel classSubLabel = new JLabel("Class Subject");
        JLabel classResponsible = new JLabel("Professor in Charge");
        JLabel classDescLabel = new JLabel("Class Description");
        StylingFunctions.initiateTextFields(mainFrame,classSubject,200,200,300,40);
        StylingFunctions.comboBoxStyling(mainFrame,profsList,200,250,300,40);
        StylingFunctions.styleTextArea(mainFrame,classDescription,200,300,300,100);
        StylingFunctions.initiateLabels(mainFrame,classSubLabel,20,200,150,40,Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,classResponsible,20,250,150,40,Font.PLAIN,16);
        StylingFunctions.initiateLabels(mainFrame,classDescLabel,20,300,150,40,Font.PLAIN,16);
        JButton addButton = new JButton("Save");
        StylingFunctions.buttonStyling(mainFrame,addButton,200,450,100,50);
        AtomicReference<String> profName = new AtomicReference<String>();
        profsList.addActionListener((ae)->{
            profName.set((String) profsList.getSelectedItem());
        });
        addButton.addActionListener((ae)->addClassButtonListener(classSubject,classDescription,profName.toString()));
    }

    private void addClassButtonListener(JTextField classSubject,JTextArea classDescription,String profName){
        if(classSubject.getText().equals("")||classDescription.getText().equals("")||profName ==null){
            JOptionPane.showMessageDialog(mainFrame,"All the fields are Required","Add Failed",JOptionPane.ERROR_MESSAGE);
        }else{
            ClassInfos classData = new ClassInfos(classSubject.getText(), classDescription.getText());
            if(admin.addClass(classData,profName)){
                adminDashboard.onClassAdded();
                JOptionPane.showMessageDialog(mainFrame,"class Added successfully");
                mainFrame.dispose();
            }else{
                JOptionPane.showMessageDialog(mainFrame,"Something went wrong! Please try again later","Login Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JComboBox<String> initiateComboBox(){
        String[] options = admin.getAllProfs().stream().map(ProfessorInfos::getProf_username).toArray(String[]::new);
        return new JComboBox<String>(options);
    }
}
