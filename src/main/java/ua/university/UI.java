package ua.university;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    private int buttonsAmount;
    AppPanel appPanel;
    JCheckBox eraser, grid;
    JButton clear;
    public UI(AppPanel appPanel){
        this.appPanel=appPanel;
        setup();
    }
    public void setup(){
        buttonsAmount =3;
        clear= new JButton("Очистити");
        clear.addActionListener(e -> appPanel.getEmbroidery().clear());
        clear.setBounds(50, EmbroideryCreator.HEIGHT*5/6, 100, EmbroideryCreator.HEIGHT/40);
        eraser = new JCheckBox("Гумка");
        eraser.setSelected(appPanel.getEmbroidery().isEraserOn());
        eraser.addActionListener(e -> appPanel.getEmbroidery().switchEraser());
        eraser.setBounds(170, EmbroideryCreator.HEIGHT*5/6, 80, EmbroideryCreator.HEIGHT/40);
        eraser.setHorizontalAlignment(SwingConstants.CENTER);
        grid = new JCheckBox("Сітка");
        grid.setSelected(appPanel.getEmbroidery().isEnableGrid());
        grid.addActionListener(e -> appPanel.getEmbroidery().switchGrid());
        grid.setBounds(290, EmbroideryCreator.HEIGHT*5/6, 80, EmbroideryCreator.HEIGHT/40);
        grid.setHorizontalAlignment(SwingConstants.CENTER);
        appPanel.add(grid);
        appPanel.add(eraser);
        appPanel.add(clear);
    }
}
