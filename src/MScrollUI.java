package mwindow;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.*;

public class MScrollUI extends BasicScrollBarUI
{
    public MScrollUI()
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

    @Override
    public JButton createDecreaseButton(int orientation)
    {
        return new MButton();
    }
}    