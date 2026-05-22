package ua.university;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EmbroideryCreator {
    private static BufferedImage background;
    AppWindow appWindow;
    AppPanel appPanel;
    public static final int WIDTH=900;
    public static final int HEIGHT=800;
   public EmbroideryCreator (){
       try {
           background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/background.png")));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
            appWindow = new AppWindow();
            appPanel = new AppPanel(appWindow.getJframe());

    }
    public static void drawBackground(Graphics g){
        g.drawImage(background, 0, 0, EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT, null);

    }
}
