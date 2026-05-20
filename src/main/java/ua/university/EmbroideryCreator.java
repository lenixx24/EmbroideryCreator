package ua.university;

public class EmbroideryCreator {
    AppWindow appWindow;
    AppPanel appPanel;
    public static final int WIDTH=900;
    public static final int HEIGHT=800;
   public EmbroideryCreator (){
            appPanel = new AppPanel();
            appWindow = new AppWindow(appPanel);
    }
}
