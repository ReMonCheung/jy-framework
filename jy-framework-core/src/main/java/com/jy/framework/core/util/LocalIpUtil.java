package com.jy.framework.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Get Local IP
 * 
 * @author zhanglj
 *
 */
public class LocalIpUtil {
	
	private static final String ANYHOST = "0.0.0.0";
	private static final String LOCALHOST = "127.0.0.1";
	public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
	
	public static volatile InetAddress LOCAL_ADDRESS = null;
	
	/**
	 * Valid Address
	 * @param address
	 * @return
	 */
	private static boolean isVaildAddress(InetAddress address) {
		if (address == null || address.isLoopbackAddress()) 
			return false;
		String name = address.getHostAddress();
		return (name!=null
				&& !ANYHOST.equals(name)
				&& !LOCALHOST.equals(name)
				&& IP_PATTERN.matcher(name).matches());
	}
	
	/**
	 * Get First Vaild Address
	 * @return
	 */
	private static InetAddress getFirstVaildAddress() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isVaildAddress(localAddress)) {
				return localAddress;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
//			logger.error("Failed to retriving ip address, " + e.getMessage(), e);
		}
		
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					NetworkInterface networkInterface = interfaces.nextElement();
					Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
					if (addresses != null) {
						while (addresses.hasMoreElements()) {
							InetAddress inetAddress = addresses.nextElement();
							if (isVaildAddress(inetAddress)) {
								return inetAddress;
							}
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		return localAddress;
	}
	
	/**
	 * Get Address
	 * @return
	 */
	private static InetAddress getAddress() {
		if (LOCAL_ADDRESS != null)
			return LOCAL_ADDRESS;
		InetAddress localAddress = getFirstVaildAddress();
		LOCAL_ADDRESS = localAddress;
		return localAddress;
	}
	
	/**
	 * Get IP
	 * @return
	 */
	public static String getIp(){
		InetAddress address = getAddress();
		if (address == null) 
			return null;
		return address.getHostAddress();
	}
	
	/**
	 * Get ip:port
	 * @param port
	 * @return
	 */
	public static String getIpPort(int port){
		String ip =  getIp();
		if (ip == null)
			return null;
		return ip.concat(":").concat(String.valueOf(port));
	}
	
	public static void main(String[] args) {
		System.out.println(getIp());
		System.out.println(getIpPort(80));
	}
}
