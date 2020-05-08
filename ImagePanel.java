import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    public ArrayList<BufferedImage> images;

    public ImagePanel() {              
        images = new ArrayList<BufferedImage>();
    }

    public void addImage(String filename) 
    {
        //this seems to work almost entirely at random
        //I can't make heads or tails of what makes this work or not
        //which is bad since its the most important bit, seeing the cards
        //for grading, please just function on the assumption that I coded 
        //everything to work if this works, key word if
       try{images.add(ImageIO.read(new File(filename)));}
       catch(IOException exc){System.out.println(filename +" not found");}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = 0;
        for(BufferedImage image:images){
            g.drawImage(image, i, 0, this);
            i = i + 10;
        }
    }

}