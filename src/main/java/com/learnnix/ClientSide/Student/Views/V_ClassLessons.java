package com.learnnix.ClientSide.Student.Views;

import com.learnnix.ClientSide.ChatImplementations.ChatHandlerIMP;
import com.learnnix.ClientSide.ChatIntefaces.ChatGUI;
import com.learnnix.ClientSide.Student.Student;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.DrawArea;
import com.learnnix.HelperClasses.StylingFunctions;
import com.learnnix.ServerSide.Interfaces.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class V_ClassLessons implements ChatGUI {
    private static String FILE_DOWNLOAD_PATH = "C:\\Users\\user\\Desktop\\Learnnix\\";
    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabs = new JTabbedPane();
    private JPanel groupChatTab = new JPanel(null);
    private JPanel whiteBoardTab = new JPanel();
    private JPanel filesTab = new JPanel(null);
    private ClassInfos classInfos;
    private Student student;
    private ChatServer chatServer;
    private ChatHandlerIMP chatHandler;
    private JList<String> groupMessages;
    private DefaultListModel<String> groupMessagesModel = new DefaultListModel<>();
    private JList<String> filesList;
    private DefaultListModel<String> filesListModel = new DefaultListModel<>();
    private DrawArea drawArea;
    public V_ClassLessons(ClassInfos classInfos, Student student, ChatServer chatServer) throws MalformedURLException, RemoteException {
        this.classInfos = classInfos;
        this.chatServer = chatServer;
        this.student = student;
        groupMessages = new JList<>(groupMessagesModel);
        filesList = new JList<>(filesListModel);
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
        styleWhiteBoardTab();
        styleFilesTab();
        styleGroupChatTab();
        styleTabsContainer();
        mainFrame.add(tabs);
    }
    private void styleTabsContainer(){
        tabs.setBounds(0,0,800,700);
        tabs.setPreferredSize(mainFrame.getSize());
        JLabel groupChat = new JLabel("Group Chat",JLabel.CENTER);
        JLabel whiteBoard = new JLabel("White Board",JLabel.CENTER);
        JLabel files = new JLabel("Files",JLabel.CENTER);
        tabs.add("Group Chat",groupChatTab);
        tabs.add("White Board",whiteBoardTab);
        tabs.add("Files",filesTab);
        StylingFunctions.styleTabsTitle(tabs,groupChat,0);
        StylingFunctions.styleTabsTitle(tabs,whiteBoard,1);
        StylingFunctions.styleTabsTitle(tabs,files,2);
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

    private void styleFilesTab(){
        StylingFunctions.setupMessageList(filesList);
        filesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                fileListListener(evt);
            }
        });
        JScrollPane filesScrollPane = new JScrollPane(filesList);
        filesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        filesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        filesScrollPane.setBounds(0,0,800,700);
        ArrayList<String> uploadedFiles = student.getUploadedFiles(classInfos.getClassId());
        if(!uploadedFiles.isEmpty()){
            uploadedFiles.forEach(filesListModel::addElement);
        }
        filesTab.add(filesScrollPane);
    }

    private void fileListListener(MouseEvent event){
        filesList = (JList<String>)event.getSource();
        //verifier si double click
        if (event.getClickCount() == 2) {
            //recuperer l'index de l'element selectionne
            int index = filesList.locationToIndex(event.getPoint());
            //recuperer le nom de l'element selectionne
            String selectedValue = filesList.getModel().getElementAt(index).toString();
            try {
                //creer ,si n'existe pas, le dossier de telechargement
                File directory = new File(FILE_DOWNLOAD_PATH);
                if (! directory.exists()){
                    directory.mkdir();
                }
                //verifier si le fichier existe deja
                File file = new File(FILE_DOWNLOAD_PATH + selectedValue);
                //si oui, l'ouvrir sans le telecharger une nouvelle fois
                if(file.exists()&&file.isFile()){
                    Desktop.getDesktop().open(file);
                }else{
                    //sinon, le telecharger et l'ouvrir
                    byte[] fileData = chatHandler.downloadFile(selectedValue);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();
                    Desktop.getDesktop().open(file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void styleWhiteBoardTab(){
        drawArea = new DrawArea();
        whiteBoardTab.setLayout(new BorderLayout());
        whiteBoardTab.setBackground(Color.WHITE);
        whiteBoardTab.setBounds(0,0,800,600);
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(0,0,100,50);
        clearButton.addActionListener(e -> {
            drawArea.clear();
            chatHandler.broadCastClear(student.getEmail());
        });
        whiteBoardTab.add(clearButton,BorderLayout.SOUTH);
        whiteBoardTab.add(drawArea,BorderLayout.CENTER);
        drawArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                chatHandler.broadCastMouseClick(e,student.getEmail());
                drawArea.onMousePressed(e);
            }
        });
        drawArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                chatHandler.broadCastMouseDragged(e,student.getEmail());
               drawArea.onMouseDragged(e);
            }
        });
    }

    public void addNewMessage(String newMessage) {
        groupMessagesModel.add(groupMessagesModel.size(),newMessage);
    }
    public void addNewFile(String newFile) {
        filesListModel.add(filesListModel.size(),newFile);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        drawArea.onMousePressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        drawArea.onMouseDragged(e);
    }

    @Override
    public void clear() {
        drawArea.clear();
    }
}
