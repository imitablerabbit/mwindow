package info.markhillman.mwindow;

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
    private int labelWidth;
    private int labelHeight;

    //Colour
    private Color backgroundColor = Color.DARK_GRAY;
    private Color backgroundColorHover = new Color(255, 255, 255, 50);

    //Font for Label
    private Font labelFont = new Font("Dialog", Font.BOLD, 12);
    
    //The EventListeners
    protected EventListenerList mActionListeners = new EventListenerList();

    public MButton()
    {
        this("");
    }

    //Creates the Button
    public MButton(String text)
    {
        super();        
        setBorderPainted(false);

        //Add the Label
        label = new JLabel(text);
        label.setForeground(UIManager.getColor("Panel.background"));
        label.setFont(labelFont);        
        add(label);

        //Set the background
        setBackground(UIManager.getColor("panel.background"));

        //Fires the event when clicked
        addMouseListener(new MouseAdapter()
            {
        		@Override
                public void mousePressed(MouseEvent e)
                {
                    fireMAction(new MActionEvent(this));
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

	//Update the dimensions of the button
    private void updateDimensions()
    {
        width = getPreferredSize().width;
        height = getPreferredSize().height;
        labelWidth = label.getPreferredSize().width;
        labelHeight = label.getPreferredSize().height;	
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

    //--GETTERS AND SETTERS--
    //Sets the background colour
    public void setBackgroundColor(Color color)
    {
        backgroundColor = color;
        repaint();
    }

    //Returns the background colour
    public Color getBackground()
    {
        return backgroundColor;
    }

    //Set the label
    public void setLabel(JLabel label)
    {
    	this.label = label;
    	repaint();
    }
    
    //Changes the text on the CheckBox
    public void setLabelText(String text)
    {
        label.setText(text);
        repaint();
    }

    //Returns the text
    public String getLabelText()
    {
    	return label.getText();
    }      
    
    //Sets the label font
    public void setLabelFont(Font font)
    {
    	labelFont = font;
    	label.setFont(labelFont);
    }
    
    //Gets the labels font
    public Font getLabelFont()
    {
    	return labelFont;
    }
    
    //Sets the label position
    public void setLabelPosition(int position)
    {
    	updateDimensions();
    	if (position == LEFT)
    	{
    		setLayout(null);
    		label.setBounds(((width - labelWidth) / 8), (height - labelHeight) / 2, labelWidth, labelHeight);
    	}
    	else if (position == RIGHT)
    	{
    		setLayout(null);
    		label.setBounds(((width - labelWidth) / 8) * 7, (height - labelHeight) / 2, labelWidth, labelHeight);    		
    	}
    	else
    	{
            setLayout(null);
            label.setBounds((width - labelWidth) / 2, (height - labelHeight) / 2, labelWidth, labelHeight);
    	}
    }
    
    //Returns the dimensions of the button
    public Dimension getButtonSize()
    {
    	updateDimensions();
    	return new Dimension(width, height);
    }

    //--ACTION EVENTS--
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

    //Fire the action event
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

    //--TESTS THE BUTTON--
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
        button.setBackgroundColor(new Color(200, 100, 100));
        button.setPreferredSize(new Dimension(200, 100));
        button.setLabelFont(new Font("Dialog", Font.BOLD, 18));
        button.setLabelPosition(CENTER);
        pane.add(button);
        window.add(pane);
        window.revalidate();
    }
}
