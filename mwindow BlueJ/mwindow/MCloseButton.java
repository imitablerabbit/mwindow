package mwindow;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

class MCloseButton extends JPanel
{
    //Has the mouse entered the button
    boolean hasEntered = false;

    MCloseButton()
    {
        //Set the size
        setPreferredSize(new Dimension(20, 20));

        //Set background
        setBackground(new Color(173, 0, 0));

        //Check for mouse Events
        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    System.exit(1);
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

        //Check if the mouse has entered
        if(hasEntered)
        {
            setBackground(new Color(210, 0, 0));
        }
        else
        {            
            setBackground(new Color(173, 0, 0));
        }

        //Draw the cross
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.drawLine(5, 5, 15, 15);
        g2.drawLine(15, 5, 5, 15);
    }
}