package icesi.ugoogle;

import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.conf.Configuration;

public class Main {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new WikipediaParsingPageRank(), args);
		res = ToolRunner.run(new Configuration(),
				new WikipediaIterationPageRank(), args);
		res = ToolRunner.run(new Configuration(),
				new WikipediaFinalPageRank(), args);
		System.exit(res);
	}
}
