package jpize.tests.mcose.launcher.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JPanelWithBackground extends JPanel{

    private Image backgroundImage;

    public JPanelWithBackground(String fileName){
        try{
            backgroundImage = ImageIO.read(Objects.requireNonNull(JPanelWithBackground.class.getResourceAsStream(fileName)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, super.getWidth(), super.getHeight(), this);
    }

}