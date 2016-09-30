package data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Shira Elitzur
 * 
 * An instance of this class represent A specific meal that a specific customer ordered.
 * An ordered meal will contain a list of the chosen extras and it can contain a comment
 * 
 */
@Entity
@Table ( name = "ordered_meals")
public class OrderedMeal {

	/**
	 * The id of this ordered meal (auto generated by db)
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "Id", nullable=false)
	private int id;
	
	/**
	 * The meal in the menu that this ordered meal is an actual instance of
	 */
	@ManyToOne // OrderedMeal has only one Meal, Meal can have many OrderedMeal(s). Unidirectional ( No access from Meal to its OrderedMeal(s) )
	@JoinColumn( name = "Meal_Id")
	private Meal parentMeal;
	
	/**
	 * List of the extras that the customer chose
	 */
	@ManyToMany // OrderedMeal have many items, Item can be connected to many meals. Unidirectional ( No access from Item to its OrderedMeal(s) )
	@JoinTable( inverseJoinColumns = @JoinColumn( name = "Chosen_Extra_Id"))
	private List<Extra> chosenExtras;
	
	/** 
	 * The drink that the customer chose (optional)
	 */
	@ManyToOne // OrderedMeal has only one Drink, Drink can be connected to many OrderedMeal(s). Unidirectional ( No access from Drink to its OrderedMeal(s) )
	@JoinColumn( name = "Drink_Id" )
	private Drink chosenDrink;
	
	/**
	 * The customer can attach comment to the ordered meal
	 */
	@Column( name = "Comment", nullable=true, length=5000)
	private String comment;
	
	/**
	 * The parent-order of this ordered meal
	 */
	@ManyToOne // OrderedMeal has only one Order, Order can have many OrderedMeal(s). Bidirectional ( Order has list of OrderedMeal )
	@JoinColumn( name = "Order_Id" )
	private Order order;
	
	

	/**
	 * Returns the id of this ordered meal
	 * @return the id of this ordered meal
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id for this ordered meal
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the Meal that is this orderer-meal parent 
	 * @return
	 */
	public Meal getParentMeal() {
		return parentMeal;
	}

	/**
	 * Sets meal as parent meal for this ordered meal
	 * @param parentMeal
	 */
	public void setParentMeal(Meal parentMeal) {
		this.parentMeal = parentMeal;
	}
	
	/**
	 * Returns the list of the extras that the customer chose for his meal ( this meal...)
	 * @return the list of the extras that the customer chose 
	 */
	public List<Extra> getChosenExtras() {
		return chosenExtras;
	}

	/**
	 * Sets the list of extras that the customer chose
	 * @param chosenExtras
	 */
	public void setChosenExtras(List<Extra> chosenExtras) {
		this.chosenExtras = chosenExtras;
	}

	/**
	 * Returns the Drink that the customer chose
	 * @return the Drink that the customer chose
	 */
	public Drink getChosenDrink() {
		return chosenDrink;
	}

	/**
	 * Sets drink (the customer choice) to this meal
	 * @param chosenDrink
	 */
	public void setChosenDrink(Drink chosenDrink) {
		this.chosenDrink = chosenDrink;
	}

	/**
	 * Returns the comment that the customer attached to the meal
	 * @return comment that was attached to the meal
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets a comment to the ordered meal ( customer comment. example:
	 * "must be ready until 14:00")
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Returns the order object of this ordered meal
	 * @return the order object of the meal
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Sets order object to this meal
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
