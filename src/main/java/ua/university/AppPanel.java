package ua.university;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AppPanel extends JPanel implements MouseListener, MouseMotionListener {
    private Embroidery embroidery;
    private AppUI appUI;
    private MainUI mainUI;
    private JFrame appFrame;
    private int gameState=0;
    public AppPanel(JFrame appFrame) {
        this.appFrame=appFrame;
        setPanel();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        setUI();
    }

    private void setUI() {
        mainUI = new MainUI(this, appFrame);

        mainUI.setup();
        appUI = new AppUI(this, appFrame );
    }

    public void setAppUI() {
        appUI.setup();
        appUI.setComponentsPosition();
        appUI.addButtons();
        this.repaint();
        appFrame.repaint();
    }

    public void setUpCanvas(int w, int h) {
        embroidery= new Embroidery(this, w, h);
    }
    public void setUpCanvas(String filePath) {
        embroidery= new Embroidery(this, filePath);
    }

    private void setPanel() {
        Dimension size = new Dimension(EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT);
        setPreferredSize(size);
        appFrame.add(this);
        appFrame.setResizable(false);
        appFrame.pack();
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameState==0) {
            if(mainUI!=null) {
                EmbroideryCreator.drawBackground(g);
                mainUI.addButtons();
            }

        }
        else if(gameState==1)
            drawApp(g);
        }
    private void drawApp(Graphics g){
    EmbroideryCreator.drawBackground(g);
    g.setColor(Color.BLACK);
    g.fillRect(0,EmbroideryCreator.HEIGHT-115-80, EmbroideryCreator.WIDTH, appUI.BUTTON_HEIGHT *2+10);
       embroidery.draw(g);
    }
    public Embroidery getEmbroidery(){
        return embroidery;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(gameState==1) embroidery.onClick(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(gameState==1) embroidery.onClick(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        if(gameState==1) embroidery.onClick(e);
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public AppUI getAppUI() {
        return appUI;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

}
