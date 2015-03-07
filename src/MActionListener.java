package mwindow;

import java.awt.event.*;
import java.util.*;

public interface MActionListener extends EventListener
{
    //Event dispatch methods
    public void mActionPerformed(MActionEvent e);
}