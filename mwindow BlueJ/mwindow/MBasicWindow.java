package mwindow;
import javax.swing.*;
import java.awt.*;

public class MBasicWindow extends JFrame
{    
    public static void main(String args[])
    {
        MBasicWindow mWnd = new MBasicWindow("Test Title", new Dimension(600, 600));
    }

    public MBasicWindow()
    {
        this("Window", new Dimension(500, 300));
    }
    
    MBasicWindow(String title)
    {
        this(title, new Dimension(500, 300));
    }
    
    MBasicWindow(Dimension size)
    {
        this("Window", size);
    }
    
    public MBasicWindow(String title, Dimension size)
    {
        //Names the window
        super(title);
        
        //Sets the size
        setSize(size);
        
        //Centers the window
        setLocationRelativeTo(null);
        
        //Shows the window on screen
        setVisible(true); 
    }
}
