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
    private UI ui;
    private final BufferedImage background;
    public AppPanel() {
        setPanel();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setUpCanvas();
        setUpUI();
    }

    private void setUpUI() {
        this.setLayout(null);
        ui = new UI(this);
    }

    private void setUpCanvas() {
        embroidery= new Embroidery(this, 30, 30);
    }

    private void setPanel() {
        Dimension size = new Dimension(EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT);
        setPreferredSize(size);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT, null);
        g.fillRect(0,5+(EmbroideryCreator.HEIGHT+embroidery.MAX_HEIGHT)/2, EmbroideryCreator.WIDTH, ui.BUTTON_HEIGHT *2+10);
        g.setColor(Color.WHITE);
        g.fillRect(0,10+(EmbroideryCreator.HEIGHT+embroidery.MAX_HEIGHT)/2, EmbroideryCreator.WIDTH, ui.BUTTON_HEIGHT *2);
        embroidery.draw(g);
    }
    public Embroidery getEmbroidery(){
        return embroidery;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        embroidery.onClick(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {
       embroidery.onClick(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        embroidery.onClick(e);
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public UI getUi() {
        return ui;
    }
}
