package com.zw.hbase.class6;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {
	
	public static Integer converIp2Int(String ip) {
		Integer returnInt = null;
		
		String[] ips = ip.split("\\.");
		int addr =(Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16)  
        	+ (Integer.parseInt(ips[2]) << 8) + Integer.parseInt(ips[3]);  
		returnInt = Integer.valueOf(addr);
		
		return returnInt;

	}
	public static String converInt2Ip(int a){
		StringBuffer sb = new StringBuffer();
		int b = (a >> 0) & 0xff;
		sb.append(b + ".");
		b = (a >> 8) & 0xff;
		sb.append(b + ".");
		b = (a >> 16) & 0xff;
		sb.append(b + ".");
		b = (a >> 24) & 0xff;
		sb.append(b);
		return sb.toString();
	}
   
   public static Long converTimeStemp2Long(String timeStamp,String format) {
	   Long returnLong = null;
	   try{
		   
		   SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.ENGLISH);
		   //
		   Date date = sdf.parse(timeStamp);
		   returnLong = new Long(date.getTime()); 
	   }
	   catch(Exception e){
		   returnLong = null;
	   }
	    
	   return returnLong;
   }
   
   public static String converLong2Date(Long a,String format) {
	   String retStr = null;
	   try{
		   
		   SimpleDateFormat sdf = new SimpleDateFormat(format);
		   //
		   Date date = new Date(a);
		   retStr = sdf.format(date);
	   }
	   catch(Exception e){
		   retStr = "";
	   }
	    
	   return retStr;
   }
   
   public static Long dateToLong(String timeStamp,String format) {
	   Long returnLong = null;
	   try{
		   
		   SimpleDateFormat sdf1 = new SimpleDateFormat(format,Locale.ENGLISH);
		   SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		   //
		   Date date = sdf1.parse(timeStamp);
		   String dateStr=sdf2.format(date);
		   returnLong = new Long(dateStr); 
	   }
	   catch(Exception e){
		   returnLong = null;
	   }
	    
	   return returnLong;
   }
   
   public static String LongToDate(Long a,String format) {
	   String retStr = null;
	   try{
		   
		   SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		   SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		   //
		   
		   Date date = sdf2.parse(Long.toString(a));
		   retStr = sdf1.format(date);
	   }
	   catch(Exception e){
		   retStr = "";
	   }
	    
	   return retStr;
   }
   
   public static void main(String[] args) throws Exception {
	   System.out.println("converIp2Int:"+converIp2Int("175.44.16.122"));
	   System.out.println("converInt2Ip:"+converInt2Ip(999657558));
	   
	   System.out.println("converTimeStemp2Long:"+converTimeStemp2Long("29/Sep/2013:00:13:52","dd/MMM/yyyy:hh:mm:ss"));
	   System.out.println("converLong2Date:"+converLong2Date(1380109031000L,"dd/MM/yyyy hh:mm:ss"));
	   
	   System.out.println("dateToLong:"+dateToLong("29/Sep/2013:00:13:52","dd/MMM/yyyy:hh:mm:ss"));
	   System.out.println("converLong2Date:"+LongToDate(20130912120303L,"dd/MM/yyyy hh:mm:ss"));
   }

}
