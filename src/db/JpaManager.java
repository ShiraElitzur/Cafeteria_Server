package db;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import org.eclipse.persistence.exceptions.DatabaseException;

import com.google.gson.Gson;

import sun.swing.PrintingStatus;
import data.Category;
import data.Customer;
import data.Drink;
import data.Extra;
import data.Item;
import data.Main;
import data.Meal;
import data.Order;
import data.OrderedMeal;
import data.Cafeteria;
import data.ServingForm;


public class JpaManager {

	/**
	 * Entity Manger Factory
	 */
	private EntityManagerFactory emf;
	
	/**
	 * Entity Manager
	 */
	private EntityManager em;
	
	/**
	 * Singleton instance of this class
	 */
	private static JpaManager jpa;
	
	/**
	 * Returns the instance of jpaManger
	 * @return
	 */
	public static JpaManager getInstance() {
		if(jpa == null){
			return new JpaManager();
		} else {
			return jpa;
		}
	}
	
	/**
	 * Constructor of this class.
	 * Create the entity manger.
	 */
	private JpaManager() {
		emf = Persistence.createEntityManagerFactory("CafeteriaServer");
		em = emf.createEntityManager();
	}
	
	/**
	 * Returns list of categories
	 * @return list of categories
	 */
	public List<Category> getCategories() {
		Query query = em.createQuery("select c from Category c");
		Vector<Category> categories = (Vector<Category>)query.getResultList();
		return categories;	
	}
	
	/**
	 * Returns list of drinks
	 * @return list of drinks
	 */
	public List<Drink> getDrinks() {
		Query query = em.createQuery("select d from Drink d");
		Vector<Drink> drinks = (Vector<Drink>)query.getResultList();
		return drinks;	
	}
	
	
	/**
	 * Returns list of extras
	 * @return list of extras
	 */
	public List<Extra> getExtras() {
		Query query = em.createQuery("select e from Extra e");
		Vector<Extra> extras = (Vector<Extra>)query.getResultList();
		return extras;	
	}
	
	
	/**
	 * Returns list of items
	 * @return list of items
	 */
	public List<Item> getItems() {
		Query query = em.createQuery("select i from Item i");
		Vector<Item> items = (Vector<Item>)query.getResultList();
		return items;	
	}
	
	
	/**
	 * Returns list of mains
	 * @return list of mains
	 */
	public List<Main> getMains() {
		Query query = em.createQuery("select m from Main m");
		Vector<Main> mains = (Vector<Main>)query.getResultList();
		return mains;
	}
	
