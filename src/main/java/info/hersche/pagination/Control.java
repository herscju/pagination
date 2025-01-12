/**
 * 
 */
package info.hersche.pagination;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author adm-jhersche
 *
 */
public class Control implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3213432628027693222L;

	/**
	 * Member
	 */
	private int[] pageSizes;
	private int defaultSize;
	private int defaultPage;

	/**
	 * Constructor
	 * 
	 * @param pageSizes
	 * @param defaultSize
	 * @param defaultPage
	 */
	public Control(int[] pageSizes, int defaultSize, int defaultPage)
	{
		this.pageSizes = pageSizes;
		this.defaultSize = defaultSize;
		this.defaultPage = defaultPage;
	}


	/**
	 * Get the current default page.
	 * 
	 * @return the default page
	 */
	public int getDefaultPage()
	{
		return this.defaultPage;
	}


	/**
	 * Size of hits to show per page.
	 * 
	 * @return the default size
	 */
	public int getDefaultSize()
	{
		return this.defaultSize;
	}


	/**
	 * Available size pages to set in control.
	 * 
	 * @return the page size
	 */
	public int[] getPageSizes()
	{
		return this.pageSizes;
	}


	public Control setDefaultSize(int defaultSize)
	{
		this.defaultSize = defaultSize;

		return this;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(Control.class.getSimpleName());
		sb.append(": Default page=[");
		sb.append(this.defaultPage);
		sb.append("] Default size=[");
		sb.append(this.defaultSize);
		sb.append("] Page sizes=[");
		sb.append(Arrays.toString(this.pageSizes));
		sb.append("]");

		return sb.toString();
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		return false;
	}
}
