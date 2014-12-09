import java.awt.Point;
import java.util.ArrayList;

/**
 * THis class contains (and initializes) all the game objects (ships, astroids, etc. .) and handles most of their movement.  with the exception of the cosine calculations for the speed of the ship those are done in the move() method int he game engine.
 * the move() method in the GameEngine calls the other methods in Mover to update the numbers for where everything should be on screen.  */
public class Mover 
{
	public ArrayList<AstroidField> myAstFields =new ArrayList<AstroidField>();
//	public ArrayList<Astroid> myAstroids = new ArrayList<Astroid>();
	Ship myShip;
	DShip crashedShip;
	boolean startUp;
	boolean shipCrashed=false;
	
	
	/**
	 * constructor for the Mover class*/
	public Mover()
	{
		startUp=true;
		myShip = new Ship();
		int crShipSegment = (int) Math.round(Math.random()*8);
		if(crShipSegment==4)
		{
			crShipSegment =8;
		}
//		Point p = new Point();
//		p.x=500;
//		p.y=200;
		crashedShip = new DShip(createStartingPoints(crShipSegment));  
		shipCrashed=true;
		for(int j=0;j<9;j++)//These nested loops call the methods to create astroids and put them in the astroid field. 
		{
			AstroidField myAstroidField = new AstroidField();
			for(int i=0;i<20;i++)
			{
				Astroid myAstroid = new Astroid(createStartingPoints(j)); 
				myAstroidField.myAstroids.add(myAstroid);
			}
			myAstFields.add(myAstroidField);
		}
		startUp=false;
	}
	
	
	/**
	 * This method updates the point at which the astroid is drawn.  
	 * @param Double how far moved along x
	 * @param Double how fare moved along y
	 * @param boolean is x orientation positive
	 * @param boolean is y orientation positive*/
	public void moveAstroid(Double xMoved,Double yMoved, Boolean xPositive,Boolean yPositive)
	{
		//nested for loops move the astroid fields
		for(int i=0;i<9;i++)
		{
			for(Astroid el: myAstFields.get(i).myAstroids)
			{
				if(xPositive)
				{
					el.astroidPoint.x= el.astroidPoint.x +(int)Math.round(xMoved*2);
				}
				else
				{
					el.astroidPoint.x= el.astroidPoint.x -(int)Math.round(xMoved*2);
				}
				if(yPositive)
				{
					el.astroidPoint.y= el.astroidPoint.y +(int)Math.round(yMoved*2);
				}
				else
				{
					el.astroidPoint.y= el.astroidPoint.y -(int)Math.round(yMoved*2);
				}
			}//end nested for loop
		}//end for loop
		
		//this moves the crashed ship.
		if(xPositive)
		{
			crashedShip.shipPoint.x=crashedShip.shipPoint.x +(int)Math.round(xMoved*2);
		}
		else
		{
			crashedShip.shipPoint.x=crashedShip.shipPoint.x -(int)Math.round(xMoved*2);
		}
		if(yPositive)
		{
			crashedShip.shipPoint.y=crashedShip.shipPoint.y +(int)Math.round(yMoved*2);
		}
		else
		{
			crashedShip.shipPoint.y=crashedShip.shipPoint.y -(int)Math.round(yMoved*2);
		}
	}//end move Astroid(and crashed ship method)
	
