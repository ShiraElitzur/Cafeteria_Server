package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Shira Elitzur
 * 
 * An instance of this class represents Drink item.
 * 
 */
@Entity
@Table (name = "drinks")
public class Drink {
	/**
	 * The id of this drink (auto generated by db)
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "Id", nullable=false)
	private int id;
	
	/**
	 * The title of this drink.
	 * 1-3 words that describes the drink
	 */
	@Column( name = "Title", nullable=false, length=30)
	private String title;
	
	/**
	 * The price of this item (only for stand alone item)
	 */
	@Column( name = "Price", nullable=true)
	private double price;
	
	
	/**
	 * Returns the id of this drink
	 * @return the id of this drink
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id for this drink
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the title of this drink
	 * @return the title of this drink
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title to this drink
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the price of this drink
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets price for this drink
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Drink [id=" + id + ", title=" + title + ", price=" + price + "]";
	}
	
	

}
