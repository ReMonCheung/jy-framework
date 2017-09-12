package com.jy.framework.core.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 请求
 * @author zhanglj
 *
 */
public class HttpRequestUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);
	
	/**
	 * 解析请求参数
	 * @param request
	 * @return
	 */
	public static Map<String,Object> getParameterMap(HttpServletRequest request) {
		Map<String,String[]> attributes = request.getParameterMap();
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Iterator<Entry<String, String[]>> iter = attributes.entrySet().iterator();
		String value = null;
		while (iter.hasNext()) {
			Entry<String, String[]> entry = iter.next();
			String name = entry.getKey();
			Object objVal = entry.getValue();
			if(objVal == null) {
				value = "";
			} else if(objVal instanceof String[]) {
				String[] values = (String[]) objVal;
				for (String str : values) {
					value = str + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = objVal.toString();
			}
			
			returnMap.put(name, value);
		}
		
		return returnMap;
	}
	
	
    /**
     * 获取request值
     * @param request
     * @param paraName
     * @param defaultValue
     * @return
     */
    public static String getParameter(HttpServletRequest request,String paraName,String defaultValue){
        if(StringUtils.isEmpty(request.getParameter(paraName))){
            return defaultValue;
        }else {
            return request.getParameter(paraName);
        }
    }

    public static int getIntParameter(HttpServletRequest request,String paraName,int defaultValue){
        String value = getParameter(request,paraName,defaultValue+"");
        try{
            return Integer.parseInt(value);
        }catch (Exception ex){
            return 0;
        }
    }
    
    
    /**      
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException      
     */
    protected String getRequestPostStr(HttpServletRequest request) throws IOException {
    	String uri = request.getRequestURI();
    	String method = request.getMethod();
    	int contentLength = request.getContentLength();
        byte[] buffer = getRequestPostBytes(request);
        if(buffer == null){
        	return "{}";
        }
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        String rs = new String(buffer, charEncoding);
        LOGGER.info("============  request recive params ==========> URI:{}, Method:{}, ContentLength:{}, json:{}", uri, method, contentLength,rs);
        return rs;
    }
    
    /**      
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException      
     */
    protected byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }
}
