package services;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.Order;
import db.JpaManager;

@Path("/push")
public class PushServices {
	
	/**
	 * The instance of jpaManger
	 */
	private JpaManager jpa = JpaManager.getInstance();

	/**
	 * Notify the customer that the order is ready
	 * @param orderId
	 */
	@GET
	@Path("/OrderReadyNotify")
	public void sendOrderReadyNotification( @QueryParam("orderId") int orderId ) {
		System.out.println("send notification for order id: "+orderId);
		Order order =  jpa.getOrderById(orderId);
		System.out.println("send notification to user: "+order.getCustomer());
		int userId = order.getCustomer().getId();
		String token = jpa.getUserPushToken(userId);
		try {
		   String jsonResponse;
		   URL url = new URL("https://onesignal.com/api/v1/notifications");
		   HttpURLConnection con = (HttpURLConnection)url.openConnection();
		   con.setUseCaches(false);
		   con.setDoOutput(true);
		   con.setDoInput(true);
		   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		   con.setRequestProperty("Authorization", "Basic NGEwMGZmMjItY2NkNy0xMWUzLTk5ZDUtMDAwYzI5NDBlNjJj");
		   con.setRequestMethod("POST");
		   String msg = "הזמנה מס' " + order.getId() + "\nההזמנה המוכנה מחכה לך בקפיטריה :)";
		   String strJsonBody = "{"
		                      +   "\"app_id\": \"272de338-7ae2-498a-b1fc-a56f3f603dd2\","
		                      +   "\"include_player_ids\": [\""+token+"\"],"
		                      +   "\"data\": {\"order\": \""+order.getId()+"\"},"
		                      +   "\"contents\": {\"en\": \""+msg+"\"}"
		                      + "}";

		   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
		   con.setFixedLengthStreamingMode(sendBytes.length);

		   OutputStream outputStream = con.getOutputStream();
		   outputStream.write(sendBytes);

		   int httpResponse = con.getResponseCode();
		   System.out.println("httpResponse: " + httpResponse);

		   if (  httpResponse >= HttpURLConnection.HTTP_OK
		      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
		      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
		      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		      scanner.close();
		   }
		   else {
		      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
		      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		      scanner.close();
		   }
		   System.out.println("jsonResponse:\n" + jsonResponse);

			
		} catch(Throwable t) {
		   t.printStackTrace();
		}
	}
	
	/**
	 * Inserts the token to the user with the given id
	 * @param userId
	 * @param pushId
	 */
	@GET
	@Path("/attachPushIdToUser")
	public void attachPushIdToUser(@QueryParam("userId") int userId ,@QueryParam("pushId")String pushId) {
		System.out.println("inside attach token");
		jpa.insertPushTokenToUserRecord(userId, pushId);
	}

	/**
	 * Returns the token to the user with given id
	 * @param user
	 * @return the token
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getToken")
	public String getToken(@QueryParam("user") int user ) {
		System.out.println("for user id : "+jpa.getUserPushToken(user));
		return jpa.getUserPushToken(user);
	}
}