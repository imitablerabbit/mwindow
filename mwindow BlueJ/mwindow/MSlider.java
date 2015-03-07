package mwindow;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;

public class MSlider extends JSlider
{
    private class MSliderUI extends BasicSliderUI
    {
        MSliderUI(JSlider s)
        {
            super(s);
        }

        @Override
        public void paintThumb(Graphics g)
        {
            Rectangle knobBounds = thumbRect;
            int w = knobBounds.width;
            int h = knobBounds.height;
            
            g.translate(knobBounds.x, knobBounds.y);
            
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, w, h - 3);
        }
        
        @Override
        public void paintTrack(Graphics g)
        {
            Rectangle trackBounds = trackRect;
            int w = trackBounds.width;
            int h = trackBounds.height;
            
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect((thumbRect.width / 2) + 2, (h / 8) * 4, w, h / 4);
        }
        
        @Override
        public void paintFocus(Graphics g)
        {
            
        }
    }

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
        setBorder(null);
    }
    
    public static void main(String[] args)
    {
        MBasicWindow wind = new MBasicWindow();
        wind.setLayout(new FlowLayout());
        MSlider slider = new MSlider();
        wind.add(slider);
    }
}
