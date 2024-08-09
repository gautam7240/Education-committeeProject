package com.educationCommittee.otpService;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail{
	
	public void SendMail(String msg,String Sub,String reciver) {
		
		String massage = msg;
		String Subject = Sub;
		String to=reciver;
		String from = "Educommitteestudy@gmail.com";
		
		sendEmail(massage,Subject,to,from);
	}

	
//	this is method for responsible for sending massage
	private void sendEmail(String massage, String subject, String to, String from) {
		//variable for gmail
		String host = "smtp.gmail.com";
		
		//get system properties
		Properties properties = System.getProperties();
		
		//setting important information to properties object
		
//		set host
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		
		//step 1: to get the session object..
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(from,"kxfg lfwy qsmd itip");
			}
			
		});
		session.setDebug(true);
		
		//step 2: compose the message..
		
		MimeMessage m = new MimeMessage(session);
		
//		from ..
		
		try {
			m.setFrom(from);
			
			//adding recipient to message
			
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//adding subject to message
			
			m.setSubject(subject);
			
			//adding massage text to message
			
			
			m.setText(massage);
			
			
			//send
			
			//step 3: send the message using transport class
			
			Transport.send(m);
//			send Success
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}