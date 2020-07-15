package com.nec.email.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.nec.email.model.Email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.StringUtils;

@Component
public class EmailClient{

	Logger LOGGER;
	private JavaMailSender emailSender;
	private Configuration freemarkerConfig;
	private final String sender;
	private final String templateName;

	@Autowired
	public EmailClient(JavaMailSender emailSender, Configuration freemarkerConfig,
			@Value("${freemarker.template.path}") final String templatePath,
			@Value("${mail.sender}") final String sender,
			@Value("${email.template.name}") String templateName) {
		this.emailSender = emailSender;
		this.freemarkerConfig = freemarkerConfig;
		this.freemarkerConfig.setClassForTemplateLoading(this.getClass(), templatePath);
		this.sender = sender;
		this.LOGGER = LoggerFactory.getLogger(EmailClient.class);
		this.templateName = templateName;
	}

	/*
	 * This function is to send the email
	 */
	public void sendEmail(Email mail) throws MessagingException, IOException, TemplateException {
		LOGGER.info("==================sendEmail============== {}", mail);
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Template t = freemarkerConfig.getTemplate(templateName + ".ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());
	
		emailSender.send(message);
	}
	
	/**
	 * send email
	 * @param mail
	 * @param userName
	 * @param password
	 * @param pathID
	 * @throws MessagingException
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void sendEmail(Email mail, String userName, String password, String pathID, String filePath, String fileName) throws MessagingException, IOException, TemplateException {
		LOGGER.info("==================sendEmail============== {}", mail);
		JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)emailSender;
		javaMailSender.setUsername(userName);
		javaMailSender.setPassword(password);
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		String tempName = templateName;
		if(!StringUtils.isEmpty(fileName)){
			tempName = fileName;
		}
		//Template template = freemarkerConfig.getTemplate(tempName + "_" +pathID + ".ftl");
		Template template = freemarkerConfig.getTemplate(tempName + ".ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());

		if(!StringUtils.isEmpty(filePath)){
			FileSystemResource file = new FileSystemResource(filePath);
			helper.addAttachment(file.getFilename(), file);
		}
	
		javaMailSender.send(message);

	}

	
	/**
	 * send email 
	 * @param data
	 */
	public void emailConfig(Map<String, String> data, String receipent, String subject) {
		Email mail = new Email();
		mail.setFrom(sender);
		mail.setModel(data);
		mail.setTo(receipent);
		mail.setSubject(subject);
		try {
			this.sendEmail(mail);
		} catch (MessagingException | IOException | TemplateException e) {
			LOGGER.error(e.getMessage());
		}
	}
	

	/**
	 * send email with specified email , password
	 * @param data
	 */
	public void emailConfig(Map<String, String> data,String userName, String password, String pathID, String receipent, String subject, String filePath, String fileName) {
		Email mail = new Email();
		mail.setFrom(sender);
		mail.setModel(data);
		mail.setTo(receipent);
		mail.setSubject(subject);
		try {
			this.sendEmail(mail, userName, password, pathID, filePath, fileName);
		} catch (MessagingException | IOException | TemplateException e) {
			LOGGER.error(e.getMessage());
		}
	}



}
