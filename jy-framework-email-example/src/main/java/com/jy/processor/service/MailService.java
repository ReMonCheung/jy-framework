package com.jy.processor.service;

import java.util.Collection;
import java.util.Map;

public interface MailService {
	
	boolean sendMail(final String[] to, final String from, final String[] cc, final String[] bcc, final String subject, String templateName, Object model, Map<String,Object> attachments);
	
	boolean sendMail(final String[] to, final String from, final String[] cc, final String[] bcc, final String subject, String templateName, Object model);
	
	boolean sendMail(final String to, final String from, final String[] cc, final String[] bcc, final String subject, String templateName, Object model);
	
	boolean sendMail(final Collection<String> to, final String from, final Collection<String> cc, final Collection<String> bcc, final String subject, String templateName, Object model);
	
	boolean sendMail(final String to, final String from, final Collection<String> cc, final Collection<String> bcc, final String subject, String templateName, Object model);
	
}
