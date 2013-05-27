package icesi.ugoogle.jobs;

import icesi.ugoogle.pagerank.NodoWritable;
import icesi.ugoogle.pagerank.WikipediaPageRankReducer;
import icesi.ugoogle.pagerank.WikipediaParsingPageRankMapper;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;

public class ParsingWikipediaPageRank extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {

		Job parsingJob = new Job(getConf());
		parsingJob.setJobName("parsingjob");

		parsingJob.setJarByClass(ParsingWikipediaPageRank.class);
		parsingJob.setMapperClass(WikipediaParsingPageRankMapper.class);
		parsingJob.setReducerClass(WikipediaPageRankReducer.class);

		// Set the outputs for the Map
		parsingJob.setMapOutputKeyClass(Text.class);
		parsingJob.setMapOutputValueClass(ObjectWritable.class);

		// Set the outputs for the Job
		parsingJob.setOutputKeyClass(Text.class);
		parsingJob.setOutputValueClass(NodoWritable.class);

		parsingJob.setInputFormatClass(TextInputFormat.class);
		parsingJob.setOutputFormatClass(SequenceFileOutputFormat.class);

		FileInputFormat.setInputPaths(parsingJob, new Path(arg0[0]));
		FileSystem fs = FileSystem.get(getConf());
		fs.delete(new Path(arg0[1]), true);
		FileOutputFormat.setOutputPath(parsingJob, new Path(arg0[1]));

		boolean success = parsingJob.waitForCompletion(true);

		return success ? 0 : 1;
	}

}
