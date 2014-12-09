import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/**class for the crashed ship.*/
public class DShip 
{
	Image shipImage;
	int shipWidth,shipHeight;
	Point shipPoint;
	
	/**constructor
	 * @param Point at which to draw crashed ship*/
	public DShip(Point tempPoint)
	{
		ImageIcon ii = new ImageIcon(getClass().getResource("crash.png"));
		shipImage=ii.getImage();
		shipWidth=ii.getImage().getWidth(null);
		shipHeight=ii.getImage().getHeight(null);
		
		shipPoint = tempPoint;
	}

}
