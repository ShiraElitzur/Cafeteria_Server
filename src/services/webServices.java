package services;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import data.Icon;
import data.Item;
import data.Main;
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
	System.out.println("In get extras");
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

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/getMains")
public String getMains() {
	System.out.println("In get mains");
	Gson json = new Gson();
	List<Main> mains = new ArrayList<>();
	mains = jpa.getMains();
    return json.toJson(mains.toArray());
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/getIcons")
public String getIcons() {
	Gson json = new Gson();
	List<Icon> icons = new ArrayList();
	icons = jpa.getIcons();
    return json.toJson(icons.toArray());
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

@POST
@Path("/addDrink")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String addDrink(Drink drink) {
	System.out.println("in adddrink method " + drink.getTitle() + " " + drink.getPrice());
	jpa.insertDrink(drink);
	return "{\"result\": \"Good\"}";

}

@POST
@Path("/deleteDrink")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String deleteDrink(Drink drink) {
	System.out.println("in delete drink method " + drink.getId() + drink.getTitle() + " " + drink.getPrice());
	jpa.deleteDrink(drink);
	return "{\"result\": \"Good\"}";

}


@POST
@Path("/editDrink")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String editDrink(Drink drink) {
	System.out.println("in edit drink method id is " + drink.toString());
	jpa.editDrink(drink);
	return "{\"result\": \"Good\"}";
	}


@POST
@Path("/deleteExtra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String deleteExtra(Extra extra) {
	System.out.println("in delete drink method " + extra.getId() + extra.getTitle() + " " + extra.getPrice());
	//jpa.deleteExtra(extra);
	// need some logic to remove all of this extra instance 
	// before deleting
	return "{\"result\": \"Good\"}";

}


@POST
@Path("/editExtra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String editExtra(Extra extra) {
	System.out.println("in edit drink method id is " + extra.toString());
	jpa.editExtra(extra);
	return "{\"result\": \"Good\"}";
}

@POST
@Path("/addExtra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String addExtra(Extra extra) {
	System.out.println("in add extra method " + extra.getTitle() + " " + extra.getPrice());
	Extra insertedExtra = jpa.insertExtra(extra);
	Gson gson = new Gson();
	String jsonString = gson.toJson(insertedExtra);
	System.out.println(jsonString);
	return jsonString;

	}

//@POST
//@Path("/addCategory")
//@Produces(MediaType.APPLICATION_JSON)
//public String addCategory(@FormParam("id") int id,@FormParam("title")String title,
//		@FormParam("description")String description,@FormParam("items")String items,
//		@FormParam("meals")String meals,@FormParam("icon")String icon ) {
//	System.out.println("in add add category method \n id: " + id + "\n title: " + title + "\n desc: " + description
//			+ "\n items: " + items + "\n meals: " + meals + "\n icon: " + icon);
//	Extra e = new Extra();
//	e.setPrice(10.0);
//	e.setTitle("ten");
//	e.setId(1);
//	Gson gson = new Gson();
//	String jsonString = gson.toJson(e);
//	System.out.println(jsonString);
//	return jsonString;
//
//}

@POST
@Path("/addCategory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String addCategory(Category category) {
	System.out.println("in add category method " + category.getTitle());
	jpa.insertCategory(category);
	return "{\"result\": \"Good\"}";
}


}

