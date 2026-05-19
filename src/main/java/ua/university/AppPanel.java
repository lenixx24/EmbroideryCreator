package ua.university;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AppPanel extends JPanel implements MouseListener {
    private Embroidery embroidery;
    private BufferedImage background;
    public AppPanel() {
        setPanel();
        this.addMouseListener(this);
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setUpCanvas();
    }

    private void setUpCanvas() {
        embroidery= new Embroidery(40, 20);
    }

    private void setPanel() {
        Dimension size = new Dimension(EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT);
        setPreferredSize(size);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT, null);
        embroidery.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        embroidery.onClick(e);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
       /* embroidery.onClick(e);
        repaint();*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
