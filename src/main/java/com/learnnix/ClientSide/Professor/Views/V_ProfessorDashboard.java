package com.learnnix.ClientSide.Professor.Views;

import com.learnnix.ClientSide.ChatImplementations.ChatHandlerIMP;
import com.learnnix.ClientSide.Professor.Professor;
import com.learnnix.ClientSide.Student.Views.V_ClassLessons;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StylingFunctions;
import com.learnnix.ServerSide.Interfaces.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class V_ProfessorDashboard {
    private Professor professor;
    private ArrayList<ClassInfos> assignedClasses;
    private JFrame mainFrame;
    public V_ProfessorDashboard(Professor professor) {
        this.professor = professor;
        assignedClasses = professor.getAssignedClasses();
        initiateFrame();
    }
    private void initiateFrame(){
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        frameStyling();
    }

    private void frameStyling(){
        JPanel labelPanel = new JPanel(new FlowLayout());
        mainFrame.add(labelPanel,BorderLayout.NORTH);
        if(assignedClasses.isEmpty()){
            JLabel label = new JLabel("No class has been assigned to you");
            StylingFunctions.initiateLabels(labelPanel,label,0,0,300, 40,Font.BOLD,32);
        }else{
            JLabel label = new JLabel("Your assigned classes");
            StylingFunctions.initiateLabels(labelPanel,label,0,0,300, 40,Font.BOLD,32);
            GridBagConstraints constraints  = new GridBagConstraints();
            JPanel cardsPanel = setupCardsPanel(constraints);
            mainFrame.add(cardsPanel,BorderLayout.CENTER);
            assignedClasses.forEach(classInfos -> {
                cardsPanel.add(createClassCard(classInfos));
            });
        }
    }

    private JPanel setupCardsPanel(GridBagConstraints constraints){
        JPanel panel = new JPanel(new GridBagLayout());
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return panel;
    }

    private JPanel createClassCard(ClassInfos classInfos) {
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
                try {
                    new V_AssignedClass(classInfos,professor,(ChatServer) professor.getServer());
                } catch (MalformedURLException | RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return classCard;
    }
}
