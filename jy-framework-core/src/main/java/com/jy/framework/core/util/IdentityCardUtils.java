package com.jy.framework.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.jy.framework.core.vo.IdentityCardVO;

/**
 * 身份证校验工具类
 * 
 * @author zhanglj
 *
 */
public class IdentityCardUtils {

	/**
	 * 身份证有效性
	 * @param in
	 * @return
	 */
	public static boolean checkID(String in) {
		int len = in.length();
		if (len == 18) {
			// 前17位必须都是数字
			if (!isNumber(in.substring(0, 16))) {
				return false;
			} else {
				// 最后一位只能是“X”或者数字
				String last = in.substring(17, 18);
				if (!last.toUpperCase().equals("X") && !isNumber(last)) {
					return false;
				} else {
					// 生成校验码与输入值比较
					if (createCode(in).equals(last.toUpperCase())) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else if (len == 15) {
			if (!isNumber(in)) {
				return false;
			} else {
				// 格式转换
				in = in.substring(0, 6) + "19" + in.substring(6);
				in += createCode(in);
				return true;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 解析ID
	 * @param in
	 * @return
	 */
	public static IdentityCardVO resolveID(String in){
		int len = in.length();
		IdentityCardVO obj = new IdentityCardVO();
		try {
			if (checkID(in)) {
				if(len==15){
					in = in.substring(0, 6) + "19" + in.substring(6);
					in += createCode(in);
				}
				String location = in.substring(0,6);
				String birthdayStr = in.substring(6, 14);
				Long birthday = DateUtil.parse(birthdayStr,"yyyyMMdd").getTime();
				Integer gender = Integer.valueOf(in.charAt(16));
				obj.setGender(gender%2-1<0?1:0);
				obj.setId(in);
				obj.setLocation(location);
				obj.setBirthday(birthday);
				obj.setBirthdayStr(DateUtil.formateDate(DateUtil.parse(in.substring(6, 14),"yyyyMMdd"),"yyyy-MM-dd"));
				obj.setAge(getAge(DateUtil.parse(birthdayStr, "yyyyMMdd")));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 判断输入是否全是数字
	 * 
	 * @param in
	 * @return
	 */
	private static boolean isNumber(String in) {
		String s;
		for (int i = 0; i < in.length(); i++) {
			s = in.substring(i, i + 1);
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 计算校验码
	 * 
	 * @param in
	 * @return
	 */
	private static String createCode(String in) {
		String[] codes = new String[] { "1", "0", "X", "9", "8", "7", "6", "5",
				"4", "3", "2" };
		int[] weights = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2 };

		int ai, wi, sum = 0;
		for (int i = 0; i < 17; i++) {
			ai = Integer.parseInt(in.substring(i, i + 1));
			// //这个没理解，写成死的了
			// wi = (2 *i) % 11;
			wi = weights[i];
			sum += ai * wi;
		}
		sum %= 11;
		return codes[sum];
	}
	
	/**
	 * 计算年龄
	 * @param birthday
	 * @return
	 */
	public static int getAge(Date birthday) {
		Date now = new Date();
		Calendar calSrc = Calendar.getInstance();
		calSrc.setTime(now);
		Calendar calDes = Calendar.getInstance();
		calDes.setTime(birthday);
		int amount = DateUtil.matuDatetime(Calendar.YEAR, calSrc, calDes);
		Date matuDate = DateUtil.computeTime(Calendar.YEAR, amount, birthday);
		if(matuDate.getTime() > now.getTime()) {
			amount = amount - 1;
		}
		return amount;
	}
	
	public static void main(String[] args) {
		String in = "530121197008214197";
		in = "36078219871117624X";
//		in = "522635198308283119";
		IdentityCardVO obj = resolveID(in);
		if(obj!=null){
			System.out.println(obj.getGender()+"-"+obj.getBirthday()+"-"+obj.getAge()+"-"+obj.getBirthdayStr());
		}
	}
}
