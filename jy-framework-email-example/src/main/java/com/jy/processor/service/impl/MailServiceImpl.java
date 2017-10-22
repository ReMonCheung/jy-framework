package com.jy.processor.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.jy.processor.service.FreeMarkerService;
import com.jy.processor.service.MailService;

@Service
public class MailServiceImpl implements MailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private FreeMarkerService freeMarkerService;
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final String encoding = "UTF-8"; 
	
	@Override
	public boolean sendMail(final String[] to,final String from,final String[] cc,final String[] bcc,
			final String subject,final String templateName, Object model,final Map<String,Object> attachments) {
		
		final String text = freeMarkerService.getFreeMarkerTemplateContent(templateName, model); 
		
		javaMailSender.send(new MimeMessagePreparator() {  
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {  
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, encoding);  
				message.setTo(to);  
				message.setFrom(from);  
				message.setCc(cc != null ? cc : new String[0]);  
				message.setBcc(bcc != null ? bcc : new String[0]);  
				message.setSubject(subject != null ? subject : "");  
				message.setText(text, true);  
				if(!attachments.isEmpty()) {
					for (String str : attachments.keySet()) {
						Object obj = attachments.get(str);
						if(obj instanceof File){
							message.addAttachment(str, (File)obj);
						}else if(obj instanceof InputStreamSource){
							message.addAttachment(str, (InputStreamSource)obj);
						}else if(obj instanceof DataSource){
							message.addAttachment(str, (DataSource)obj);
						}
					}
				}
			}  
		});  
		return true;  
		
	}
	
	@Override
	public boolean sendMail(final String[] to,final String from,final String[] cc,final String[] bcc,
			final String subject,final String templateName, Object model) {
		try {
			final String text = freeMarkerService.getFreeMarkerTemplateContent(templateName, model); 
			
			javaMailSender.send(new MimeMessagePreparator() { 
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {  
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, encoding);  
					message.setTo(to);  
					message.setFrom(from);  
					message.setCc(cc != null ? cc : new String[0]);  
					message.setBcc(bcc != null ? bcc : new String[0]);  
					message.setSubject(subject != null ? subject : "");  
					message.setText(text, true);  
				}  
			});  
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("send email excetion:{}", e.getMessage());
			return false;
		}
		return true;  
		
	}

	@Override
	public boolean sendMail(final String to,final String from,final String[] cc,final String[] bcc,
			final String subject,final String templateName,final Object model) {
		return sendMail(new String[]{to}, from, cc, bcc, subject, templateName, model);  
	}

	@Override
	public boolean sendMail(final Collection<String> to,final  String from,
			final Collection<String> cc,final Collection<String> bcc,final String subject,
			final String templateName,final Object model) {
		return sendMail(_toArray(to), from, _toArray(cc), _toArray(bcc), subject, templateName, model);  
	}

	@Override
	public boolean sendMail(final String to,final String from,final Collection<String> cc,
			final Collection<String> bcc,final String subject,final String templateName,
			final Object model) {
		return sendMail(to, from, _toArray(cc), _toArray(bcc), subject, templateName, model);
	}

	private String[] _toArray(Collection<String> collection) {  
        if (collection == null || collection.isEmpty()) {
        	return new String[0];  
        }
        return collection.toArray(new String[0]);  
    }  
	
}
