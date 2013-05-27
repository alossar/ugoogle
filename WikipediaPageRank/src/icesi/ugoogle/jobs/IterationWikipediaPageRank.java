package icesi.ugoogle.jobs;

import icesi.ugoogle.pagerank.NodoWritable;
import icesi.ugoogle.pagerank.WikipediaPageRankMapper;
import icesi.ugoogle.pagerank.WikipediaPageRankReducer;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;

public class IterationWikipediaPageRank extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {

		System.out.println("Beginning iteration job... ");
		Job pageRankJob = new Job(getConf());
		pageRankJob.setJarByClass(IterationWikipediaPageRank.class);
		pageRankJob.setJobName("pagerank");

		// Set the outputs for the Map
		pageRankJob.setMapOutputKeyClass(Text.class);
		pageRankJob.setMapOutputValueClass(ObjectWritable.class);

		// Set the outputs for the Job
		pageRankJob.setOutputKeyClass(Text.class);
		pageRankJob.setOutputValueClass(NodoWritable.class);

		pageRankJob.setMapperClass(WikipediaPageRankMapper.class);
		pageRankJob.setReducerClass(WikipediaPageRankReducer.class);
		// pageRankJob.setCombinerClass(WikipediaPageRankReducer.class);

		pageRankJob.setInputFormatClass(SequenceFileInputFormat.class);
		pageRankJob.setOutputFormatClass(SequenceFileOutputFormat.class);

		FileInputFormat.setInputPaths(pageRankJob, new Path(arg0[1]));

		FileSystem fs = FileSystem.get(getConf());
		fs.delete(new Path(arg0[2]), true);
		FileOutputFormat.setOutputPath(pageRankJob, new Path(arg0[2]));

		boolean success = pageRankJob.waitForCompletion(true);

		if (success) {
			fs.delete(new Path(arg0[1]), true);
			fs.rename(new Path(arg0[2]), new Path(arg0[1]));
		}

		return success ? 0 : 1;
	}

}
