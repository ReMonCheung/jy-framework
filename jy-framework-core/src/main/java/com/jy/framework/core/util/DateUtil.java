package com.jy.framework.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 * @author zhanglj
 * 
 */
public class DateUtil {
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_CN = "yyyy年MM月dd日";
	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DATETIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	public final static String MONTH_FORMAT = "yyyy-MM";
	public final static String MONTH_FORMAT_CN = "yyyy年MM月";
	public final static String DAY_FORMAT = "yyyyMMdd";
	
	public static String formateDate(Date date,String pattern) {
		return DateFormatUtils.format(date, pattern);
	}
	
	public static String formateDate(long millis, String pattern){
		return DateFormatUtils.format(millis, pattern);
	}
	
	public static String formateDate(Calendar calendar, String pattern){
		return DateFormatUtils.format(calendar, pattern);
	}
	
	public static Date parse(String dateStr,String pattern) throws ParseException{
		return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, pattern);
	}
	
	public static void main(String[] args) {
		try {
//			System.out.println(formateDate(new Date(),DATE_FORMAT));
//			System.out.println(parse("2017-08-30 17:56:11",DATETIME_FORMAT));
			for (int i = 0; i < 5; i++) {
				new TestThread().start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class TestThread extends Thread {
		@Override
		public void run() {
			while(true){
				try {
                    this.join(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    System.out.println(this.getName()+":"+DateUtil.parse(formateDate(new Date(), DATETIME_FORMAT),DATETIME_FORMAT));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
			}
		}
	}
}