	/**calls point creation method "createPoints()" and checks that there is not collision with myShip or crashedShip using "collision()"
	 * This method takes a segment which is a number between 0 and 8 that represents what area it will be drawn in each segment is the size of the computer screen.
	 * When the game starts segments 0,1,2,3 are off screen 4 is the screen, and 5,6,7,8 are off screen. 
	 * @param int segment in which the thing is found (0-8)
	 * @return Point*/
	private Point createStartingPoints(int segment)  
	{
		Point wanabePoint = createPoints(segment);
		if(collision(wanabePoint))
		{
			return createStartingPoints(segment);
		}
		return wanabePoint;
	}
	
	
	/**This method uses a MAth.ranodm() to create a random point in each segment.
	 * @param int the segment of the astroid field in which to generate points.
	 * @return Point*/
	private Point createPoints(int segment)
	{
		Point point=new Point();
		ScreenManager tempScrMan = new ScreenManager();
		Double randNumX =Math.random();
		Double randNumY =Math.random();
		if(segment==4)
		{
			point.x= (int) Math.round(randNumX*tempScrMan.getWidth());
			point.y= (int) Math.round(randNumY*tempScrMan.getHeight());
		}
		else if(segment==0)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth())-tempScrMan.getWidth());
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight())-tempScrMan.getHeight());
		}
		else if(segment==1)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth()));
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight())-tempScrMan.getHeight());
		}
		else if(segment==2)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth())+tempScrMan.getWidth());
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight())-tempScrMan.getHeight());
		}
		else if(segment==3)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth())-tempScrMan.getWidth());
			point.y= (int) Math.round(randNumY*tempScrMan.getHeight());
		}
		else if(segment==5)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth())+tempScrMan.getWidth());
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight()));
		}
		else if(segment==6)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth())-tempScrMan.getWidth());
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight())+tempScrMan.getHeight());
		}
		else if(segment==7)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth()));
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight())+tempScrMan.getHeight());
		}
		else if(segment==8)
		{
			point.x= (int) Math.round((randNumX*tempScrMan.getWidth())+tempScrMan.getWidth());
			point.y= (int) Math.round((randNumY*tempScrMan.getHeight())+tempScrMan.getHeight());
		}
		
		return point;
	}
	
	/**
	 * this method tests if there has been a collision.  It uses a square area overlap test at the beginning to make sure the ship doesn't over lap with any astroids. 
	 * after initializing it uses a circle radius to see if the ship has colided with anything.
	 * @param Point
	 * @return Boolean true if there is a collision */
	private Boolean collision(Point possiblePoint)
	{
		ScreenManager scrnM = new ScreenManager();
		
		int scrWidth = scrnM.getWidth();
		int scrHeight = scrnM.getHeight();
		
		if(!startUp)//This block of code uses a circle to test for collision in game 
		{
			//astroid
			int astW = 127;
			int astH = 80;
			
			Point astCenter = new Point();
			astCenter.x= possiblePoint.x+(astW/2);
			astCenter.y= possiblePoint.y +(astH/2);
			
			int radiusAst = astH/2;
			
			Point astLeft= new Point();
			astLeft.x=possiblePoint.x+21;
			astLeft.y=possiblePoint.y+43;
			int leftRadius = 6;
			
			Point astRight= new Point();
			astRight.x=possiblePoint.x+90;
			astRight.y=possiblePoint.y+49;
			int rightRadius =24;
			
			
			
			//ship
			//( width/2)-(mover.myShip.shipWidth/2), (height/2)-(mover.myShip.shipHeight/2),mover.myShip.shipWidth,mover.myShip.shipHeight,null); 
			
			Point shCenter = new Point();
			shCenter.x= scrWidth/2;
			shCenter.y = scrHeight/2;
			int radiusShip =(int) Math.round((myShip.shipWidth/2)*0.70);
			
			
			if(shCenter.distance(astCenter)<= (radiusAst+radiusShip)||shCenter.distance(astLeft)<=(radiusShip+leftRadius)||shCenter.distance(astRight)<=(radiusShip+rightRadius))
			{
				return true;
			}
			
		}
		else //this block of code uses the square outline of the image size when game is initialized to make sure there is no over lap with astroids and ship on startup. 
		{ 
				//@@@@square check
			int x1 = (int)Math.round((scrWidth/2)-(myShip.shipWidth/3)*2);
			int x2 = (int)Math.round((scrWidth/2)+(myShip.shipWidth/3)*2);
			int y1 = (int)Math.round((scrHeight/2)-(myShip.shipHeight/3)*2);
			int y2 = (int)Math.round((scrHeight/2)+(myShip.shipHeight/3)*2);
			
			if(possiblePoint.x >=x1 && possiblePoint.x <=x2 && possiblePoint.y>=y1 && possiblePoint.y <= y2)
			{
				return true;
			}
			else if((possiblePoint.x+127) >=x1 && (possiblePoint.x+127) <=x2 && possiblePoint.y>=y1 && possiblePoint.y <= y2)
			{
				return true;
			}
			else if((possiblePoint.x+127) >=x1 && (possiblePoint.x+127) <=x2 && (possiblePoint.y+88)>=y1 && (possiblePoint.y+88) <= y2)
			{
				return true;
			}
			else if(possiblePoint.x >=x1 && possiblePoint.x <=x2 && (possiblePoint.y+88)>=y1 && (possiblePoint.y+88) <= y2)
			{
				return true;
			}
			
			//check if crashed ship has overlaping astroids
			if(shipCrashed)
			{
				if(crashedShip.shipPoint.x >= possiblePoint.x  && crashedShip.shipPoint.x<=(possiblePoint.x+127) && crashedShip.shipPoint.y>=possiblePoint.y &&  crashedShip.shipPoint.y<=(possiblePoint.y+88))
				{System.out.println("TL true");
					return true;
				}
				else if((crashedShip.shipPoint.x+crashedShip.shipWidth) >= possiblePoint.x  && (crashedShip.shipPoint.x+crashedShip.shipWidth)<=(possiblePoint.x+127) && crashedShip.shipPoint.y>=possiblePoint.y &&  crashedShip.shipPoint.y<=(possiblePoint.y+88))
				{System.out.println("TR true");
					return true;
				}
				else if((crashedShip.shipPoint.x+crashedShip.shipWidth) >= possiblePoint.x  && (crashedShip.shipPoint.x+crashedShip.shipWidth)<=(possiblePoint.x+127) && (crashedShip.shipPoint.y+crashedShip.shipHeight)>=possiblePoint.y &&  crashedShip.shipPoint.y<=(possiblePoint.y+88))
				{System.out.println("BR true");
					return true;
				}
				else if(crashedShip.shipPoint.x >= possiblePoint.x  && crashedShip.shipPoint.x<=(possiblePoint.x+127) && (crashedShip.shipPoint.y+crashedShip.shipHeight)>=possiblePoint.y &&  crashedShip.shipPoint.y<=(possiblePoint.y+88))
				{System.out.println("BL true");
					return true;
				}
			}
			
			return false;
		}
		return false;
	}//end collision()
	
	
	/**
	 * This method is called by the game loop to check if the ship has collided with any astroids this method calls "collision()" to do so
	 * @param Boolean*/
	public Boolean checkCollision()
	{
		for(int i=0;i<9;i++)
		{
			for(Astroid el: myAstFields.get(i).myAstroids)
			{
				if( collision(el.astroidPoint))
				{
					return true;
				}
			}//end nested loop
		}//end for loop
		return false;
	}
	
	
	/**THis method checks if you have found the crashed ship.
	 * @return Boolean*/
	public Boolean win()
	{
		ScreenManager scrnM = new ScreenManager();
		
		int scrWidth = scrnM.getWidth();
		int scrHeight = scrnM.getHeight();
		
		Point crashedShipCenter = new Point();
		crashedShipCenter.x = crashedShip.shipPoint.x+(crashedShip.shipWidth/2);
		crashedShipCenter.y = crashedShip.shipPoint.y+(crashedShip.shipHeight/2);
		int crashedRadius =(int)Math.round((crashedShip.shipHeight/2)*0.85);
		
		Point shCenter = new Point();
		shCenter.x= scrWidth/2;
		shCenter.y = scrHeight/2;
		int radiusShip =(int) Math.round((myShip.shipWidth/2)*0.70);
		
		if(shCenter.distance(crashedShipCenter)<= (crashedRadius+radiusShip))
		{
			return true;
		}
		return false;
	}
	
}//end of class Mover

