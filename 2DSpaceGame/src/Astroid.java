import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;


public class Astroid 
{
	public Image astroidImage;
	public int velocity;
	public int astHeight, astWidth;
	public Point astroidPoint;
	
	/**constructor for the Astroid class
	 * @param Point at which the astroid should be placed in game */
	public Astroid(Point point)
	{
		ImageIcon ii= new ImageIcon(getClass().getResource("astroid.png"));
		astroidImage = ii.getImage();
		velocity=0;
		astWidth = ii.getImage().getWidth(null);
		astHeight =ii.getImage().getHeight(null);
		astroidPoint = new Point();
		astroidPoint.x=point.x;
		astroidPoint.y=point.y;
	}
	

}
