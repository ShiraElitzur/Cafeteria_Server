package data;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import org.eclipse.persistence.jpa.config.Cascade;


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
	@OneToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)// One Order can have many OrderedMeal(s), OrderedMeal can have only one Order. Bidirectional
	private List<OrderedMeal> meals;
	
	/**
	 * The items that this order contains
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)// One Order can have many Items, Item can be connected to many Orders. Unidirectional
	private List<OrderedItem> items;
	
	/**
	 * The date and time when this order created
	 */
	@Column( name = "Date_Time", nullable=false)
	@Temporal( TemporalType.TIMESTAMP)
	private Date date;
	
	/**
	 * The amount to pay for the items/meals in this order
	 */
	@Column( name = "Payment", nullable=false)
	private double payment;

	
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
	public List<OrderedMeal> getMeals() {
		return meals;
	}

	/**
	 * Sets list of meals for this order
	 * @param meals
	 */
	public void setMeals(List<OrderedMeal> meals) {
		this.meals = meals;
	}

	/**
	 * Returns the items that this order contains
	 * @return the items that this order contains
	 */
	public List<OrderedItem> getItems() {
		return items;
	}

	/**
	 * Sets list of items for this order
	 * @param items
	 */
	public void setItems(List<OrderedItem> items) {
		this.items = items;
	}

    /**
     * Returns the Date&Time when this order was created
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the Date and Time for this order ( when it was created )
     * @param
     */
    public void setDate(Date date) {
        this.date = date;
    }

	/**
	 * Returns the amount to pay for this order
	 * @return the amount to pay for this order
	 */
    public double getPayment() {
        payment = 0;
        
        for( OrderedMeal meal : getMeals() ) {
        	payment += meal.getTotalPrice();
        }

        for( OrderedItem item : getItems() ) {
            payment += item.getParentItem().getPrice();
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

