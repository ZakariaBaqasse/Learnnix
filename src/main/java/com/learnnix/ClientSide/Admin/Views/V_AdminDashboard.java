package com.learnnix.ClientSide.Admin.Views;

import com.learnnix.ClientSide.Admin.Admin;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;
import com.learnnix.HelperClasses.StylingFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Stream;

//Class qui affiche le tableau de bord d'admin
public class V_AdminDashboard {
    private Admin admin;
    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabs = new JTabbedPane();
    private JPanel profsTab = new JPanel();
    private JPanel classesTab = new JPanel();
    private JPanel studentsTab = new JPanel();
    private DefaultTableModel profModel=new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel classModel=new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel studentModel=new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public V_AdminDashboard(Admin admin){
        this.admin = admin;
        initiateFrame();
    }
    private void initiateFrame() {
            mainFrame.setVisible(true);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800,700);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setLayout(null);
            styleTabsContainer();
            mainFrame.add(tabs);
    }

    //fonction pour ajouter du style a `tabs`
    private void styleTabsContainer(){
        tabs.setBounds(0,0,800,700);
        styleProfsTab();
        styleClassesTab();
        styleStudentsTab();
        tabs.setPreferredSize(mainFrame.getSize());
        JLabel profTabTitle = new JLabel("Professors",JLabel.CENTER);
        JLabel classTabTitle = new JLabel("Classes",JLabel.CENTER);
        JLabel studentsTabTitle = new JLabel("Students",JLabel.CENTER);
        tabs.add("Professors",profsTab);
        tabs.add("Classes",classesTab);
        tabs.add("Students",studentsTab);
        StylingFunctions.styleTabsTitle(tabs,profTabTitle,0);
        StylingFunctions.styleTabsTitle(tabs,classTabTitle,1);
        StylingFunctions.styleTabsTitle(tabs,studentsTabTitle,2);
    }

    //fonction pour ajouter du style a l'onglet dedie a la vue des professeurs
    private void styleProfsTab(){
       //retrouver la liste des profs
        List<ProfessorInfos> professors = admin.getAllProfs();
        //initialiser et styler un bouton d'ajout des professeurs
        JButton addProf = new JButton("Add a Professor");
        addProf.addActionListener((ae)->{
            new V_AddProfessor(admin,this);
        });
        StylingFunctions.buttonStyling(profsTab,addProf,0,100,200,50);
        if(professors.size() == 0 ||professors==null){
            JLabel label = new JLabel("There is currently no professors Add new One");
            StylingFunctions.initiateLabels(this.profsTab,label,200,350,300, 40,Font.BOLD,32);
        }else{
            JLabel label = new JLabel("The list Below is of the available Professors");
            StylingFunctions.initiateLabels(profsTab,label,50,0,300,40,Font.BOLD,32);
            //noms des columns
            String[] columnsNames = new String[]{"ID","Professor Name","Professor Speciality"};
            //creer le modele
            createTableModel(profModel,columnsNames);
            //ajouter les professeurs au modele
            professors.forEach(professor->profModel.addRow(new Object[]{professor.getProfId(),professor.getProf_username(),professor.getProf_speciality()}));
            profModel.fireTableDataChanged();
            //creer une JTable a partir du modele
            JTable profsTable = new JTable(profModel);
            //ecouter a un double click sur une des lignes de la table
            V_AdminDashboard dashboard = this;
            profsTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    //recuperer la table source du click
                    JTable table = (JTable) me.getSource();
                    Point point = me.getPoint();
                    //recuperer le numero de la ligne
                    int row = table.rowAtPoint(point);
                    //verifier si la ligne a recu un double click et que la ligne appartient a la table
                    if(me.getClickCount()==2 && row!=-1){
                        //recuperer l'id du professeur a partir de la ligne
                        int profId = (int) table.getValueAt(row,0);
                        new V_UpdateProf(profId,admin,dashboard);
                    }
                }
            });
            //ajouter la table a la panel
            StylingFunctions.addStyleToTable(profsTable,profsTab,mainFrame);
        }
    }
    //fonction aide qui initialise le modele qui va contenir les donnees des tables
    private void createTableModel(DefaultTableModel model,String[] columns){
        //ajouter les colones au modele
        Stream.of(columns).forEach(model::addColumn);
    }

    //Cette fonction s'execute lorsque l'admin ajoute un nouveau professeur, elle affiche le nouveau professeur
    public void onProfAdded(){
        List<ProfessorInfos> professors = admin.getAllProfs();
        profModel.setRowCount(0);
        professors.forEach((professor)->profModel.addRow(new Object[]{professor.getProfId(),professor.getProf_username(),professor.getProf_speciality()}));
    }

    private void styleClassesTab(){
        //retrouver la liste des profs
        List<ClassInfos> classes = admin.getAllClasses();
        //initialiser et styler un bouton d'ajout des professeurs
        JButton addClass = new JButton("Add a Class");
        addClass.addActionListener((ae)->{
           new V_AddClass(admin,this);
        });
        StylingFunctions.buttonStyling(classesTab,addClass,0,100,200,50);
        if(classes.size() == 0 ||classes==null){
            JLabel label = new JLabel("There is currently no Class Add new One");
            StylingFunctions.initiateLabels(this.classesTab,label,200,350,300, 40,Font.BOLD,32);
        }else{
            JLabel label = new JLabel("The list Below is of the available Classes");
            StylingFunctions.initiateLabels(classesTab,label,50,0,300,40,Font.BOLD,32);
            //noms des columns
            String[] columnsNames = new String[]{"ID","Class Name","Class Subject","Class Description"};
            //creer le modele
            createTableModel(classModel,columnsNames);
            //ajouter les professeurs au modele
            classes.forEach(classData->classModel.addRow(new Object[]{classData.getClassId(),classData.getClassName(),classData.getClassSubject(),classData.getClassDescription()}));
            classModel.fireTableDataChanged();
            //creer une JTable a partir du modele
            JTable classesTable = new JTable(classModel);
            //ecouter a un double click sur une des lignes de la table
            V_AdminDashboard dashboard = this;
            classesTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    //recuperer la table source du click
                    JTable table = (JTable) me.getSource();
                    Point point = me.getPoint();
                    //recuperer le numero de la ligne
                    int row = table.rowAtPoint(point);
                    //verifier si la ligne a recu un double click et que la ligne appartient a la table
                    if(me.getClickCount()==2 && row!=-1){
                        //recuperer l'id du professeur a partir de la ligne
                        int classId = (int) table.getValueAt(row,0);
                        new V_UpdateClass(classId,admin,dashboard);
                    }
                }
            });

            //ajouter la table a la panel
            StylingFunctions.addStyleToTable(classesTable,classesTab,mainFrame);
        }
    }

    public void onClassAdded(){
        List<ClassInfos> classes = admin.getAllClasses();
        classModel.setRowCount(0);
        classes.forEach((classData)->classModel.addRow(new Object[]{classData.getClassId(),classData.getClassName(),classData.getClassSubject(),classData.getClassDescription()}));
    }

    private void styleStudentsTab(){
        //retrouver la liste des profs
        List<StudentInfos> students = admin.getAllStudents();
        //initialiser et styler un bouton d'ajout des professeurs
        if(students.size() == 0 ||students==null){
            JLabel label = new JLabel("There is currently no Students");
            StylingFunctions.initiateLabels(this.studentsTab,label,200,350,300, 40,Font.BOLD,32);
        }else{
            JLabel label = new JLabel("The list Below is of the available Students");
            StylingFunctions.initiateLabels(studentsTab,label,50,0,300,40,Font.BOLD,32);
            //noms des columns
            String[] columnsNames = new String[]{"ID","Student Name","Student Email"};
            //creer le modele
            createTableModel(studentModel,columnsNames);
            //ajouter les professeurs au modele
            students.forEach(student->studentModel.addRow(new Object[]{student.getStudentId(),student.getStudentUsername(),student.getStudentEmail()}));
            studentModel.fireTableDataChanged();
            //creer une JTable a partir du modele
            JTable studentsTable = new JTable(studentModel);
            //ajouter la table a la panel
            StylingFunctions.addStyleToTable(studentsTable,studentsTab,mainFrame);
        }
    }
}

