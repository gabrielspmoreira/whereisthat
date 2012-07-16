package com.whereisthat.data;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import android.content.res.Resources;

import com.whereisthat.R;

public class Levels {
	
	private static List<Level> levels;
	
	public static void loadFromXml(Resources resource)
	{
		InputStream isLevels = resource.openRawResource(R.raw.gamelevels);
		levels = LevelsParser.parseXml(isLevels);
	}
	
	public static List<Level> getLevels(){
		return levels;
	}
	
	
	public static void setLevels(List<Level> levelsList){
		levels = levelsList;
	}

}
