package mwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MDragPanel extends JPanel
{
    private Point initialClick;
    private JFrame parent;

    public MDragPanel(final JFrame parent)
    {
        super();        
        setLayout(new BorderLayout());

        //Gets the parent JFrame
        this.parent = parent;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(parent.getWidth(), 30));

        //Gets the origin click
        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    initialClick = e.getPoint();
                    getComponentAt(initialClick);
                }
            });

        //Translates the window with the movement of the mouse
        addMouseMotionListener(new MouseMotionAdapter()
            {
                @Override
                public void mouseDragged(MouseEvent e)
                {
                    int thisX = parent.getLocation().x;
                    int thisY = parent.getLocation().y;

                    int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                    int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    parent.setLocation(X, Y);
                }
            });
    }
}