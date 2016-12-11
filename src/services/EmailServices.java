package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Customer;
import data.Order;
import db.JpaManager;

@Path("email")
public class EmailServices {
	
	private JpaManager jpa = JpaManager.getInstance();
	/**
	 * This service checks if the user with the given details exists in the db
	 * @param email
	 * @param password
	 * @return json string that represents the customer or null if doesn't exist
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/forgotPassword")
	public String forgotPassword( @QueryParam("email") String email ){
		System.out.println("inside forgot password");
		Customer customer = jpa.findUserByEmail(email);
		if (customer == null){
			return "not ok";
		} else{
			sendEmail(customer);
			return "OK";
		}
	}
	
	public void sendEmail(Customer customer) {
		System.out.println("inside send email");

		final String username = "time2eat2016@gmail.com";
		final String password = "time2eat2016";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("time2eat@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(customer.getEmail()));
			message.setSubject("Time2eaT - Password Recovery");
			message.setText("Dear " + customer.getFirstName() + " " + customer.getLastName() + ", "
				+ "\n Your password is: " + customer.getPassword());

			Transport.send(message);


		} catch (MessagingException e) {
			System.out.println("Something wen wrong while sending email");
			throw new RuntimeException(e);
		}
		
		System.out.println("Email Sent");
	}
	
//	public static void main(String[] args) {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "465");
//
//		Session session = Session.getDefaultInstance(props,
//			new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication("time2eat2016@gmail.com","time2eat2016");
//				}
//			});
//
//		try {
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress("time2eat2016@gmail.com"));
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse("anaelshomrai@gmail.com"));
//			message.setSubject("Testing Subject");
//			message.setText("Dear Mail Crawler," +
//					"\n\n No spam to my email, please!");
//
//			Transport.send(message);
//
//			System.out.println("Done");
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
}