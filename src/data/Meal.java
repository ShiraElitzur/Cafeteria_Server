package data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Shira Elitzur
 * 
 * An instance of this class represent A meal that the cafeteria menu offers.
 * Meal can be a collection of items or one main item + extra items
 * 
 */
@Entity
@Table (name = "meals")
public class Meal {

	/**
	 * The id of the meal (auto generated by db)
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id", nullable=false)
	private int id;
	
	/**
	 * The main item of the meal (optional)
	 * For example : Chicken
	 */
	@ManyToOne(fetch=FetchType.EAGER) // Meal can have only one Main but One Main can be connected to many meals
	@JoinColumn (name = "Main_Id", nullable=true)
	private Main main;
	
	/**
	 * The title of this meal
	 */
	@Column(name = "Title", nullable=false, length=50)
	private String title;
	
	/**
	 * List of the items that the meal contains, the options for extras.
	 * For example : Fries, Salad, Rice, Potatoes... from this 
	 * list the customer can choose *extraAmount*
	 */
	@ManyToMany(fetch=FetchType.EAGER)// One Meal have many items, One Item can be connected to many meals
	@JoinTable( name = "meals_extras", inverseJoinColumns = @JoinColumn( name = "Extra_Id" ))
	private List<Extra> extras;
	
	/**
	 * Extra amount means how many extras the customer can choose 
	 */
	@Column(name = "Extras_Amount", nullable=false)
	private int extraAmount;
	
	/**
	 * The price of this meal
	 */
	@Column(name = "Price", nullable=false)
	private double price;
	
	/**
	 * The serving form of the meal
	 * For example : Pitta
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn (name = "Serving_Id", nullable=false)
	private ServingForm serving;
	
	/**
	 * Is this meal included drink in the price
	 */
	@Column (name = "Includes_Drink", nullable=false)
	private boolean includesDrink;

	/**
	 * Returns the id of this meal
	 * @return the id of this meal
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id for this meal
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the main item of this meal
	 * @return the main item of this meal
	 */
	public Main getMain() {
		return main;
	}

	/**
	 * Sets main item for this meal
	 * @param main
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Returns the title of this meal
	 * @return the title of this meal
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title for this meal
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the items that this meal contains or the extras for meal with main+extras
	 * @return the items that this meal contains
	 */
	public List<Extra> getExtras() {
		return extras;
	}

	/**
	 * Sets the items list for this meal
	 * @param extras
	 */
	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}


	/**
	 * Returns the price of this meal
	 * @return the price of this meal
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets price for this meal
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns the amount of extras that the customer can choose in this meal
	 * @return the amount of extras 
	 */
	public int getExtraAmount() {
		return extraAmount;
	}

	/**
	 * Sets the amount of extras that the customer can choose in this meal
	 * @param extraAmount
	 */
	public void setExtraAmount(int extraAmount) {
		this.extraAmount = extraAmount;
	}
	
	/**
	 * Returns the serving form of this meal
	 * @return the serving form
	 */
	public ServingForm getServing() {
		return serving;
	}
	
	/**
	 * Sets the serving in this meal
	 * @param serving
	 */
	public void setServing(ServingForm serving) {
		this.serving = serving;
	}
	
	/**
	 * Returns whether this meal includes a drink in the price
	 * @return a boolean represent if this meal includes a drink in the price
	 */
	public boolean isIncludesDrink() {
		return includesDrink;
	}
	
	/**
	 * Sets if this meal includes drink in the price
	 * @param drinkIncluded
	 */
	public void setIncludesDrink(boolean includesDrink) {
		this.includesDrink = includesDrink;
	}
	
	

}
