
/**The angles for the ship have been made based off of x and y axis as if the ship where at point (0,0) */
public class Angle 
{
	private int degreeX;
	private boolean xPositive;
	
	private int degreeY;
	private boolean yPositive;
	
	//1,22,45,68,90,112,135,158,180,202,225,248,270,292,315,338
	
	/**
	 * constructor
	 * @param int the angle in degrees.*/
	public Angle(int angle1)
	{
		updateDegreeX(angle1);
		updateDegreeY(angle1);
		
	}
	
	
	/**updates the degree with respect to the x axis (pointing left on the x-axis being 0 degrees and pointing right on the x-axis being 180 degrees
	 * IT also updates the orientation (above  x axis xpositive=true and below x axis xpositive=false
	 * @param int in degrees*/
	public void updateDegreeX(int angle)
	{
		if(angle >=0 && angle <=180)
		{
			degreeX = Math.abs(90-angle);
			xPositive = false;
		}
		else if(angle >=202 && angle <=338)
		{
			degreeX = Math.abs((angle-180)-90);
			xPositive = true;
		}
		else
		{
			System.out.println("\n out of bounds angle X, class Angle, line 33\n");
		}
	}
	
	/**updates the degree with respect to the Y axis (pointing up on the y-axis being 0 degrees and pointing down on the y-axis being 180 degrees
	 * It also updates the orientation left of the axis ypositive=false and to the right of the y axis ypositive=treu
	 * @param int in degrees*/
	public void updateDegreeY(int angle)
	{
		if(angle >= 0 && angle <= 90)
		{
			degreeY = Math.abs(angle);
			yPositive = true;
		}
		else if(angle >=112 && angle <= 270)
		{
			degreeY = Math.abs(angle -180);
			yPositive = false;
		}
		else if(angle >= 292 && angle <=338)
		{
			degreeY = Math.abs((angle-180)-180);
			yPositive = true;
		}
		else
		{
			System.out.println("\n out of bounds angle Y, class Angle, line 52\n");
		}
	}
	
	

	
/**
 * @return int degrees respective to x*/
	public int getDegreeX() {
		return degreeX;
	}

	/**
	 * @return boolean  true if ship points above x axis*/
	public boolean isxPositive() {
		return xPositive;
	}
	/**
	 * @return int degrees respective to y*/
	public int getDegreeY() {
		return degreeY;
	}

	/**
	 * @return boolean is true if it is pointing right of y axis*/
	public boolean isyPositive() {
		return yPositive;
	}
	
	
	

}
