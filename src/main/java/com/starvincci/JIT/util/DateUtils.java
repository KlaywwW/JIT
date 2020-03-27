package com.starvincci.JIT.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.omg.CORBA.INTERNAL;

/**
 * 时间工具类
 * 
 * @author ZHANGZHE
 * @return 
 *
 */
public class DateUtils {
	
	
	
	//获取两个时间 差对应的天数
	public static String getDayDiffer(Timestamp time1,Timestamp time2) {
		Date date1=new Date();
		Date date2=new Date();
		date1=time1;
		date2=time2;
		long diff = date2.getTime() - date1.getTime();//这样得到的差值是微秒级别
	    long days = diff / (1000 * 60 * 60 * 24);
	    long hours=diff / (1000 * 60 * 60);
	    Integer j=Math.round(hours);
	    if(j<1) {
	    	j=1;
	    }
	    Integer i=Math.round(days);
	    if(i<1) {
	    	i=1;
	    }
	    String mes=i+"天"+"("+j+"H)";
	    return  mes;
		
	}
	public static Integer getHourDiffer(Timestamp time1,Timestamp time2) {
		Date date1=new Date();
		Date date2=new Date();
		date1=time1;
		date2=time2;
		long diff = date2.getTime() - date1.getTime();//这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours=diff / (1000 * 60 * 60);
		Integer j=Math.round(hours);
//		if(j<1) {
//			j=1;
//		}
//		Integer i=Math.round(days);
//		if(i<1) {
//			i=1;
//		}
//		String mes=i+"天"+"("+j+"H)";
		return  j;

	}
	

}
