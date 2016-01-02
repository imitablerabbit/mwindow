package info.markhillman.mwindow;

import javax.swing.*;
import java.awt.*;

public class MDragWindow extends JFrame
{
    //Background color of the drag bar
    private Color dragBarColor;
    
    //Options for drag pane
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
        setSize(size); 

        //Centers the window
        setLocationRelativeTo(null);

        //Removes the decorations from the window
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

    public int getTitleBarHeight() {
        return drag.getTitleBarHeight();
    }
}