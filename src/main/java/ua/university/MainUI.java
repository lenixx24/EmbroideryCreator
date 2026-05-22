package ua.university;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainUI extends UI{
    private JButton newCanvas, loadFile, exit;
    private JLabel welcome;
    public MainUI(AppPanel appPanel, JFrame appFrame){
        super(appPanel, appFrame);
    }
    protected void setup() {
        mainFont = mainFont.deriveFont(16.f);
        int buttonWidth=250;
        newCanvas = new JButton("Створити новий проєкт");
        newCanvas.addActionListener(e->createNewCanvas());
        setupComponentFontAndBounds(newCanvas, (EmbroideryCreator.WIDTH-buttonWidth)/2, 370, buttonWidth, 50);

        loadFile = new JButton("Завантажити з файлу");
        loadFile.addActionListener(e->loadFromFile());
        setupComponentFontAndBounds(loadFile, (EmbroideryCreator.WIDTH-buttonWidth)/2, 300, buttonWidth, 50);

        buttonWidth=140;
        exit = new JButton("Вийти");
        exit.addActionListener(e->exitApp());
        setupComponentFontAndBounds(exit, (EmbroideryCreator.WIDTH-buttonWidth)/2, 440, buttonWidth, 50);

        addButtons();
        appFrame.repaint();
        appPanel.repaint();

    }
    public void addButtons() {
        appPanel.add(exit, 0);
        appPanel.add(newCanvas, 0);
        appPanel.add(loadFile, 0);

    }
    private void exitApp(){
        System.exit(0);
    }
    private void createNewCanvas(){
        showSizeChooser(appFrame);
    }

    private void showSizeChooser(JFrame jFrame) {
        SpinnerNumberModel widthModel = new SpinnerNumberModel(30, 5, 50, 5);
        SpinnerNumberModel heightModel = new SpinnerNumberModel(30, 5, 50, 5);
        JSpinner widthSpinner = new JSpinner(widthModel);
        JSpinner heightSpinner = new JSpinner(heightModel);
        JButton defaultButton = new JButton("Полотно за замовчуванням");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
        defaultButton.addActionListener(this::defaultCanvas);
        panel.add(new JLabel("Ширина (5-50):"));
        panel.add(widthSpinner);
        panel.add(new JLabel("Висота (5-50):"));
        panel.add(heightSpinner);
        panel.add(new JLabel(""));
        panel.add(defaultButton);
        int result = JOptionPane.showConfirmDialog(
                jFrame,
                panel,
                "Оберіть кількість клітинок в полотні",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            int width = (int) widthSpinner.getValue();
            int height = (int) heightSpinner.getValue();
            jFrame.remove(panel);
            appPanel.setUpCanvas(width, height);
            goToApp();
        }
    }

    private void goToApp(){
        appPanel.removeAll();
        appPanel.setGameState(1);
        appPanel.setAppUI();
        appPanel.repaint();
        appFrame.repaint();
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
    private void defaultCanvas(ActionEvent e){
        appPanel.setUpCanvas("src/main/saves/AngelinaEmbroidery.png");
        goToApp();
        Component component = (Component) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);

        if (window != null) {
            window.dispose();
    }}
}
