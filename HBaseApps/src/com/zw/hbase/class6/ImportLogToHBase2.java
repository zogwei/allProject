package com.zw.hbase.class6;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class ImportLogToHBase2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		 Configuration conf = new Configuration();
		 conf = HBaseConfiguration.create(conf);
		 //set hbase-site.xml
//		 InputStream in = null;
//		 try{
//			 in = ImportLogToHBase.class.getResourceAsStream("hbase-site.xml");
//			 conf.addResource(in);
//		 }
//		 catch(Exception e){
//			 e.printStackTrace();
//		 }
//		 finally{
//			 in.close();
//		 }
		 Job job = new Job(conf, "HBase_ImportLogToHbase");
		 job.setJarByClass(ImportLogToHBase2.class);
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
		 
		 Scan scan = new Scan();
//		 scan.addColumn(Bytes.toBytes("author"),Bytes.toBytes("nickname"));
//		 scan.addColumn(Bytes.toBytes("article"),Bytes.toBytes("tags"));
//		 TableMapReduceUtil.initTableMapperJob("access-info", scan,Mapper.class,ImmutableBytesWritable.class, ImmutableBytesWritable.class, job);
		 job.setMapperClass(ImportLog2Mapper.class);
		 TableMapReduceUtil.initTableReducerJob("access-log",ImportLog2Reducer.class, job);
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}


class ImportLog2Mapper extends Mapper<Object,Text,Text, Text>{
	
	public void map(Object key,Text value,Context context) throws IOException,InterruptedException{
//			ImmutableBytesWritable outKey  = null;
//		    ImmutableBytesWritable outValue = null;
		    String ipStr = null;
		    String timeStampStr = null;
		    String subDic = null;
		    Integer ipInt = null;
		    Long timeStampLong = null;
		    
		    //获得ip 端口  访问的网站子目录
		    StringTokenizer itr = new StringTokenizer(value.toString());
		    int index = 0;
		    while(itr.hasMoreTokens()){
				//ip
		    	if(index==0){
		    		ipStr = itr.nextToken();
				}
		    	//timeStamp
		    	else if(index==3){
		    		timeStampStr = itr.nextToken();
				}
		    	//timeStamp
		    	else if(index==6){
		    		subDic = itr.nextToken();
				}
		    	else{
		    		itr.nextToken();
		    	}
				index++;
			}
		    
		    /**
		     * 转换IP 和端口
		     */
		    ipInt = CommonUtil.converIp2Int(ipStr);
		    //e.g:[29/Sep/2013:00:13:51  ,remove the first '['
		    timeStampLong = CommonUtil.dateToLong(timeStampStr.substring(1),"dd/MMM/yyyy:hh:mm:ss");
		    
		    //输出
//		    outKey = new ImmutableBytesWritable(Bytes.toBytes(ipInt+"-"+timeStampLong));
//		    outValue = new ImmutableBytesWritable(Bytes.toBytes(subDic));
		    
		    try {
		          context.write(new Text(Bytes.toBytes(ipInt+"-"+timeStampLong)),new Text(Bytes.toBytes(subDic)));
		    } catch (InterruptedException e) {
		         throw new IOException(e);
		    }
	   }
}

class ImportLog2Reducer extends TableReducer <Text, Text, ImmutableBytesWritable> {
	 @Override
	 public void reduce(Text key,Iterable<Text> values,
		   Context context) throws IOException, InterruptedException {
		  String url="";
		  for (Text val : values) {
			  url += (url.length()>0?",":"")+val.toString();
		  }
		  Put put = new Put(Bytes.toBytes(key.toString()));
		  put.add(Bytes.toBytes("info"), Bytes.toBytes("url"),Bytes.toBytes(url));
		  context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())), put);
	 }
}



//class Mapper extends TableMapper <ImmutableBytesWritable, ImmutableBytesWritable> {
//	 public Mapper() {}
//	 @Override
//	 public void map(ImmutableBytesWritable row, Result values,Context context) throws IOException {
//	    ImmutableBytesWritable value = null;
//	    ImmutableBytesWritable key  = null;
//	    String ipStr = null;
//	    String timeStampStr = null;
//	    String subDic = null;
//	    int ipInt = 0;
//	    long timeStampLong = 0L;
//	    
//	    //获得ip 端口  访问的网站子目录
//	    
//	    //转换IP 和端口
//	    
//	    //输出
//	    key = new ImmutableBytesWritable(Bytes.toBytes(ipInt+"-"+timeStampLong));
//	    value = new ImmutableBytesWritable(Bytes.toBytes(subDic));
//	    
//	    try {
//	          context.write(key,value);
//	    } catch (InterruptedException e) {
//	         throw new IOException(e);
//	    }
//	   }
//	}
