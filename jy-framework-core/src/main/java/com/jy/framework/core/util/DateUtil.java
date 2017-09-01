package com.jy.framework.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 * @author zhanglj
 * 
 */
public class DateUtil {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_CN = "yyyy年MM月dd日";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String MONTH_FORMAT = "yyyy-MM";
	public static final String MONTH_FORMAT_CN = "yyyy年MM月";
	public static final String DAY_FORMAT = "yyyyMMdd";
	
	/**
	 * 闰年校验
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * 
	 * @param type   类型<br>
	 * @see 
	 * 			Calendar.YEAR<br>
	 *      	Calendar.MONTH<br>
	 *      	Calendar.WEEK_OF_YEAR<br>
	 *      	Calendar.WEEK_OF_MONTH<br>
	 *      	Calendar.DATE<br>
	 *      	Calendar.DAY_OF_MONTH<br>
	 *      	Calendar.DAY_OF_YEAR<br>
	 *      	Calendar.DAY_OF_WEEK<br>
	 *      	Calendar.DAY_OF_WEEK_IN_MONTH<br>
	 *      	Calendar.HOUR<br>
	 *      	Calendar.MINUTE<br>
	 *      	Calendar.SECOND<br>
	 *      	Calendar.MILLISECOND<br>
	 * @param value  数量
	 * @param date   指定日期
	 * @return
	 */
	public static Date computeTime(int type, int value, Date date){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(type, value);
		return gc.getTime();
	}
	
	
	/**
	 * 获取指定日期的某月第一天或最后一天
	 * @param date   指定日期
	 * @param isLast 是否最后一天
	 * @return
	 */
	public static Date getMonthFirstOrLastDay(Date date, boolean isLast) {
		Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	if(isLast) {
    		calendar.add(Calendar.MONTH, 1);
        	calendar.add(Calendar.DAY_OF_MONTH, -1);
    	}
    	return calendar.getTime();
	}
	
	
	/**
	 * 日期格式化
	 * @param date      日期
	 * @param pattern   日期格式
	 * @return
	 * ps:如果日期格式为空，默认格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String formateDate(Date date,String pattern) {
		if(StringUtils.isBlank(pattern))
			pattern = DATETIME_FORMAT;
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
	 * 时间戳格式化
	 * @param millis   时间戳
	 * @param pattern  日期格式
	 * @return
	 * ps:如果日期格式为空，默认格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String formateDate(long millis, String pattern) {
		if(StringUtils.isBlank(pattern))
			pattern = DATETIME_FORMAT;
		return DateFormatUtils.format(millis, pattern);
	}
	
	/**
	 * 日历格式化
	 * @param calendar  日历
	 * @param pattern   日格式
	 * @return
	 * ps:如果日期格式为空，默认格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String formateDate(Calendar calendar, String pattern) {
		if(StringUtils.isBlank(pattern))
			pattern = DATETIME_FORMAT;
		return DateFormatUtils.format(calendar, pattern);
	}
	
	/***
	 * 日期解析
	 * @param dateStr  日期字符串
	 * @param pattern  日期格式
	 * @return 
	 * ps:如果日期格式为空，默认格式为：yyyy-MM-dd HH:mm:ss
	 * @throws ParseException
	 */
	public static Date parse(String dateStr, String pattern) throws ParseException {
		if(StringUtils.isBlank(pattern))
			pattern = DATETIME_FORMAT;
		return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, pattern);
	}
	
	
	//=======================================测试=========================================//
	public static void main(String[] args) {
		try {
//			System.out.println(formateDate(new Date(),DATE_FORMAT));
//			System.out.println(parse("2017-08-30 17:56:11",DATETIME_FORMAT));
//			System.out.println(getMonthFirstDay(new Date()));
//			System.out.println(getMonthLastDay(new Date()));
//			System.out.println(computeTime(Calendar.WEEK_OF_MONTH,-2,new Date()));
//			for (int i = 0; i < 5; i++) {
//				new TestThread().start();
//			}
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
