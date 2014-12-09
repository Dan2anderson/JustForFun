import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;



public class ScreenManager 
{
	//video card 
	private GraphicsDevice videoCard;
	
	/**constructor for the ScreenManager class*/
	public ScreenManager()
	{
		//get the screen Device from the graphics environment and assign it to the videoCard.
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		videoCard = env.getDefaultScreenDevice();
	}

	/**
	 * gets an array of display modes from hardwar (video card I believe)
	 * @return array of DisplayMode*/
	public DisplayMode[] getCompatibleDisplayModes()
	{
		//gets an array of supported display modes (hXw refreshrate)
		return videoCard.getDisplayModes();
	}
	
	/**
	 * takes an array of preferred display modes compares with display modes on hardware 
	 * @parram array DisplayMode
	 * @return DisplayMode*/
	public DisplayMode  findBestCompatibleDisplayMode(DisplayMode modes[])
	{
		DisplayMode[] nativeModes = getCompatibleDisplayModes();
		for(int i=0;i<modes.length;i++)
		{
			for(int j=0;j<nativeModes.length;j++)
			{
				if(modes[i].equals(nativeModes[j]))
				{  
					return nativeModes[j];
				}
			}
		}
		return null;
	}
	
	/**The method creates the JFrame and makes it take the entire screen
	 * @Param DisplayMode*/
	public void setFullScreen(DisplayMode mode1)
	{
		JFrame gameWindow = new JFrame();
		gameWindow.setUndecorated(true);
		gameWindow.setResizable(false);
		gameWindow.setIgnoreRepaint(true);    ////ignores commands from the OX to repaint screen
		
		videoCard.setFullScreenWindow(gameWindow);   //make the JFrame full screen
		
		
		//Checks that there is a display mode and that we can actually change the display mode
		if(mode1!=null && videoCard.isDisplayChangeSupported())
		{
			try
			{
				videoCard.setDisplayMode(mode1);    ///sets the height width, bit depth and refresh rate.
			}
			catch(Exception excep)
			{
				System.out.println("Exception 1:\nCould not set the display Mode");
			}
		}
		
		gameWindow.createBufferStrategy(3);     //this lets the system draw images off screen before puttin them on screen.  Reduces clipping.
		
	} //END setFullScreen
	
	
	/**method to exit the game.  closes the full screen window.*/
	public void exitGame()
	{
		Window win = videoCard.getFullScreenWindow();
		if(win!=null)
		{
			win.dispose();
		}
		videoCard.setFullScreenWindow(null);
	}
	
	/**
	 * gets buffer strategy from the window, gets the graphics context from buffer Strategy.  returns graphics context
	 * @return Graphics2D (Graphics context) */
	public Graphics2D getGraphics()
	{
		Window win = videoCard.getFullScreenWindow();
		if(win!=null)
		{
			BufferStrategy strategy= win.getBufferStrategy();
			return (Graphics2D) strategy.getDrawGraphics();  
			///context is a class that has "state" information  in this case  java.awt.Graphics or java.awt.Graphics2D classes
		}
		else
		{
			return null;
		}
	}
	
	
	/**THe update method will update the graphics on the window. It makes the bufferStrategy show the next images it has drawn.
	 * */
	public void updat()
	{
		Window win = videoCard.getFullScreenWindow();
		if(win!=null)
		{
			BufferStrategy strategy = win.getBufferStrategy();
			strategy.show();
		}
	}
	
	
	/**returns the window object of the full screen window for the game
	 * @return Window*/
	public Window getFullScreenWindow()
	{
		return videoCard.getFullScreenWindow();
	}
	/**gets the width
	 * @return int of the width*/
	public int getWidth()
	{
		Window win = videoCard.getFullScreenWindow();
		if(win!=null)
		{
		return win.getWidth();
		}
		else
		{
		return 0;
		}
	}
	/**gets the height of the full screen window
	 *@Return int height of window*/
	public int getHeight()
	{
		Window win = videoCard.getFullScreenWindow();
		if(win!=null)
		{
			return win.getHeight();
		}
		else
		{
			return 0;
		}
	}
	
	
}
