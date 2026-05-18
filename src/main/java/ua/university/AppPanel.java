package ua.university;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AppPanel extends JPanel {
    private Embroidery embroidery;
    private Image background;
    public AppPanel() {
        setPanel();
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
}
