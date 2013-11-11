package com.zw.hadoop.class4;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class LogIPAnalysis {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
//		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		
		Job job = new Job(conf,"analysis ip count");
		job.setNumReduceTasks(1);
		job.setJobName("analysis ip count");
		job.setJarByClass(LogIPAnalysis.class);
		job.setMapperClass(LogIPAnalysisMapper.class);
		job.setReducerClass(LogIPAnalysisReducer.class);
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

class LogIPAnalysisMapper extends Mapper<Object,Text,Text,IntWritable>{
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	public void map(Object key,Text value,Context context) throws IOException,InterruptedException{
		StringTokenizer itr = new StringTokenizer(value.toString());
		//第一个ip段
		word.set(itr.nextToken());
		context.write(word, one);
	}
}

class LogIPAnalysisReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
	private final static IntWritable result = new IntWritable(0); 
	 
	//最后一条记录就是独立ip数 结果是
	public void reduce(Text key,Iterable<IntWritable> values,Context context)throws IOException,InterruptedException{
		result.set(1+result.get());
		context.write(key, result);
	}
}
