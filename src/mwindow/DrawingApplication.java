package mwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.*;

public class DrawingApplication extends MDragWindow
{
    //Sizes
    private final static int CANVAS_WIDTH = 800;
    private final static int CANVAS_HEIGHT = 600;
    private final int CONTROL_PANEL_WIDTH = 200;
    private final int MESSAGE_AREA_HEIGHT = 100;

    //Sections of JFrame
    private Canvas canvas;
    private MPanel messages;
    private MPanel control;

    //Control Areas
    private MScrollPane controlScroll;
    private MPanel coordinatesPanel;
    private MPanel toolPanel;
    private MPanel sliderPanel;
    private MPanel gridPanel;
    private MPanel colorPanel;
    private MPanel buttonPanel;

    //JMenu
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu help;
    private JMenuItem fileSaveItem;
    private JMenuItem fileLoadItem;
    private JMenuItem fileExitItem;
    private JMenuItem helpAboutItem;

    //Message area
    private MScrollPane messageScroll;
    private JTextArea messageText;

    //Control components
    private JLabel coordinatesLabel;
    private MRadioButton lineRadioButton;
    private MRadioButton ovalRadioButton; 
    private MRadioButton rectRadioButton;
    private MRadioButton freehandRadioButton;
    private MButtonGroup group;
    private MSlider freehandSlider;
    private MCheckBox fineCheckBox; 
    private MCheckBox coarseCheckBox;
    private MButton colorButton; 
    private MButton clearButton; 
    private MButton animateButton;

    //Canvas mouse position
    private int initialClickXPos;
    private int initialClickYPos;
    private int prevXPos;
    private int prevYPos;
    private int xPos;
    private int yPos;

    //Mouse Pressed
    private boolean mousePressed = false;
    private boolean mouseReleased = false;

    //Current drawing tool
    private int currentTool = -1;
    private final int LINE = 0;
    private final int OVAL = 1;
    private final int RECT = 2;
    private final int FREE = 3;

    //Drawings
    private Color color = Color.BLACK;

    //Freehand
    private int freehandPenSize = 3;

    //Shapes
    private ArrayList<Shape> shapes = new ArrayList<Shape>(0);
    private ArrayList<Integer> ovalIndex = new ArrayList<Integer>(0);
    private int topOvalIndex = -1;
    private int numOfShapes = 0;

    //Animation
    private boolean isAnimating = false;
    private Thread thread;

    //The Canvas class
    private class Canvas extends MPanel
    {
        Canvas()
        {
            addMouseMotionListener(new MouseMotionAdapter()
                {
                    public void mouseMoved(MouseEvent e)
                    {
                        prevXPos = xPos;
                        prevYPos = yPos;
                        xPos = e.getX();
                        yPos = e.getY();
                        coordinatesLabel.setText("X = " + xPos + "  Y = " + yPos);
                        repaint();
                    }

                    public void mouseDragged(MouseEvent e)
                    {           
                        prevXPos = xPos;
                        prevYPos = yPos;
                        xPos = e.getX();
                        yPos = e.getY();
                        coordinatesLabel.setText("X = " + xPos + "  Y = " + yPos);         
                        repaint();
                    }
                });

            addMouseListener(new MouseAdapter()
                {
                    public void mousePressed(MouseEvent e)
                    {
                        if(!mousePressed)
                        {
                            initialClickXPos = e.getX();
                            initialClickYPos = e.getY();
                        }
                        mousePressed = true;
                        repaint();
                    }

                    public void mouseReleased(MouseEvent e)
                    {
                        mouseReleased = true;
                        repaint();
                    }
                });
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            draw(g);
        }
    }

    //The shape class
    private abstract class Shape implements Serializable
    {
        protected int xPos, yPos;
        protected double yVel = 0;
        protected double gravity = 9.8;
        protected int penSize;
        protected Color penColor;

        public abstract void draw(Graphics2D g2);        

        protected void setStroke(int width)
        {
            penSize = width;
        }

        protected void setColor(Color color)
        {
            penColor = color;
        }
    }

