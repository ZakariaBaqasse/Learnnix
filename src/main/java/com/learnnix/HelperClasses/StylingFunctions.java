package com.learnnix.HelperClasses;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StylingFunctions {
    public static void initiateLabels(Container container,JLabel label, int x, int y, int width, int height, int fontStyle, int fontSize){
        label.setBounds(x,y,width,height);
        label.setFont(new Font("Roboto",fontStyle,fontSize));
        container.add(label);
    }
    public static void initiateTextFields(Container container,JComponent component,int x,int y,int width,int height){
        component.setBounds(x,y,width,height);
        component.setFont(new Font("Roboto",Font.PLAIN,16));
        container.add(component);
    }
    public static void buttonStyling(Container container,JButton button,int x,int y,int width,int height){
        button.setBounds(x,y,width,height);
        button.setBackground(Color.decode("#CCE6F4"));
        button.setForeground(Color.decode("#175676"));
        button.setFont(new Font("Roboto",Font.PLAIN,16));
        container.add(button);
    }

    public static void styleTabsTitle(JTabbedPane tabs,JLabel label,int tabIndex){
        label.setPreferredSize(new Dimension(100,30));
        label.setForeground(Color.decode("#175676"));
        tabs.setTabComponentAt(tabIndex,label);
    }

    public static void addStyleToTable(JTable table,JPanel panel,JFrame frame){
        table.setRowHeight(30);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                c.setForeground(Color.decode("#175676"));
                c.setFont(new Font("Roboto", Font.PLAIN, 14));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.LIGHT_GRAY);
                c.setForeground(Color.decode("#175676"));
                c.setFont(new Font("Roboto",Font.BOLD,16));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //code pour permettre au tableau d'etre responsive au changement de la taille de la frame
        table.setPreferredScrollableViewportSize(new Dimension(frame.getWidth(),500));
        panel.add(scrollPane);
    }

    public static void comboBoxStyling(Container container,JComboBox comboBox,int x,int y,int width,int height){
        comboBox.setFont(new Font("Roboto",Font.PLAIN,14));
        comboBox.setBounds(x,y,width,height);
        container.add(comboBox);
    }

    public static  void styleTextArea(Container container,JTextArea textArea,int x,int y,int width,int height){
        textArea.setFont(new Font("Roboto",Font.PLAIN,16));
        textArea.setForeground(Color.decode("#175676"));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(x,y,width,height);
        container.add(scrollPane);
    }
    public static void wrapLabelText(Container container,JTextArea textArea,int x,int y,int width,int height){
        textArea.setFont(new Font("Roboto",Font.PLAIN,16));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(10); // number of rows
        textArea.setColumns(20); // number of columns
        textArea.setSize(new Dimension(250, textArea.getPreferredSize().height));
        textArea.setMaximumSize(new Dimension(250, textArea.getPreferredSize().height));
        textArea.setBounds(x,y,width,height);
        container.add(textArea);
    }
}
