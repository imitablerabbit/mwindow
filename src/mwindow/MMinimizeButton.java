package mwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

class MMinimizeButton extends JPanel
{
    boolean hasEntered = false;

    //Color variables
    Color backgroundColor = new Color(0, 130, 180);
    Color backgroundHoverColor = new Color(255, 255, 255, 50);
    
    MMinimizeButton(final JFrame parent)
    {
        setPreferredSize(new Dimension(20, 20));
        
        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    parent.setState(JFrame.ICONIFIED);
                }

                public void mouseEntered(MouseEvent e)
                {
                    hasEntered = true;
                    repaint();
                }

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
        
        //Draw the minus sign
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.drawLine(5, 10, 15, 10);
    }
}