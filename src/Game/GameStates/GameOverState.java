package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
    
     // RESTART Button 
        uiManager.addObjects(new UIImageButton(600, 600, 128, 64, Images.reStartButt, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().reStart();	//Use the reStart method
            State.setState(handler.getGame().gameState);
        }));
        
        // QUIT Button 
        uiManager.addObjects(new UIImageButton(600,680, 128, 64, Images.QuitButton, () -> {
            handler.getMouseManager().setUimanager(null);
            System.exit(1);
        }));
        

        
        //WE MIGHT ADD A QUIT BUTTON	
    }
    
    

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameOverState);
        }


    }

    @Override public void render(Graphics g) {
    	g.drawImage(Images.GameOverState,0,0,800,800,null); uiManager.Render(g);

    }
}