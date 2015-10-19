package tools;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailTool {

	private static final String FROM_EMAIL = "674725574@qq.com";// 发送邮件的邮箱地址
	private static final String FROM_EMAIL_PASS = "unic2009";// 发送邮件的邮箱密码

	// 开发实例
	public static void main(String[] args) {
		new EmailTool().sendEmail(FROM_EMAIL, "主题", "内容");
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param content
	 *            内容，可以是HTML格式
	 */
	public void sendEmail(String to, String subject, String content) {

		JavaMailSenderImpl senderImpl = getSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				"utf-8");
		try {
			// 设置收件人，寄件人
			messageHelper.setTo(to);
			messageHelper.setFrom(senderImpl.getUsername());
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		// 发送邮件
		senderImpl.send(mailMessage);
	}

	private JavaMailSenderImpl getSenderImpl() {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost("smtp." + FROM_EMAIL.split("@")[1]);
		senderImpl.setUsername(FROM_EMAIL);
		senderImpl.setPassword(FROM_EMAIL_PASS);

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.timeout", "2500");

		senderImpl.setJavaMailProperties(properties);
		return senderImpl;
	}

}
