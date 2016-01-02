package info.markhillman.mwindow;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MCheckBox extends JComponent
{
    //The Box and text
    private Box checkBox;
    private JLabel label;

    //Is the box checked
    private boolean checked = false;

    //The EventListeners
    protected EventListenerList mActionListeners = new EventListenerList();

    //Color variables
    Color squareBackgroundColor = new Color(50, 50, 50);
    Color squareInnerColor = Color.LIGHT_GRAY;

    //--SQUARE CLASS--
    //Box that gets checked
    private class Box extends JComponent
    {
        //Dimensions
        private int height = 12;
        private int width = 12;
        private int xOffset = 0;
        private int yOffset = 1;

        private Box()
        {
            setPreferredSize(new Dimension(width + xOffset, height + yOffset));

            //Checks the box when clicked
            addMouseListener(new MouseHandler());
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;

            //Set anti-aliasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setBackground(UIManager.getColor("Panel.background"));

            //Draw the background square
            g2.setColor(squareBackgroundColor);
            g2.fill(new Rectangle2D.Double(xOffset, yOffset, width, height));

            //If the CheckBox is checked
            if(checked == true)
            {
                g2.setColor(squareInnerColor);
                g2.fill(new Rectangle2D.Double(xOffset + (width / 4), yOffset + (height / 4), width / 2, height / 2));
            }
        }
    }

    //Handles the mouse events
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            if(checked == true)
            {
                checked = false;
                fireMAction(new MActionEvent(this));                
                checkBox.repaint();
            }
            else
            {
                checked = true;
                fireMAction(new MActionEvent(this));                
                checkBox.repaint();
            }
        }
    }

    //--CONSTRUCTORS--
    public MCheckBox()
    {
        this("CheckBox", false);
    }

    public MCheckBox(String text)
    {
        this(text, false);
    }

    //Creates the Checkbox
    public MCheckBox(String text, boolean checked)
    {
        super();
        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 1));

        //Add the Label and CheckBox
        label = new JLabel(text);
        checkBox = new Box();
        add(checkBox);
        add(label);

        //Set as checked
        this.checked = checked;

        //Set the background as transparent
        setBackground(UIManager.getColor("panel.background"));

        //Fires the event when clicked
        addMouseListener(new MouseHandler());
    }

    //--GETTERS AND SETTERS    
    //Set the background color
    public void setBackgroundColor(Color color)
    {
        squareBackgroundColor = color;
        repaint();
    }

    //Returns the squares background color
    public Color getBackgroundColor()
    {
        return squareBackgroundColor;
    }

    //Sets the squares inner color
    public void setInnerColor(Color color)
    {
        squareInnerColor = color;
        repaint();
    }
    
    //Returns the squares inner color
    public Color getInnerColor()
    {
        return squareInnerColor;
    }
    
    //Changes the text on the CheckBox
    public void setText(String text)
    {
        label.setText(text);
        repaint();
    }

    //Returns the text on the label
    public String getText()
    {
        return label.getText();
    }

    //Returns the state of the checkBox
    public boolean isSelected()
    {
        return checked;
    }

    //Sets the state of the checkbox
    public void setSelected(boolean checked)
    {
        this.checked = checked;
    }

    //--ACTION LISTENERS--
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

    //Fire the event
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

    //--TESTING METHOS--
    //Test the check box
    public static void main(String[] args)
    {
        MDragWindow window = new MDragWindow("Title", new Dimension(500,500), true);
        MPanel pane = new MPanel();
        MCheckBox check = new MCheckBox("This is a CheckBox");        
        pane.add(check);
        window.add(pane);
        window.revalidate();
    }
}