package icesi.ugoogle.pagerank;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WikipediaFinalPageRankReducer extends
		Reducer<Text, ObjectWritable, Text, FloatWritable> {

	protected void reduce(Text word, Iterable<ObjectWritable> values,
			Context context) throws IOException, InterruptedException {
		float pr = 0;

		for (ObjectWritable w : values) {
			if (w.getDeclaredClass().toString()
					.equals(FloatWritable.class.toString())) {
				pr += ((FloatWritable) w.get()).get();
			}
		}
		FloatWritable value = new FloatWritable(pr);
		context.write(word, value);
	}
}
