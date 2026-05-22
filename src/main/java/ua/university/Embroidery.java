package ua.university;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Embroidery {
    private final AppPanel appPanel;
    public final int MAX_WIDTH = EmbroideryCreator.WIDTH*5/8;
    public final int MAX_HEIGHT = EmbroideryCreator.HEIGHT*5/8;
    public final int DEFAULT_STITCH_SIZE=15;
    public final int Y_OFFSET=50;
    public final static int NO_SYMMETRY=0, VERTICAL_SYMMETRY=1,
            HORIZONTAL_SYMMETRY=2, CENTRAL_SYMMETRY=3;
    public final float BORDER_WIDTH=4;
    public final Color BEIGE = new Color(250, 240, 230);
    private Color currentColor = Color.RED;
    private boolean eraserOn=false;
    private boolean enableGrid=true;
    private int currentSymmetryMode=NO_SYMMETRY;
    private int width, height;
    private int stitchSize, stitchOffset;
    private Color[][] canvas;
    public Embroidery (AppPanel appPanel,  int width, int height){
        this.appPanel=appPanel;
       this.width= width;
       this.height=height;
       canvas=new Color[height][width];
       countStitchVars();
    }


    public Embroidery (AppPanel appPanel,  String filePath){
        this.appPanel=appPanel;
        loadFromFile(filePath);
        countStitchVars();
    }

    private void countStitchVars() {
        stitchSize = width>height? MAX_WIDTH/width: MAX_HEIGHT/height;
        stitchOffset = (int) (stitchSize*0.25);
    }

    public void loadFromFile(String fileName){

        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            height=image.getHeight()/DEFAULT_STITCH_SIZE;
            width=image.getWidth()/DEFAULT_STITCH_SIZE;
            canvas = new Color[height][width];
            Color c;
            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    c= new Color(image.getRGB(j*DEFAULT_STITCH_SIZE+DEFAULT_STITCH_SIZE/2,
                            i*DEFAULT_STITCH_SIZE+DEFAULT_STITCH_SIZE/2));
                    if(c.equals(BEIGE)) c=null;
                    canvas[i][j]=c;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveToFile(String name){
        try {
            int fileWidth = DEFAULT_STITCH_SIZE*width;
            int fileHeight = DEFAULT_STITCH_SIZE*height;
            BufferedImage bufferedImage = new BufferedImage(fileWidth, fileHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(BEIGE);
            g2d.fillRect(0, 0, fileWidth, fileHeight);
            stitchSize=DEFAULT_STITCH_SIZE;
            stitchOffset = (int) (stitchSize*0.25);
            if(enableGrid) drawGrid(0, 0, g2d);
            drawStitches(0,0, g2d);
            g2d.dispose();
            countStitchVars();
            File file = new File("src/main/saves/"+name+".png");
            ImageIO.write(bufferedImage, "png", file);

            JOptionPane.showMessageDialog(
                    null,
                    "Вишивку збережено в "+file.getPath(),
                    "Успіх!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Виникла помилка при збереженні", "Помилка", JOptionPane.ERROR_MESSAGE);

        }



    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int startX=(EmbroideryCreator.WIDTH-width*stitchSize)/2;
        int startY= Y_OFFSET;//(EmbroideryCreator.HEIGHT-height*stitchSize)/2;
        drawCanvas(startX, startY, g2);
        if(enableGrid) drawGrid(startX, startY, g2);
        drawStitches(startX, startY, g2);
    }

    private void drawStitches(int startX, int startY,  Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(stitchOffset, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(canvas[i][j]!=null)
                    drawStitch(startX+j*stitchSize, startY+i*stitchSize, stitchSize, canvas[i][j], g2);
            }
        }
    }

    public void drawCanvas(int x, int y, Graphics2D g2){
        g2.setColor(BEIGE);
        g2.fillRect(x, y, width*stitchSize, height*stitchSize);
        g2.setStroke(new BasicStroke(BORDER_WIDTH*2));
        g2.setColor(Color.BLACK);
        g2.drawRect((int)(x-BORDER_WIDTH), (int)(y-BORDER_WIDTH),width*stitchSize+(int)BORDER_WIDTH*2, height*stitchSize+(int)BORDER_WIDTH*2);
    }
    public void drawGrid(int x, int y, Graphics2D g2){g2.setStroke(new BasicStroke(1.5f));
       g2.setColor(Color.BLACK);
        for(int i=0; i<=width; i++)
            g2.drawLine(x+i*stitchSize, y, x+i*stitchSize, y+height*stitchSize);
        for(int j=0; j<=height; j++)
            g2.drawLine(x, y+j*stitchSize, x+width*stitchSize, y+j*stitchSize);
    }
    public void drawStitch(int x, int y, int size, Color color, Graphics g){
        g.setColor(color);
        g.drawLine(x+stitchOffset, y+stitchOffset, x+size-stitchOffset, y+size-stitchOffset);
        g.drawLine(x+size-stitchOffset, y+stitchOffset, x+stitchOffset, y+size-stitchOffset);
    }
    private Color getRandomColor(){
        return switch (r.nextInt(4)) {
            case 0 -> Color.RED;
            case 1 -> Color.BLACK;
            case 2 -> Color.WHITE;
            default -> null;
        };
    }
    public void onClick(MouseEvent e){
      //  System.out.println(e.getPoint());
       int col = (e.getX()-(EmbroideryCreator.WIDTH - stitchSize*width)/2)/stitchSize;
       int row = (e.getY()-Y_OFFSET)/stitchSize;
       int reversedCol=width-1-col;
       int reversedRow=height-1-row;
      //System.out.println(row+", "+col+ "reversed: "+reversedRow+", "+reversedCol);
       if(col<0||row<0||col>=width||row>=height) return;
       if(eraserOn) currentColor=null;
       else currentColor = appPanel.getAppUI().getPickColor().getBackground();
       canvas[row][col]=currentColor;
       switch (currentSymmetryMode){
           case VERTICAL_SYMMETRY -> {
                canvas[row][reversedCol]=currentColor;
           }
           case HORIZONTAL_SYMMETRY -> {
                canvas[reversedRow][col]=currentColor;
           }
           case CENTRAL_SYMMETRY -> {
               canvas[reversedRow][reversedCol]=currentColor;
                   canvas[reversedRow][col]=currentColor;
                   canvas[row][reversedCol]=currentColor;
           }
       }

       repaintStitchesOnly();
    }
    public void clear(){
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++)
                canvas[i][j] = null;
        repaintStitchesOnly();
    }
    private void repaintStitchesOnly(){

        appPanel.repaint((EmbroideryCreator.WIDTH-MAX_WIDTH)/2, Y_OFFSET-10, MAX_WIDTH, MAX_HEIGHT+10);
    }

    public void switchGrid(){
        this.enableGrid=!enableGrid;
        repaintStitchesOnly();
    }
    private Random r = new Random();
public void setCurrentColor(Color color){
    this.currentColor=color;
}
    public Color getCurrentColor() {
        return currentColor;
    }
    public void switchEraser() {
        this.eraserOn=!eraserOn;
    }
    public boolean isEraserOn() {
        return eraserOn;
    }
    public boolean isEnableGrid() {
        return enableGrid;
    }
    public void setCurrentSymmetryMode(int currentSymmetryMode) {
        this.currentSymmetryMode = currentSymmetryMode;
       // System.out.println(currentSymmetryMode);
    }
    public void flipVert(){
            Color temp;
            for(int i=0; i<height; i++)
                for(int j=0; j<width/2; j++){
                    temp=canvas[i][j];
                    canvas[i][j] = canvas[i][width-1-j];
                    canvas[i][width-1-j]=temp;
                }
            repaintStitchesOnly();
    }
    public void flipHoriz(){
        //horizontal
        Color temp;
        for(int i=0; i<height/2; i++)
            for(int j=0; j<width; j++){
                temp=canvas[i][j];
                canvas[i][j] = canvas[height-1-i][j];
                canvas[height-1-i][j]=temp;
            }
        repaintStitchesOnly();
    }
    public  void duplicateVert(){
        Color[][] colorsTemp = new Color[height*2][width];
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++){
                colorsTemp[i][j]=canvas[i][j];
                colorsTemp[i+height][j]=canvas[i][j];
            }
        this.height*=2;
        canvas=colorsTemp;
        countStitchVars();
        repaintStitchesOnly();
       // appPanel.repaint();
    }
    public  void duplicateHoriz(){
        Color[][] colorsTemp = new Color[height][width*2];
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++){
                colorsTemp[i][j]=canvas[i][j];
                colorsTemp[i][j+width]=canvas[i][j];
            }
        this.width*=2;
        canvas=colorsTemp;
        countStitchVars();
        repaintStitchesOnly();
    }

}
