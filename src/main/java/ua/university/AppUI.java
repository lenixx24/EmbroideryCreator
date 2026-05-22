package ua.university;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AppUI extends UI {
    JCheckBox eraser, grid;
    JButton clear, pickColor, vertFlip, horizFlip, horizDuplicate, vertDuplicate,
            saveToFile, backToMenu;
    JLabel pickColorLabel, symmetryModeLabel, duplicateLabel, enterFileNameLabel;
    JComboBox<String> symmetryMode;
    JTextField enterFileName;
    String[] symmetryOptions;
    private JPanel uiPanel, filePanel;
    private Color selectedColor;
    public AppUI(AppPanel appPanel, JFrame appFrame){
        super(appPanel, appFrame);
    }
    public void setup(){

        uiPanel = new JPanel();
        uiPanel.setLayout(null);
        uiPanel.setPreferredSize(new Dimension(EmbroideryCreator.WIDTH, 110));
        uiPanel.setOpaque(true);
        appFrame.add(uiPanel, BorderLayout.SOUTH);
        uiPanel.setBackground(Color.white);
        filePanel = new JPanel();
        filePanel.setLayout(null);
        filePanel.setPreferredSize(new Dimension(EmbroideryCreator.WIDTH, 80));
        filePanel.setOpaque(true);
        appFrame.add(filePanel, BorderLayout.NORTH);
        clear= new JButton("Очистити");
        clear.addActionListener(e -> confirmClearing());

        eraser = new JCheckBox("Гумка");
        eraser.setSelected(appPanel.getEmbroidery().isEraserOn());
        eraser.addActionListener(e -> appPanel.getEmbroidery().switchEraser());
        eraser.setHorizontalAlignment(SwingConstants.CENTER);

        grid = new JCheckBox("Сітка");
        grid.setSelected(appPanel.getEmbroidery().isEnableGrid());
        grid.addActionListener(e -> appPanel.getEmbroidery().switchGrid());
        grid.setHorizontalAlignment(SwingConstants.CENTER);

        selectedColor=Color.RED;
        pickColor = new JButton();
        pickColor.addActionListener(e -> pickColor());
        pickColor.setBackground(appPanel.getEmbroidery().getCurrentColor());
         pickColor.setHorizontalAlignment(SwingConstants.CENTER);
        pickColorLabel = new JLabel("Колір");
        pickColorLabel.setLabelFor(pickColor);

        symmetryOptions = new String[]{"Відсутня", "Вертикальна", "Горизонтальна", "Центральна"};
        symmetryMode = new JComboBox<>(symmetryOptions);
        symmetryMode.addActionListener(e->changeSymmetry());
         symmetryModeLabel = new JLabel("Симетрія");
        symmetryModeLabel.setLabelFor(symmetryMode);

        vertFlip=new JButton(getIcon(37, "src/main/resources/verticalFlip.png"));
        vertFlip.addActionListener(_ ->appPanel.getEmbroidery().flipVert());

        horizFlip=new JButton(getIcon(37, "src/main/resources/horizontalFlip.png"));
        horizFlip.addActionListener(_ ->appPanel.getEmbroidery().flipHoriz());

        vertDuplicate=new JButton("Вертикально");
        vertDuplicate.addActionListener(_ ->appPanel.getEmbroidery().duplicateVert());

        horizDuplicate=new JButton("Горизонтально");
        horizDuplicate.addActionListener(_ ->appPanel.getEmbroidery().duplicateHoriz());
         duplicateLabel = new JLabel("Дублювати");

        enterFileName=new JTextField();
        enterFileName.addActionListener(e->saveToFile());
        enterFileNameLabel = new JLabel("Введіть ім'я файлу");
         enterFileNameLabel.setFont(enterFileNameLabel.getFont().deriveFont(15f));

        enterFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enterFileNameLabel.setVerticalAlignment(SwingConstants.NORTH);
        enterFileNameLabel.setOpaque(true);
       // enterFileNameLabel.setBackground(Color.WHITE);
        saveToFile = new JButton("Зберегти");
        saveToFile.addActionListener(e->saveToFile());

        backToMenu=new JButton("Повернутись в меню");
        backToMenu.addActionListener(_ ->backToMenu());
        setComponentsPosition();
        addButtons();
        appFrame.repaint();
    }
    public void setComponentsPosition(){
        setupComponentFontAndBounds(clear, BUTTON_OFFSET, BUTTON_Y, 100, BUTTON_HEIGHT);
        setupComponentFontAndBounds(eraser, BUTTON_OFFSET+clear.getX()+clear.getWidth(), BUTTON_Y, 80, BUTTON_HEIGHT);
        setupComponentFontAndBounds(grid, BUTTON_OFFSET+eraser.getX()+eraser.getWidth(), BUTTON_Y, 80, BUTTON_HEIGHT);
        setupComponentFontAndBounds(
                pickColor, BUTTON_OFFSET+grid.getX()+grid.getWidth(), BUTTON_Y, BUTTON_HEIGHT, BUTTON_HEIGHT);
        setupComponentFontAndBounds(
                pickColorLabel, pickColor.getX()-2, BUTTON_Y-30, 50, BUTTON_HEIGHT);
        setupComponentFontAndBounds(
                symmetryMode, BUTTON_OFFSET+pickColor.getX()+pickColor.getWidth(), BUTTON_Y, 90, BUTTON_HEIGHT);
        setupComponentFontAndBounds(
                symmetryModeLabel, symmetryMode.getX()+16, BUTTON_Y-30, 90, BUTTON_HEIGHT);
        setupComponentFontAndBounds(
                vertFlip, BUTTON_OFFSET+symmetryMode.getX()+symmetryMode.getWidth(), BUTTON_Y-3, 37, 37);
        setupComponentFontAndBounds(horizFlip,
                BUTTON_OFFSET+vertFlip.getX()+vertFlip.getWidth(), vertFlip.getY(), vertFlip.getWidth(), vertFlip.getHeight());
        setupComponentFontAndBounds(vertDuplicate,
                BUTTON_OFFSET+horizFlip.getX()+horizFlip.getWidth(), BUTTON_Y, 120, BUTTON_HEIGHT);
        setupComponentFontAndBounds(horizDuplicate,
                BUTTON_OFFSET-5+vertDuplicate.getX()+vertDuplicate.getWidth(), BUTTON_Y, 135, BUTTON_HEIGHT);
        setupComponentFontAndBounds(duplicateLabel,
                horizDuplicate.getX()-35, BUTTON_Y-30, 110, BUTTON_HEIGHT);
        setupComponentFontAndBounds(enterFileName, 300, 40, 200, 30);
        setupComponentFontAndBounds(enterFileNameLabel,
                enterFileName.getX()-10, enterFileName.getY()-30, 325, 65);
        setupComponentFontAndBounds(saveToFile,
                enterFileName.getX()+enterFileName.getWidth()+5, enterFileName.getY(), 100, enterFileName.getHeight());
        setupComponentFontAndBounds(backToMenu,
                30, 30, 170,40);
    }
    private void backToMenu(){
        int response = JOptionPane.showConfirmDialog(
                null,
                "Ви точно хочете повернутись в меню?\nНе збережену вишивку буде втрачено",
                "Оберіть варіант",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            appPanel.setGameState(0);
            appFrame.remove(filePanel);
            appFrame.remove(uiPanel);
            appPanel.removeAll();
            filePanel.removeAll();
            uiPanel.removeAll();
            appFrame.revalidate();
            appFrame.repaint();
        }
    }
    private Icon getIcon(int size, String path){
        ImageIcon originalIcon = new ImageIcon(path);
        Image image = originalIcon.getImage();
        Image newImg = image.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
    public void addButtons(){
        appFrame.add(uiPanel, BorderLayout.SOUTH);
        uiPanel.add(horizFlip, 0);
        uiPanel.add(vertFlip, 0);
        uiPanel.add(grid, 0);
        uiPanel.add(eraser, 0);
        uiPanel.add(clear,0);
        uiPanel.add(pickColor, 0);
        uiPanel.add(pickColorLabel,0);
        uiPanel.add(symmetryMode,0);
        uiPanel.add(symmetryModeLabel,0);
        uiPanel.add(horizDuplicate,0);
        uiPanel.add(vertDuplicate,0);
        uiPanel.add(duplicateLabel,0);
        appFrame.add(filePanel,BorderLayout.NORTH );
        filePanel.add(enterFileName);
        filePanel.add(saveToFile,0);
        filePanel.add(enterFileNameLabel);
        filePanel.add(backToMenu);
        appFrame.revalidate();
        appFrame.repaint();
    }
    private void pickColor(){
        selectedColor = JColorChooser.showDialog(null,
                "Оберіть колір для вишивання",
                appPanel.getEmbroidery().getCurrentColor());
        if (selectedColor != null) {
            appPanel.getEmbroidery().setCurrentColor(selectedColor);
            pickColor.setBackground(selectedColor);
        }
    }
    private void confirmClearing(){
        int response = JOptionPane.showConfirmDialog(
                null,
                "Ви точно хочете очистити полотно?\nНе збережену вишивку буде втрачено",
                "Оберіть варіант",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            appPanel.getEmbroidery().clear();
        }
    }
    private void changeSymmetry(){
        String selectedMode = Objects.requireNonNull(symmetryMode.getSelectedItem()).toString();
        int selectedSymmetry = switch (selectedMode) {
            case "Вертикальна" -> Embroidery.VERTICAL_SYMMETRY;
            case "Горизонтальна" -> Embroidery.HORIZONTAL_SYMMETRY;
            case "Центральна" -> Embroidery.CENTRAL_SYMMETRY;
            default -> Embroidery.NO_SYMMETRY;
        };
        appPanel.getEmbroidery().setCurrentSymmetryMode(selectedSymmetry);
    }
    private void saveToFile(){
       String fileName = enterFileName.getText();
       if(fileName.matches("^[^\\\\/:*?\"<.>|]+$"))
        appPanel.getEmbroidery().saveToFile(fileName);
       else {
           enterFileName.setText("");
           JOptionPane.showMessageDialog(null, "Назва файлу містить заборонені символи", "Помилка", JOptionPane.ERROR_MESSAGE);
       }
    }
    public JButton getPickColor() {
        return pickColor;
    }
}
