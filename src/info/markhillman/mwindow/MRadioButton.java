package info.markhillman.mwindow;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class MRadioButton extends JComponent
{
    //The Box and text
    private Circle radioCircle;
    private JLabel label;    

    //Button group
    private MButtonGroup group;

    //Is the box checked
    private boolean checked = false;

    //The EventListeners
    protected EventListenerList mActionListeners = new EventListenerList();
    
    //Color variables
    Color circleBackgroundColor = new Color(50, 50, 50);
    Color circleInnerColor = Color.LIGHT_GRAY;

    //--OTHER CLASSES--
    //Draws the Button next to the label
    private class Circle extends JComponent
    {
        private int height = 12;
        private int width = 12;
        private int xOffset = 0;
        private int yOffset = 1;

        private Circle()
        {
            //Set size of circle
            setPreferredSize(new Dimension(width + xOffset, height + yOffset));

            //Checks the box when clicked
            addMouseListener(new MouseHandler());
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            //Create the g2 object
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setBackground(UIManager.getColor("Panel.background"));

            //Draw outer ellipse
            g2.setColor(circleBackgroundColor);
            g2.fill(new Ellipse2D.Double(xOffset, yOffset, width, height));
            if(checked)
            {
                //Draw inner ellipse
                g2.setColor(circleInnerColor);
                g2.fill(new Ellipse2D.Double(xOffset + (width / 4), yOffset + (height / 4), width / 2, height / 2));
            }
        }
    }

    //--EVENT HANDLER--
    //Handles the mouse events
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            if(!checked)
            {
                checked = true;
                fireMAction(new MActionEvent(this));
                radioCircle.repaint();
            }                                 
        }
    }
    
    //--CONSTRUCTORS--
    public MRadioButton()
    {
        this("RadioButton", false);
    }

    public MRadioButton(String text)
    {
        this(text, false);
    }    

    //Creates the Checkbox
    public MRadioButton(String text, boolean checked)
    {
        super();
        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 1));

        //Add the Label and CheckBox
        label = new JLabel(text);
        radioCircle = new Circle();
        add(radioCircle);
        add(label);

        //Set as checked
        this.checked = checked;

        //Set the background as transparent
        setBackground(UIManager.getColor("panel.background"));

        //Fires the event when clicked
        addMouseListener(new MouseHandler());
    }

    //--GETTERS AND SETTERS
    //Returns the background color of the inner circle
    public Color getBackgroundColor()
    {
        return circleBackgroundColor;
    }
    
    //Sets the background color
    public void setBackgroundColor(Color color)
    {
        circleBackgroundColor = color;
        repaint();
    }
    
    //Check if the RadioButton is checked
    public boolean isSelected()
    {
        return checked;
    }

    //Sets whether the Radiobutton should be checked
    public void setSelected(boolean checked)
    {
        this.checked = checked; 
    }

    //Returns the text in the RadioButton
    public String getText()
    {
        return label.getText();
    }

    //Set the text for the RadioButton
    public void setText(String text)
    {
        label.setText(text);
        repaint();
    }
    
    //--BUTTON GROUPS--
    public void setButtonGroup(MButtonGroup group)
    {
        this.group = group;
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

    //This will send an event when the component is selected
    protected void fireMAction(MActionEvent event)
    {
        //Create a list of the listeners
        Object[] listeners = mActionListeners.getListenerList();

        if(group != null)
        {
            group.reset();
        }

        //Sends the event to all relevant objects
        for(int i = 0; i < listeners.length; i += 2)
        {
            if(listeners[i] == MActionListener.class)
            {                
                ((MActionListener) listeners[i + 1]).mActionPerformed(event);                
            }
        }
    }

    //Test the radio Button
    public static void main(String[] args)
    {
        MDragWindow window = new MDragWindow("Title", new Dimension(500,500), true);
        MPanel pane = new MPanel();
        MRadioButton button = new MRadioButton("This is a RadioButton", false);       
        pane.add(button);
        window.add(pane);
        window.revalidate();
    }
}