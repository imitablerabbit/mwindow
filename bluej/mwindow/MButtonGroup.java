package mwindow;
import java.util.*;
import javax.swing.event.*;
import java.awt.*;

public class MButtonGroup
{
    private ArrayList<MRadioButton> radioButtons;
    private int currentChecked = -1;

    MButtonGroup()
    {
        radioButtons = new ArrayList<MRadioButton>(0);
    }

    //Add a Radiobutton to the group
    public void add(MRadioButton button)
    {
        button.setButtonGroup(this);
        if(button.isSelected())
        {
            currentChecked = getLength();
        }
        radioButtons.add(button);
    }

    public void remove(int index)
    {
        radioButtons.remove(index);
    }

    public int getLength()
    {
        return radioButtons.size();
    }
    
    //Reset the other radioButtons
    public void reset()
    {
        for(int i = 0; i < getLength(); i++)
        {
            if(radioButtons.get(i).isSelected())
            {
                if(currentChecked == i)
                {
                    continue;
                }
                currentChecked = i;
                for(int j = 0; j < getLength(); j++)
                {
                    if(j != currentChecked)
                    {
                        radioButtons.get(j).setSelected(false);                        
                    }
                    radioButtons.get(j).repaint();
                }
            }
        }
    }

    //Test the Button Group
    public static void main(String[] args)
    {
        MDragWindow window = new MDragWindow("Title", new Dimension(500,500), true);
        MPanel pane = new MPanel();
        MRadioButton button = new MRadioButton("RadioButton", true);     
        MRadioButton button2 = new MRadioButton("RadioButton", false);   
        MRadioButton button3 = new MRadioButton("RadioButton", false);   
        MButtonGroup group = new MButtonGroup();
        group.add(button);
        group.add(button2);
        group.add(button3);
        pane.add(button);
        pane.add(button2);
        pane.add(button3);
        window.add(pane);
        window.revalidate();
    }
}