	/**
	 * Returns list of serving
	 * @return list of serving
	 */
	public List<ServingForm> getServings() {
		Query query = em.createQuery("select s from ServingForm s");
		Vector<ServingForm> servings = (Vector<ServingForm>)query.getResultList();
		return servings;
	}

	
	/**
	 * This method checks if the customer exist according to the email and the password.
	 * if so, returns the customer, else returns null
	 * @param email
	 * @param password
	 * @return customer if exist otherwise null
	 */
	public Customer isUserExist( String email,String password ) {	
		Query query = em.createQuery("select c from Customer c where c.email = :email and c.password = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		Customer c = null;
		try{
		c = (Customer)query.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		return c;
	}

	
	/**
	 * This method checks if the admin exist according to the email and the password.
	 * if so, returns his cafeteria, else returns null
	 * @param email
	 * @param password
	 * @return cafeteria if exist otherwise null
	 */
	public Cafeteria isAdminExist( String email,String password ) {	
		Query query = em.createQuery("select c from Cafeteria c where c.adminEmail = :email and c.adminPassword = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		Cafeteria c = null;
		try{
		c = (Cafeteria)query.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		return c;
	}
	
	/**
	 * This method checks if the customer exist according to the email.
	 * if so, returns the customer, else returns null
	 * @param email
	 * @param password
	 * @return customer if exist otherwise null
	 */
	public Customer findUserByEmail( String email ) {	
		Query query = em.createQuery("select c from Customer c where c.email = :email");
		query.setParameter("email", email);
		
		Customer c = null;
		try{
		c = (Customer)query.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		return c;
	}
	
	/**
	 * Returns the password of the customer according to the given email
	 * @param email
	 * @return the customer if exist, otherwise null
	 */
	public Customer getUserPassword( String email ) {	
		Query query = em.createQuery("select c from Customer c where c.email = :email");
		query.setParameter("email", email);
		
		Customer c = null;
		try{
		c = (Customer)query.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		return c;
	}
	
	/**
	 * This method returns the customer is inserted successfully, else returns null
	 * @return customer if exist otherwise null
	 */
	public Customer ResigsterUser( Customer customer ) {
		em.getTransaction().begin();		
		em.persist(customer);
		em.getTransaction().commit();
		
		return customer;
	}
	
	/**
	 * Returns a boolean that indicates if user inserted successfully
	 * 
	 * @param customer
	 * @return a boolean that indicates if user inserted successfully
	 */
	public int insertUser(Customer customer) {
		em.getTransaction().begin();

		try {
			em.persist(customer);
			em.getTransaction().commit();

			if (em.contains(customer)) {
				return 0;
			} else {
				return 1;
			}

		} catch (PersistenceException e) {
			return -1;
		}
	}
	
	/**
	 * Returns the user that inserted
	 * @param customer
	 * @return the user that inserted
	 */
	public Customer insertFacebookUser( Customer customer ) {
		em.getTransaction().begin();		
		em.persist(customer);
		em.getTransaction().commit();
		
		return customer;
	}
	
	/**
	 * Returns a boolean that indicates if user updated successfully
	 * @param customer
	 * @return a boolean that indicates if user updated successfully
	 */
	public boolean updateUser( Customer customer ) {
		Customer c = em.find(Customer.class, customer.getId());
		 
		  em.getTransaction().begin();
		  c.setEmail(customer.getEmail());
		  c.setFirstName(customer.getFirstName());
		  c.setLastName(customer.getLastName());
		  c.setPassword(customer.getPassword());
		  c.setImage(customer.getImage());
		  em.getTransaction().commit();
		  
		  return true;
	}
	
//	public byte[] getImageForUser( int userId ) {
//		Customer c = em.find(Customer.class, userId);
//		return c.getImage();
//	}
	
	/**
	 * This method inserts the given order
	 * @param order
	 */
	public boolean insertOrder( Order order ) {
		em.getTransaction().begin();		
		em.merge(order);
		em.getTransaction().commit();
		
		if(em.contains(order)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method inserts the given category
	 * @param category
	 */
	public void insertCategory( Category category ) {
		em.getTransaction().begin();
				
		em.merge(category);
		em.getTransaction().commit();
	}
	
	/**
	 * This method updates the given category
	 * @param category
	 */

	public Boolean updateCategory(Category category) {

		Category c = em.find(Category.class, category.getId());

	      c.setTitle(category.getTitle());
		  c.setDescription(category.getDescription());
		  c.setIcon(category.getIcon());
		  System.out.println("size of items of new: " + category.getItems());
		  System.out.println("size of items: " + c.getItems());

		  for (Item item: category.getItems()){
			  Item itemUpdate = em.find(Item.class, item.getId());
			  if (itemUpdate != null){
				  itemUpdate.setPrice(item.getPrice());
				  itemUpdate.setTitle(item.getTitle());
			  } else{
				  insertItem(item);
			  }
		  }
		  c.setItems(category.getItems());
		  for (Meal meal: category.getMeals()){
			  Meal mealUpdate = em.find(Meal.class, meal.getId());
			  if (mealUpdate != null){
			  mealUpdate.setExtraAmount(meal.getExtraAmount());
			  mealUpdate.setExtras(meal.getExtras());
			  mealUpdate.setMain(meal.getMain());
			  mealUpdate.setPrice(meal.getPrice());
			  mealUpdate.setTitle(meal.getTitle());
			  mealUpdate.setServing(meal.getServing());
			  mealUpdate.setIncludesDrink(meal.isIncludesDrink());
			  } else{
				  insertMeal(meal);
			  }
		  }
		  c.setMeals(category.getMeals());
		  em.getTransaction().begin();
		  em.merge(c);
		  em.getTransaction().commit();	
		  
		  return true;
	}
	
	/**
	 * This method inserts the given drink
	 * 
	 * @param drink
	 * @return the inserted drink
	 */
	public String insertDrink(Drink drink) {
		em.getTransaction().begin();

		try {
			em.persist(drink);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			return "-1";
		}
		return new Gson().toJson(drink);
	}
	
	/**
	 * This method inserts the given main
	 * @param main
	 * @return the inserted main
	 */
	public String insertMain( Main main ) {
		em.getTransaction().begin();
			
		try {
			em.persist(main);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			return "-1";
		}
		
		return new Gson().toJson(main);
	}
	
	/**
	 * This method inserts the given serving form
	 * @param serving form
	 * @return the inserted serving form
	 */
	public ServingForm insertServingForm(ServingForm servingForm) {
		em.getTransaction().begin();
		
		em.persist(servingForm);
		em.getTransaction().commit();
		return servingForm;
	}
	
	/**
	 * This method inserts the given extra
	 * @param extra
	 * @return the inserted extra
	 */
	public String insertExtra(Extra extra) {
		em.getTransaction().begin();
		
		try{
			em.persist(extra);
			em.getTransaction().commit();
			} catch(PersistenceException ex){
				return "-1";
			}
		return new Gson().toJson(extra);
	}
	
	/**
	 * Delete the given drink
	 * @param toDelete
	 */
	public void deleteDrink( Drink toDelete ) {
		 Drink drink = em.find(Drink.class, toDelete.getId());
		 
		  em.getTransaction().begin();
		  em.remove(drink);
		  em.getTransaction().commit();
	}
	
	/**
	 * Edit the given drink
	 * @param drink
	 */
	public String editDrink(Drink drink) {
		Drink update = em.find(Drink.class, drink.getId());
			try {
				  em.getTransaction().begin();
				  update.setPrice(drink.getPrice());
				  update.setTitle(drink.getTitle());
				  em.getTransaction().commit();
			} catch (PersistenceException e) {
				return "-1";
			}
		return "0";
		
	}

	/**
	 * Deletes the given extra
	 * @param toDelete
	 */
	public void deleteExtra(Extra toDelete) {
		Extra extra = em.find(Extra.class, toDelete.getId());
		em.getTransaction().begin();

		Query query = em.createQuery("select m from Meal m");
		Vector<Meal> meals = (Vector<Meal>) query.getResultList();
		for (Meal meal : meals) {
			for (int i=0; i< meal.getExtras().size(); i++){
				if (extra.getId() == meal.getExtras().get(i).getId()){
					meal.getExtras().remove(i);
				}
			}
		}
		em.remove(extra);
		em.getTransaction().commit();
	}
	
	/**
	 * Deletes the given main
	 * @param toDelete
	 */
	public void deleteMain(Main toDelete) {
		em.getTransaction().begin();
		
		Main main = em.find(Main.class, toDelete.getId());
		List<Meal> todel = new ArrayList<>();
		Meal currMeal;
		System.out.println("d");
		Query query = em.createQuery("select c from Category c");
		Vector<Category> categories = (Vector<Category>) query.getResultList();
		for (Category category: categories) {
			System.out.println("meals size: " + category.getMeals().size());
			for (Meal meal: category.getMeals()){
				currMeal = meal;
				System.out.println(meal.getMain().getId() + " = " + main.getId());
				if (meal.getMain().getId() == main.getId()){
					todel.add(meal);
				}
			}
			System.out.println("to del size: " + todel.size());
			if (todel.size() > 0){
				category.getMeals().removeAll(todel);
				em.merge(category);
			}
			for (int i=0;i<todel.size();i++){
				em.remove(todel.get(i));
			}
			todel.clear();
		}

		em.remove(main);
		em.getTransaction().commit();
	}
	
	/**
	 * Deletes the given category
	 * @param toDelete
	 */
	public void deleteCategory(Category toDelete) {
		Category category = em.find(Category.class, toDelete.getId());
		 
		  em.getTransaction().begin();
		  em.remove(category);
		  em.getTransaction().commit();			
	}
	
	/**
	 * Deletes the given meals
	 * @param meals
	 */
	public void deleteMeals(List<Meal> meals) {
		for (Meal m: meals){
			Meal meal = em.find(Meal.class, m.getId());
			
			em.getTransaction().begin();

			Query query = em.createQuery("select meal from OrderedMeal meal where meal.parentMeal = :parentMeal");
			query.setParameter("parentMeal", m);
			Vector<OrderedMeal> orderedMeals = (Vector<OrderedMeal>)query.getResultList();
			for( OrderedMeal om : orderedMeals) {
				om.setParentMeal(null);
			}
			
		    em.remove(meal);
		    em.getTransaction().commit();
		}
	}

	/**
	 * Edits the given extra 
	 * @param extra
	 */
	public String editExtra(Extra extra) {
		Extra update = em.find(Extra.class, extra.getId());
		 
		try{
		  em.getTransaction().begin();
		  update.setPrice(extra.getPrice());
		  update.setTitle(extra.getTitle());
		  em.getTransaction().commit();	
		} catch(PersistenceException ex){
			return "-1";
		}
		return "0";
		  
	}
	
	/**
	 * Edits the given main
	 * 
	 * @param main
	 */
	public String editMain(Main main) {
		Main update = em.find(Main.class, main.getId());
		try {
			em.getTransaction().begin();
			update.setTitle(main.getTitle());
			em.getTransaction().commit();
			em.getTransaction().commit();
		} catch (PersistenceException ex) {
			return "-1";
		}
		return "0";
	}
	
	/**
	 * Update the given item
	 * @param item
	 */
	public void updateItem(Item item){
		Item it = em.find(Item.class, item.getId());
		 
		  em.getTransaction().begin();
		  it.setPrice(item.getPrice());
		  it.setTitle(item.getTitle());
		  it.setInStock(item.isInStock());
		  em.getTransaction().commit();
	}
	
	/**
	 * Update the given extra
	 * @param extra
	 */
	public void updateExtra(Extra extra) {
		Extra ex = em.find(Extra.class, extra.getId());
		 
		  em.getTransaction().begin();
		  ex.setPrice(extra.getPrice());
		  ex.setTitle(extra.getTitle());
		  ex.setInStock(extra.isInStock());
		  em.getTransaction().commit();
	}
	
	/**
	 * Update the given main
	 * @param main
	 */
	public void updateMain(Main main) {
		Main ma = em.find(Main.class, main.getId());
		 
		  em.getTransaction().begin();
		  ma.setTitle(main.getTitle());
		  ma.setInStock(main.isInStock());
		  em.getTransaction().commit();
	}

	/**
	 * Update the given servingForm
	 * @param servingForm
	 */
	public void updateServingForm(ServingForm servingForm) {
		ServingForm sf = em.find(ServingForm.class, servingForm.getId());
		 
		  em.getTransaction().begin();
		  sf.setTitle(servingForm.getTitle());
		  sf.setInStock(servingForm.isInStock());
		  em.getTransaction().commit();		
	}
	

	/**
	 * Updates the given meal
	 * @param meal
	 */
	public void updateMeal(Meal meal) {
		Meal m = em.find(Meal.class, meal.getId());
		 
		  em.getTransaction().begin();
		  m.setPrice(meal.getPrice());
		  m.setTitle(meal.getTitle());
		  m.setExtraAmount(meal.getExtraAmount());
		  m.setExtras(meal.getExtras());
		  m.setMain(meal.getMain());
		  em.getTransaction().commit();		
		  
	}
	

	/**
	 * Inserts the given meal
	 * @param meal
	 */
	public void insertMeal( Meal meal ) {
		em.getTransaction().begin();
				
		em.persist(meal);
		em.getTransaction().commit();
	}
	
	/**
	 * Inserts the given item
	 * @param item
	 */
	public void insertItem( Item item ) {
		em.getTransaction().begin();
				
		em.persist(item);
		em.getTransaction().commit();
	}
	
	/**
	 * Inserts the given token to the user with given id
	 * @param user
	 * @param pushToken
	 */
	public void insertPushTokenToUserRecord( int user, String pushToken ) {
		deletePushTokenFromUserRecord(pushToken);
		Customer c = em.find(Customer.class, user);
		  em.getTransaction().begin();
		  c.setPushToken(pushToken);
		  em.getTransaction().commit();	
	}
	
	/**
	 * Deletes the given token from the user with the given id
	 * @param pushToken
	 */
	public void deletePushTokenFromUserRecord( String pushToken ) {
		System.out.println("inside delete token");
		Query query = em.createQuery("select c from Customer c where c.pushToken = :push");
		query.setParameter("push", pushToken);
		Customer customer;
		try{
			customer = (Customer)query.getSingleResult();
			System.out.println(customer.getFirstName());
		} catch ( NoResultException e ) {
			return;
		}
		em.getTransaction().begin();
		customer.setPushToken(null);
		em.getTransaction().commit();	
	}
	
	/**
	 * Returns the order with the given id
	 * @param orderId
	 * @return the order
	 */
	public Order getOrderById( int orderId ) {
		Order order = em.find(Order.class, orderId );
		if ( order != null ) {
			return order;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the token of the user with the given id
	 * @param userId
	 * @return the token
	 */
	public String getUserPushToken( int userId ) {
		System.out.println("get token for : "+userId);
		Customer c = em.find(Customer.class, userId );
		if ( c != null ) {
			return c.getPushToken();
		}
		return null;
	}
	
		
	/**
	 * Returns list of the orders
	 * @return list of the orders
	 */
	public List<Order> getOrders() {
		Query query = em.createQuery("select o from Order o where o.isDelivered = false AND o.isReady = false");
		Vector<Order> orders = (Vector<Order>)query.getResultList();
		return orders;
	}
	
	
	/**
	 * Returns list of the orders
	 * @return list of the orders
	 */
	public List<Order> getOrdersReady() {
		Query query = em.createQuery("select o from Order o where o.isReady = true AND o.isDelivered = false");
		Vector<Order> orders = (Vector<Order>)query.getResultList();
		return orders;
	}
	
	/**
	 * Returns list of the orders
	 * @return list of the orders
	 */
	public List<Order> getDeliveredOrders() {
		Query query = em.createQuery("select o from Order o where o.isReady = true AND o.isDelivered = true");
		Vector<Order> orders = (Vector<Order>)query.getResultList();
		return orders;
	}
	

	public List<Order> getOrdersByDate(String date) {
		Calendar calendar = Calendar.getInstance();
		Calendar oneMonthAfter = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		try {
			calendar.setTime(sdf.parse(date));
			oneMonthAfter.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after formating: " + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.DAY_OF_MONTH));
		
		oneMonthAfter.add(Calendar.MONTH, 1);
		Query query = em.createQuery("select o from Order o where o.date >= :date AND o.date < :endMonth");
		query.setParameter("date", calendar.getTime());
		query.setParameter("endMonth", oneMonthAfter.getTime());

		Vector<Order> orders = (Vector<Order>)query.getResultList();
		System.out.println("ORDERS SIZE "+ orders.size());

		return orders;
	}
	
	/**
	 * Update the given order is delivered property
	 * @param order
	 */
	public void updateOrderDelivered(Order order) {
		Order or = em.find(Order.class, order.getId());
		 
		  em.getTransaction().begin();
		  or.setDelivered(order.isDelivered());
		  em.getTransaction().commit();		
	}
	
	/**
	 * Update the given order is ready property
	 * @param order
	 */
	public void updateOrderReady(Order order) {
		Order or = em.find(Order.class, order.getId());
		 
		  em.getTransaction().begin();
		  or.setReady(order.isReady());
		  em.getTransaction().commit();		
	}
	
	public Cafeteria updateCafeteria(Cafeteria c) {
		Query query = em.createQuery("select c from Cafeteria c where c.adminEmail = :email");
		query.setParameter("email", c.getAdminEmail());
		
		Cafeteria cafeteria = null;
		try{
			cafeteria = (Cafeteria)query.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		 em.getTransaction().begin();
		 cafeteria.setCafeteriaName(c.getCafeteriaName());
		 cafeteria.setOpeningHoursStart(c.getOpeningHoursStart());
		 cafeteria.setOpeningHoursEnd(c.getOpeningHoursEnd());
		 em.getTransaction().commit();	
		
		return c;
	}
	
	public Cafeteria getAdminPassword(String email) {
		Query query = em.createQuery("select c from Cafeteria c where c.adminEmail = :email");
		query.setParameter("email", email);
		
		Cafeteria cafeteria = null;
		try{
			cafeteria = (Cafeteria)query.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		 em.getTransaction().begin();
		
		return cafeteria;
	}
	
	/**
	 * 
	 */
	public List<Meal> getFavoriteMeals( int userId ) {
		Customer c = em.find(Customer.class, userId );
		System.out.println("get f meals for user " + userId);
//		Query query = em.createQuery("select o.meals from Order o where o.customer = :customer");
//		query.setParameter("customer", c);
//		SELECT * FROM ROOT.ORDERED_MEALS WHERE Id In 
//		(SELECT Meals_Id FROM ROOT.ORDERS_ORDERED_MEALS WHERE Order_Id IN
//				(SELECT Id FROM ROOT.ORDERS WHERE Customer_Id = 1));

//		Query query = em.createQuery("select om.parentMeal from OrderedMeal om where om in ("
//				+ "select o.meals from Order o where o.customer = :customer)");
		
		List<Meal> favoritesMeals = new ArrayList<>();
		String sqlScript = "select meal_id from ordered_meals where id in "
				+ "( select meals_id from orders_ordered_meals where order_id in "
				+"( select id from orders where customer_id = "+userId+") ) group by meal_id order by count(meal_id)" 
				+"desc FETCH FIRST 5 ROWS ONLY";
		
		Query q = em.createNativeQuery(sqlScript);
		List<Integer> meals = q.getResultList();

		for (Integer m : meals) {
			Meal meal = em.find(Meal.class, m );
			favoritesMeals.add(meal);
		}
		
		return favoritesMeals;
		//Vector<OrderedMeal> meals = (Vector<OrderedMeal>)query.getResultList();
	}
	
	public Meal getMeal( int mealId ) {
		Meal meal = em.find(Meal.class, mealId );
		return meal;
	}
	
	public boolean isMealExist( Meal meal ) {
		if (em.contains(meal)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public List<Item> getFavoriteItems( int userId ) {
		Customer c = em.find(Customer.class, userId );

		System.out.println("get f items for user " + userId);
		List<Item> favoriteItems = new ArrayList<>();
		String sqlScript = "select item_id from ordered_items where id in "
				+ "( select items_id from orders_ordered_items where order_id in "
				+"( select id from orders where customer_id = "+userId+") ) group by item_id order by count(item_id)" 
				+"desc FETCH FIRST 10 ROWS ONLY";
		
		Query q = em.createNativeQuery(sqlScript);
		List<Integer> items = q.getResultList();

		for (Integer i : items) {
			Item item = em.find(Item.class, i );
			favoriteItems.add(item);
		}
		
		return favoriteItems;
		//Vector<OrderedMeal> meals = (Vector<OrderedMeal>)query.getResultList();
	}

	public List<Cafeteria> getServers() {
		Query query = em.createQuery("select c from Cafeteria c");
		Vector<Cafeteria> servers = (Vector<Cafeteria>)query.getResultList();
		return servers;
	}

	
	public static void main ( String [] args ) {
		JpaManager jpa = JpaManager.getInstance();
	}


}
