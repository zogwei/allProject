package com.zw.hadoop.class4;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class LogSubcatalogAnalysis {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
//		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		
		Job job = new Job(conf,"analysis sub  catalog");
		job.setNumReduceTasks(1);
		job.setJobName("analysis sub  catalog");
		job.setJarByClass(LogSubcatalogAnalysis.class);
		job.setMapperClass(LogSubcatalogAnalysisMapper.class);
		job.setReducerClass(LogSubcatalogAnalysisReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
//		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
//		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		FileInputFormat.addInputPaths(job, "/testMapReduce/logSrc/Sep-2013/Sep-2013-0," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-1," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-2," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-3," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-4," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-5," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-6," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-7," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-8," +
				"/testMapReduce/logSrc/Sep-2013/Sep-2013-9");
		FileOutputFormat.setOutputPath(job, new Path("/Output/log"));
		System.exit(job.waitForCompletion(true)?0:1);
	}

}

class LogSubcatalogAnalysisMapper extends Mapper<Object,Text,Text,IntWritable>{
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	public void map(Object key,Text value,Context context) throws IOException,InterruptedException{
		StringTokenizer itr = new StringTokenizer(value.toString());
		int index = 0;
		while(index<6){
			index++;
			itr.nextToken();
		}
		word.set(itr.nextToken());
		context.write(word, one);
	}
}

class LogSubcatalogAnalysisReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
	private final static IntWritable result = new IntWritable(0); 
	
	public void reduce(Text key,Iterable<IntWritable> values,Context context)throws IOException,InterruptedException{
		int sum = 0;
		for(IntWritable val : values){
			sum += val.get();
		}
		result.set(sum);
		context.write(key, result);
	}
}


