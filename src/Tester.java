import java.awt.*;

import mwindow.*;

public class Tester {

	public static void main(String[] args) 
	{
		//MColor.backgroundColor = new Color(255, 130, 130);
		MColor.componentColor = new Color(30, 130, 135);
		MColor.updateColor();
		new DrawingApplication();
		//new MBasicWindow();
		//new MDragWindow();
		MUndecoratedWindow wnd = new MUndecoratedWindow();
		wnd.setLayout(new FlowLayout());
		wnd.add(new MButton("Button"));
		wnd.add(new MCheckBox());		
		wnd.add(new MDragPanel(wnd));
		wnd.add(new MCloseButton(wnd));
		wnd.add(new MMinimizeButton(wnd));
		wnd.add(new MPanel());
		wnd.add(new MRadioButton());
		wnd.add(new MScrollPane(new MPanel()));
		wnd.add(new MSlider());		
		wnd.revalidate();
	}

}
