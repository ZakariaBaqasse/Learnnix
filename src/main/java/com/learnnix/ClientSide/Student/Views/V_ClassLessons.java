package com.learnnix.ClientSide.Student.Views;

import com.learnnix.ClientSide.ChatImplementations.ChatHandlerIMP;
import com.learnnix.ClientSide.Student.Student;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StylingFunctions;
import com.learnnix.ServerSide.Interfaces.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class V_ClassLessons {
    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabs = new JTabbedPane();
    private JPanel groupChatTab = new JPanel(null);
    private JPanel whiteBoardTab = new JPanel();
    private JPanel filesTab = new JPanel();
    private ClassInfos classInfos;
    private Student student;
    private ChatServer chatServer;
    private ChatHandlerIMP chatHandler;
    private JList<String> groupMessages;
    private DefaultListModel<String> groupMessagesModel = new DefaultListModel<>();
    public V_ClassLessons(ClassInfos classInfos, Student student, ChatServer chatServer) throws MalformedURLException, RemoteException {
        this.classInfos = classInfos;
        this.chatServer = chatServer;
        this.student = student;
        groupMessages = new JList<>(groupMessagesModel);
        this.chatHandler = new ChatHandlerIMP(student, classInfos.getClassId(), chatServer);
        initiateFrame();
        chatHandler.bindWithGui(this);
    }
    private void initiateFrame() {
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(800,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        styleGroupChatTab();
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
        tabs.add("Files",filesTab);
        tabs.add("White Board",whiteBoardTab);
        StylingFunctions.styleTabsTitle(tabs,groupChat,0);
        StylingFunctions.styleTabsTitle(tabs,privateChats,1);
        StylingFunctions.styleTabsTitle(tabs,whiteBoard,2);
        StylingFunctions.styleTabsTitle(tabs,files,3);
    }
    private void styleGroupChatTab(){
        StylingFunctions.setupMessageList(groupMessages);
        JScrollPane messagesScrollPane = new JScrollPane(groupMessages);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel inputPanel = new JPanel(null);
        messagesScrollPane.setBounds(0,0,800,500);
        inputPanel.setBounds(0,500,800,200);
        groupChatTab.add(messagesScrollPane);
        groupChatTab.add(inputPanel);
        JTextField inputField = new JTextField();
        StylingFunctions.initiateTextFields(inputPanel,inputField,10,0,300,40);
        JButton sendButton = new JButton("Send");
        StylingFunctions.buttonStyling(inputPanel,sendButton,320,0,100,40);
        sendButton.addActionListener(e -> sendButtonListener(inputField));
    }
    private void sendButtonListener(JTextField inputField){
        if(!inputField.getText().equals("")){
            try {
                String message = new String("You : " + inputField.getText());
                groupMessagesModel.add(groupMessagesModel.size(),message);
                chatServer.broadcastMessage(inputField.getText(),student.getEmail());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            inputField.setText("");
        }
    }

    public void addNewMessage(String newMessage) {
        groupMessagesModel.add(groupMessagesModel.size(),newMessage);
    }

}
