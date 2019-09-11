package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage GameOverState;		//image for the Game Over's background 
    public static BufferedImage reStartButt[];   		//image for the reset button 
    public static BufferedImage QuitButton[];   		//image for the quit button 
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static ImageIcon icon;

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        reStartButt = new BufferedImage[2];		//for the restart button
        QuitButton = new BufferedImage[2];		//for the quit button
        

        try {

            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            GameOverState = ImageIO.read(getClass().getResourceAsStream("/Buttons/GameOverState.jpg"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            reStartButt[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/reStartButt.png"));	//reStart button n
            reStartButt[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/reStartButt.png"));	//reStart button after clicked?
            QuitButton[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/QuitButton.png"));		//for the quit button
            QuitButton[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/QuitButton.png"));	//for the quit button
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut

            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
