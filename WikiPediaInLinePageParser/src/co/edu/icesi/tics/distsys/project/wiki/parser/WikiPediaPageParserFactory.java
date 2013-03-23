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

/**
 * Factory class that allow get concrete implementations of the WikiPedia page parsers
 * @author jlmontoya
 *
 */
public abstract class WikiPediaPageParserFactory {
	
	/**
	 * Return a concrete implementation of a WikiPedia page parser
	 * Default implementation WikiPediaInLineXmlPageSaxParser
	 * @return
	 */
	public static WikiPediaPageParser newInstance(){
		return new WikiPediaInLineXmlPageSaxParser();
	}
}
