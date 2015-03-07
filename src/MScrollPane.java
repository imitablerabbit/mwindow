package mwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.*;

public class MScrollPane extends JScrollPane
{
    public MScrollPane(Component c)
    {
        super(c);
        getVerticalScrollBar().setUI(new MScrollUI());
    }
    
    public static void main(String[] args)
    {
        MDragWindow wnd = new MDragWindow();

        wnd.revalidate();
    }
}