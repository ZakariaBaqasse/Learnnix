package com.learnnix.ClientSide.ChatIntefaces;

import java.awt.event.MouseEvent;

public interface ChatGUI {
    public void addNewMessage(String newMessage);
    public void addNewFile(String fileName);
    public void mouseClicked(MouseEvent e);
    public void mouseDragged(MouseEvent e);
    public void clear();
}
