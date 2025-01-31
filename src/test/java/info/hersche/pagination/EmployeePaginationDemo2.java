/**
 * 
 */
package info.hersche.pagination;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class EmployeePaginationDemo2
{
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePaginationDemo2.class);

	/**
	 * Create data provider
	 * 
	 * @param number
	 * 
	 * @return the data provider
	 */
	private static PaginationProvider<Employee> createDataProvider(int number)
	{
		final List<Employee> list = new LinkedList<>();
		for (int i = 1; i <= number; i++)
		{
			Employee e = new Employee();
			e.setId(i);
			e.setName("Name '" + i + "'");
			e.setPhoneNumber("Phone '" + i + "'");
			e.setAddress("Address '" + i + "'");
			list.add(e);
		}

		return new PaginationProviderImpl<Employee>(list);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BasicConfigurator.configure();

		LOGGER.info("=== Main =====================================================================");

		int size = 3;
		int startPage = 1;
		PaginationProvider<Employee> employees = EmployeePaginationDemo2.createDataProvider(3);
		Provider<Employee> provider = new DataProvider<>(employees.getAll(), 25);
		Control control = new Control(new Integer[] { 8, 16, 30, 60, 120 }, size, startPage, 7);
		Paginator<Employee> paginator = Paginator.<Employee> toBuilder() //
				.provider(provider) //
				.control(control) //
				.build() //
				.init();

		for (int i = startPage; i <= paginator.getNumberOfPages(); i++)
		{
			// Get page from paginator
			Page page = paginator.paginate(i);
			// Get list of components
			List<Component> components = page.getComponents();

			// Iterate over list and build navigation
			StringBuilder output = new StringBuilder();
			output.append("Page ").append(i).append(":  ");
			for (Component component : components)
			{
				if (component.getSelected())
				{
					output.append("[").append(component.getLabel()).append("] ");
				}
				else
				{
					output.append("<").append(component.getLabel()).append("> ");
				}
			}

			LOGGER.info("{}", output);
		}

	}

}
