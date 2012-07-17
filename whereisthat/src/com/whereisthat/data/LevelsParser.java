package com.whereisthat.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.androidquery.util.XmlDom;

public class LevelsParser {
	
	public static List<Level> parseXml(InputStream is){		
		try {
			XmlDom xmlRoot = new XmlDom(is);
			List<XmlDom> levelsDom = xmlRoot.children("Level");
			List<Level> levels = new ArrayList<Level>();
			
			for(XmlDom levelDom: levelsDom){
				Level level = new Level();
				level.setName(levelDom.attr("Name"));
				level.setMaxRoundsToPass(Integer.parseInt(levelDom.attr("MaxRoundsToPass")));
				level.setMinScoreToPass(Integer.parseInt(levelDom.attr("MinScoreToPass")));
				level.setHasLongQuestions(Boolean.parseBoolean(levelDom.attr("HasLongQuestions")));
				level.setDataset(levelDom.attr("Dataset"));
				level.setDescription(levelDom.attr("Description"));
				levels.add(level);
			}
			
			return levels;
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
}