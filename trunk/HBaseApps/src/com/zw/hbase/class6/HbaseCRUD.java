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

public class HbaseCRUD {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		// conf.addResource("hbase-site-cluster.xml");//指定文件加载
		conf = HBaseConfiguration.create(conf);
		HBaseAdmin admin = new HBaseAdmin(conf);//HBaseAdmin负责跟表相关的操作如create,drop等 

		/**========创建表=========*/
		HTableDescriptor desc = new HTableDescriptor("blog");
		desc.addFamily(new HColumnDescriptor("article"));
		desc.addFamily(new HColumnDescriptor("author"));
		admin.createTable(desc );
		HTable table = new HTable(conf, Bytes.toBytes("blog"));//HTabel负责跟记录相关的操作如增删改查等
		
		/**=========插入数据=========*/
		Put put = new Put(Bytes.toBytes("1"));
		put.add(Bytes.toBytes("article"), Bytes.toBytes("title"), Bytes.toBytes("Head First HBase"));
		put.add(Bytes.toBytes("article"), Bytes.toBytes("content"), Bytes.toBytes("HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data."));
		put.add(Bytes.toBytes("article"), Bytes.toBytes("tags"), Bytes.toBytes("Hadoop,HBase,NoSQL"));
		put.add(Bytes.toBytes("author"), Bytes.toBytes("name"), Bytes.toBytes("hujinjun"));
		put.add(Bytes.toBytes("author"), Bytes.toBytes("nickname"), Bytes.toBytes("一叶渡江"));
		table.put(put);
		
		/**=========根据rowkey 查询数据=========*/
		Get get = new Get(Bytes.toBytes("1")); 
		Result result = table.get(get);
		for(KeyValue kv :result.list()){
		  System.out.println("family:" +Bytes.toString(kv.getFamily()));
		  System.out.println("qualifier:" +Bytes.toString(kv.getQualifier()));
		  System.out.println("value:" +Bytes.toString(kv.getValue()));
		  System.out.println("Timestamp:" +kv.getTimestamp());
		}
		  
		/**=========遍历查询=========*/
		Scan scan = new Scan();
		ResultScanner rs =null;
		try {
		  rs = table.getScanner(scan);
		  for (Result r : rs) {
		    for(KeyValue kv :r.list()){
		      System.out.println("family:" +Bytes.toString(kv.getFamily()));
		      System.out.println("qualifier:" +Bytes.toString(kv.getQualifier()));
		      System.out.println("value:" +Bytes.toString(kv.getValue()));
		    }
		  }
		} finally {
		  rs.close();
		}
		
		/**=========更新=========*/
		//查询更新前的值
		Get get2 = new Get(Bytes.toBytes("1"));
		get2.addColumn(Bytes.toBytes("author"), Bytes.toBytes("nickname"));
//		assertThat(Bytes.toString(table.get(get2).list().get(0).getValue()),is("一叶渡江"));
		//更新nickname为yedu
		Put put2 = new Put(Bytes.toBytes("1")); 
		put2.add(Bytes.toBytes("author"), Bytes.toBytes("nickname"), Bytes.toBytes("yedu"));
		table.put(put2);
		//查询更新结果
		Get get3 = new Get(Bytes.toBytes("1"));
		get3.addColumn(Bytes.toBytes("author"), Bytes.toBytes("nickname"));
//		assertThat(Bytes.toString(table.get(get3).list().get(0).getValue()),is("yedu"));
		
		//查询nickname的多个(本示例为2个)版本值
		Get get4 = new Get(Bytes.toBytes("1"));
		get4.addColumn(Bytes.toBytes("author"), Bytes.toBytes("nickname"));
		get4.setMaxVersions(2);
		List results = table.get(get4).list();
//		assertThat(results.size(),is(2));
//		assertThat(Bytes.toString(results.get(0).getValue()),is("yedu"));
//		assertThat(Bytes.toString(results.get(1).getValue()),is("一叶渡江"));
		
		/**=========删除记录=========*/
		//删除指定column
		Delete deleteColumn = new Delete(Bytes.toBytes("1"));
		deleteColumn.deleteColumns(Bytes.toBytes("author"),Bytes.toBytes("nickname"));
		table.delete(deleteColumn);
//		assertThat( table.get(get4).list(),nullValue());
		//删除所有column
		Delete deleteAll = new Delete(Bytes.toBytes("1"));
		table.delete(deleteAll);
//		assertThat(table.getScanner(scan).next(),nullValue());
		
		/**=========删除表=========*/
		admin.disableTable("blog");
		admin.deleteTable("blog");
//		assertThat(admin.tableExists("blog"),is(false));

	}
	

}
