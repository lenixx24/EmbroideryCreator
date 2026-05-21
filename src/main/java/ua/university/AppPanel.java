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
    private final BufferedImage background;
    private int gameState=0;
    public AppPanel() {
        setPanel();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setUpUI();

    }

    private void setUpUI() {
        this.setLayout(null);
        mainUI = new MainUI(this);
    }

    public void setAppUI(AppUI appUI) {
        this.appUI = appUI;
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
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT, null);
        if(gameState==0) {
            mainUI.addButtons();
        }
        else if(gameState==1)
            drawApp(g);
        }
    private void drawApp(Graphics g){
        g.fillRect(0,5+(EmbroideryCreator.HEIGHT+embroidery.MAX_HEIGHT)/2, EmbroideryCreator.WIDTH, appUI.BUTTON_HEIGHT *2+10);
        g.setColor(Color.WHITE);
        g.fillRect(0,10+(EmbroideryCreator.HEIGHT+embroidery.MAX_HEIGHT)/2, EmbroideryCreator.WIDTH, appUI.BUTTON_HEIGHT *2);
        embroidery.draw(g);
    }
    private void drawMenu(Graphics g){

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
