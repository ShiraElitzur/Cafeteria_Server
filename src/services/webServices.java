package services;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Category;
import data.Customer;
import data.Drink;
import data.Extra;
import data.Item;
import data.Meal;
import db.JpaManager;

@Path("/web")
public class webServices {
	
	private JpaManager jpa = JpaManager.getInstance();


@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/getAllCategories")
public String getAllCategories() {
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
    return json.toJson(drinks.toArray());
	
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/getExtras")
public String getExtras() {
	Gson json = new Gson();
	List<Extra> extras = new ArrayList<>();
	extras = jpa.getExtras();
    return json.toJson(extras.toArray());
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/getItems")
public String getItems() {
	Gson json = new Gson();
	List<Item> items = new ArrayList<>();
	items = jpa.getItems();
    return json.toJson(items.toArray());
}

@POST
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/updateItem")
public Boolean updateItem( Item item ) {
	System.out.println("In test " + item.getTitle() + " " + item.getPrice() + " " + item.getId());
	jpa.updateItem(item);
    return true;
}

@POST
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/updateMeal")
public Boolean updateMeal( Meal meal ) {
	System.out.println("In test " + meal.getTitle() + " " + meal.getPrice() + " " + meal.getId());
	jpa.updateMeal(meal);
    return true;
	}


@POST
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/addCategory")
public Boolean addCategory( Category category ) {
	System.out.println("in add" + category.getItems().toString());
	jpa.insertCategory(category);
    return true;
	}

@POST
@Path("/validateUser")
@Produces(MediaType.APPLICATION_JSON)
public String validateUser(@FormParam("email") String email,@FormParam("password")String password) {
	System.out.println("in checkUser method " + email + " " + password);
	Customer customer = jpa.isUserExist(email,password);		
	Gson gson = new Gson();
	
	String jsonString = gson.toJson(customer);
	System.out.println(jsonString);
	return jsonString;
	}
}

