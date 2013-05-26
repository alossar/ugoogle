package icesi.ugoogle.pagerank;

import icesi.ugoogle.NodoWritable;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.FloatWritable;
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

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		WikiPediaPageParser wikiPageParser = new WikiPediaInLineXmlPageSaxParser();
		String line = value.toString();
		WikiPediaPage page = null;
		try {
			page = wikiPageParser.parseLine(line);
		} catch (Exception e) {

		}
		if (page != null) {
			List<String> lista = page.getAdjacencyList();
			String[] arreglo = new String[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				arreglo[i] = lista.get(i);
			}
			NodoWritable p = new NodoWritable(1.0f, arreglo);
			title.set(page.getTitle());
			context.write(title, new ObjectWritable(p));

			for (String paginaAdyacente : page.getAdjacencyList()) {
				title.set(paginaAdyacente);
				context.write(title, new ObjectWritable(new FloatWritable(
						1 / page.getAdjacencyList().size())));
			}
		}
	}

}
