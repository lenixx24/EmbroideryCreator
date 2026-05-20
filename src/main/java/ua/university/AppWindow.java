package ua.university;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class AppWindow {
    private JFrame jframe;

    public AppWindow(AppPanel appPanel)  {
        jframe = new JFrame();
        ImageIcon im = new ImageIcon("src/main/resources/icon.png");
        jframe.setIconImage(im.getImage());
        jframe.setTitle("Ukrainian embroidery creator made by Shepetiuk Angelina");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(appPanel);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

}
