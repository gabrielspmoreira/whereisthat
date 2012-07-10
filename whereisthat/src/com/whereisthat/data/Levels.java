package com.whereisthat.data;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import android.content.res.Resources;

import com.whereisthat.R;

public class Levels implements Iterable<Level> {
	
	private List<Level> levels;
	
	public List<Level> getLevels(){
		return levels;
	}
	
	public void loadFromXml(Resources resource)
	{
		InputStream isLevels = resource.openRawResource(R.raw.gamelevels);
		levels = LevelsParser.parseXml(isLevels);
	}
	
	public Iterator<Level> iterator(){
		return levels.iterator();
	}
}
