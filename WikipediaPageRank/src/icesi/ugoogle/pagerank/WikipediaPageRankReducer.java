package icesi.ugoogle.pagerank;

import icesi.ugoogle.NodoWritable;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WikipediaPageRankReducer extends
		Reducer<Text, ObjectWritable, Text, ObjectWritable> {

	protected void reduce(Text word, Iterable<ObjectWritable> values,
			Context context) throws IOException, InterruptedException {
		NodoWritable n = new NodoWritable();
		float pr = 0;

		for (ObjectWritable w : values) {
			if (w.getDeclaredClass().toString()
					.equals(FloatWritable.class.toString())) {
				pr += ((FloatWritable) w.get()).get();
			}
			if (w.getDeclaredClass().toString()
					.equals(NodoWritable.class.toString())) {
				n = (NodoWritable) w.get();
			}
		}
		NodoWritable m = new NodoWritable(pr, n.getListaDeAdyacencia());
		context.write(word, new ObjectWritable(m));
	}
}
