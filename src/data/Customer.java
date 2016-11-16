package data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Shira Elitzur
 * 
 * An instance of this class represent a customer (buyer)
 * 
 */
@Entity
@Table (name = "customers")
public class Customer {

	/**
	 * The id of the customer (auto generated by db)
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "Id", nullable = false)
	private int id;
	
	/**
	 * The first name of the customer
	 */
	@Column( name = "First_Name", nullable=false, length=30)
	private String firstName;
	
	/**
	 * The last name of the customer
	 */
	@Column( name = "Last_Name", nullable=false, length=30)
	private String lastName;
	
	/**
	 * The email address of the customer
	 */
	@Column( name = "Email", nullable=false, length=40,unique=true)
	private String email;
	
	
	/**
	 * The password of this customer
	 */
	@Column( name = "Password", nullable=false, length=20)
	private String password;
	
//	/**
//	 * The orders of this customer
//	 */
//	@OneToMany( mappedBy = "customer",cascade=CascadeType.ALL ) // Customer have
//	private List<Order> orders;

	/**
	 * A token for sending push notifications from the server
	 */
	@Column( name = "PushToken", length=50)
	private String pushToken;
	
	/**
	 * A path to the image of this customer
	 */
	private String imagePath;
	
	
	/**
	 * Returns the id of the customer
	 * @return the id of the customer
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id for the customer
	 * @param id an integer for the customer identification
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the first name of the customer
	 * @return the first name of the customer
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name for the customer
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the customer
	 * @return the last name of the customer
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name for the customer
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the email address of the customer
	 * @return the email address of the customer
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email for the customer
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Returns the password of this customer
	 * @return the password of this customer
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password for this customer
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

//	/**
//	 * Returns the orders of this customer
//	 * @return the orders of this customer
//	 */
//	public List<Order> getOrders() {
//		return orders;
//	}
//
//	/**
//	 * Sets list of order to this customer
//	 * @param orders
//	 */
//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
	
	/**
	 * Returns pushToken of this customer
	 * @return pushToken of this customer
	 */
	public String getPushToken() {
		return pushToken;
	}
	
	/**
	 * Sets pushToken to this customer
	 * @param pushToken
	 */
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
	
	/**
	 * Returns the path of the image of this customer
	 * @return the image of this customer
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Sets the path of the image of this customer
	 * @param image
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
