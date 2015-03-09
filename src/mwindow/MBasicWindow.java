package mwindow;

import javax.swing.*;
import java.awt.*;

public class MBasicWindow extends JFrame
{    
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
        setSize(size);
        
        //Centers the window
        setLocationRelativeTo(null);        
        setVisible(true); 
    }
    
    public static void main(String args[])
    {
        MBasicWindow mWnd = new MBasicWindow("Test Title", new Dimension(600, 600));
    }
}
