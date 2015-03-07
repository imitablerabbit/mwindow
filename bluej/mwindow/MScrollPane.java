package mwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.*;

public class MScrollPane extends JScrollPane
{
    MScrollPane(Component c)
    {
        super(c);
        getVerticalScrollBar().setUI(new MScrollUI());
    }

    public class MScrollUI extends BasicScrollBarUI
    {
        MScrollUI()
        {            
            super();
        }

        @Override
        public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.LIGHT_GRAY);
            g2.fill(trackBounds);
        }
        
        @Override
        public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.DARK_GRAY);
            g2.fill(thumbBounds);
        }
        
        @Override
        public JButton createIncreaseButton(int orientation)
        {
            return new MButton();
        }
        
        public JButton createDecreaseButton(int orientation)
        {
            return new MButton();
        }
    }    
    
    public static void main(String[] args)
    {
        MDragWindow wnd = new MDragWindow();

        wnd.revalidate();
    }
}