    //Ovals
    private class Oval extends Shape
    {
        private int width;
        private int height;        

        Oval(int x, int y, int w, int h)
        {
            xPos = x;
            yPos = y;
            width = w;
            height = h;
            if(w < 0)
            {            
                width = -w;
                xPos = xPos - width;
            }
            if(h < 0)
            {
                height = -h;
                yPos = yPos - height;
            }
        }

        public void draw(Graphics2D g2)
        {
            g2.setStroke(new BasicStroke(penSize));
            g2.setColor(penColor);
            g2.draw(new Ellipse2D.Double(xPos, yPos, width, height));
        }
    }

    //Rectangles
    private class Rectangle extends Shape
    {
        private int width;
        private int height;

        Rectangle(int x, int y, int w, int h)
        {
            xPos = x;
            yPos = y;
            width = w;
            height = h;
            if(w < 0)
            {            
                width = -w;
                xPos = xPos - width;
            }
            if(h < 0)
            {
                height = -h;
                yPos = yPos - height;
            }
        }

        public void draw(Graphics2D g2)
        {
            g2.setStroke(new BasicStroke(penSize));
            g2.setColor(penColor);
            g2.drawRect(xPos, yPos, width, height);
        }
    }

    //Lines
    private class Line extends Shape
    {
        private int xPos2;
        private int yPos2;

        Line(int x1, int y1, int x2, int y2)
        {
            xPos = x1;
            yPos = y1;
            xPos2 = x2;
            yPos2 = y2;
        }

        public void draw(Graphics2D g2)
        {
            g2.setStroke(new BasicStroke(penSize));
            g2.setColor(penColor);
            g2.drawLine(xPos, yPos, xPos2, yPos2);
        }
    }

    //The animation class
    private class Animation extends Thread
    {
        Animation()
        {
            super();
            start();
        }

        @Override
        public void run()
        {
            while(true)
            {                             
                //Move the ovals down
                for(int i = 0; i < ovalIndex.size(); i++)
                {
                    if(shapes.get(ovalIndex.get(i)).getClass() == Oval.class)
                    {
                        shapes.get(ovalIndex.get(i)).yPos += shapes.get(ovalIndex.get(i)).yVel;
                        shapes.get(ovalIndex.get(i)).yVel += (shapes.get(ovalIndex.get(i)).gravity)/30;      
                    }
                }

                //Check if the the highest oval is off the screen
                if(topOvalIndex >= 0 && shapes.get(topOvalIndex).yPos >= CANVAS_HEIGHT)
                {
                    break;
                }

                //Repaint and sleep the thread
                repaint();

                try
                {
                    Thread.sleep(1000/30);
                }
                catch(InterruptedException e)
                {
                    System.out.println(e.toString());
                }
            }  
            isAnimating = false;
            postMessage("Animation has Finished!");
        }        
    }

