/**
 * 
 */
package info.hersche.pagination;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author adm-jhersche
 *
 */
public class Paginator implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Values to control behavior of paginator.
	 */
	private Control control;

	/**
	 * (Total) Number of hits in list to display.
	 */
	private int number;


	/**
	 * Constructor
	 * 
	 * @param builder
	 */
	public Paginator(PaginatorBuilder builder)
	{
		this.control = builder.getControl();
		this.number = builder.getNumber();
	}


	/**
	 * @return the control
	 */
	public Control getControl()
	{
		return this.control;
	}


	/**
	 * @return the number
	 */
	public int getNumber()
	{
		return this.number;
	}


	public Page paginate(int currentPage)
	{
		Stream<Integer> stream = Arrays.stream(this.control.getPageSizes()).boxed();

		Page page = PageBuilder.getInstance()//
				.setControl(this.control) //
				.setCurrentPage(currentPage) //
				.build();
		
		return page;
	}

}
