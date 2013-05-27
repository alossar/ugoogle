package icesi.ugoogle.pagerank;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WikipediaPageRankReducer extends
		Reducer<Text, ObjectWritable, Text, NodoWritable> {

	protected void reduce(Text word, Iterable<ObjectWritable> values,
			Context context) throws IOException, InterruptedException {
		NodoWritable n = new NodoWritable();
		double pr = 0;
		for (ObjectWritable w : values) {
			if (w.getDeclaredClass().toString()
					.equals(DoubleWritable.class.toString())) {
				pr += ((DoubleWritable) w.get()).get();
			}
			if (w.getDeclaredClass().toString()
					.equals(NodoWritable.class.toString())) {
				n = (NodoWritable) w.get();
			}
		}
		NodoWritable obj = new NodoWritable(pr, n.getListaDeAdyacencia());
		context.write(word, obj);
	}
}
