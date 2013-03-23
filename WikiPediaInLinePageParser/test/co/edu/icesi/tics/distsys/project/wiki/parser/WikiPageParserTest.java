/**
 * 
 */
package co.edu.icesi.tics.distsys.project.wiki.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * @author jlmontoya
 *
 */
public class WikiPageParserTest {
	
	private WikiPediaPageParser wikiPageParser;
	
	@Before
	public void setUp(){
		wikiPageParser = new WikiPediaInLineXmlPageSaxParser();
	}

	/**
	 * Test method for {@link co.edu.icesi.tics.distsys.project.wiki.parser.WikiPediaInLineXmlPageSaxParser#parseLine(java.lang.String)}.
	 */
	@Test
	public void testParseSimpleLine() {
		String line = "<page><title>Node1</title><revsion><text>This is the text of the Node1, connected to [[Node2]] and [[Node4]]</text></revsion></page>";
		WikiPediaPage wikiPage = wikiPageParser.parseLine(line);
			
		Assert.assertNotNull(wikiPage);
		Assert.assertNotNull(wikiPage.getTitle());
		Assert.assertNotNull(wikiPage.getText());
		
		System.out.println("wikiPage.Text="+wikiPage.getText());
	}

}