    public DrawingApplication()
    {
        //Create the window
        super("Drawing Application", new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        MPanel panel = new MPanel();

        //Set up the Panels
        canvas = new Canvas();
        canvas.setBorder(new TitledBorder(new EtchedBorder(), "Canvas"));
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        messages = new MPanel();
        messages.setBorder(new TitledBorder(new EtchedBorder(), "Messages"));        
        messages.setPreferredSize(new Dimension(CANVAS_WIDTH, MESSAGE_AREA_HEIGHT));

        control = new MPanel();
        control.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
        control.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CANVAS_HEIGHT));
        controlScroll = new MScrollPane(control);
        controlScroll.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH + 30, CANVAS_HEIGHT));
        controlScroll.setBorder(null);

        coordinatesPanel = new MPanel();
        coordinatesPanel.setBorder(new TitledBorder(new EtchedBorder(), "Coordinates"));
        coordinatesPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));

        toolPanel = new MPanel();
        toolPanel.setLayout(new GridLayout(4, 1));
        toolPanel.setBorder(new TitledBorder(new EtchedBorder(), "Drawing Tools"));
        toolPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 140));

        sliderPanel = new MPanel();
        sliderPanel.setBorder(new TitledBorder(new EtchedBorder(), "Free Hand Slider"));
        sliderPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 80));

        gridPanel = new MPanel();
        gridPanel.setBorder(new TitledBorder(new EtchedBorder(), "Grid"));
        gridPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 80));
        gridPanel.setLayout(new GridLayout(2, 1));

        colorPanel = new MPanel();
        colorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Colour"));
        colorPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 80));

        buttonPanel = new MPanel();
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Other"));
        buttonPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 100));

        //Message Area
        messageText = new JTextArea();
        messageText.setText("");
        messageText.setEditable(false);
        messageText.setBackground(UIManager.getColor("panel.background"));
        messageScroll = new MScrollPane(messageText);
        messageScroll.setBorder(null);
        messages.setLayout(new BorderLayout());
        messages.add(messageScroll);

        //Add the panels to the frame
        panel.setLayout(new BorderLayout());
        panel.add(messages, BorderLayout.SOUTH);
        panel.add(controlScroll, BorderLayout.LINE_START);
        panel.add(canvas);
        add(panel, BorderLayout.CENTER);
        revalidate();

        //Add the inner panels
        control.add(coordinatesPanel);
        control.add(toolPanel);
        control.add(sliderPanel);
        control.add(gridPanel);
        control.add(colorPanel);
        control.add(buttonPanel);

        //Set up the JMenu Bar
        menuBar = new JMenuBar();

        //File
        file = new JMenu("File");
        fileSaveItem = new JMenuItem("Save");
        fileSaveItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(isAnimating)
                    {
                        postMessage("ERROR: Cannot save while animation is in progress");
                    }
                    else
                    {
                        JFileChooser chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "DRW Files", "drw");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showSaveDialog(null);
                        if(returnVal == JFileChooser.APPROVE_OPTION) 
                        {                    
                            File file = chooser.getSelectedFile();
                            File newFile = new File(file.getAbsolutePath() + ".drw");
                            save(newFile);
                        } 
                    }
                }
            });

        fileLoadItem = new JMenuItem("Load");
        fileLoadItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(isAnimating)
                    {
                        postMessage("ERROR: Cannot load while animation is in progress");
                    }
                    else
                    {
                        //Open dialogue box for .drw files
                        JFileChooser chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "DRW Files", "drw");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showOpenDialog(null);
                        if(returnVal == JFileChooser.APPROVE_OPTION) 
                        {                        
                            load(chooser.getSelectedFile());
                        }    
                    }
                }
            });

        fileExitItem = new JMenuItem("Exit");
        fileExitItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    //Close the program
                    System.exit(1);
                }
            });

        //Help
        help = new JMenu("Help");
        helpAboutItem = new JMenuItem("About");
        helpAboutItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(null, "Drawing Application created by Mark Hillman");
                }
            });

        //Add the menu items
        file.add(fileSaveItem);
        file.add(fileLoadItem);
        file.add(fileExitItem);
        help.add(helpAboutItem);
        menuBar.add(file);
        menuBar.add(help);        
        panel.add(menuBar, BorderLayout.NORTH);

        //Control Bar
        //Coordinates Area
        coordinatesLabel = new JLabel("X = 0" + "  Y = 0");
        coordinatesPanel.add(coordinatesLabel);

        //Drawing Tool Area
        lineRadioButton = new MRadioButton("Line", true);
        ovalRadioButton = new MRadioButton("Oval");
        rectRadioButton = new MRadioButton("Rectangle");
        freehandRadioButton = new MRadioButton("Free Hand");
        currentTool = LINE;
        lineRadioButton.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    currentTool = LINE;
                    postMessage("Current tool = Line tool");
                }
            });

        ovalRadioButton.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    currentTool = OVAL;
                    postMessage("Current tool = Oval tool");
                }
            });

        rectRadioButton.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    currentTool = RECT;
                    postMessage("Current tool = Rectangle tool");                    
                }
            });

        freehandRadioButton.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    currentTool = FREE;
                    postMessage("Current tool = Freehand tool");                    
                }
            });

        group = new MButtonGroup();
        group.add(lineRadioButton);
        group.add(ovalRadioButton);
        group.add(rectRadioButton);
        group.add(freehandRadioButton);
        toolPanel.add(lineRadioButton);
        toolPanel.add(ovalRadioButton);
        toolPanel.add(rectRadioButton);
        toolPanel.add(freehandRadioButton);

        //Freehand Slider
        freehandSlider = new MSlider(0, 20);
        freehandSlider.setPreferredSize(new Dimension(150, 50));
        freehandSlider.setLabelTable(freehandSlider.createStandardLabels(5));        
        freehandSlider.setPaintLabels(true);
        freehandSlider.setMajorTickSpacing(5);
        freehandSlider.setMinorTickSpacing(1);
        freehandSlider.setPaintTicks(true);
        freehandSlider.setValue(freehandPenSize);
        freehandSlider.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    freehandPenSize = freehandSlider.getValue();
                }
            });   

        sliderPanel.add(freehandSlider);

        //Grid checkBoxes
        fineCheckBox = new MCheckBox("Fine", false);
        fineCheckBox.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {                    
                    canvas.repaint();
                    revalidate();
                    postMessage("Fine grid is now enabled");
                }
            });

        coarseCheckBox = new MCheckBox("Coarse", false);
        coarseCheckBox.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    canvas.repaint();
                    revalidate();
                    postMessage("Coarse grid is now enabled");
                }
            });

        gridPanel.add(fineCheckBox);
        gridPanel.add(coarseCheckBox);

        //Color button
        colorButton = new MButton();
        colorButton.setPreferredSize(new Dimension(40, 40));
        colorButton.setBackgroundColor(color);
        colorButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    color = JColorChooser.showDialog(null, "Pick a colour", color);
                    colorButton.setBackgroundColor(color);
                }
            });

        colorPanel.add(colorButton);

        //Other Buttons
        clearButton = new MButton("Clear Canvas");
        clearButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 50, 30));
        clearButton.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {           
                    reset();
                    repaint();
                    postMessage("Canvas has been cleared");
                }
            });

        animateButton = new MButton("Animate");
        animateButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 50, 30));
        animateButton.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    if(isAnimating)
                    {
                        postMessage("ERROR: Animation is already in progress");
                    }
                    else if(ovalIndex.size() == 0)
                    {
                        postMessage("ERROR: There are no ovals to animate");
                    }
                    else
                    {
                        isAnimating = true;
                        new Animation();
                    }
                }
            });

        buttonPanel.add(clearButton);
        buttonPanel.add(animateButton);

        //Show the window
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Called by the canvas' paintComponent method
    void draw(Graphics g)
    {
        //if the fine checkbox is checked draw a thin grid
        if(fineCheckBox != null && fineCheckBox.isSelected())
        {
            g.setColor(new Color(0.8F, 0.8F, 0.8F));
            for(int i = 0; i < canvas.getWidth(); i += 10)
            {
                g.drawLine(i, 0, i, canvas.getHeight());
                g.drawLine(0, i, canvas.getWidth(), i);
            }
        }

        //If the coarse checkbox is checked draw a large grid
        if(coarseCheckBox != null && coarseCheckBox.isSelected())
        {
            g.setColor(new Color(0.6F, 0.6F, 0.6F));
            for(int i = 0; i < canvas.getWidth(); i += 50)
            {
                g.drawLine(i, 0, i, canvas.getHeight());
                g.drawLine(0, i, canvas.getWidth(), i);
            }
        }

        //Stops drawing if animation is in progress
        if(!isAnimating)
        {
            //If the mouse is being pressed
            if(mousePressed)
            {
                //Draw the shapes
                if(currentTool == FREE)
                {
                    shapes.add(new Line(prevXPos, prevYPos, xPos, yPos));
                    shapes.get(shapes.size()-1).setStroke(freehandPenSize);                
                    shapes.get(shapes.size()-1).setColor(color);
                    numOfShapes++;
                }
                else if(currentTool == LINE)
                {
                    if(numOfShapes > shapes.size()-1)
                    {
                        shapes.add(new Line(initialClickXPos, initialClickYPos, xPos, yPos));
                        shapes.get(shapes.size()-1).setColor(color);                    
                    }
                    else
                    {
                        shapes.set(numOfShapes, new Line(initialClickXPos, initialClickYPos, xPos, yPos));
                        shapes.get(shapes.size()-1).setColor(color);
                    }

                    if(mouseReleased == true)
                    {
                        numOfShapes++;
                    }
                }
                else if(currentTool == OVAL)
                {
                    //Create a new Oval shape in the shapes array
                    if(numOfShapes > shapes.size()-1)
                    {
                        shapes.add(new Oval(initialClickXPos, initialClickYPos, xPos - initialClickXPos, yPos - initialClickYPos));
                        shapes.get(shapes.size()-1).setColor(color);
                    }
                    else
                    {
                        shapes.set(numOfShapes, new Oval(initialClickXPos, initialClickYPos, xPos - initialClickXPos, yPos - initialClickYPos));
                        shapes.get(shapes.size()-1).setColor(color);
                    }           

                    //Check if the oval is the highest oval
                    if(topOvalIndex == -1 || shapes.get(shapes.size()-1).yPos < shapes.get(topOvalIndex).yPos)
                    {
                        topOvalIndex = shapes.size()-1;
                    }

                    //Increase the number of shapes
                    if(mouseReleased == true)
                    {                    
                        //Create a new index for an oval
                        ovalIndex.add(new Integer(shapes.size()-1));     
                        numOfShapes++;
                    }
                }
                else if(currentTool == RECT)
                {
                    if(numOfShapes > shapes.size()-1)
                    {
                        shapes.add(new Rectangle(initialClickXPos, initialClickYPos, xPos - initialClickXPos, yPos - initialClickYPos));
                        shapes.get(shapes.size()-1).setColor(color);
                    }
                    else
                    {
                        shapes.set(numOfShapes, new Rectangle(initialClickXPos, initialClickYPos, xPos - initialClickXPos, yPos - initialClickYPos));
                        shapes.get(shapes.size()-1).setColor(color);
                    }

                    if(mouseReleased == true)
                    {
                        numOfShapes++;
                    }
                }

                //Reset the mouse pressed
                if(mouseReleased == true)
                {
                    mousePressed = false;
                    mouseReleased = false;
                }
            }
        }   

        //Draw the lines
        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i < shapes.size(); i++)
        {
            shapes.get(i).draw(g2);
        }

        //Reset the graphics object
        g2.setStroke(new BasicStroke(1));        
    }    

    //Saving and Loading
    public void save(File file)
    {
        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
            stream.writeObject(shapes);
            stream.writeObject(numOfShapes);
            stream.writeObject(topOvalIndex);
            stream.close();
            postMessage("File saved!");
        }
        catch(IOException e)
        {
            postMessage("ERROR: " + e.toString());
        }
    }

    public void load(File file)
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
            @SuppressWarnings("unchecked")
            ArrayList<Shape> shapesNew = (ArrayList<Shape>)stream.readObject();
            shapes = shapesNew;
            numOfShapes = (int)stream.readObject();
            topOvalIndex = (int)stream.readObject();
            stream.close();
            postMessage("File loaded!");
            repaint();
        }
        catch(IOException e)
        {
            postMessage("ERROR: " + e.toString());
        }
        catch(ClassNotFoundException e)
        {
            postMessage("ERROR: " + e.toString());
        }
    }    

    //Message box
    public void postMessage(String text)
    {
        messageText.append(text + "\n");
    }

    //Reset the program
    public void reset()
    {
        numOfShapes = 0;
        shapes = new ArrayList<Shape>(0);
        topOvalIndex = -1;
        ovalIndex = new ArrayList<Integer>(0);
    }

    //Main method call
    public static void main(String[] args)
    {
        new DrawingApplication();
    }
}

