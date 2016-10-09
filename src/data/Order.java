package data;

import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import org.eclipse.persistence.annotations.Customizer;
//import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 * 
 * @author Shira Elitzur
 * 
 * An instance of this class represent an order from the cafeteria
 */
@Entity
@Table (name = "orders")
public class Order {

	/**
	 * The id of the order (auto generated by db)
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "Id", nullable=false)
	private int id;
	
	/**
	 * The customer who made this order
	 */
	@ManyToOne // Order has one Customer, Customer has many orders. Bidirectional ( Customer has list of orders )
	@JoinColumn( name = "Customer_Id")
	private Customer customer;
	
	/**
	 * The meals that this order contains
	 */
	@OneToMany( mappedBy = "order" ) // One Order can have many OrderedMeal(s), OrderedMeal can have only one Order. Bidirectional
	private ArrayList<OrderedMeal> meals;
	
	/**
	 * The items that this order contains
	 */
	@ManyToMany // One Order can have many Items, Item can be connected to many Orders. Unidirectional
	private ArrayList<Item> items;
	
	/**
	 * The date and time when this order created
	 */
	@Column( name = "Date_Time", nullable=false)
	@Temporal( TemporalType.TIMESTAMP)
	private Calendar calendar;
	
	/**
	 * The amount to pay for the items/meals in this order
	 */
	@Column( name = "Payment", nullable=false)
	private double payment;
	
	/**
	 * An indicator if the customer already paid for this order
	 */
	@Column( name = "Is_Paid", nullable=false)
	private boolean isPaid;
	
	/**
	 * An indicator if the order was delivered
	 */
	@Column( name = "Is_Delivered", nullable=false)
	private boolean isDelivered;
	
	/**
	 * An indicator if the order is ready
	 */
	@Column( name = "Is_Ready", nullable=false)
	private boolean isReady;

	/**
	 * The customer can attach a comment to his order
	 */
	@Column( name = "Comment", nullable=true, length=5000)
	private String comment;
	
    /**
     * The customer can add a time for picking up his order
     */
	@Column( name = "Pickup_Time", nullable=true, length=50)
    private String pickupTime;
	
	/**
	 * Returns the id of this order
	 * @return the id of this order
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id for this order
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the customer that made this order
	 * @return the customer that made this order
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Sets customer object to this order
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Returns the meals that this order contains
	 * @return the meals that this order contains
	 */
	public ArrayList<OrderedMeal> getMeals() {
		return meals;
	}

	/**
	 * Sets list of meals for this order
	 * @param meals
	 */
	public void setMeals(ArrayList<OrderedMeal> meals) {
		this.meals = meals;
	}

	/**
	 * Returns the items that this order contains
	 * @return the items that this order contains
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * Sets list of items for this order
	 * @param items
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	/**
	 * Returns the Date&Time when this order was created
	 * @return
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Sets the Date and Time for this order ( when it was created )
	 * @param dateTime
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * Returns the amount to pay for this order
	 * @return the amount to pay for this order
	 */
    public double getPayment() {
        payment = 0;
        double drinkPrice = 0;
        double extrasPrice = 0;
        
        for( OrderedMeal meal : getMeals() ) {
            if (meal.getChosenDrink()!= null) {
                drinkPrice = meal.getChosenDrink().getPrice();
            }
        	// if the amount of extras chosen is bigger then the amount allowed,
        	// add the price of last meal - to be changed according to the expensive/cheap extra 
        	if (meal.getChosenExtras().size() > meal.getParentMeal().getExtraAmount()){
        		extrasPrice = meal.getChosenExtras().get(meal.getChosenExtras().size()-1).getPrice();
        	}
            payment += (meal.getParentMeal().getPrice()) + drinkPrice + extrasPrice;
        }

        for( Item item : getItems() ) {
            payment += item.getPrice();
        }

        return payment;
    }

	/**
	 * Sets the amount to pay for this order
	 * @param payment
	 */
	public void setPayment(double payment) {
		this.payment = payment;
	}

	/**
	 * Returns true if the order was already paid or false if not
	 * @return true if the order was already paid
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * Sets true if the order was already paid or false if not
	 * @param isPaid
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/**
	 * Returns true if the order was already delivered or false if not
	 * @return true if the order was already delivered 
	 */
	public boolean isDelivered() {
		return isDelivered;
	}

	/**
	 * Sets whether order was already delivered or false if not
	 * @param isDelivered
	 */
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	/**
	 * Returns true if the order is ready or false if it is not yet ready
	 * @return
	 */
	public boolean isReady() {
		return isReady;
	}

	/**
	 * Sets whether the order is ready
	 * @param isReady
	 */
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	/**
	 * Returns the comment that was attached to this order
	 * @return the comment of this order
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets comment to this order
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
