package info.markhillman.mwindow;

import java.awt.*;

public class Test extends MBasicWindow
{
    MCheckBox check;
    MRadioButton radio;
    MButton button;

    Test()
    {
        check = new MCheckBox("CheckBox");
        radio = new MRadioButton("RadioButton");
        button = new MButton("Button");

        check.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {                    
                    if(check.isSelected())
                    {
                        System.out.println("Checked");
                    }
                }
            });

        radio.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    if(radio.isSelected())
                    {
                        System.out.println("Selected");
                    }
                }
            });

        button.addMActionListener(new MActionListener()
            {
                @Override
                public void mActionPerformed(MActionEvent e)
                {
                    System.out.println("Clicked");
                }
            });

        setLayout(new FlowLayout());
        add(check);
        add(radio);
        add(button);
    }

    public static void main(String[] args)
    {
        Test test = new Test();
    }
}