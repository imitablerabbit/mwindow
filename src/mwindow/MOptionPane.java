package mwindow;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MOptionPane extends MDragWindow
{
	//CONSTRUCTORS
	//Small window with default title and not message
	public MOptionPane()
	{
		this("Dialog Box", new Dimension(300, 100));
	}
	
	//Small window with custom title
	public MOptionPane(String title)
	{
		this(title, new Dimension(300, 100));
	}
	
	//Window with custom title and size
	public MOptionPane(String title, Dimension size)
	{
		super(title, size);
	}
	
	//Create a message dialog box
	public static void createMessageDialog(String title, String message, Dimension size)
	{
		//Create the window
		MOptionPane window = new MOptionPane();
		MPanel pane = new MPanel();
		pane.setLayout(null);
		window.add(pane);
		
		//Create the warning image
		BufferedImage image = null;
		try 
		{
			image = ImageIO.read(new File("resources/warning.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Create JLabel for image
		JLabel symbol = new JLabel();
		symbol.setIcon(new ImageIcon(image));

		//Align the symbol
		int symbolWidth = image.getWidth();
		int symbolHeight = image.getHeight();
		int paneWidth = window.getWidth();
		int paneHeight = window.getHeight() - window.getDragBarHeight();
				
		//Set the position of the image
		symbol.setBounds((paneWidth - symbolWidth) / 16, ((paneHeight - symbolHeight) / 2), symbolWidth, symbolHeight);
		
		//Add the image
		pane.add(symbol);
		window.revalidate();		
	}
	
	public static void main(String[] args) 
	{
		MOptionPane.createMessageDialog("Title", "Message here", new Dimension(300, 100));
	}
}
