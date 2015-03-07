package mwindow;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

class MMinimizeButton extends JPanel
{
    boolean hasEntered = false;
    
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
        
        //Paint the background
        if(hasEntered)
        {
            setBackground(new Color(0, 160, 200));
        }
        else
        {
            setBackground(new Color(0, 130, 180));
        }
        
        //Draw the minus sign
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.drawLine(5, 10, 15, 10);
    }
}