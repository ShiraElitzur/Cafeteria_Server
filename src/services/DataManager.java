package services;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import data.Category;
import data.Customer;
import data.DataHolder;
import data.Drink;
import data.Item;
import data.Meal;
import db.JpaManager;

@Path("/data")
public class DataManager {

	private JpaManager jpa = JpaManager.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCategories")
	public String getCategories() {
		Gson json = new Gson();
		List<Category> categories = new ArrayList<>();
		categories = jpa.getCategories();
		return json.toJson(categories.toArray());	
	}
	
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
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getHello")
	public String getHello() {
		return "hello";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOne")
	public Response  getOne() {
		Category c = new Category();
		Gson gson = new Gson();
		c.setId(1);
		c.setTitle("Food");
		c.setDescription("good  food");
		c.setItems(new ArrayList<Item>());
		c.setMeals(new ArrayList<Meal>());
		c.setIcon(null);
		
	    return Response.ok(gson.toJson(c)).header("Access-Control-Allow-Origin", "*").build();	}

}
