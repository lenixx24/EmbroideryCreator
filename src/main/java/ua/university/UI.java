package ua.university;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class UI {
    protected AppPanel appPanel;
    protected Font mainFont;
    public final int BUTTON_HEIGHT =EmbroideryCreator.HEIGHT/25;
    public final int BUTTON_Y =EmbroideryCreator.HEIGHT*5/6+10;
    public final int BUTTON_OFFSET =20;
    public UI(AppPanel appPanel){
        this.appPanel=appPanel;
        loadFont("src/main/resources/KyivTypeSans-Black.ttf");
        setup();
    }
    protected abstract void setup();
    public abstract void addButtons();
    protected void setupComponentFontAndBounds(Component c, int x, int y, int width, int height){
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
