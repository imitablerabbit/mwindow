package mwindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MDragWindow extends JFrame
{
    private Color dragBarColor;
    private boolean hasTitle = true;
    private boolean hasButtons = true;
    private boolean hasDecorations = true;
    private String title;
    private Dimension size;
    private MDragPanel drag;
    private MCloseButton close;
    private MMinimizeButton mini;

    public MDragWindow()
    {
        this("Window", new Dimension(500, 300), true, true, true);
    }

    public MDragWindow(String title)
    {
        this(title, new Dimension(500, 300), true, true, true);
    }

    public MDragWindow(Dimension size)
    {
        this("Window", size, true, true, true);
    }

    public MDragWindow(String title, Dimension size)
    {
        this(title, size, true, true, true);
    }

    public MDragWindow(String title, Dimension size, boolean hasDecorations)
    {
        this(title, size, hasDecorations, true, true);
    }

    public MDragWindow(String title, Dimension size, boolean hasDecorations, boolean hasTitle, boolean hasButtons)
    {
        //Gives the window a title
        super(title);

        this.title = title;
        this.size = size;

        //Sets the size of the window
        setSize(size); 

        //Centers the window
        setLocationRelativeTo(null);

        //Removes the decorations from the window
        setUndecorated(true);
        
        //Gives the window a safe close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Shows the window on screen
        setVisible(true);

        //Creates the DragPanel at the top of the window    
        drag = new MDragPanel(this);
        add(drag, BorderLayout.NORTH);
        dragBarColor = Color.DARK_GRAY;

        if(hasDecorations)
        {
            if(hasButtons)
            {
                //Creates a panel for the buttons on the right hand side of the bar
                JPanel buttons = new JPanel();
                FlowLayout lm = new FlowLayout(FlowLayout.RIGHT);
                buttons.setLayout(lm);
                buttons.setBackground(dragBarColor);
                mini = new MMinimizeButton(this);
                close = new MCloseButton();

                //Adds the buttons to the bar
                buttons.add(mini);
                buttons.add(close);
                drag.add(buttons, BorderLayout.EAST);
            }

            if(hasTitle)
            {
                //Creates a panel for the title on the left hand side of the bar
                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
                titlePanel.setBackground(dragBarColor);
                
                //Create the title
                JLabel titleLabel = new JLabel(title);
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setFont(new Font("Dialog", Font.BOLD, 14));
                titlePanel.add(titleLabel);
                drag.add(titlePanel, BorderLayout.WEST);  
            }
        }   

        revalidate();   
    }

    public static void main(String[] args)
    {
        MDragWindow mWnd = new MDragWindow("Title", new Dimension(600, 600), true);
    }
}