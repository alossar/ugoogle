package icesi.ugoogle;

import icesi.ugoogle.jobs.FinalWikipediaPageRank;
import icesi.ugoogle.jobs.IterationWikipediaPageRank;
import icesi.ugoogle.jobs.ParsingWikipediaPageRank;

import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.conf.Configuration;

public class Main {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new ParsingWikipediaPageRank(), args);
		res = ToolRunner.run(new Configuration(), new IterationWikipediaPageRank(),
				args);
		res = ToolRunner.run(new Configuration(), new FinalWikipediaPageRank(),
				args);
		System.exit(res);
	}
}
