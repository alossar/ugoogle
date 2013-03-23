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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author jlmontoya
 *
 */
public class WikiPediaXmlPageSaxHandler extends DefaultHandler {

	private WikiPediaPage wikiPage;
	private StringBuffer stringBuffer;

	public WikiPediaXmlPageSaxHandler(WikiPediaPage wikiPage) {
		super();
		this.wikiPage = wikiPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("title".equals(qName)) {
			wikiPage.setTitle(getText());
		} else if ("text".equals(qName)) {
			wikiPage.setText(getText());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String s = new String(ch, start, length);
		if (stringBuffer == null) {
			stringBuffer = new StringBuffer(s);
		} else {
			stringBuffer.append(s);
		}

	}

	private String getText() {
		String s = null;
		if (stringBuffer == null) {
			s = "";
		} else {
			s = stringBuffer.toString();
			stringBuffer = null;
		}
		return s;
	}
}