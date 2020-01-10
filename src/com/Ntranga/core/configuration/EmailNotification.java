package com.Ntranga.core.configuration;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import common.Logger;
 
@Service("emailNotification")
public class EmailNotification 
{
    @Autowired
    private JavaMailSender javaMailSender;        
 
    private static Logger log = Logger.getLogger(EmailNotification.class);
  
    public void sendMail(String to, String subject, String body) 
    {
    	try{
	    	log.info( to+"::to :: subject::"+subject+"  ::body:: "+body);
	        /*SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        javaMailSender.send(message);*/
	        String smtpHostServer = "10.12.12.9";
		    
		    Properties props = System.getProperties();

		    props.put("mail.smtp.host", smtpHostServer);

		    Session sessiontest = Session.getInstance(props, null);
		    EmailNotification.sendEmail(sessiontest, to,subject, body);
			
    	}catch (Exception e) {
			log.error(e);
		}
    }
    
    
    public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("eNotify@fulchrum.com", ""));

	      msg.setReplyTo(InternetAddress.parse(toEmail, false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
 
   
}