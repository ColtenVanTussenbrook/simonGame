/**
 * Colten Van Tussenbrook
 * Chad Mano CS 2410 Java 
 * Simonish Supreme
 */

package components;

import java.awt.Color;
import javax.swing.JPanel;
import game.SimonishSupreme;

/**
 * class to create and manipulate the color panels
 * @author ColtenTuss
 *
 */

public class ColorPanelOrig extends JPanel {
	private Color color;
	
	/**
	 * resets colors
	 * @param color
	 */
	public ColorPanelOrig(Color color) 
	{
		this.color = color.darker();
		reset();
	}
	
	public void pressed() //method for pressed - makes it brighter
	{
		setBackground(getBackground().brighter());
		update(getGraphics());
	}
	public void released() //method for released - makes it darker
	{
		setBackground(getBackground().darker());
		update(getGraphics());
	}
	public void reset() //resets the color panel
	{
		setBackground(this.color);		
		update(getGraphics());		
	}
	public void newColors() 
	{
		setBackground(SimonishSupreme.color1);		
	}
	public void changeBackground() //changes background color
	{
		setBackground(this.getBackground());
		this.color = getBackground().darker();
	}
}

