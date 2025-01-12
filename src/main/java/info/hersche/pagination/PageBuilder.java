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


	private PageBuilder()
	{

	}


	public Page build()
	{
		return new Page(this);
	}


	public Control getControl()
	{
		return this.control;
	}


	public int getCurrentPage()
	{
		return this.currentPage;
	}


	public PageBuilder setControl(Control control)
	{
		this.control = control;

		return this;
	}
	
	
	public PageBuilder setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;

		return this;
	}
}
