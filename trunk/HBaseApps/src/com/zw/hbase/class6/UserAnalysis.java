package com.zw.hbase.class6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;

//import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public class UserAnalysis {
	private static final Logger LOG = Logger.getLogger(UserAnalysis.class);
	private final static String TABLE_NAME = "userlog";
	private final static String family = "i";
	private final static String qualifier = "v";

	public static class UserMapper extends Mapper {

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] slist = value.toString().split("\t", 2);

			String[] slead = slist[0].split(" ");
			String date = slead[0];
			String uid = slead[3];
			LOG.info("uid:" + uid + " date:" + date);
			String keystring = uid + ":" + date;
			LOG.info("keystring:" + keystring);

			if (uid != "0") {
				context.write(new Text(Bytes.toBytes(keystring)), value);
			}
		}
	}

	public static class UserReducer extends Reducer {

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			String value = "";
			// Collections.sort(values);

			List vl = new ArrayList();
			for (Text v : values)

			{
				vl.add(v.toString());
				// value +=v.toString()+"\n";

			}

			Collections.sort(vl);
			value = StringUtils.join("\n", vl);
			LOG.info("key:" + key.toString());
			Put put = new Put(Bytes.toBytes(key.toString()));
			put.add(family.getBytes(), qualifier.getBytes(), value.getBytes());
			context.write(new ImmutableBytesWritable(TABLE_NAME.getBytes()),
					put);
		}
	}

	public static Job configureJob(Configuration conf, String[] args)
			throws IOException {
		// conf.set(TableOutputFormat.OUTPUT_TABLE,TABLE_NAME);
		// conf.set("hbase.zookeeper.quorum", "Hadoop46,Hadoop47,Hadoop48");
		// conf.set("hbase.zookeeper.property.clientPort", "22228");

		Job job = new Job(conf, "user anaysis");
		job.setJarByClass(UserAnalysis.class);
		job.setMapperClass(UserMapper.class);
		job.setInputFormatClass(TextInputFormat.class);
		TableMapReduceUtil.initTableReducerJob(TABLE_NAME, // output table
				null, // reducer class
				job);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setReducerClass(UserReducer.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));

		// job.setOutputFormatClass(TableOutputFormat.class);
		return job;
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 1) {
			System.err.println("Usage: useras  ");
			System.exit(2);
		}

		Job job = configureJob(conf, otherArgs);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}