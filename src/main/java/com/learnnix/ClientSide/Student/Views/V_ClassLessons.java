package com.learnnix.ClientSide.Student.Views;

import com.learnnix.ClientSide.Student.Student;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;

public class V_ClassLessons {
    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabs = new JTabbedPane();
    private JPanel groupChatTab = new JPanel();
    private JPanel privateChatsTab = new JPanel();
    private JPanel whiteBoardTab = new JPanel();
    private JPanel filesTab = new JPanel();
    private ClassInfos classInfos;
    private Student student;
    public V_ClassLessons(ClassInfos classInfos, Student student) {
        this.classInfos = classInfos;
        this.student = student;
        initiateFrame();
    }
    private void initiateFrame() {
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(800,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        styleTabsContainer();
        mainFrame.add(tabs);
    }
    private void styleTabsContainer(){
        tabs.setBounds(0,0,800,700);
        tabs.setPreferredSize(mainFrame.getSize());
        JLabel groupChat = new JLabel("Group Chat",JLabel.CENTER);
        JLabel privateChats = new JLabel("Private Chats",JLabel.CENTER);
        JLabel whiteBoard = new JLabel("White Board",JLabel.CENTER);
        JLabel files = new JLabel("Files",JLabel.CENTER);
        tabs.add("Group Chat",groupChatTab);
        tabs.add("Private Chats",privateChatsTab);
        tabs.add("White Board",whiteBoardTab);
        tabs.add("Files",filesTab);
        StylingFunctions.styleTabsTitle(tabs,groupChat,0);
        StylingFunctions.styleTabsTitle(tabs,privateChats,1);
        StylingFunctions.styleTabsTitle(tabs,whiteBoard,2);
        StylingFunctions.styleTabsTitle(tabs,files,3);
    }
}
