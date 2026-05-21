package ua.university;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class MainUI extends UI{
    private JButton newCanvas, loadFile, exit;
    private JLabel welcome;
    public MainUI(AppPanel appPanel){
        super(appPanel);
    }
    protected void setup() {
        exit = new JButton("Вийти");
        exit.addActionListener(e->exitApp());
        setupComponentFontAndBounds(exit, 0, 0, 190, 40);

        newCanvas = new JButton("Створити новий проєкт");
        newCanvas.addActionListener(e->createNewCanvas());
        setupComponentFontAndBounds(newCanvas, 0, 60, 190, 40);

        loadFile = new JButton("Завантажити з файлу");
        loadFile.addActionListener(e->loadFromFile());
        setupComponentFontAndBounds(loadFile, 0, 100, 190, 40);

        addButtons();
    }
    public void addButtons() {
        appPanel.add(exit);
        appPanel.add(newCanvas);
        appPanel.add(loadFile);
    }
    private void exitApp(){
        System.exit(0);
    }
    private void createNewCanvas(){
        int width=50, height=50;
        JOptionPane.showInputDialog(null, "");
        appPanel.setUpCanvas(width, height);
        goToApp();
    }
    private void goToApp(){
        appPanel.removeAll();
        appPanel.setGameState(1);
        appPanel.setAppUI(new AppUI(appPanel));
        appPanel.repaint();
    }
    private void loadFromFile(){
        JFileChooser fileChooser = new JFileChooser(new File("src/main/saves"));
        fileChooser.setFileFilter(new FileNameExtensionFilter(".png","png"));
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            appPanel.setUpCanvas(selectedFile.getPath());
            goToApp();
        }

    }
}
