package info.markhillman.mwindow;

import java.util.*;

public interface MActionListener extends EventListener
{
    //Event dispatch methods
    public void mActionPerformed(MActionEvent e);
}