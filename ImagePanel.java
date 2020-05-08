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