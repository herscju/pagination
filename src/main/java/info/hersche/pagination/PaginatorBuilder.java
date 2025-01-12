/**
 * 
 */
package info.hersche.pagination;

/**
 * @author adm-jhersche
 *
 */
public class PaginatorBuilder
{
	public enum Values
	{
		PREVIOUS(-1, "«"), NEXT(1, "»"), SEPARATOR(0, "…");

		/**
		 * Member
		 */
		private int nextPage;
		private String label;


		/**
		 * Constructor
		 * 
		 * @param nextPage
		 * @param label
		 */
		private Values(int nextPage, String label)
		{
			this.nextPage = nextPage;
			this.label = label;
		}


		/**
		 * @return the label to display
		 */
		public String getLabel()
		{
			return this.label;
		}


		/**
		 * @return the next page
		 */
		public int getNextPage()
		{
			return this.nextPage;
		}
	};

	/**
	 * Private static member
	 */
	private static final int DEFAULT_PAGE = 1;
	private static final int[] PAGE_SIZES = new int[] { 5, 10, 20, 50, 75, 100 };
	private static final int DEFAULT_SIZE = 10;

	/**
	 * Public static member
	 */
	public static final int MAX_PAGING_COMPONENTS = 9;
	public static final Control DEFAULT_CONTROL = new Control(PAGE_SIZES, DEFAULT_SIZE, DEFAULT_PAGE);


	/**
	 * Get a paginator builder instance
	 * 
	 * @param number
	 * 
	 * @return new instance of these class
	 */
	public static PaginatorBuilder getInstance(int number)
	{
		return new PaginatorBuilder(number);
	}

	/**
	 * Values to control behavior of paginator.
	 */
	private Control control = DEFAULT_CONTROL;

	/**
	 * Maximum number of paging components to show. The number does not inlcude the single step buttons.
	 */
	private int maxPagingComponents = MAX_PAGING_COMPONENTS;

	/**
	 * (Total) Number of hits in list to display.
	 */
	private int number;


	/**
	 * Private default constructor
	 * 
	 * @param number
	 */
	private PaginatorBuilder(int number)
	{
		this.number = number;
	}


	/**
	 * Build the new paginator instance.
	 * 
	 * @return the paginator
	 */
	public Paginator build()
	{
		return new Paginator(this);
	}


	/**
	 * Control behavior of paginator.
	 * 
	 * @return thgs control
	 */
	public Control getControl()
	{
		return this.control;
	}


	/**
	 * Get maximum nuber of buttons to show.
	 * 
	 * @return the number to show.
	 */
	public int getMaxPagingComponents()
	{
		return this.maxPagingComponents;
	}


	/**
	 * (Total) Number of hits in list to display.
	 * 
	 * @return the number of hits
	 */
	public int getNumber()
	{
		return this.number;
	}


	/**
	 * Set the page control values.
	 * 
	 * @param control the page control to set
	 * @return the builder
	 */
	public PaginatorBuilder setControl(Control control)
	{
		this.control = control;

		return this;
	}


	/**
	 * Set the maximum nuber of buttons to show.
	 * 
	 * @param maxPagingComponents the max. number of components to show
	 * @return the builder
	 */
	public PaginatorBuilder setMaxPagingComponents(int maxPagingComponents)
	{
		this.maxPagingComponents = maxPagingComponents;

		return this;
	}
}
