package mwindow;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class MButton extends JButton
{
    //The text
    private JLabel label;

    //Is mouse hovering over button
    private boolean hasEntered = false;

    //Drawing dimensions
    private int width;
    private int height;
    private int wOffset = 30;
    private int hOffset = 25;
    
    //Color
    private Color backgroundColor = Color.DARK_GRAY;
    private Color backgroundColorHover = new Color(80, 80, 80);

    //Font for Label
    private Font labelFont = new Font("Dialog", Font.BOLD, 12);

    //The EventListeners
    protected EventListenerList mActionListeners = new EventListenerList();

    public MButton()
    {
        this("");
    }

    //Creates the Checkbox
    public MButton(String text)
    {
        super();        
        setBorderPainted(false);

        //Add the Label
        label = new JLabel(text);
        label.setForeground(UIManager.getColor("Panel.background"));
        label.setFont(labelFont);
        add(label);        
        
        //Set the width and height from label
        width = label.getWidth();
        height = label.getHeight();

        //Set the background
        setBackground(UIManager.getColor("panel.background"));

        //Fires the event when clicked
        addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    fireMAction(new MActionEvent(this));
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

    //Paint the button
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //Create the g2 Object
        Graphics2D g2 = (Graphics2D)g;

        //Set anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(UIManager.getColor("Panel.background"));
        g2.setColor(backgroundColor);
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

        if(hasEntered)
        {
            g2.setColor(backgroundColorHover);
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        }
    }    

    //Add a Listener
    public void addMActionListener(MActionListener listener)
    {
        mActionListeners.add(MActionListener.class, listener);
    }

    //Remove a Listener
    public void removeMActionListener(MActionListener listener)
    {
        mActionListeners.remove(MActionListener.class, listener);
    }

    protected void fireMAction(MActionEvent event)
    {
        //Create a list of the listeners
        Object[] listeners = mActionListeners.getListenerList();

        //Sends the event to all relevant objects
        for(int i = 0; i < listeners.length; i += 2)
        {
            if(listeners[i] == MActionListener.class)
            {
                ((MActionListener) listeners[i + 1]).mActionPerformed(event);
            }
        }
    }    

    //Changes the text on the CheckBox
    public void setText(String text)
    {
        label.setText(text);
    }
    
    //Test the button
    public static void main(String[] args)
    {
        MDragWindow window = new MDragWindow("Title", new Dimension(500,500), true);
        MPanel pane = new MPanel();
        MButton button = new MButton("This is a Button");  
        button.addMActionListener(new MActionListener()
        {
            @Override
            public void mActionPerformed(MActionEvent e)
            {
                System.out.println("Button");
            }
        });
        pane.add(button);
        window.add(pane);
        window.revalidate();
    }
}
