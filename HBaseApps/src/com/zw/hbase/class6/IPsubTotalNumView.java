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

import org.apache.hadoop.hbase.util.Bytes;

public class IPsubTotalNumView {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		// conf.addResource("hbase-site-cluster.xml");//指定文件加载
		conf = HBaseConfiguration.create(conf);
		HTable table = new HTable(conf, Bytes.toBytes("total-access"));//HTabel负责跟记录相关的操作如增删改查等
		
		/**=========根据rowkey 查询数据=========*/
		Get get = new Get(Bytes.toBytes(CommonUtil.converIp2Int("175.44.19.36"))); 
//		Get get = new Get(Bytes.toBytes(999657558)); 
		Result result = table.get(get);
		System.out.println(Bytes.toString(result.getRow()));
		if(result.list()==null){
			System.out.println("no result");
		}
		else{
			for(KeyValue kv :result.list()){
			  System.out.println("family:" +Bytes.toString(kv.getFamily()));
			  System.out.println("qualifier:" +Bytes.toString(kv.getQualifier()));
			  System.out.println("value:" +Bytes.toInt(kv.getValue()));
			  System.out.println("Timestamp:" +kv.getTimestamp());
			}
		}

	}
	

}
