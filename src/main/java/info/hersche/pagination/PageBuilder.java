/**
 * 
 */
package info.hersche.pagination;

/**
 * @author herscju
 *
 */
public class PageBuilder
{

	public static PageBuilder getInstance()
	{
		return new PageBuilder();
	}

	private Control control = PaginatorBuilder.DEFAULT_CONTROL;
	private int currentPage = this.control.getDefaultPage();

	/**
	 * Private constructor
	 */
	private PageBuilder()
	{
		// Nothing to do here
	}


	/**
	 * @return
	 */
	public Page build()
	{
		return new Page(this);
	}


	/**
	 * @return
	 */
	public Control getControl()
	{
		return this.control;
	}


	/**
	 * @return
	 */
	public int getCurrentPage()
	{
		return this.currentPage;
	}


	/**
	 * @param control
	 * @return
	 */
	public PageBuilder setControl(Control control)
	{
		this.control = control;

		return this;
	}


	/**
	 * @param currentPage
	 * @return
	 */
	public PageBuilder setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;

		return this;
	}
}
