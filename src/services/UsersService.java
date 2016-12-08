package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.xml.internal.bind.v2.TODO;

import data.Customer;
import db.JpaManager;

@Path("/users")
public class UsersService {

	/**
	 * The instance of jpaManger
	 */
	private JpaManager jpa = JpaManager.getInstance();
	
	/**
	 * This service checks if the user with the given details exists in the db
	 * @param email
	 * @param password
	 * @return json string that represents the customer or null if doesn't exist
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/isUserExist")
	public String isUserExist( @QueryParam("email") String email, @QueryParam("pass") String password ){
		System.out.println("in check user: " + email + " " + password);
		Customer c = jpa.isUserExist(email,password);
		//c.setImage(null);
		Gson json = new Gson();
		return json.toJson(c);
	}
	
	/**
	 * This service inserts a new user to the db
	 * @param input an input stream between the server and the client
	 * @return 'ok' if user inserted successfully otherwise 'notOk'
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/insertUser")
	public String insertUser( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
		StringBuilder jsonUser = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonUser.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		// with Gson i convert the json to Customer object
		Gson gson = new Gson();
		Customer c = gson.fromJson(jsonUser.toString(), Customer.class);
		System.out.println(c.getFirstName());
		boolean result = jpa.insertUser(c);
		if(result) {
			return "OK";
		} else {
			return "notOk";
		}
		// **** Fix the return value to boolean or json !!
//		JsonObject jsonObject = new JsonObject();
//		jsonObject.addProperty("result", );
//		return jsonObject;
	}
	
	/**
	 * This service inserts a new user to the db
	 * @param input an input stream between the server and the client
	 * @return 'ok' if user inserted successfully otherwise 'notOk'
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/insertFacebookUser")
	public String insertFacebookUser( InputStream input ) {
		StringBuilder jsonUser = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonUser.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		Gson gson = new Gson();
		Customer c = gson.fromJson(jsonUser.toString(), Customer.class);
		System.out.println(c.getFirstName());
		Customer result = jpa.insertFacebookUser(c);
		return gson.toJson(result);
	}
	
	/**
	 * This service updates a new user to the db
	 * @param input an input stream between the server and the client
	 * @return 'ok' if user updated successfully otherwise 'notOk'
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateUser")
	public String updateUser( InputStream input ) {
		// to get the json from the client i receive in this method the input stream and build the json string
		StringBuilder jsonUser = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonUser.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		// with Gson i convert the json to Customer object
		Gson gson = new Gson();
		Customer c = gson.fromJson(jsonUser.toString(), Customer.class);
		System.out.println(c.getFirstName());
		boolean result = jpa.updateUser(c);
		if(result) {
			return "OK";
		} else {
			return "notOk";
		}
	}
	
	/**
	 * This service checks if the user with the given details exists in the db, if he isn't register
	 * the user to the db.
	 * @param email
	 * @param password
	 * @return json string that represents the customer or null if doesn't exist
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/validateOrSignUpUser")
	public String validateOrSignUpUser( InputStream input ){
		StringBuilder jsonUser = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonUser.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		Gson gson = new Gson();
		Customer c = gson.fromJson(jsonUser.toString(), Customer.class);
		c.setId(0);
		
		Customer isExist = jpa.isUserExist(c.getEmail(),c.getPassword());
		if (isExist == null){
			isExist = jpa.ResigsterUser(c);
		}
		return new Gson().toJson(isExist);
	}
	
//	/**
//	 * This service returns the image of the user with the given id
//	 * @param userId
//	 * @return json string that represents the image if exists
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getImageForUser")
//	public String getImageForUser( @QueryParam("userId") int userId ){
//		
//		byte[] imageBytes = jpa.getImageForUser(userId);
//		Gson json = new Gson();
//		return json.toJson(imageBytes);
//	}
}
