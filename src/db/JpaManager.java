package db;
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
		System.out.println(categories.get(0).getTitle());
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
				
		em.persist(category);
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
	
	private Category getCategory() {
		Query query = em.createQuery("select c from Category c where c.title = 'Hot Drinks'");
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
	

	
	public static void main ( String [] args ) {
		JpaManager jpa = new JpaManager();
	}



}
