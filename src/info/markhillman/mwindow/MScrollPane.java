package info.markhillman.mwindow;

import javax.swing.*;
import java.awt.*;

public class MScrollPane extends JScrollPane
{
    public MScrollPane(Component c)
    {
        super(c);
        getVerticalScrollBar().setUI(new MScrollUI());
        getHorizontalScrollBar().setUI(new MScrollUI());
    }
    
    public static void main(String[] args)
    {
        MDragWindow wnd = new MDragWindow();

        wnd.revalidate();
    }
}