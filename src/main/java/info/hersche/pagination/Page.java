/**
 * 
 */
package info.hersche.pagination;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author herscju
 *
 */
public class Page implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6824303794309996822L;

	/**
	 * Member
	 */
	private Control control;
	private int currentPage;

	/**
	 * Constructor
	 * 
	 * @param builder
	 */
	public Page(PageBuilder builder)
	{
		this.control = builder.getControl();
		this.currentPage = builder.getCurrentPage();
	}


	/**
	 * Get the page controls
	 * 
	 * @return the control
	 */
	public Control getControl()
	{
		return this.control;
	}


	/**
	 * Get the current page number
	 * 
	 * @return the page
	 */
	public int getCurrentPage()
	{
		return this.currentPage;
	}


	/**
	 * Get page sizes
	 * 
	 * @return the page sizes
	 */
	public Stream<Integer> getPageSizesAsStream()
	{
		return Arrays.stream(this.control.getPageSizes()).boxed();
	}


	/**
	 * Get the current start row
	 * 
	 * @return the current row
	 */
	public int getStartRow()
	{
		return ((this.getCurrentPage() - 1) * this.control.getDefaultSize()) + 1;
	}


	/**
	 * Set the current page number
	 * 
	 * @param currentPage current page number
	 * @return the page object
	 */
	public Page setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;

		return this;
	}


	/**
	 * Set the current default page size
	 * 
	 * @param defaultSize current default page size
	 * @return the page object
	 */
	public Page setDefaultSize(int defaultSize)
	{
		this.control.setDefaultSize(defaultSize);

		return this;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(Page.class.getSimpleName());
		sb.append(": Ctrl=[");
		sb.append(this.control);
		sb.append("] Current page=[");
		sb.append(this.currentPage);
		sb.append("] Page sizes=[");
		sb.append(this.getPageSizesAsStream().collect(Collectors.toList()));
		sb.append("]");

		return sb.toString();
	}
}
