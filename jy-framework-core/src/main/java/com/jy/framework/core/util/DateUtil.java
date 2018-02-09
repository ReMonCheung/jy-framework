package com.jy.framework.core.util;

import java.sql.Timestamp;
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
	 * 系统当前的时间戳
	 * @return 系统当前的时间戳
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}
	
	/**
	 * 指定日期的时间戳
	 * @param date  指定日期
	 * @return      指定日期的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 指定日历的时间戳
	 * @param cal  指定日历
	 * @return     指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar cal) {
		return new Timestamp(cal.getTime().getTime());
	}
	
	/**
	 * 系统时间的毫秒数
	 * @return 系统时间的毫秒数
	 */
	public static long getMillis() {
		return new Date().getTime();
	}

	/**
	 * 指定日历的毫秒数
	 * @param cal  指定日历
	 * @return     指定日历的毫秒数
	 */
	public static long getMillis(Calendar cal) {
		return cal.getTime().getTime();
	}

	/**
	 * 指定日期的毫秒数
	 * @param date  指定日期
	 * @return      指定日期的毫秒数
	 */
	public static long getMillis(Date date) {
		return date.getTime();
	}
	
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
	public static Date computeTime(int type, int value, Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(type, value);
		return gc.getTime();
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
	 * @param value     数量
	 * @param date      指定日期
	 * @param pattern   日期格式
	 * @return
	 */
	public static String computeTime(int type, int value, Date date, String pattern) {
		return formateDate(computeTime(type,value,date), pattern);
	}
	
	
	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 * @param type  计算标志，表示按照年/月/日/时/分/秒等计算
	 * @see 
	 * 			Calendar.YEAR<br>
	 *      	Calendar.MONTH<br>
	 *      	Calendar.DATE<br>
	 *      	Calendar.HOUR<br>
	 *      	Calendar.MINUTE<br>
	 *      	Calendar.SECOND<br>
	 * @param calSrc  减数
	 * @param calDes  被减数
	 * @return        两个日期之间的差值
	 */
	public static int matuDatetime(int type, Calendar calSrc, Calendar calDes) {
		if ( type == Calendar.YEAR
				|| type == Calendar.MONTH
				|| type == Calendar.DATE
				|| type == Calendar.HOUR
				|| type == Calendar.MINUTE
				|| type == Calendar.SECOND
				|| type == Calendar.DAY_OF_YEAR
				)
			return calSrc.get(type) - calDes.get(type);
		return 0;
	}
	
	
	/**
	 * 根据日期获取季度
	 * @param cal
	 * @return
	 */
	public static int getQuarter(Calendar cal){
		int month = cal.get(Calendar.MONTH);
		int quarter = 0;
		if(month<3){
			quarter = 1;
		}else if(month<6 && month>2){
			quarter = 2;
		}else if(month<9 && month>5){
			quarter = 3;
		}else if(month<12 && month>8){
			quarter = 4;
		}
		return quarter;
	}
	
	/**
	 * 获取每个季度已过天数（不含当天）
	 * @param date 当前日期
	 * @return
	 */
	public static int matuQuarterDay(Date date){
		int day = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int quarter = getQuarter(cal);
		if(quarter == 0){
			return 0;
		}
		
		try {
			Calendar cal1 =  Calendar.getInstance();
			Date date1 = null;
			if(quarter == 1){
				date1 = parse(year+"-1-1",DATE_FORMAT);
				cal1.setTime(date1);
				day = matuDatetime(Calendar.DAY_OF_YEAR,cal,cal1);
			}else if(quarter == 2){
				date1 = parse(year+"-4-1",DATE_FORMAT);
				cal1.setTime(date1);
				day = matuDatetime(Calendar.DAY_OF_YEAR,cal,cal1);
			}else if(quarter == 3){
				date1 = parse(year+"-7-1",DATE_FORMAT);
				cal1.setTime(date1);
				day = matuDatetime(Calendar.DAY_OF_YEAR,cal,cal1);
			}else if(quarter == 4){
				date1 = parse(year+"-10-1",DATE_FORMAT);
				cal1.setTime(date1);
				day = matuDatetime(Calendar.DAY_OF_YEAR,cal,cal1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return day;
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
			Calendar cal = Calendar.getInstance();
			System.out.println(cal.get(Calendar.MONTH));
			System.out.println(cal.get(Calendar.YEAR));
			System.out.println(cal.get(Calendar.DAY_OF_YEAR));
			System.out.println(matuQuarterDay(new Date()));
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
