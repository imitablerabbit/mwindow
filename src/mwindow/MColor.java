package mwindow;

import java.awt.*;

public class MColor {

	//Main background colors
	public static Color backgroundColor = new Color(240,240,240);
	
	//Offshoot background colors
	public static Color mOptionPaneBackgroundColor = backgroundColor;
	public static Color mOptionPane_MessageBackgroundColor = backgroundColor;
	public static Color mOptionPane_WarningBackgroundColor = backgroundColor;
	public static Color mTextAreaBackgroundColor = backgroundColor;
	
	//Main Component Colors
	public static Color componentColor = Color.DARK_GRAY;
	
	//Offshoot Component Colors
	public static Color mButtonColor = componentColor;
	public static Color mCheckBoxColor = componentColor;
	public static Color mRadioButtonColor = componentColor;
	public static Color mScrollPaneColor = componentColor;
	public static Color mSliderColor = componentColor;
	public static Color mCloseButtonColor = new Color(175, 0, 0);
	public static Color mMinimizeButtonColor = new Color(0, 130, 180);
	
	//Text/foreground colors
	public static Color foregroundColor = Color.WHITE;
	
	public static Color mCheckBoxForegroundColor = foregroundColor;
	public static Color mRadioButtonForegroundColor = foregroundColor;
	public static Color mScrollPaneTrackColor = new Color(componentColor.getRed(), componentColor.getGreen(), componentColor.getBlue(), 50);
	public static Color mSliderForegroundColor = new Color(componentColor.getRed(), componentColor.getGreen(), componentColor.getBlue(), 50);
	
	public static void updateColor()
	{
		//Offshoot background colors
		mOptionPaneBackgroundColor = backgroundColor;
		mOptionPane_MessageBackgroundColor = backgroundColor;
		mOptionPane_WarningBackgroundColor = backgroundColor;
		mTextAreaBackgroundColor = backgroundColor;
		
		//Offshoot Component Colors
		mButtonColor = componentColor;
		mCheckBoxColor = componentColor;
		mRadioButtonColor = componentColor;
		mScrollPaneColor = componentColor;
		mSliderColor = componentColor;
		mCloseButtonColor = new Color(175, 0, 0);
		mMinimizeButtonColor = new Color(0, 130, 180);		
		
		//Foreground colours
		mCheckBoxForegroundColor = foregroundColor;
		mRadioButtonForegroundColor = foregroundColor;
		mScrollPaneTrackColor = new Color(componentColor.getRed(), componentColor.getGreen(), componentColor.getBlue(), 50);
		mSliderForegroundColor = new Color(componentColor.getRed(), componentColor.getGreen(), componentColor.getBlue(), 50);
	}

}
