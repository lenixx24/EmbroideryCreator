package ua.university;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Embroidery {
    public final int MAX_WIDTH = EmbroideryCreator.WIDTH*3/4;
    public final int MAX_HEIGHT = EmbroideryCreator.HEIGHT*3/4;
    public final int DEFAULT_STITCH_SIZE=15;
    public final float BORDER_WIDTH=4;
    public final Color BEIGE = new Color(250, 240, 230);
    private Color currentColor = Color.RED;
    private boolean eraserOn=false;
    private int width;
    private int height;
    private int stitchSize;
    private int stitchOffset;
    private Color[][] canvas;
    public Embroidery (int width, int height){
       this.width= width;
       this.height=height;
       // if(width*stitchSize>MAX_WIDTH||height*stitchSize>MAX_HEIGHT)
        stitchSize = width>height? MAX_WIDTH/width: MAX_HEIGHT/height;
        stitchOffset = (int) (stitchSize*0.2);
        setupCanvas();


    }
    private void setupCanvas(){
        loadFromFile( "src/main/resources/vyshyvanka_40x20.txt");
        /*canvas = new Color[width][height];
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                canvas[i][j] = null;

            }
        }*/
    }
    public void loadFromFile(String filePath){
        canvas = new Color[height][width];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null && y < height) {
                String[] pixels = line.trim().split(" ");
                for (int x = 0; x < Math.min(width, pixels.length); x++) {
                    String[] rgb = pixels[x].split(",");
                    int r = Integer.parseInt(rgb[0]);
                    int g = Integer.parseInt(rgb[1]);
                    int b = Integer.parseInt(rgb[2]);
                    canvas[y][x] = new Color(r, g, b);
                }
                y++;
            }
            System.out.println("File Color[" + height + "][" + width + "] was read successfully");

        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Wrong format: " + e.getMessage());
        }
    }
    public void saveToFile(String name){

    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int startX=(EmbroideryCreator.WIDTH-width*stitchSize)/2;
        int startY=(EmbroideryCreator.HEIGHT-height*stitchSize)/2;
        drawCanvas(startX, startY, g2);
        drawGrid(startX, startY, g2);
        drawStitches(startX, startY, g2);
    }

    private void drawStitches(int startX, int startY,  Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
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
        g2.drawRect((int)(x-BORDER_WIDTH), (int)(y-BORDER_WIDTH),
                width*stitchSize+(int)BORDER_WIDTH*2, height*stitchSize+(int)BORDER_WIDTH*2);
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
       int col = (e.getX()-(EmbroideryCreator.WIDTH - stitchSize*width)/2)/stitchSize;
       int row = (e.getY()-(EmbroideryCreator.HEIGHT - stitchSize*height)/2)/stitchSize;
       System.out.println(row+", "+col);
       if(col<0||row<0||col>=width||row>=height) return;
      if(eraserOn) canvas[row][col]=null;
      else canvas[row][col]=currentColor;
    }
    private Random r = new Random();
}
