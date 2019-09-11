package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.Entities.Static.Apple;
import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;

	public int xCoord;
	public int yCoord;

	public int moveCounter;

	private int moveCheck = 4;  //this value decides the speed of the snake, default speed 4

	public String direction;    //is your first name one?

	public float currScore = 0; // stores current score
	public int stepCounter = 0;

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;
		direction= "Right";
		justAte = false;
		lenght= 1;

	}

	public void tick(){
		moveCounter++;

		//TODO this changes the speed of the snake
		// Checks move counter each tick, the lower the value the higher the speed
		if(moveCounter>=moveCheck) {
			checkCollisionAndMove();
			moveCounter=0;
		}

		// Checks for previous direction and prevents backtracking
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
			if(direction != "Down")  
				direction="Up";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
			if(direction != "Up") 
				direction="Down";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
			if(direction != "Right") 
				direction="Left";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
			if(direction != "Left") 
				direction="Right"; 
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			State.setState(handler.getGame().pauseState);
		}


		// Adds a new tail portion when N is pressed, alternative code commented
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
			lenght++;
			//            Tail tail= null;
			//            tail = new Tail(this.xCoord,this.yCoord,handler);
			//            handler.getWorld().body.addLast(tail);
			//            handler.getWorld().playerLocation[tail.x][tail.y] = true;
			handler.getWorld().body.addLast(new Tail(xCoord, yCoord, handler));

		}

		// Subtracts 1 from moveCheck, subtracting will increase speed
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
			moveCheck--;
		}
		// Adds 1 to moveCheck, adding will decrease speed
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
			moveCheck++;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P))
		{
			lenght--;
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();

		}

	}

	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			// adds to step count
			stepCounter++;
			if(xCoord==0){               	
				// if player goes off screen to the left, appear on the right side
				xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
				//kill();
			}else{
				xCoord--;
			}
			break;
		case "Right":
			stepCounter++;
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				// if player goes off screen to the right, appear on the left side
				xCoord = 0;
				//kill();
			}else{
				xCoord++;
			}
			break;
		case "Up":
			stepCounter++;
			if(yCoord==0){
				// if player goes off screen upwards, appear on the bottom
				yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
				//kill();
			}else{
				yCoord--;
			}
			break;
		case "Down":
			stepCounter++;
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				// if player goes off screen downwards, appear on the top
				yCoord = 0;
				//kill();
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;

			///FOR LOOP

		for(int i=0; i < handler.getWorld().body.size(); i++) { 
			if((this.xCoord == handler.getWorld().body.get(i).x) && (this.yCoord ==
				handler.getWorld().body.get(i).y)) { 
				kill();
				State.setState(handler.getGame().gameOverState); 
				} 
			}


		
		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}

		
	}

	public void render(Graphics g, Boolean[][] playeLocation){
		Random r = new Random();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {

			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				g.setColor(Color.GREEN);

				if(playeLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

				//If step count exceeds Grid width then apple is false
				if(stepCounter == handler.getWorld().GridWidthHeightPixelCount)
				{
					Apple.setGood(false);

				} 

				// If apple is good, then it is red. Otherwise, it is black
				if(Apple.isGood())
				{
					g.setColor(Color.RED);	
				}
				else
				{
					g.setColor(Color.BLACK);
				}

				if(handler.getWorld().appleLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);

				}

			}
		}


	}

	public void Eat(){
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		//checks if the apple is a good apple, else it is a bad apple
		if(Apple.isGood())
		{
			//adds to points 
			currScore += (float) Math.sqrt((2 * currScore) + 1);
			// adds body segment
			lenght++;
			
			// Makes snake move faster when a good apple is eaten
			moveCheck -=1;
			
			switch (direction){
			case "Left":
				if( handler.getWorld().body.isEmpty()){
					if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail = new Tail(this.xCoord+1,this.yCoord,handler);
					}else{
						if(this.yCoord!=0){
							tail = new Tail(this.xCoord,this.yCoord-1,handler);
						}else{
							tail =new Tail(this.xCoord,this.yCoord+1,handler);
						}
					}
				}else{
					if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
					}else{
						if(handler.getWorld().body.getLast().y!=0){
							tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
						}else{
							tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

						}
					}

				}
				break;
			case "Right":
				if( handler.getWorld().body.isEmpty()){
					if(this.xCoord!=0){
						tail=new Tail(this.xCoord-1,this.yCoord,handler);
					}else{
						if(this.yCoord!=0){
							tail=new Tail(this.xCoord,this.yCoord-1,handler);
						}else{
							tail=new Tail(this.xCoord,this.yCoord+1,handler);
						}
					}
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						if(handler.getWorld().body.getLast().y!=0){
							tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
						}else{
							tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
						}
					}

				}
				break;
			case "Up":
				if( handler.getWorld().body.isEmpty()){
					if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail=(new Tail(this.xCoord,this.yCoord+1,handler));
					}else{
						if(this.xCoord!=0){
							tail=(new Tail(this.xCoord-1,this.yCoord,handler));
						}else{
							tail=(new Tail(this.xCoord+1,this.yCoord,handler));
						}
					}
				}else{
					if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}else{
						if(handler.getWorld().body.getLast().x!=0){
							tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
						}else{
							tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
						}
					}

				}
				break;
			case "Down":
				if( handler.getWorld().body.isEmpty()){
					if(this.yCoord!=0){
						tail=(new Tail(this.xCoord,this.yCoord-1,handler));
					}else{
						if(this.xCoord!=0){
							tail=(new Tail(this.xCoord-1,this.yCoord,handler));
						}else{
							tail=(new Tail(this.xCoord+1,this.yCoord,handler));
						} System.out.println("Tu biscochito");
					}
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						if(handler.getWorld().body.getLast().x!=0){
							tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
						}else{
							tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
						}
					}

				}
				break;
			}


			handler.getWorld().body.addLast(tail);
			handler.getWorld().playerLocation[tail.x][tail.y] = true;

		}
		else
		{
			//deducts from points
			currScore -= (float) Math.sqrt((2 * currScore) + 1);
			if(currScore < 0 )
			{
				currScore = 0;
			}
			
			// Makes snake move slower when a bad apple is eaten
			moveCheck +=1;
			
			// removes body segment
			
			if (lenght == 1) {
				kill();
				State.setState(handler.getGame().gameOverState);
				handler.getGame().reStart();
				
			}
			else {
				lenght--;
				handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
				handler.getWorld().body.removeLast();
			}
				
			

		}

		// makes new apple good
		Apple.setGood(true);
		// resets count to 0
		stepCounter = 0;

	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}