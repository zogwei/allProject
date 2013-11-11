package com.zw.hbase.class6;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
//import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class IPVistSubNumHbase2Hbase {

	/**第四小题
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		 Configuration conf = new Configuration();
		 conf = HBaseConfiguration.create(conf);

		 Job job = new Job(conf, "HBase_IPVistSubNum");
		 job.setJarByClass(IPVistSubNumHbase2Hbase.class);
		 
		 Scan scan = new Scan();
		 scan.addColumn(Bytes.toBytes("info"),Bytes.toBytes("url"));
		 TableMapReduceUtil.initTableMapperJob("access-log", scan,IPVistSubNumMapper.class,ImmutableBytesWritable.class, ImmutableBytesWritable.class, job);
		 TableMapReduceUtil.initTableReducerJob("total-access",IPVistSubNumReducer.class, job);
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}

class IPVistSubNumMapper extends TableMapper <ImmutableBytesWritable, ImmutableBytesWritable> {
	@Override
	public void map(ImmutableBytesWritable row, Result values, Context context)
			throws IOException {
		ImmutableBytesWritable value = null;
		ImmutableBytesWritable key = null;
		// get ip 不能直接分隔”-“，ip的int可能包括负号
		String ipAndTime = Bytes.toString(row.get());
		String ip = ipAndTime.substring(0,ipAndTime.lastIndexOf("-"));
		
		key = new ImmutableBytesWritable(Bytes.toBytes(Integer.valueOf(ip).intValue()));
		
		//处理同一秒，同一个ip访问了多个url情况
		String url = Bytes.toString(values.getColumnLatest(Bytes.toBytes("info"), Bytes.toBytes("url")).getValue());
		String[] urls = url.split(",");
		for(String item : urls){
			value = new ImmutableBytesWritable(Bytes.toBytes(item));
			try {
				context.write(key, value);
			} catch (InterruptedException e) {
				throw new IOException(e);
			}
		}
	}
}

class IPVistSubNumReducer extends TableReducer <ImmutableBytesWritable, ImmutableBytesWritable, ImmutableBytesWritable> {
	 @Override
	 public void reduce(ImmutableBytesWritable key,Iterable<ImmutableBytesWritable> values,Context context) throws IOException, InterruptedException {
		 //set 去重 
		 Set<String> urlSet = new HashSet<String>();
		  for (ImmutableBytesWritable val : values) {
			  urlSet.add(Bytes.toString(val.get()));
		  }
		  Put put = new Put(key.get());
		  put.add(Bytes.toBytes("info"), Bytes.toBytes("urlTotalNum"),Bytes.toBytes(urlSet.size()));
		  context.write(key, put);
		  
	 }
}



