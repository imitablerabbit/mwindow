package info.markhillman.mwindow;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;

//UI Override class
public class MSliderUI extends BasicSliderUI
{
    public MSliderUI(JSlider s)
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