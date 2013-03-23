/**
 * WikiPediaInLinePageParser - ICESI University Distributed Systems course.
 * COPYRIGHT (C) 2010 Jose Luis Montoya. ALL RIGHTS RESERVED.
 * This code has been created for educational purposes and therefore
 * should not be used in production environments
 *  
 * This file is part of WikiPediaInLinePageParser.
 *    
 * HadoopInvertedIndex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WikiPediaInLinePageParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HadoopInvertedIndex.  If not, see <http://www.gnu.org/licenses/>.
 */
package co.edu.icesi.tics.distsys.project.wiki.parser;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Concrete implementation of the WikiPediaPageParser interface that uses the asynchronous
 * XML processing API for Java SAX  
 * @author jlmontoya
 *
 */
public class WikiPediaInLineXmlPageSaxParser implements WikiPediaPageParser {
	
	/* Parse a flattened WikiPedia page representation returning a object oriented representation of the page
	 * @see co.edu.icesi.tics.distsys.project.wiki.parser.WikiPediaInLineXmlPageParser#parseLine(java.lang.String)
	 */
	@Override
	public WikiPediaPage parseLine(String line){
				
		if( line == null || line.isEmpty())
			return null;
		
		WikiPediaPage wikiPage = new WikiPediaPage();
		
		try{
			StringReader stringReader = new StringReader(line);
			InputSource inputSource = new InputSource(stringReader);
			DefaultHandler dh = new WikiPediaXmlPageSaxHandler(wikiPage);
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(inputSource, dh );
		}catch(Exception e){
			e.printStackTrace();
			wikiPage = null;
		}
		
		return wikiPage;
	}

}