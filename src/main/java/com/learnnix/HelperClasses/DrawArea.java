package com.learnnix.HelperClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class DrawArea extends JComponent {
    private Image image;
    private Graphics2D g2;
    Point start, end;

    public DrawArea() {
        setDoubleBuffered(false);
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public void onMousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    public void onMouseDragged(MouseEvent e) {
        end = e.getPoint();
        if (g2 != null) {
            g2.drawLine(start.x, start.y, end.x, end.y);
            repaint();
            start = end;
        }
    }

}



