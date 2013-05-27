package icesi.ugoogle.pagerank;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WikipediaFinalPageRankReducer extends
		Reducer<Text, ObjectWritable, Text, DoubleWritable> {

	protected void reduce(Text word, Iterable<ObjectWritable> values,
			Context context) throws IOException, InterruptedException {
		double pr = 0;

		for (ObjectWritable w : values) {
			if (w.getDeclaredClass().toString()
					.equals(DoubleWritable.class.toString())) {
				pr += ((DoubleWritable) w.get()).get();
			}
		}
		DoubleWritable value = new DoubleWritable(pr);
		context.write(word, value);
	}
}
