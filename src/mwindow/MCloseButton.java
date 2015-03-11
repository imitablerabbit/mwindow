package mwindow;

import javax.swing.*;

import com.sun.glass.events.WindowEvent;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class MCloseButton extends JPanel
{
    //Has the mouse entered the button
    boolean hasEntered = false;

    //Color variables
    Color backgroundColor = MColor.mCloseButtonColor;
    Color backgroundHoverColor = new Color(255, 255, 255, 50);
    
    public MCloseButton(JFrame parent)
    {
        setPreferredSize(new Dimension(20, 20));

        //Check for mouse Events
        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    parent.dispose();
                }

                @Override
                public void mouseEntered(MouseEvent e)
                {
                    hasEntered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    hasEntered = false;
                    repaint();
                }
            });
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //Set the background color
        setBackground(backgroundColor);
        
        if(hasEntered)
        {
            g.setColor(backgroundHoverColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        //Draw the cross
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(MColor.foregroundColor);
        g2.drawLine(5, 5, 15, 15);
        g2.drawLine(15, 5, 5, 15);
    }
}