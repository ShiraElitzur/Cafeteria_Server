package db;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import sun.swing.PrintingStatus;
import data.Category;
import data.Customer;
import data.Drink;
import data.Extra;
import data.Item;
import data.Main;
import data.Meal;


public class JpaManager {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	private static JpaManager jpa;
	
	public static JpaManager getInstance() {
		if(jpa == null){
			return new JpaManager();
		} else {
			return jpa;
		}
	}
	
	private JpaManager() {
		emf = Persistence.createEntityManagerFactory("CafeteriaServer");
		em = emf.createEntityManager();
	}
	
	public List<Category> getCategories() {
		Query query = em.createQuery("select c from Category c");
		Vector<Category> categories = (Vector<Category>)query.getResultList();
		return categories;	
	}
	
	public List<Drink> getDrinks() {
		Query query = em.createQuery("select d from Drink d");
		Vector<Drink> drinks = (Vector<Drink>)query.getResultList();
		return drinks;	
	}
	
	public List<Extra> getExtras() {
		Query query = em.createQuery("select e from Extra e");
		Vector<Extra> extras = (Vector<Extra>)query.getResultList();
		return extras;	
	}
	
	public List<Item> getItems() {
		Query query = em.createQuery("select i from Item i");
		Vector<Item> items = (Vector<Item>)query.getResultList();
		return items;	
	}
	
	public List<Main> getMains() {
		Query query = em.createQuery("select m from Main m");
		Vector<Main> mains = (Vector<Main>)query.getResultList();
		return mains;
	}
	
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
	
	public boolean insertUser( Customer customer ) {
		em.getTransaction().begin();		
		em.persist(customer);
		em.getTransaction().commit();
		
		if(em.contains(customer)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void insertCategory( Category category ) {
		em.getTransaction().begin();
				
		em.merge(category);
		em.getTransaction().commit();
	}
	
	public void updateCategory(Category category) {

		Category c = em.find(Category.class, category.getId());

	      c.setTitle(category.getTitle());
		  c.setDescription(category.getDescription());
		  c.setIcon(category.getIcon());
		  for (Item item: c.getItems()){
			  Item itemUpdate = em.find(Item.class, item.getId());
			  itemUpdate.setPrice(item.getPrice());
			  itemUpdate.setTitle(item.getTitle());
		  }
		  c.setItems(category.getItems());
		  for (Meal meal: c.getMeals()){
			  Meal mealUpdate = em.find(Meal.class, meal.getId());
			  mealUpdate.setExtraAmount(meal.getExtraAmount());
			  mealUpdate.setExtras(meal.getExtras());
			  mealUpdate.setMain(meal.getMain());
			  mealUpdate.setPrice(meal.getPrice());
			  mealUpdate.setTitle(meal.getTitle());
		  }
		  c.setMeals(category.getMeals());
		  em.getTransaction().begin();
		  em.merge(c);
		  em.getTransaction().commit();		
	}
	
	public Drink insertDrink( Drink drink ) {
		em.getTransaction().begin();
				
		em.persist(drink);
		em.getTransaction().commit();
		return drink;
	}
	
	public Main insertMain( Main main ) {
		em.getTransaction().begin();
				
		em.persist(main);
		em.getTransaction().commit();
		return main;
	}
	

	public Extra insertExtra(Extra extra) {
		em.getTransaction().begin();
		
		em.persist(extra);
		em.getTransaction().commit();	
		return extra;
	}
	
	public void deleteDrink( Drink toDelete ) {
		 Drink drink = em.find(Drink.class, toDelete.getId());
		 
		  em.getTransaction().begin();
		  em.remove(drink);
		  em.getTransaction().commit();
	}
	
	public void editDrink(Drink drink) {
		Drink update = em.find(Drink.class, drink.getId());
		 
		  em.getTransaction().begin();
		  update.setPrice(drink.getPrice());
		  update.setTitle(drink.getTitle());
		  em.getTransaction().commit();
		
	}

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
	

	public void deleteCategory(Category toDelete) {
		Category category = em.find(Category.class, toDelete.getId());
		 
		  em.getTransaction().begin();
		  em.remove(category);
		  em.getTransaction().commit();			
	}
	
	public void deleteMeals(List<Meal> meals) {
		for (Meal m: meals){
			Meal meal = em.find(Meal.class, m.getId());
			
			  em.getTransaction().begin();
			  em.remove(meal);
			  em.getTransaction().commit();
		}
		
	}

	public void editExtra(Extra extra) {
		Extra update = em.find(Extra.class, extra.getId());
		 
		  em.getTransaction().begin();
		  update.setPrice(extra.getPrice());
		  update.setTitle(extra.getTitle());
		  em.getTransaction().commit();		
	}
	
	public void editMain(Main main) {
		Main update = em.find(Main.class, main.getId());
		 
		  em.getTransaction().begin();
		  update.setTitle(main.getTitle());
		  em.getTransaction().commit();		
	}
	
	
	public void updateItem(Item item){
		Item it = em.find(Item.class, item.getId());
		 
		  em.getTransaction().begin();
		  it.setPrice(item.getPrice());
		  it.setTitle(item.getTitle());
		  em.getTransaction().commit();
	}
	

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
	
	
	public void insertPushTokenToUserRecord( int user, String pushToken ) {
		Customer c = em.find(Customer.class, user);
		em.getTransaction().begin();
		c.setPushToken(pushToken);
		em.getTransaction().commit();	
	}
	
	public String getUserPushToken( int userId ) {
		System.out.println("get token for : "+userId);
		Customer c = em.find(Customer.class, userId );
		if ( c != null ) {
			return c.getPushToken();
		}
		return null;
	}
	
	private Category getCategory() {
		Query query = em.createQuery("select c from Category c where c.title = 'סלטים'");
		Category category = (Category)query.getSingleResult();
		return category;
	}
	
	private void printItemsInCat( Category c ) {
		Query query = em.createQuery("select i from Item i where i.category = :c");
		query.setParameter("c",c);
		List<Item> items = query.getResultList();
		for( Item item : items ) {
			System.out.println(item.getTitle());
		}
	}
	

	public void insertMeal( Meal meal ) {
		em.getTransaction().begin();
				
		em.persist(meal);
		em.getTransaction().commit();
	}
	
	public static void main ( String [] args ) {
		JpaManager jpa = new JpaManager();
		
	}













}
