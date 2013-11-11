package com.zw.hbase.class6;

import java.io.IOException;
import java.util.List;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.CompareFilter;

import org.apache.hadoop.hbase.util.Bytes;

public class ImportLogToHbaseView {

	/**
	 * 第三小题
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		// conf.addResource("hbase-site-cluster.xml");//指定文件加载
		conf = HBaseConfiguration.create(conf);
		HTable table = new HTable(conf, Bytes.toBytes("access-log"));//HTabel负责跟记录相关的操作如增删改查等

//		/**=========根据rowkey 查询数据=========*/
//		//175.44.19.36 - - [29/Sep/2013:00:10:21 +0800] "POST /wp-comments-post.php HTTP/1.1" 302 517 "http://dongxicheng.org/mapreduce-nextgen/client-codes/" "Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0"
//		Get get = new Get(Bytes.toBytes(CommonUtil.converIp2Int("175.44.19.36")+"-"+CommonUtil.dateToLong("29/Sep/2013:00:10:21", "dd/MMM/yyyy:hh:mm:ss"))); 
//		Result result = table.get(get);
//		
//		for(KeyValue kv :result.list()){
//		  System.out.println("rowkey:" +Bytes.toString(result.getRow()));
//		  System.out.println("family:" +Bytes.toString(kv.getFamily()));
//		  System.out.println("qualifier:" +Bytes.toString(kv.getQualifier()));
//		  System.out.println("value:" +Bytes.toString(kv.getValue()));
//		  System.out.println("Timestamp:" +kv.getTimestamp());
//		}
		  
		/**=========遍历查询=========*/
		Scan scan = new Scan();
		ResultScanner rs =null;
//		String startRowkey = CommonUtil.converIp2Int("175.44.19.36")+"-"+CommonUtil.dateToLong("29/Sep/2012:00:01:01", "dd/MMM/yyyy:hh:mm:ss");
//		String stopRowKey = CommonUtil.converIp2Int("175.44.19.36")+"-"+CommonUtil.dateToLong("30/Sep/2014:00:01:01", "dd/MMM/yyyy:hh:mm:ss");
//		scan.setStartRow(Bytes.toBytes(startRowkey));
//		scan.setStopRow(Bytes.toBytes(stopRowKey));
		
		
		String ipstr = CommonUtil.converIp2Int("175.44.19.36")+"";
		String matchStr = null;
		//第三小题第一道题的正则表达式
//		matchStr = ipstr+"-2013[0-9]{10}";
		//第三小题第二道题的正则表达式
		matchStr ="("+ ipstr+"-2013[0-9]{4}(00|01|02|03|04|05|06|07|08|09|10|11)[0-9]{4})|("+ipstr+"-2013[0-9]{4}120000)";
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(matchStr));  
		scan.setFilter(filter);
		
		try {
		  rs = table.getScanner(scan);
			for (Result r : rs) {
				System.out.println("rowkey:" + Bytes.toString(r.getRow()));
				for (KeyValue kv : r.list()) {
					System.out.println("family:"+Bytes.toString(kv.getFamily()));
					System.out.println("qualifier:"+Bytes.toString(kv.getQualifier()));
					System.out.println("value:" + Bytes.toString(kv.getValue()));
				}
			}
		} finally {
		  rs.close();
		}

	}
}
