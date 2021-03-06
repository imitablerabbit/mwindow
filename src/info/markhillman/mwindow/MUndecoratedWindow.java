package info.markhillman.mwindow;

import javax.swing.*;
import java.awt.*;

public class MUndecoratedWindow extends JFrame
{
    public MUndecoratedWindow()
    {
        this("Window", new Dimension(500, 300));
    }
    
    public MUndecoratedWindow(String title)
    {
        this(title, new Dimension(500, 300));
    }
    
    MUndecoratedWindow(Dimension size)
    {
        this("Window", size);
    }
    
    public MUndecoratedWindow(String title, Dimension size)
    {
        //Gives the window a title
        super(title);
        setSize(size); 
        
        //Centers the window
        setLocationRelativeTo(null);
        
        //Removes the decorations from the window
        setUndecorated(true);
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        MUndecoratedWindow mWnd = new MUndecoratedWindow();
    }
}