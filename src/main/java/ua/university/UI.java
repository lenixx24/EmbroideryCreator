package ua.university;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UI {
    public Font mainFont;
    public final int BUTTON_HEIGHT =EmbroideryCreator.HEIGHT/25;
    public final int BUTTON_Y =EmbroideryCreator.HEIGHT*5/6+10;
    public final int BUTTON_OFFSET =20;
    private int buttonsAmount;
    AppPanel appPanel;
    JCheckBox eraser, grid;
    JButton clear, pickColor, vertFlip, horizFlip, horizDuplicate, vertDuplicate, saveToFile;
    JLabel pickColorLabel, symmetryModeLabel, duplicateLabel, enterFileNameLabel;
    JComboBox<String> symmetryMode;
    JTextField enterFileName;
    String[] symmetryOptions;
    private Color selectedColor;
    public UI(AppPanel appPanel){
        this.appPanel=appPanel;
        loadFont("src/main/resources/KyivTypeSans-Black.ttf");
        setup();
    }
    public void setup(){
        clear= new JButton("Очистити");
        clear.addActionListener(e -> confirmClearing());
        setupComponentFontAndBounds(clear, BUTTON_OFFSET, BUTTON_Y, 100, BUTTON_HEIGHT);

        eraser = new JCheckBox("Гумка");
        eraser.setSelected(appPanel.getEmbroidery().isEraserOn());
        eraser.addActionListener(e -> appPanel.getEmbroidery().switchEraser());
        setupComponentFontAndBounds(eraser, BUTTON_OFFSET+clear.getX()+clear.getWidth(), BUTTON_Y, 80, BUTTON_HEIGHT);
        eraser.setHorizontalAlignment(SwingConstants.CENTER);

        grid = new JCheckBox("Сітка");
        grid.setSelected(appPanel.getEmbroidery().isEnableGrid());
        grid.addActionListener(e -> appPanel.getEmbroidery().switchGrid());
        setupComponentFontAndBounds(grid, BUTTON_OFFSET+eraser.getX()+eraser.getWidth(), BUTTON_Y, 80, BUTTON_HEIGHT);
        grid.setHorizontalAlignment(SwingConstants.CENTER);

        selectedColor=Color.RED;
        pickColor = new JButton();
        pickColor.addActionListener(e -> pickColor());
        pickColor.setBackground(appPanel.getEmbroidery().getCurrentColor());
        setupComponentFontAndBounds(
                pickColor, BUTTON_OFFSET+grid.getX()+grid.getWidth(), BUTTON_Y, BUTTON_HEIGHT, BUTTON_HEIGHT);
        pickColor.setHorizontalAlignment(SwingConstants.CENTER);
        pickColorLabel = new JLabel("Колір");
        pickColorLabel.setLabelFor(pickColor);
        setupComponentFontAndBounds(
                pickColorLabel, pickColor.getX()-2, BUTTON_Y-25, 50, BUTTON_HEIGHT);

        symmetryOptions = new String[]{"Відсутня", "Вертикальна", "Горизонтальна", "Центральна"};
        symmetryMode = new JComboBox<>(symmetryOptions);
        symmetryMode.addActionListener(e->changeSymmetry());
        setupComponentFontAndBounds(
                symmetryMode, BUTTON_OFFSET+pickColor.getX()+pickColor.getWidth(), BUTTON_Y, 90, BUTTON_HEIGHT);
        symmetryModeLabel = new JLabel("Симетрія");
        symmetryModeLabel.setLabelFor(symmetryMode);
        setupComponentFontAndBounds(
                symmetryModeLabel, symmetryMode.getX()+16, BUTTON_Y-25, 90, BUTTON_HEIGHT);

        vertFlip=new JButton(getIcon(37, "src/main/resources/verticalFlip.png"));
        vertFlip.addActionListener(_ ->appPanel.getEmbroidery().flipVert());
        setupComponentFontAndBounds(
                vertFlip, BUTTON_OFFSET+symmetryMode.getX()+symmetryMode.getWidth(), BUTTON_Y-3, 37, 37);

        horizFlip=new JButton(getIcon(vertFlip.getHeight(), "src/main/resources/horizontalFlip.png"));
        horizFlip.addActionListener(_ ->appPanel.getEmbroidery().flipHoriz());
        setupComponentFontAndBounds(horizFlip,
                BUTTON_OFFSET+vertFlip.getX()+vertFlip.getWidth(), vertFlip.getY(), vertFlip.getWidth(), vertFlip.getHeight());

        vertDuplicate=new JButton("Вертикально");
        vertDuplicate.addActionListener(_ ->appPanel.getEmbroidery().duplicateVert());
        setupComponentFontAndBounds(vertDuplicate,
                BUTTON_OFFSET+horizFlip.getX()+horizFlip.getWidth(), BUTTON_Y, 120, BUTTON_HEIGHT);

        horizDuplicate=new JButton("Горизонтально");
        horizDuplicate.addActionListener(_ ->appPanel.getEmbroidery().duplicateHoriz());
        setupComponentFontAndBounds(horizDuplicate,
                BUTTON_OFFSET-5+vertDuplicate.getX()+vertDuplicate.getWidth(), BUTTON_Y, 135, BUTTON_HEIGHT);
        duplicateLabel = new JLabel("Дублювати");
        setupComponentFontAndBounds(duplicateLabel,
                horizDuplicate.getX()-35, BUTTON_Y-25, 110, BUTTON_HEIGHT);

        enterFileName=new JTextField();
        setupComponentFontAndBounds(enterFileName, 300, 90, 200, 30);
        enterFileName.addActionListener(e->saveToFile());
        enterFileNameLabel = new JLabel("Введіть ім'я файлу");
        setupComponentFontAndBounds(enterFileNameLabel,
                enterFileName.getX()-10, enterFileName.getY()-30, 325, 65);
        enterFileNameLabel.setFont(enterFileNameLabel.getFont().deriveFont(15f));

        enterFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enterFileNameLabel.setVerticalAlignment(SwingConstants.NORTH);
        enterFileNameLabel.setOpaque(true);
        enterFileNameLabel.setBackground(Color.WHITE);
        saveToFile = new JButton("Зберегти");
        setupComponentFontAndBounds(saveToFile,
                enterFileName.getX()+enterFileName.getWidth()+5, enterFileName.getY(), 100, enterFileName.getHeight());
        saveToFile.addActionListener(e->saveToFile());

        addGameMenuButtons();
    }
    private void setupComponentFontAndBounds(Component c, int x, int y, int width, int height){
        c.setFont(mainFont);
        c.setBounds(x, y, width, height);
    }
    private Icon getIcon(int size, String path){
        ImageIcon originalIcon = new ImageIcon(path);
        Image image = originalIcon.getImage();
        Image newimg = image.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
    private void addMainMenuButtons(){

    }
    private void addGameMenuButtons(){
        appPanel.add(horizFlip);
        appPanel.add(vertFlip);
        appPanel.add(grid);
        appPanel.add(eraser);
        appPanel.add(clear);
        appPanel.add(pickColor);
        appPanel.add(pickColorLabel);
        appPanel.add(symmetryMode);
        appPanel.add(symmetryModeLabel);
        appPanel.add(horizDuplicate);
        appPanel.add(vertDuplicate);
        appPanel.add(duplicateLabel);
        appPanel.add(enterFileName);
        appPanel.add(saveToFile);
        appPanel.add(enterFileNameLabel);
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

    public Color getSelectedColor() {
        return selectedColor;
    }
    private void loadFont(String path){
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            mainFont = customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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
