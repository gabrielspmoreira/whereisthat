package com.whereisthat.helper;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public abstract class FontHelper {

	private static Typeface font;
	
	public static void Init(AssetManager asset)
	{
		font = Typeface.createFromAsset(asset, GameConstants.CUSTOM_FONT);
	}
	
	public static void SetFont(TextView view)
	{		
		view.setTypeface(font);
	}
}
