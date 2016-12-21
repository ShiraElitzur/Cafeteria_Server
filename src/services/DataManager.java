package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Category;
import data.Customer;
import data.Drink;
import data.Extra;
import data.Item;
import data.Main;
import data.Meal;
import data.Order;
import data.OrderedMeal;
import data.ServingForm;
import db.JpaManager;

@Path("/data")
public class DataManager {

	/**
	 * The instance of jpaManger
	 */
	private JpaManager jpa = JpaManager.getInstance();
	
	/**
	 * Returns list of categories
	 * @return list of categories
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCategories")
	public String getCategories() {
		System.out.println("inside getCategories");
		Gson json = new Gson();
		List<Category> categories = new ArrayList<>();
		categories = jpa.getCategories();
		return json.toJson(categories.toArray());	
	}
	
	/**
	 * Returns list of drinks
	 * @return list of drinks
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDrinks")
	public String getDrinks() {
		Gson json = new Gson();
		List<Drink> drinks = new ArrayList<>();
		drinks = jpa.getDrinks();
		System.out.println(drinks.get(0).getTitle());
		return json.toJson(drinks.toArray());
	}
	
	/**
	 * Inserts the given order
	 * @param input
	 * @return the order
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/insertOrder")
	public String insertOrder( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
		StringBuilder jsonOrder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonOrder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		// with Gson i convert the json to Order object
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss.SSSZ").create();
		Order order = gson.fromJson(jsonOrder.toString(), Order.class);

		boolean result = jpa.insertOrder(order);
		if(result) {
			return "OK";
		} else {
			return "notOk";
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getItems")
	public String getItems() {
		Gson json = new Gson();
		List<Item> items = new ArrayList<>();
		items = jpa.getItems();
		if (items == null) {
			System.out.println("items is empty");
			return "null";
		}
		System.out.println("items size: " + items.size());
		return json.toJson(items.toArray());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMains")
	public String getMains() {
		Gson json = new Gson();
		List<Main> mains = new ArrayList<>();
		mains = jpa.getMains();

		if (mains == null) {
			System.out.println("mains is empty");
			return "null";
		}
		System.out.println("mains size: " + mains.size());
		return json.toJson(mains.toArray());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getExtras")
	public String getExtras() {
		Gson json = new Gson();
		List<Extra> extras = new ArrayList<>();
		extras = jpa.getExtras();

		if (extras == null) {
			System.out.println("extras is empty");
			return "null";
		}
		System.out.println("extras size: " + extras.size());
		return json.toJson(extras.toArray());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getServings")
	public String getServings() {
		Gson json = new Gson();
		List<ServingForm> servings = new ArrayList<>();
		servings = jpa.getServings();

		if (servings == null) {
			System.out.println("servings is empty");
			return "null";
		}
		System.out.println("servings size: " + servings.size());
		return json.toJson(servings.toArray());
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateItem")
	public String updateItem( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
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
		Item item = gson.fromJson(json.toString(), Item.class);
		jpa.updateItem(item);
		System.out.println("Finished update item");
		return "ok";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateMain")
	public String updateMain( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
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
		Gson gson = new Gson();
		Main main = gson.fromJson(json.toString(), Main.class);
		jpa.updateMain(main);
		return "OK";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateExtra")
	public String updateExtra( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
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
		Extra extra = gson.fromJson(json.toString(), Extra.class);
		jpa.updateExtra(extra);
		return "OK";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateServing")
	public String updateServing( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
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
		ServingForm servingForm = gson.fromJson(json.toString(), ServingForm.class);
		jpa.updateServingForm(servingForm);
		return "OK";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOrdersReady")
	public String getOrdersReady() {
		Gson json = new Gson();
		List<Order> orders = new ArrayList<>();
		orders = jpa.getOrdersReady();

		return json.toJson(orders.toArray());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOrders")
	public String getOrders() {
		Gson json = new Gson();
		List<Order> orders = new ArrayList<>();
		orders = jpa.getOrders();

		return json.toJson(orders.toArray());
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateOrderDelivered")
	public String updateOrderDelivered( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
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
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss.SSSZ").create();
		Order order = gson.fromJson(json.toString(), Order.class);
		jpa.updateOrderDelivered(order);
		return "OK";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateOrderReady")
	public String updateOrderReady( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
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
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss.SSSZ").create();
		Order order = gson.fromJson(json.toString(), Order.class);
		jpa.updateOrderReady(order);
		sendNotificationToUser(order);
		return "OK";
	}
	
	public void sendNotificationToUser( Order order ) {
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
	 * Returns list of favorites meals
	 * @return list of favorites meals
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getFavoriteMeals")
	public String getFavoriteMeals(@QueryParam("userId") int userId ) {
		System.out.println("inside getFavorites");
		Gson json = new Gson();
		List<Meal> meals;
		
		// get the top 3 meals that the user ordered 
		meals = jpa.getFavoriteMeals(userId);

		// add to the meals our favorites - should be replaced with some query
		// but before we must add it to the table and to the website, do we really want to do so?
//		if( !meals.contains(jpa.getMeal(4))) {
//			meals.add(jpa.getMeal(4));
//		}
//		if( !meals.contains(jpa.getMeal(5))) {
//			meals.add(jpa.getMeal(5));
//		}
//		if( !meals.contains(jpa.getMeal(6))) {
//			meals.add(jpa.getMeal(6));
//		}
//		if( !meals.contains(jpa.getMeal(7))) {
//			meals.add(jpa.getMeal(7));
//		}
//		
		return json.toJson(meals.toArray());	
	}
	
	/**
	 * Returns list of favorites items
	 * @return list of favorites items
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getFavoriteItems")
	public String getFavoriteItems(@QueryParam("userId") int userId ) {
		System.out.println("inside getFavoriteItems");
		Gson json = new Gson();
		List<Item> items;
		
		// get the top 3 meals that the user ordered 
		items = jpa.getFavoriteItems(userId);
		System.out.println("favorites items size - "+items.size());
		return json.toJson(items.toArray());	
	}

}
