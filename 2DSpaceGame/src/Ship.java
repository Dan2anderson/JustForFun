import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;


public class Ship 
{
	Image currentShip;
	int currentOrientation;
	int shipWidth;
	int shipHeight;
	Point currentMapPoint = new Point();
	private int velocity;
//	private int turningMomentum;
	public boolean headingU,headingD,headingL,headingR;
	
	int[] orientationIndex={0,22,45,68,90,112,135,158,180,202,225,248,270,292,315,338};
	Image[] shipOrientations =new Image[16];
	
	Angle shipAngle;
	
	
	/**constructor
	 * */
	public Ship()
	{
		for(int i =0; i<16;i++)
		{
			String s = Integer.toString(orientationIndex[i]);
			StringBuilder sb = new StringBuilder();
			sb.append("ship");
			sb.append(s);
			sb.append(".png");
			ImageIcon ii = new ImageIcon(getClass().getResource(sb.toString()));
			currentMapPoint.x=0;
			currentMapPoint.y=0;
			headingU=true;
			shipOrientations[i]=ii.getImage();
		}
		
		currentShip = shipOrientations[0];
		shipWidth= shipOrientations[0].getWidth(null);
		shipHeight = shipOrientations[0].getHeight(null);
		
		currentOrientation = 0;
		shipAngle = new Angle(orientationIndex[currentOrientation]);
		velocity=0;
//		turningMomentum=0;
	}
	
	/**
	 * @return int the ship velocity*/
	public int getVelocity()
	{
		return velocity;
	}

	/**
	 * @return Image of the ship*/
	public Image getImage()
	{
		return currentShip;
	}
	
	/**increases the velocity speeding up the movement of the ship
	 * */
	public void accelerate()
	{
		if(velocity>60)
		{
			return;
		}
		else if(velocity==0)
		{
			velocity=2;
			return;
		}
		velocity=velocity+1;
	}
	
	/**decreases the velocity slowing down the movement of the ship
	 * */
	public void decelerate()
	{
		if(velocity<-5)
		{
			return;
		}
		else if(velocity ==2)
		{
			velocity=0;
			return;
		}
		velocity=velocity-1;
	}
	
	/**this changes the orientation of the ship 22degrees left.  turn left
	 * */
	public void changeOrientationLeft()
	{
//		turningMomentum = turningMomentum +1;
//		
//		if(turningMomentum >=(velocity/2))    
//		{ 
			//change orientation
			if(currentOrientation==0)
			{
				currentOrientation =15;
//				turningMomentum =0;
			}
			else
			{
				currentOrientation = currentOrientation-1;
//				turningMomentum=0;
				
			}   
			updateShip(currentOrientation); //update image and h/w
//		}
		
	}//END turn left
	
	/**this changes the orientation of the ship 22degrees right.  turn right
	 * */
	public void changeOrientationRight()
	{
//		turningMomentum =turningMomentum +1;
//		if(turningMomentum >(velocity/2))
//		{
			if(currentOrientation ==15)
			{
				currentOrientation = 0;
//				turningMomentum=0;
			}
			else
			{
				currentOrientation = currentOrientation +1;
//				turningMomentum=0;
			}
			updateShip(currentOrientation);//update image and h/w
//		}
	}
	
	/**updates the image of the ship for the correct orientation.  as well as the image width and height.
	 * @pram int the current orientation of the ship.*/
	private void updateShip(int curentOr )
	{
		currentShip = shipOrientations[curentOr];
		shipWidth=currentShip.getWidth(null);
		shipHeight = currentShip.getHeight(null);
		shipAngle.updateDegreeX(orientationIndex[curentOr]);
		shipAngle.updateDegreeY(orientationIndex[curentOr]);
		
		if(currentOrientation >=0 && currentOrientation <=3 )
		{
			headingU=true;
		}
		else
		{
			headingU=false;
		}
		if(currentOrientation >=13 && currentOrientation <=15)
		{
			headingU=true;
		}
		else
		{
			headingU=false;
		}
		if(currentOrientation >=5 && currentOrientation <=11)
		{
			headingD=true;
		}
		else
		{
			headingD=false;
		}
		if(currentOrientation >=1 && currentOrientation <=7)
		{
			headingR=true;
		}
		else
		{
			headingR=false;
		}
		if(currentOrientation >=8 && currentOrientation <=15)
		{
			headingL=true;
		}
		else
		{
			headingL=false;
		}
		
	}
	
	/**This method overrides the accelorate and deccelorate methods
	 * @param int the number you want the velocity to be*/
	public void setVelocity(int num)
	{
		velocity=num;
	}
	
	/**
	 * This method updates the point of where the ship is.  it is currently not in use but I plan on using it when I expand the game latter. 
	 * @param int */
	public void updateMapPoint(int n)
	{
		currentMapPoint.x=currentMapPoint.x+n;
		
	}
	
	
}
