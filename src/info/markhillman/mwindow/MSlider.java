package info.markhillman.mwindow;

import javax.swing.*;
import java.awt.*;

public class MSlider extends JSlider
{
    public MSlider()
    {
        this(0, 20);
    }
    
    public MSlider(int start, int end)
    {
        super(start, end);
        setUI(new MSliderUI(this));        
        setPreferredSize(new Dimension(150, 50));
        setLabelTable(this.createStandardLabels(5));        
        setPaintLabels(true);
        setMajorTickSpacing(5);
        setMinorTickSpacing(1);
        setPaintTicks(true);
    }
        
    //Tester
    public static void main(String[] args)
    {
        MBasicWindow wind = new MBasicWindow();
        wind.setLayout(new FlowLayout());
        MSlider slider = new MSlider();
        wind.add(slider);
    }
}
