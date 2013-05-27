package icesi.ugoogle.pagerank;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import co.edu.icesi.tics.distsys.project.wiki.parser.WikiPediaInLineXmlPageSaxParser;
import co.edu.icesi.tics.distsys.project.wiki.parser.WikiPediaPage;
import co.edu.icesi.tics.distsys.project.wiki.parser.WikiPediaPageParser;

public class WikipediaParsingPageRankMapper extends
		Mapper<LongWritable, Text, Text, ObjectWritable> {

	private Text title = new Text();
	private double initialPageRank = 1.0;
	private WikiPediaPageParser wikiPageParser = new WikiPediaInLineXmlPageSaxParser();

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String line = value.toString();
		WikiPediaPage page = null;
		try {
			page = wikiPageParser.parseLine(line);
		} catch (Exception e) {
		}
		if (page != null) {

			List<String> lista = page.getAdjacencyList();
			NodoWritable p = new NodoWritable(initialPageRank, lista);
			title.set(page.getTitle());
			context.write(title, new ObjectWritable(p));

			double newPageRank = 0;
			if (page.getAdjacencyList().size() > 0) {
				newPageRank = initialPageRank
						/ ((double) page.getAdjacencyList().size());
			}
			ObjectWritable obj = new ObjectWritable(new DoubleWritable(
					newPageRank));

			for (String paginaAdyacente : page.getAdjacencyList()) {
				title.set(paginaAdyacente);
				context.write(title, obj);
			}
		}
	}

}
