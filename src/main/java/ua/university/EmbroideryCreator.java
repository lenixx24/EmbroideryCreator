package ua.university;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
            playMusic("src/main/resources/music.wav");

    }
    public static void drawBackground(Graphics g){
        g.drawImage(background, 0, 0, EmbroideryCreator.WIDTH, EmbroideryCreator.HEIGHT, null);

    }
    public static void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
