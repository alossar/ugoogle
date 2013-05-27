package icesi.ugoogle.jobs;

import icesi.ugoogle.pagerank.WikipediaFinalPageRankReducer;
import icesi.ugoogle.pagerank.WikipediaPageRankMapper;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

public class FinalWikipediaPageRank extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {

		Job finalPageRankJob = new Job(getConf());
		System.out.println("Beginning Final job... ");
		finalPageRankJob.setJarByClass(FinalWikipediaPageRank.class);
		finalPageRankJob.setJobName("finalpagerank");

		// Set the outputs for the Map
		finalPageRankJob.setMapOutputKeyClass(Text.class);
		finalPageRankJob.setMapOutputValueClass(ObjectWritable.class);

		// Set the outputs for the Job
		finalPageRankJob.setOutputKeyClass(Text.class);
		finalPageRankJob.setOutputValueClass(DoubleWritable.class);

		finalPageRankJob.setMapperClass(WikipediaPageRankMapper.class);
		finalPageRankJob.setReducerClass(WikipediaFinalPageRankReducer.class);
		// finalPageRankJob.setCombinerClass(WikipediaFinalPageRankReducer.class);

		finalPageRankJob.setInputFormatClass(SequenceFileInputFormat.class);
		finalPageRankJob.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(finalPageRankJob, new Path(arg0[1]));
		FileSystem fs = FileSystem.get(getConf());
		fs.delete(new Path(arg0[2]), true);
		FileOutputFormat.setOutputPath(finalPageRankJob, new Path(arg0[2]));

		boolean success = finalPageRankJob.waitForCompletion(true);

		return success ? 0 : 1;
	}

}
