package ua.university;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class UI {
    protected AppPanel appPanel;
   protected JFrame appFrame;
    protected Font mainFont;
    public final int BUTTON_HEIGHT =EmbroideryCreator.HEIGHT/25;
    public final int BUTTON_Y =25; //EmbroideryCreator.HEIGHT*5/6+12;
    public final int BUTTON_OFFSET =20;
    public UI(AppPanel appPanel, JFrame appFrame){
        this.appPanel=appPanel;
        this.appFrame=appFrame;
        loadFont("src/main/resources/KyivTypeSans-Black.ttf");
        appPanel.setLayout(null);
    }



    protected abstract void setup();
    public abstract void addButtons();
    protected void setupComponentFontAndBounds(Component c, int x, int y, int width, int height){
     //   ((JComponent)c).setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        c.setFont(mainFont);
        c.setBounds(x, y, width, height);
    }
    protected void loadFont(String path){
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            mainFont = customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
