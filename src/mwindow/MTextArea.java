package mwindow;

import javax.swing.JTextArea;

public class MTextArea extends JTextArea
{
	public MTextArea()
	{
		this("");
		
	}
	
	public MTextArea(String text)
	{
		super(text);
		setBackground(MColor.mTextAreaBackgroundColor);
		setEditable(false);
	}
}
