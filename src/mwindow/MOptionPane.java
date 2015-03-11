package mwindow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MOptionPane extends MDragWindow
{	
	//CONSTRUCTORS
	//Small window with default title and not message
	public MOptionPane()
	{
		this("Dialog Box", new Dimension(300, 100), "This is the default MOptionPane.\nSee the constructors for more options");
	}
	
	//Small window with custom title
	public MOptionPane(String title)
	{
		this(title, new Dimension(300, 100), "Warning");
	}
	
	public MOptionPane(String title, String message)
	{
		this(title, new Dimension(300, 100), message);
	}
	
	//Default window with custom title and size, contains a centered message
	public MOptionPane(String title, Dimension size, String message)
	{
		super(title, size);
		MPanel pane = new MPanel();
		pane.setBackground(MColor.mOptionPaneBackgroundColor);
		add(pane);
		
		//Create message label
		JTextArea text = new JTextArea(message);
		text.setBackground(MColor.mOptionPaneBackgroundColor);
		text.setEditable(false);
				
		//Calculate dimensions for layout
		int textHeight = text.getPreferredSize().height;		
		int paneHeight = getHeight() - getDragBarHeight();
		
		pane.setLayout(new FlowLayout(FlowLayout.CENTER, 1, (paneHeight - textHeight) / 2));
		
		pane.add(text);
	}
	
	//Create a message dialog box
	public static void createWarningDialog(String title, String message, Dimension size)
	{
		//Create the window
		MDragWindow window = new MDragWindow(title, size);
		MPanel pane = new MPanel();
		pane.setBackground(MColor.mOptionPane_WarningBackgroundColor);
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
		
		//Create the text
		JTextArea text = new JTextArea(message);
		text.setEditable(false);
		text.setBackground(MColor.mOptionPane_WarningBackgroundColor);

		//Align the symbol and text		
		int symbolHeight = image.getHeight();		
		int textHeight = text.getPreferredSize().height;		
		int paneHeight = window.getHeight() - window.getDragBarHeight();
		
		//Find the tallest component to centre each other component vertically
		int tallestCompHeight;		
		if(symbolHeight > textHeight)
		{
			tallestCompHeight = symbolHeight;
		}
		else
		{
			tallestCompHeight = textHeight;
		}
		
		//Apply the layout scheme
		pane.setLayout(new FlowLayout(FlowLayout.LEADING, 10,
						(paneHeight - (tallestCompHeight)) / 2));				
					
		//Add the image
		pane.add(symbol);
		pane.add(text);				
	}
	
	public static void main(String[] args) 
	{		
		new MOptionPane();
		MOptionPane.createWarningDialog("Message Box", "This is a basic message dialog box. \nIt will accommodate as much text \nas possible", new Dimension(300, 100));
	}
}
