import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;


public class GameEngine 
{
	private static DisplayMode[] displayModes = {
		new DisplayMode(1366,768,32,0),
		new DisplayMode(1366,768,24,0),
		new DisplayMode(1366,768,16,0),
		new DisplayMode(1920,1080,16,0),
		new DisplayMode(1920,1080,24,0),
		new DisplayMode(1920,1080,32,0),
		new DisplayMode(1680,1050,16,0),
		new DisplayMode(1680,1050,24,0),
		new DisplayMode(1680,1050,32,0),
		new DisplayMode(1600,1200,16,0),
		new DisplayMode(1600,1200,24,0),
		new DisplayMode(1600,1200,32,0),
		new DisplayMode(1600,900,16,0),
		new DisplayMode(1600,900,24,0),
		new DisplayMode(1600,900,32,0),
		new DisplayMode(1366,768,16,0),
		new DisplayMode(1366,768,24,0),
		new DisplayMode(1366,768,32,0),
		new DisplayMode(1360,768,16,0),
		new DisplayMode(1360,768,24,0),
		new DisplayMode(1360,768,32,0),
		new DisplayMode(1280,1024,16,0),
		new DisplayMode(1280,1024,24,0),
		new DisplayMode(1280,1024,32,0),
		new DisplayMode(1280,800,16,0),
		new DisplayMode(1280,800,24,0),
		new DisplayMode(1280,800,32,0),
		new DisplayMode(1024,768,16,0),
		new DisplayMode(1024,768,24,0),
		new DisplayMode(1024,768,32,0),
		new DisplayMode(1024,600,16,0),
		new DisplayMode(1024,600,24,0),
		new DisplayMode(1024,600,32,0),
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0)
	};

	
	private boolean running;
	protected ScreenManager scrManager;
	private Image backGround;
	private Point backGroundPoint;
	long standardDelay = 30;
	int movedCounter =0;
	Scoring timeKeeper;
	
	private Mover mover;
	
	Boolean crash=false;
	Boolean gameWin=false;
	Boolean doOnce=true;
	
	Boolean missionGiven=false;
	
	String mission1,mission2,mission3,mission4;
	
	/**
	 * this method is called in the Main method to start the game.*/
	public void run()
	{
		try
		{
			init();
			gameLoop();
		}
		catch(Exception exc)
		{
			System.out.println("\nException 2:\n error in the run method");
			System.out.println(exc.toString());
		}
		finally
		{
			scrManager.exitGame();
		}
	}//END run()
	
	public void stop()
	{
		running =false;
	}
	
	/**
	 * initializes the screen manager and calls the set full screen to get the screen going*/
	public void init()
	{
		backGroundPoint = new Point();
		
		scrManager = new ScreenManager();      //make screen manager object
		DisplayMode chosenMode = scrManager.findBestCompatibleDisplayMode(displayModes);
		scrManager.setFullScreen(chosenMode);
		Handler handler1 = new Handler();
		Window win = scrManager.getFullScreenWindow();
		win.addKeyListener(handler1);
		
		running=true;
		
		backGround = new ImageIcon(getClass().getResource("back.png")).getImage();
		int bgW = backGround.getWidth(null);
		int bgH = backGround.getHeight(null);
		backGround.getScaledInstance(bgW, bgH, Image.SCALE_FAST); //this scales the image to fit the screen. 
		
		mover = new Mover();
		mission();

		timeKeeper = new Scoring();
	}
	
	/**
	 * this is the loop that will keep the game listening for actions and redrawing stuff*/
	public void gameLoop()
	{
		long loopStartTime,timeElapsed,sleepFor;
		loopStartTime = System.currentTimeMillis();
		while(running)
		{
			move();  ///this moves the ship in space according to angle and  the velocity
			Graphics2D g = scrManager.getGraphics();  
			draw(g);
			g.dispose();
			scrManager.updat();
			if(mover.checkCollision())
			{
				crash=true;
				mover.myShip.setVelocity(0);
			}
			if(mover.win())
			{
				gameWin=true;
				mover.myShip.setVelocity(0);
				if(timeKeeper.totalTime==0)
				{
					timeKeeper.setTotalTime();
				}
				if(doOnce)
				{
					timeKeeper.writeToFile(Integer.toString(timeKeeper.totalMin)+"."+Integer.toString(timeKeeper.totalSec));
					timeKeeper.readFromFile();
					doOnce=false;
				}
			}
			
			timeElapsed = System.currentTimeMillis()-loopStartTime;
			sleepFor = standardDelay -timeElapsed;
			if(sleepFor <0)
			{
				sleepFor=20;
			}
			try
			{
				Thread.sleep(sleepFor);
			}
			catch(Exception ex)
			{
				System.out.println("Exception 3:\nCould not sleep thread.\n\n");
			}
		}
		
		
	}
	
	
	/**
	 * this will draw the graphics
	 * @param Graphics2D*/
	public void draw(Graphics2D g)
	{
		int width = scrManager.getWidth();
		int height = scrManager.getHeight();
		
		//find overlap between background images and screen
		
		backGroundPoint.x = backGroundPoint.x % width;
		backGroundPoint.y = backGroundPoint.y % height;
		
		if(backGroundPoint.x <0)
		{
			backGroundPoint.x = backGroundPoint.x + width;
		}
		if(backGroundPoint.y <0)
		{
			backGroundPoint.y = backGroundPoint.y + height;
		}
		
		int x = backGroundPoint.x;
		int y = backGroundPoint.y;
		
		g.drawImage(backGround,x,y,width,height,null); //starts on screen
		g.drawImage(backGround,x-width,y,width,height,null);  //starts to the left of the screen when backgroundpoint is neg. loops to right.
		g.drawImage(backGround,x,y-height,width,height,null);  //starts bellow the screen.  whenb backgroundpoint.y is negative loops to top of screen
		g.drawImage(backGround,x-width,y-height,width,height,null);  //starts to BottomLeft of screen when BGPoint is negative loops to top for Y and right for X
		
		g.drawImage(mover.crashedShip.shipImage,mover.crashedShip.shipPoint.x,mover.crashedShip.shipPoint.y,mover.crashedShip.shipWidth,mover.crashedShip.shipHeight,null);
		for(int i=0;i<9;i++)
		{
			for(Astroid el: mover.myAstFields.get(i).myAstroids)
			{
				g.drawImage(el.astroidImage, el.astroidPoint.x, el.astroidPoint.y, el.astWidth, el.astHeight, null);
			}
		}
		
		if(crash)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
			g.drawString("You Died. the end", 400, 200);
			
		}
		else
		{
		g.drawImage(mover.myShip.getImage(),( width/2)-(mover.myShip.shipWidth/2), (height/2)-(mover.myShip.shipHeight/2),mover.myShip.shipWidth,mover.myShip.shipHeight,null); //draws ship in the center of the screen
		}
		if(!missionGiven)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
			g.drawString(mission1,200,200);
			g.drawString(mission2,200,220);
			g.drawString(mission3,200,240);
			g.drawString(mission4,200,260);
		}
		if(gameWin)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
			g.drawString("Success!!!!   You finished in "+timeKeeper.totalMin+"."+timeKeeper.totalSec+" minutes", 400, 200);
			int n=250;
			int n2=1;
			for(String el: timeKeeper.topScores)
			{
				n=n+20;
				g.drawString(n2+": "+el.toString()+" Minutes",400,n);
				n2=n2+1;
			}
			
		}
		
		
	}//end draw
	
	
	

	/**
	 * This is the handler that implements the key listeners*/	
	class Handler implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent event) 
		{
			missionGiven=true;
			if(event.getKeyCode()==KeyEvent.VK_ESCAPE)  //checks if escape has been pressed
			{
				running =false; //ends the game loop (which calls screen manager .exit
			}
			
			if(event.getKeyCode()==KeyEvent.VK_UP)  //the up key is pressed
			{
				mover.myShip.accelerate();
			}
			if(event.getKeyCode()==KeyEvent.VK_DOWN)
			{
				mover.myShip.decelerate();
			}
			
			//the bellow code turns the ship
			if(event.getKeyCode()==KeyEvent.VK_LEFT)
			{
				mover.myShip.changeOrientationLeft();
			}
			if(event.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				mover.myShip.changeOrientationRight();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
	}//END handler
	
	
	/**
	 * updates the position of the back ground point(the point the background stars are drawn from) based on the velocity*/
	private void move()
	{
		Double yMoved,xMoved;
		
		//backGroundPoint.y = backGroundPoint.y + pixels moved
		//pixels moved = myship.getVelocity * cos (myShip.shipAngle.degreeY)
		if(mover.myShip.shipAngle.isxPositive())
		{
			xMoved = mover.myShip.getVelocity()*Math.cos(Math.toRadians(mover.myShip.shipAngle.getDegreeX()));
			backGroundPoint.x = (int) (backGroundPoint.x + Math.round(xMoved));
			
			mover.myShip.updateMapPoint((int) -Math.round(xMoved));   ///keeps track of where in the world my ship is. 
		}
		else
		{
			xMoved = mover.myShip.getVelocity()*Math.cos(Math.toRadians(mover.myShip.shipAngle.getDegreeX()));
			backGroundPoint.x = (int) (backGroundPoint.x - Math.round(xMoved));
			mover.myShip.updateMapPoint((int) Math.round(xMoved));   ///keeps track of where in the world my ship is. 
		}
		
		if(mover.myShip.shipAngle.isyPositive())
		{
			yMoved = mover.myShip.getVelocity()*Math.cos(Math.toRadians(mover.myShip.shipAngle.getDegreeY()));
			backGroundPoint.y = (int) (backGroundPoint.y + Math.round( yMoved));  ///round it and cast it to an int. 
			mover.myShip.updateMapPoint((int) -Math.round(yMoved));   ///keeps track of where in the world my ship is. 
		}
		else
		{
			yMoved = mover.myShip.getVelocity()*Math.cos(Math.toRadians(mover.myShip.shipAngle.getDegreeY()));
			backGroundPoint.y = (int) (backGroundPoint.y - Math.round( yMoved));  ///round it and cast it to an int. 
			mover.myShip.updateMapPoint((int) Math.round(yMoved));   ///keeps track of where in the world my ship is. 
		}
		
		mover.moveAstroid(xMoved,yMoved,mover.myShip.shipAngle.isxPositive(),mover.myShip.shipAngle.isyPositive());		
				
	}//end of move()
	
	/**
	 * sets text to show at beggining of the game. Init was getting too cluttered. */
	private void mission()
	{
		mission1 = "Captain, we need you to sweep the astroid field as quickly as possible";
		mission2 = "to find the crashed USS Explorer. If there are any survivors they won't";
		mission3 = "have much life support left.";
		mission4 = "And watch out for those astroids!!!!!!!";
	}
	
	
	
}//END game engine class
