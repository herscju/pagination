/**
 * https://www.logicbig.com/tutorials/java-swing/jtable-pagination.html
 */
package info.hersche.pagination;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author adm-jhersche
 *
 */
public class PaginationTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PaginationTest.class);
	private static final int NUMBER = 497;


	/**
	 * Create data provider
	 * 
	 * @return the data provider
	 */
	private static PaginationDataProvider<Employee> createDataProvider()
	{

		final List<Employee> list = new ArrayList<>();
		for (int i = 1; i <= NUMBER; i++)
		{
			Employee e = new Employee();
			e.setId(i);
			e.setName("name" + i);
			e.setPhoneNumber("phone" + i);
			e.setAddress("address " + i);
			list.add(e);
		}

		return new ImplPaginationDataProvider<Employee>(list);
	}


	/**
	 * Create a JFrame
	 * 
	 * @return the frame
	 */
	private static JFrame createFrame()
	{
		JFrame frame = new JFrame("JTable Pagination example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(600, 300));

		return frame;
	}


	/**
	 * Create an object data model
	 * 
	 * @return the object data model
	 */
	private static TableModel createObjectDataModel()
	{
		ObjectTableModel<Employee> employee = new EmployeeDataModel<Employee>();

		return employee;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BasicConfigurator.configure();

		LOGGER.info("=== Main =====================================================================");

		int[] pageSizes = new int[] { 8, 16, 30, 60, 120 };
		int defaultSize = 8;
		int defaultPage = 1;
		int defaultComponents = 9;

		JTable table = new JTable(PaginationTest.createObjectDataModel());
		table.setAutoCreateRowSorter(true);

		PaginationDataProvider<Employee> provider = PaginationTest.createDataProvider();

		Control control = new Control(pageSizes, defaultSize, defaultPage);
		Paginator paginator = PaginatorBuilder.getInstance(provider.getTotalRowCount()) //
				.setControl(control) //
				.setMaxPagingComponents(defaultComponents) //
				.build();

		PaginatedTableDecorator<Employee> decorator = PaginatedTableDecorator.decorate(table, provider, pageSizes,
				defaultSize);
		// PaginatedTableDecorator<Employee> decorator = PaginatedTableDecorator.decorate(table, provider, paginator);

		JFrame frame = createFrame();
		frame.add(decorator.getContentPanel());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
