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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * This class represents a WikiPedia page.
 * @author jlmontoya
 *
 */
public class WikiPediaPage {
	
	private String title;
	private String text;
	private List<String> adjacencyList;
	private float pageRank;
		
	public WikiPediaPage(){
		adjacencyList = new ArrayList<String>();
	}
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = StringEscapeUtils.unescapeHtml(title);
	}


	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}


	/**
	 * @param text the text to set
	 */
	public void setText(String text){
		
		this.text = StringEscapeUtils.unescapeHtml(text);
		parseAdjacencyList();
	}


	/**
	 * @return the adjacencyList
	 */
	public List<String> getAdjacencyList() {
		return adjacencyList;
	}

	/**
	 * Add a link to the node adjacency List
	 * @param link
	 */
	public void addLink(String link){
		System.out.println("wikiLink:"+link);
		adjacencyList.add(link);
	}


	/**
	 * @return the pageRank
	 */
	public float getPageRank() {
		return pageRank;
	}


	/**
	 * @param pageRank the pageRank to set
	 */
	public void setPageRank(float pageRank) {
		this.pageRank = pageRank;
	}
	
	private void parseAdjacencyList(){
				
		int start = 0;

		while (true) {
			start = text.indexOf("[[", start);

			if (start < 0)
				break;

			int end = text.indexOf("]]", start);

			if (end < 0)
				break;

			String link = text.substring(start + 2, end);

			// skip empty links
			if (link.length() == 0) {
				start = end + 1;
				continue;
			}

			// skip special links
			if (link.indexOf(":") != -1) {
				start = end + 1;
				continue;
			}

			// if there is anchor text, get only article title
			int a;
			if ((a = link.indexOf("|")) != -1) {
				link = link.substring(0, a);
			}

			if ((a = link.indexOf("#")) != -1) {
				link = link.substring(0, a);
			}

			// ignore article-internal links, e.g., [[#section|here]]
			if (link.length() == 0 ) {
				start = end + 1;
				continue;
			}
			
			addLink(link.trim());

			start = end + 1;
		}
	}
	
	/**
	 * Checks to see if this page is an actual article, and not, for example,
	 * "File:", "Category:", "Wikipedia:", etc.
	 *
	 * @return <code>true</code> if this page is an actual article
	 */
	public boolean isArticle() {
		return !(getTitle().startsWith("File:") || getTitle().startsWith("Category:")
				|| getTitle().startsWith("Special:") || getTitle().startsWith("Wikipedia:")
				|| getTitle().startsWith("Wikipedia:") || getTitle().startsWith("Template:")
				|| getTitle().startsWith("Portal:"));
	}

}
