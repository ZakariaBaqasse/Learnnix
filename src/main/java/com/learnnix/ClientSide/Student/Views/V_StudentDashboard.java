package com.learnnix.ClientSide.Student.Views;

import com.learnnix.ClientSide.Student.Student;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class V_StudentDashboard {
    private final JFrame mainFrame = new JFrame();
    private final Student student;
    private final JTabbedPane tabs = new JTabbedPane();
    private JPanel allClassesTab;
    private JPanel joinedCardsPanel = new JPanel(new GridBagLayout());
    private JPanel allCardsPanel = new JPanel(new GridBagLayout());
    private JPanel joinedClassesTab;
    private ArrayList<ClassInfos> joinedClasses;
    private ArrayList<ClassInfos> allClasses;

    public V_StudentDashboard(Student student) {
        this.student = student;
        this.joinedClasses = new ArrayList<>(student.getJoinedClasses(this.student.getEmail()));
        this.allClasses = new ArrayList<>(student.getNonJoinedClasses(this.student.getEmail()));
        initiateFrame();
    }
    private void initiateFrame() {
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        styleAllClassesTab();
        styleJoinedClassesTab();
        styleTabsContainer();
        mainFrame.add(tabs);
    }

    //fonction pour ajouter du style a `tabs`
    private void styleTabsContainer(){
        tabs.setBounds(0,0,800,700);
        tabs.setPreferredSize(mainFrame.getSize());
        JLabel myClasses = new JLabel("My Classes",JLabel.CENTER);
        JLabel availableClasses = new JLabel("Available Classes",JLabel.CENTER);
        tabs.add("My Classes",joinedClassesTab);
        tabs.add("Available Classes",allClassesTab);
        StylingFunctions.styleTabsTitle(tabs,myClasses,0);
        StylingFunctions.styleTabsTitle(tabs,availableClasses,1);
    }

    private void styleJoinedClassesTab(){
        //recuperer la liste des classes que l'etudiant les a integre
        joinedClassesTab = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new FlowLayout());
        joinedClassesTab.add(labelPanel,BorderLayout.NORTH);
        //si l'etudiant n'a integre aucune classe
        if(joinedClasses.isEmpty()){
            JLabel label = new JLabel("You aren't currently enrolled in any class");
            StylingFunctions.initiateLabels(labelPanel,label,0,0,300, 40,Font.BOLD,32);
        }else{
            //si l'etudiant a rejoint quelques classes
            JLabel label = new JLabel("Your Classes");
            StylingFunctions.initiateLabels(labelPanel,label,0,0,300, 40,Font.BOLD,32);
           GridBagConstraints constraints = new GridBagConstraints();
           joinedCardsPanel = setupCardsPanel(constraints);
           joinedClassesTab.add(V_StudentDashboard.this.joinedCardsPanel,BorderLayout.CENTER);
           joinedClasses.forEach(classInfos -> {
               JPanel classCard = createJoinedClassCard(classInfos);
               V_StudentDashboard.this.joinedCardsPanel.add(classCard);
           });
        }
    }

    private void styleAllClassesTab(){
        allClassesTab = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.setBounds(0,0,800,50);
        allClassesTab.add(labelPanel,BorderLayout.NORTH);
        //si l'etudiant n'a integre aucune classe
        if(allClasses.isEmpty()){
            JLabel label = new JLabel("There is currently no New Classes");
            StylingFunctions.initiateLabels(labelPanel,label,0,0,300, 40,Font.BOLD,32);
        }else{
            JLabel label = new JLabel("Here are our Top Classes");
            StylingFunctions.initiateLabels(labelPanel,label,0,0,300, 40,Font.BOLD,32);
            GridBagConstraints constraints = new GridBagConstraints();
            allCardsPanel = setupCardsPanel(constraints);
            allClassesTab.add(V_StudentDashboard.this.allCardsPanel,BorderLayout.CENTER);
            allClasses.forEach(classInfos -> {
                JPanel classCard = createAllClassCard(classInfos);
                V_StudentDashboard.this.allCardsPanel.add(classCard);
            });
        }
    }

    private JPanel createJoinedClassCard(ClassInfos classInfos) {
        // create panel to hold class card
        JPanel classCard = new JPanel();
        classCard.setLayout(new BorderLayout());
        classCard.setPreferredSize(new Dimension(150, 150));
        classCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        classCard.setBackground(Color.decode("#CCE6F4"));
        // add class name label to card
        JLabel classNameLabel = new JLabel(classInfos.getClassName());
        classNameLabel.setHorizontalAlignment(JLabel.CENTER);
        StylingFunctions.initiateLabels(classCard,classNameLabel,0,0,150,50,Font.BOLD,22);
        classCard.add(classNameLabel, BorderLayout.CENTER);
        // add listener to open class lessons frame on click
        classCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new V_ClassLessons(classInfos, student);
            }
        });
        return classCard;
    }

    private JPanel createAllClassCard(ClassInfos classInfos) {
        // create panel to hold class card
        JPanel classCard = new JPanel();
        classCard.setLayout(new BorderLayout());
        classCard.setPreferredSize(new Dimension(150, 150));
        classCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        classCard.setBackground(Color.decode("#CCE6F4"));
        // add class name label to card
        JLabel classNameLabel = new JLabel(classInfos.getClassName());
        classNameLabel.setHorizontalAlignment(JLabel.CENTER);
        StylingFunctions.initiateLabels(classCard,classNameLabel,0,0,150,50,Font.BOLD,22);
        classCard.add(classNameLabel, BorderLayout.CENTER);
        // add listener to open class details frame on click
        V_StudentDashboard dashboard = this;
        classCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new V_ClassDetails(classInfos,dashboard,student);
            }
        });
        return classCard;
    }

    private JPanel setupCardsPanel(GridBagConstraints constraints){
        JPanel panel = new JPanel(new GridBagLayout());
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return panel;
    }
    public void onClassJoined(ClassInfos joinedClass){
        this.allClasses.remove(joinedClass);
        this.joinedClasses.add(joinedClass);
        joinedCardsPanel.removeAll();
        joinedClasses.forEach(classInfos -> {
            JPanel classCard = createJoinedClassCard(classInfos);
            V_StudentDashboard.this.joinedCardsPanel.add(classCard);
        });
        joinedCardsPanel.revalidate();
        joinedClassesTab.repaint();
        allCardsPanel.removeAll();
        allClasses.forEach(classInfos -> {
            JPanel classCard = createAllClassCard(classInfos);
            V_StudentDashboard.this.allCardsPanel.add(classCard);
        });
        allCardsPanel.revalidate();
        allCardsPanel.repaint();
    }

}
