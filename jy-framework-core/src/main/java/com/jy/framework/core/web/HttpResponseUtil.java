package com.jy.framework.core.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 应答
 * @author zhanglj
 *
 */
public class HttpResponseUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponseUtil.class);
	
	/**
     * 应答客户端
     * @param response
     * @param responseObject
     * @param logMsg
     */
    protected void responseMsg(HttpServletResponse response, Object responseObject, String logMsg) {
    	PrintWriter out = null;   
    	try {  
	    	// 回应客户端
	    	String js = JSON.toJSONString(responseObject);
	    	LOGGER.info("Callback Response ==========>结果:{}",js);
	        response.setCharacterEncoding("UTF-8");  
	        response.setContentType("application/json; charset=utf-8");  
	       
            out = response.getWriter();  
            out.append(js);  
        } catch (IOException e) {  
        	 LOGGER.error("{} - response failed", logMsg, e); 
        } finally {  
            if (out != null) {  
                out.close();  
            }  
        }  
    }
}
