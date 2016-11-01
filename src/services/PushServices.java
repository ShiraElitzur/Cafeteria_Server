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

import db.JpaManager;

@Path("/push")
public class PushServices {
	
	private JpaManager jpa = JpaManager.getInstance();

	@GET
	@Path("/OrderReadyNotify")
	public void sendOrderReadyNotification( @QueryParam("userId") int userId ) {
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
		   String strJsonBody = "{"
		                      +   "\"app_id\": \"272de338-7ae2-498a-b1fc-a56f3f603dd2\","
		                      +   "\"include_player_ids\": [\""+token+"\"],"
		                      +   "\"data\": {\"foo\": \"bar\"},"
		                      +   "\"contents\": {\"en\": \"ההזמנה המוכנה מחכה לך בקפיטריה :)\"}"
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
	
	@GET
	@Path("/attachPushIdToUser")
	public void attachPushIdToUser(@QueryParam("userId") int userId ,@QueryParam("pushId")String pushId) {
		System.out.println("inside attach token");
		jpa.insertPushTokenToUserRecord(userId, pushId);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getToken")
	public String getToken(@QueryParam("user") int user ) {
		return jpa.getUserPushToken(user);
	}
}
