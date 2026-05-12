package ua.university;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class AppPanel extends JPanel {
    public AppPanel() {
        setPanelSize();
    }
    private void setPanelSize() {
        Dimension size = new Dimension(EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT);
        setPreferredSize(size);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    g.drawString("Hello", 100, 100);
    }
}
