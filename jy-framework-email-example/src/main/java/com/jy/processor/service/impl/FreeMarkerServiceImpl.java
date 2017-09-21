package com.jy.processor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.jy.processor.service.FreeMarkerService;

import freemarker.template.Configuration;

@Service
public class FreeMarkerServiceImpl implements FreeMarkerService{

	private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerServiceImpl.class);
	
	@Autowired
	Configuration freemarkerConfiguration;
	
	@Override
	public String getFreeMarkerTemplateContent(String template, Object model) {
		LOGGER.info("params:{},template:{}", model.toString(),template);
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
					freemarkerConfiguration.getTemplate(template),
					model));
			return content.toString();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured while processing fmtemplate:"
							+ e.getMessage(), e);
		}
		return "";
	}
	
	
}
