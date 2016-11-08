package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Category;
import data.Drink;
import data.Order;
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

}
