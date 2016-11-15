package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name = "extras")
public class Extra implements Inventory{
	/**
	 * The id of this extra (auto generated by db)
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "Id", nullable=false)
	private int id;
	
	/**
	 * The title of this extra.
	 * 1-3 words that describes the extra
	 */
	@Column( name = "Title", nullable=false, length=30, unique=true)
	private String title;
	
	
	/**
	 * The price of this extra.
	 */
	@Column(name = "Price", nullable=false)
	private Double price;
	
	@Column(name = "In_Stock", nullable=false)
	private boolean inStock;
	
	/**
	 * Returns the id of this extra
	 * @return the id of this extra
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id for this extra
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the title of this extra
	 * @return the title of this extra
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title to this extra
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the price of this extra
	 * @return the price of this extra
	 */
	public Double getPrice() {
		return price;
	}
	
	/**
	 * Sets price to this extra
	 * @param price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public boolean isInStock() {
		return inStock;
	}

	@Override
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

}
