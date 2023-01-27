package com.learnnix.ClientSide.Student.Views;

import com.learnnix.ClientSide.Student.Student;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;

public class V_ClassDetails {
    private JFrame mainFrame;
    private ClassInfos classInfos;
    private V_StudentDashboard dashboard;
    private Student student;
    public V_ClassDetails(ClassInfos classInfos, V_StudentDashboard dashboard, Student student){
        this.classInfos = classInfos;
        this.dashboard = dashboard;
        this.student = student;
        initiateFrame();
    }

    private void initiateFrame() {
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(500,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        addStyleToFrame();
    }
    private void addStyleToFrame(){
        JLabel className = new JLabel(classInfos.getClassName());
        StylingFunctions.initiateLabels(mainFrame,className,150,50,300,40,Font.BOLD,32);
        JLabel classSubject = new JLabel(classInfos.getClassSubject());
        JLabel classProf = new JLabel(classInfos.getProfInCharge());
        JTextArea classDescription = new JTextArea(classInfos.getClassDescription());
        StylingFunctions.initiateLabels(mainFrame,classSubject,200,150,100,40,Font.BOLD,18);
        StylingFunctions.initiateLabels(mainFrame,classProf,200,200,150,40,Font.BOLD,18);
        StylingFunctions.wrapLabelText(mainFrame,classDescription,200,250,200,100);
        JLabel classSubLab = new JLabel("Class Subject");
        JLabel classProfLab = new JLabel("Class Professor");
        JLabel classDescLab = new JLabel("Class Description");
        StylingFunctions.initiateLabels(mainFrame,classSubLab,20,150,150,40,Font.PLAIN,18);
        StylingFunctions.initiateLabels(mainFrame,classProfLab,20,200,150,40,Font.PLAIN,18);
        StylingFunctions.initiateLabels(mainFrame,classDescLab,20,250,150,40,Font.PLAIN,18);
        JButton joinButton = new JButton("Join");
        StylingFunctions.buttonStyling(mainFrame,joinButton,200,400,100,50);
        joinButton.addActionListener((ae)->joinClassBtnListener());
    }

    private void joinClassBtnListener(){
        if(student.joinClass(student.getEmail(),this.classInfos.getClassId())){
            dashboard.onClassJoined(this.classInfos);
            JOptionPane.showMessageDialog(mainFrame,"Congratulations on Joining this Class");
            mainFrame.dispose();
        }else {
            JOptionPane.showMessageDialog(mainFrame,"Something went wrong! Please try again later","",JOptionPane.ERROR_MESSAGE);
        }

    }
}
