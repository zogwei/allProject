package com.jw.ess.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil
{
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String NUMBER_DATE_FORMAT = "yyyyMMdd";

	public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd"; 
	
	public static final String DATE_FORMAT="yyyy-MM";
	
	public static int currentTimeSecs()
	{
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static int transformTimeSecs(String date)
	{
		SimpleDateFormat format = new SimpleDateFormat(INPUT_DATE_FORMAT);
		Date time = null;
		try 
		{
			time = format.parse(date);
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		int secs = (int) (time.getTime()/1000);
		return  secs;
	}
	
	public static int transformTimeSecs(String date,String strFormat)
	{
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		Date time = null;
		try 
		{
			time = format.parse(date);
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		int secs = (int) (time.getTime()/1000);
		return  secs;
	}
	
	public static String transformString(int timeSecs,String strFormat){
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		return format.format(timeSecsformDate(timeSecs));
	}
	
	public static int nextDayTimeSecs(int timeSecs){
		
		return timeSecs+86400;
		
	}

	public static int nextMonthTimeSecs(int timeSecs){
		
		Date date=new Date(Long.parseLong(timeSecs+"000"));
		
		Calendar cal=Calendar.getInstance();
		
		cal.setTime(date);
		
		cal.add(Calendar.MONTH,1);
		
		return (int) (cal.getTimeInMillis() / 1000);
	}
	
	//获取天，精确到天(xx年xx月xx日 00:00:00)
	public static int transformDay(int timeSecs){
		String strDate=transformString(timeSecs,INPUT_DATE_FORMAT);
		return transformTimeSecs(strDate,INPUT_DATE_FORMAT);
	}
	
	//获取月，精确到月(xx年xx月01日 00:00:00)
	public static int transformMonth(int timeSecs){
		String strDate=transformString(timeSecs,DATE_FORMAT);
		return transformTimeSecs(strDate,DATE_FORMAT);
	}
	
	public static  Date timeSecsformDate(int timeSecs){
		return new Date((long)timeSecs*1000);
	}

	public static void main(String[] args) {
		System.out.println(timeSecsformDate(transformDay(currentTimeSecs())));
		System.out.println(timeSecsformDate(transformMonth(currentTimeSecs())));
		System.out.println(currentTimeSecs());
		System.out.println(timeSecsformDate(currentTimeSecs()));
		System.out.println(currentTimeSecs()-1000);
		System.out.println(transformTimeSecs("2011-12-14"));
		System.out.println("+===========");
		System.out.println(transformString(1325159287,INPUT_DATE_FORMAT));
	}
}
