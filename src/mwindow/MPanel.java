package mwindow;

import javax.swing.*;
import java.awt.*;

public class MPanel extends JPanel
{
    public MPanel()
    {
        super(); 
        setBackground(MColor.backgroundColor);
    }
    
    @Override
    public Component add(Component c)
    {
    	super.add(c);
    	revalidate();
    	return this;
    }
}