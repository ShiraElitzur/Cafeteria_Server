package services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
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
import javax.xml.bind.DatatypeConverter;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import data.Category;
import data.Customer;
import data.Drink;
import data.Extra;
import data.Item;
import data.Main;
import data.Meal;
import data.Order;
import data.ServingForm;
import db.JpaManager;

@Path("/web")
public class WebServices {

	private final String email = "admin@gmail.com";
	private final String password = "123456";

	/**
	 * The instance of jpaManger
	 */
	private JpaManager jpa = JpaManager.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCategories")
	public String getAllCategories() {
		Gson json = new Gson();
		List<Category> categories = new ArrayList<>();
		categories = jpa.getCategories();
		if (categories == null) {
			return "{\"result\": \"empty\"}";
		}
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
	@Path("/getOrders")
	public String getOrders() {
		System.out.println("In get orders");
		Gson json = new Gson();
		List<Order> orders = new ArrayList<>();
		orders = jpa.getDeliveredOrders();
		return json.toJson(orders.toArray());
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateItem")
	public Boolean updateItem(Item item) {
		System.out.println("In test " + item.getTitle() + " " + item.getPrice() + " " + item.getId());
		jpa.updateItem(item);
		return true;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateMeal")
	public Boolean updateMeal(Meal meal) {
		System.out.println("In test " + meal.getTitle() + " " + meal.getPrice() + " " + meal.getId());
		jpa.updateMeal(meal);
		return true;
	}

	@POST
	@Path("/validateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String validateUser(@FormParam("email") String email, @FormParam("password") String password) {
		System.out.println("in checkUser method " + email + " " + password);
		Customer customer = jpa.isUserExist(email, password);
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
		String result = jpa.insertDrink(drink);
		return result;

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
	@Path("/deleteCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteCategory(Category category) {
		System.out.println("in delete category method " + category.getId() + category.getTitle());
		jpa.deleteCategory(category);
		return "{\"result\": \"Good\"}";

	}

	@POST
	@Path("/editDrink")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editDrink(Drink drink) {
		System.out.println("in edit drink method id is " + drink.toString());
		return jpa.editDrink(drink);
	}

	@POST
	@Path("/deleteExtra")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteExtra(Extra extra) {
		System.out.println("in delete drink extra " + extra.getId() + extra.getTitle() + " " + extra.getPrice());
		jpa.deleteExtra(extra);
		return "{\"result\": \"Good\"}";

	}

	@POST
	@Path("/deleteMain")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteMain(Main main) {
		System.out.println("in delete main  " + main.getId() + main.getTitle());
		jpa.deleteMain(main);
		return "{\"result\": \"Good\"}";

	}

	@POST
	@Path("/deleteMeals")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteMeals(List<Meal> meals) {
		System.out.println("in delete meals size  " + meals.size());
		jpa.deleteMeals(meals);
		return "{\"result\": \"Good\"}";

	}

	@POST
	@Path("/editMain")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editMain(Main main) {
		System.out.println("in edit main method id is " + main.toString());
		return jpa.editMain(main);
	}

	@POST
	@Path("/editExtra")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editExtra(Extra extra) {
		System.out.println("in edit drink method id is " + extra.toString());
		return jpa.editExtra(extra);
	}

	@POST
	@Path("/addExtra")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addExtra(Extra extra) {
		System.out.println("in add extra method " + extra.getTitle() + " " + extra.getPrice());
		return jpa.insertExtra(extra);

	}

	@POST
	@Path("/addMain")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMain(Main main) {
		System.out.println("in add main method " + main.getTitle());
		return jpa.insertMain(main);

	}
	
	@POST
	@Path("/addServing")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addServing(ServingForm servingForm) {
		System.out.println("in add servingForm method " + servingForm.getTitle());
		ServingForm insertedServing = jpa.insertServingForm(servingForm);
		Gson gson = new Gson();
		String jsonString = gson.toJson(insertedServing);
		System.out.println(jsonString);
		return jsonString;

	}

	@POST
	@Path("/addCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public String addCategory(@FormParam("id") int id, @FormParam("title") String title,
			@FormParam("description") String description, @FormParam("items") String items,
			@FormParam("meals") String meals, @FormParam("icon") String icon) {
		Gson gson = new Gson();
		byte[] iconB = decode(icon);
		System.out.println("in add add category method \n id: " + id + "\n title: " + title + "\n desc: " + description
				+ "\n items: " + items + "\n meals: " + meals + "\n icon: " + iconB);
		Category category = new Category();
		category.setId(id);
		category.setDescription(description);
		category.setIcon(iconB);
		Type itemType = new TypeToken<ArrayList<Item>>() {
		}.getType();
		List<Item> itemsList = gson.fromJson(items, itemType);
		category.setItems(itemsList);
		Type mealType = new TypeToken<ArrayList<Meal>>() {
		}.getType();
		List<Meal> mealsList = gson.fromJson(meals, mealType);
		category.setMeals(mealsList);
		category.setTitle(title);

		jpa.insertCategory(category);
		return "{\"result\": \"Good\"}";

	}

	@POST
	@Path("/updateCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCategory(@FormParam("id") int id, @FormParam("title") String title,
			@FormParam("description") String description, @FormParam("items") String items,
			@FormParam("meals") String meals, @FormParam("icon") String icon) {
		Gson gson = new Gson();
		System.out.println("in update category method \n id: " + id + "\n title: " + title + "\n desc: " + description
				+ "\n items: " + items + "\n meals: " + meals + "\n icon: " + icon);

		byte[] iconB = decode(icon);
		Category category = new Category();
		category.setId(id);
		category.setDescription(description);
		category.setIcon(iconB);
		Type itemType = new TypeToken<ArrayList<Item>>() {
		}.getType();
		List<Item> itemsList = gson.fromJson(items, itemType);
		category.setItems(itemsList);
		Type mealType = new TypeToken<ArrayList<Meal>>() {
		}.getType();
		List<Meal> mealsList = gson.fromJson(meals, mealType);
		category.setMeals(mealsList);
		category.setTitle(title);

		jpa.updateCategory(category);
		return "{\"result\": \"Good\"}";

	}

	private byte[] decode(String imageUrl) {
		URL url = null;
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		InputStream in;
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			in = new BufferedInputStream(url.openStream());

			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] response = out.toByteArray();
		return response;
	}

	@POST
	@Path("/adminLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public String adminLogin(@FormParam("email") String adminEmail,@FormParam("password")String adminPassword) {
		System.out.println("in admin login method " + adminEmail + " " + adminPassword);
		Gson gson = new Gson();
		if (adminEmail.equals(email) && adminPassword.equals(password)){
			Customer customer = new Customer();
			customer.setEmail(adminEmail);
			customer.setPassword(adminPassword);
			String jsonString = gson.toJson(customer);
			System.out.println(jsonString);
			return jsonString;
			
		}else{
			return null;
		}
	}

	@POST
	@Path("/getAdminPassword")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAdminPassword(@FormParam("email") String adminEmail) {
		System.out.println("in forgot password method " + adminEmail);
		if (adminEmail.equals(email)){
			Customer customer = new Customer();
			customer.setEmail(adminEmail);
			customer.setPassword(password);
			Gson gson = new Gson();

			String jsonString = gson.toJson(customer);
			System.out.println(jsonString);
			return jsonString;
		}else{
			return null;
		}

	}

}
