package icesi.ugoogle.pagerank;

import icesi.ugoogle.NodoWritable;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WikipediaPageRankMapper extends
		Mapper<Text, ObjectWritable, Text, ObjectWritable> {

	private Text title = new Text();

	@Override
	protected void map(Text key, ObjectWritable value, Context context)
			throws IOException, InterruptedException {
		NodoWritable n = (NodoWritable) value.get();
		float nuevoPR = n.getListaDeAdyacencia().length == 0 ? 0 : n.getPr()
				/ (n.getListaDeAdyacencia().length);
		FloatWritable pr = new FloatWritable(nuevoPR);
		context.write(key, new ObjectWritable(n));
		for (String paginaAdyacente : n.getListaDeAdyacencia()) {
			title.set(paginaAdyacente);
			context.write(title, new ObjectWritable(pr));
		}
	}

}
