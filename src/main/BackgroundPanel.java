package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class sets the background image of the frame and all the other panels that will be added on top of it will
 * be set to not opaque so that they do not cover the background.
 *
 * @author Elena Roncolino
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    private static final Logger logger = Logger.getLogger(BackgroundPanel.class.getName());

    /**
     * Constructor of the background panel: it reads the image and create a new login panel to be displayed on top of itself.
     */
    public BackgroundPanel(){
        try {
            backgroundImage = ImageIO.read(new File("images/BackgroundImage.jpg"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Problem finding background image", e );
        }
    }

    /**
     * Paints the background image.
     * @param g Graphic context.
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}


