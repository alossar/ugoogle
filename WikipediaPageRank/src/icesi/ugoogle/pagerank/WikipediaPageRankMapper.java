package icesi.ugoogle.pagerank;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WikipediaPageRankMapper extends
		Mapper<Text, NodoWritable, Text, ObjectWritable> {

	private Text title = new Text();

	@Override
	protected void map(Text key, NodoWritable value, Context context)
			throws IOException, InterruptedException {
		NodoWritable n = (NodoWritable) value;
		double newPageRank = 0.0;
		if (n.getListaDeAdyacencia().size() > 0) {
			newPageRank = n.getPageRank()
					/ ((double) n.getListaDeAdyacencia().size());
		}
		ObjectWritable objNodo = new ObjectWritable(n);
		context.write(key, objNodo);

		ObjectWritable objPageRank = new ObjectWritable(new DoubleWritable(
				newPageRank));
		for (String paginaAdyacente : n.getListaDeAdyacencia()) {
			title.set(paginaAdyacente);
			context.write(title, objPageRank);
		}
	}

}
