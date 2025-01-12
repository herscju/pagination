/**
 * 
 */
package info.hersche.pagination;

import java.io.Serializable;

/**
 * @author adm-jhersche
 *
 */
public class Employee implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2326829805357398956L;

	/**
	 * Member
	 */
	private long id;
	private String name;
	private String phoneNumber;
	private String address;

	/**
	 * Default constructor
	 */
	public Employee()
	{
		// Nothing to do here
	}


	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return this.address;
	}


	/**
	 * @return the ID
	 */
	public long getId()
	{
		return this.id;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}


	/**
	 * @return the phone number
	 */
	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}


	/**
	 * @param id the ID to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @param phoneNumber the phone number to set
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
