package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.Cafeteria;
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
	
	/**
	 * This service send email given from web
	 * @param input an input stream between the server and the client
	 * @return return 0 if email sent successfully, otherwise -1
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/sendMessage")
	public String sendMessage( InputStream input ) {
		StringBuilder json = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		// with Gson i convert the json to Customer object
		Gson gson = new Gson();
		WebMessage message = gson.fromJson(json.toString(), WebMessage.class);
		return String.valueOf(sendEmailFromWeb(message));
	}
	
	/**
	 * This service send email given from web
	 * @param input an input stream between the server and the client
	 * @return return 0 if email sent successfully, otherwise -1
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/adminForgotPassword")
	public String adminForgotPassword( InputStream input ) {
		StringBuilder json = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		// with Gson i convert the json to Customer object
		Gson gson = new Gson();
		Cafeteria c = gson.fromJson(json.toString(), Cafeteria.class);
		return String.valueOf(sendEmail(c));
	}
	
	
	@POST
	@Path("/getAdminPassword")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAdminPassword(@FormParam("email") String email) {
		System.out.println("In get admin password " + email);
		Gson json = new Gson();
		Cafeteria c = null;
		c = jpa.getAdminPassword(email);
		if (c == null){
			return "-1";
		}
		else{
			return String.valueOf(sendEmail(c));
		}

	}
	
	
	private int sendEmailFromWeb(WebMessage webMessage) {
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
			message.setFrom(new InternetAddress(webMessage.getEmail()));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("time2eat2016@gmail.com"));
			message.setSubject("Time2eaT - Web Message");
			message.setText("New Web Message from : " + webMessage.getEmail() + "\n" + "Message: " + webMessage.getMessage());

			Transport.send(message);


		} catch (MessagingException e) {
			System.out.println("Something went wrong while sending email");
			System.out.println(e.getMessage().toString());
			System.out.println(e.getCause().toString());
			System.out.println(e.getStackTrace());
			return -1;
		}
		
		System.out.println("Email Sent");	
		return 0;
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
			message.setFrom(new InternetAddress("time2eat2016@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(customer.getEmail()));
			message.setSubject("Time2eaT - Password Recovery");
			message.setText("Dear " + customer.getFirstName() + " " + customer.getLastName() + ", "
				+ "\n Your password is: " + customer.getPassword());

			Transport.send(message);

		} catch (MessagingException e) {
			System.out.println("Something went wrong while sending email");
			throw new RuntimeException(e);
		}
		
		System.out.println("Email Sent");
	}
	
	public int sendEmail(Cafeteria cafeteria) {
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
			message.setFrom(new InternetAddress("time2eat2016@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(cafeteria.getAdminEmail()));
			message.setSubject("Time2eaT - Password Recovery");
			message.setText("Dear Manager of " + cafeteria.getCafeteriaName() + ", "
				+ "\n Your password is: " + cafeteria.getAdminPassword());

			Transport.send(message);

		} catch (MessagingException e) {
			System.out.println("Something went wrong while sending email");
			return -1;
		}
		
		
		System.out.println("Email Sent");
		return 0;

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
	
	private class WebMessage{
		private String email;
		private String message;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		@Override
		public String toString() {
			return "Message [email=" + email + ", message=" + message + "]";
		}
		
		
		
	}
}
