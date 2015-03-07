package mwindow;

import java.util.*;
import javax.swing.event.*;
import java.awt.*;

public class MButtonGroup
{
    private ArrayList<MRadioButton> radioButtons;
    private int currentChecked = -1;

    public MButtonGroup()
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

    //Remove a button from the group at the given index
    public void remove(int index)
    {
        radioButtons.remove(index);
    }

    //Returns the size of the array
    public int getLength()
    {
        return radioButtons.size();
    }

    //Reset the other radioButtons
    public void reset()
    {
        int previousChecked = -1;
        
        //Loop through the radioButtons in the group
        for(int i = 0; i < getLength(); i++)
        {
            //Dont check the current checked radioButton
            if(i == currentChecked)
            {
                continue;
            }
            
            //Change the selected radioButton
            if(radioButtons.get(i).isSelected())
            {
                currentChecked = i;
                
                //Reset the other radioButtons
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