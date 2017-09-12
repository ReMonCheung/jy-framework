package com.jy.framework.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.jy.framework.core.encipher.base64.Base64;
import com.sun.imageio.plugins.jpeg.JPEG;

/**
 * 将远程地址图片转换为Base64字符串
 * 
 * @author zhanglj
 *
 */
public class Base64ImgUtill {
	
	/**
	 * 获取图片类型和Base64编码串
	 * 
	 * @param imgUrl
	 *        网络图片URL
	 * @return Map key: type 类型 && content Base64编码串
	 */
	public static Map<String, String> image2Base64(String imgUrl) {
		ByteArrayOutputStream outputStream = null;
		Map<String, String> map = new HashMap<String, String>();
		URL url = null;
		try {
			//获取图片类型
			InputStream is = getInputStream(imgUrl);
			String type = getContentType(is);
			map.put("type", type);
			
			//读取图片文件
			url = new URL(imgUrl);
			BufferedImage bufferedImage = ImageIO.read(url);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, type, outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		map.put("content",  Base64.getEncoder().encodeToString(outputStream.toByteArray()));
		return map;
	}
	
	/**
	 * 将网络图片进行Base64位编码
	 * 
	 * @param imgUrl
	 *            图片的url路径，如http://.....xx.jpg
	 * @return
	 */
	public static String encodeImgageToBase64(String imgUrl) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		URL url = null;
		try {
			//获取图片类型
			InputStream is = getInputStream(imgUrl);
			String type = getContentType(is);
			
			//读取图片文件
			url = new URL(imgUrl);
			BufferedImage bufferedImage = ImageIO.read(url);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, type, outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 返回Base64编码过的字节数组字符串
		return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}
	
	/**
	 * 从URL中读取图片,转换成流形式.
	 * 
	 * @param destUrl
	 * @return
	 */
	public static InputStream getInputStream(String destUrl) {
		URL url = null;
		InputStream in = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.setRequestMethod("GET");  
			httpUrl.setConnectTimeout(30 * 1000);  
			httpUrl.connect();
			in = httpUrl.getInputStream();
			return in;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 读取输入流,转换为Base64字符串
	 * 
	 * @param input
	 * @return
	 */
	@Deprecated
	public static String getImageStrByInPut(InputStream input) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			int count = 0;
			while (count == 0) {
				count = input.available();
			}
			data = new byte[count];
			input.read(data);
//			int readCount = 0; // 已经成功读取的字节的个数
//			while (readCount < count) {
//				readCount += input.read(data, readCount, count - readCount);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回Base64编码过的字节数组字符串
		return Base64.getEncoder().encodeToString(data);
	}
	
	/**
	 * base64字符串转化成图片 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 *            数据内容(字符串)
	 * @param path
	 *            输出路径
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null) // 图像数据为空
			return false;
		
		try {
			byte[] b = Base64.getDecoder().decode(imgStr);// Base64解码
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取图片类型
	 * 
	 * @param is
	 * @return
	 */
	private static String getContentType(InputStream is) {
		ImageInputStream imageInputStream;
		try {
			imageInputStream = ImageIO.createImageInputStream(is);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
			if (null != iter && iter.hasNext()) {
				ImageReader reader = iter.next();
				return reader.getFormatName(); // 获得图片的类型
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("restriction")
	private static String checkImageType(ImageInputStream stream) {
		String fileType = "jpg";
		byte[] b;
		try {
			b = new byte[8];
			stream.mark();
			stream.readFully(b);
			stream.reset();
			int byte1 = stream.read();
			int byte2 = stream.read();
			if (b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8'
					&& (b[4] == '7' || b[4] == '9') && b[5] == 'a') {
				fileType = "GIF";
			} else if ((b[0] == 0x42) && (b[1] == 0x4d)) {
				fileType = "BMP";
			} else if ((byte1 == 0xFF) && (byte2 == JPEG.SOI)) {
				fileType = "JPG";
			} else if ((b[0] == (byte) 137 && b[1] == (byte) 80
					&& b[2] == (byte) 78 && b[3] == (byte) 71
					&& b[4] == (byte) 13 && b[5] == (byte) 10
					&& b[6] == (byte) 26 && b[7] == (byte) 10)) {
				fileType = "PNG";
			} else if (((b[0] == (byte) 0) && b[1] == 0 && ((b[2] & 0x8f) != 0 || (b[2] & 0x7f) != 0))) {
				fileType = "wbmp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileType;
	} 

	 public static void main(String[] args) {
		String imgUrl = "http://www.kaisagroup.com/image/2017-05/201705231829290081512.jpg";
//		imgUrl = "https://img.alicdn.com/tfs/TB16FQXRVXXXXXoXFXXXXXXXXXX-240-240.gif";
//		imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498647544736&di=875a5277adf538360f1ac86289325bf2&imgtype=0&src=http%3A%2F%2Fp8.qhimg.com%2Ft01be331bb2f4cec767.jpg";
		imgUrl = "http://d.lanrentuku.com/down/png/1101/paradise_fruit/cherry512.png";
//		imgUrl = "http://img.sj33.cn/uploads/allimg/200908/20090821004513914.jpg";
		Map<String,String> s = image2Base64(imgUrl);
		System.out.println("type:" + s.get("type"));
//		System.out.println(s.get("content"));
		String str = s.get("content");
        String path = "D:/"+System.currentTimeMillis()+"."+s.get("type");  
        generateImage(str, path);//将Base64字符转换为图片  
        
//		String type = (String) s.get("type");
//		String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|jpg|png|gif|apk|exe|pdf|rar|zip|docx|doc";
//		Pattern pat = Pattern.compile("(" + suffixes + ")");// 正则判断
//		Matcher mc = pat.matcher(type.toLowerCase());// 条件匹配
//		String substring = "";
//		while (mc.find()) {
//			substring = mc.group();// 截取文件名后缀名
//		}
//		System.out.println(substring);
	 }
}
