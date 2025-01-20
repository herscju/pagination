/**
 * Copyright (c) 2025 Jürg Hersche (Green@rt)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * https://www.logicbig.com/tutorials/java-swing/jtable-pagination.html
 */
package info.hersche.pagination;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author adm-jhersche
 *
 */
public class EmployeePaginationJFrameDemo
{
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePaginationJFrameDemo.class);
	private static final int NUMBER_073 = 73;
	private static final int NUMBER_521 = 521;

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
	 * Create a JFrame
	 * 
	 * @return the frame
	 */
	private static JFrame createFrame()
	{
		JFrame frame = new JFrame("Employee pagination test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation when the window is closed
		frame.setLocationRelativeTo(null); // Sets the location of the window relative to the specified component.
		frame.setSize(new Dimension(600, 600)); // Set the initial size of the window
		frame.setVisible(true);

		return frame;
	}


	/**
	 * Create an object data model
	 * 
	 * @return the object data model
	 */
	private static TableModel createObjectTableModel()
	{
		ObjectTableModel<Employee> tableModel = new EmployeeDataModel<>();

		return tableModel;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BasicConfigurator.configure();

		LOGGER.info("=== Main =====================================================================");

		int[] pageSizes = new int[] { 8, 15, 29, 57, 113 };
		int defaultSize = 8;
		int startPage = 1;
		int defaultComponents = PaginatedTableDecorator.MAX_PAGING_COMPONENTS;

		PaginationProvider<Employee> employees = EmployeePaginationJFrameDemo.createDataProvider(NUMBER_521);
		Provider<Employee> provider = new DataProvider<>(employees.getAll());
		Control control = new Control(pageSizes, defaultSize, startPage, defaultComponents);
		Paginator<Employee> paginator = Paginator.<Employee> toBuilder() //
				.provider(provider) //
				.control(control) //
				.build();

		// Create table(s)
		JTable table_A = new JTable(EmployeePaginationJFrameDemo.createObjectTableModel());
		table_A.setAutoCreateRowSorter(true);
		PaginatedTableDecorator<Employee> component_A = PaginatedTableDecorator.decorate(table_A, provider, pageSizes, defaultSize, defaultComponents);

		JTable table_B = new JTable(EmployeePaginationJFrameDemo.createObjectTableModel());
		table_B.setAutoCreateRowSorter(true);
		PaginatedTableDecorator<Employee> component_B = PaginatedTableDecorator.decorate(table_B, provider, paginator);

		// Create a tabbed pane to hold the pages content 
		JTabbedPane tabbedPane = new JTabbedPane();

		// Create the first tab (page1) and add a JLabel to it
		JPanel page_A = new JPanel();
		page_A.add(component_A.getContentPanel());

		// Create the second tab (page2) and add a JLabel to it
		JPanel page_B = new JPanel();
		page_B.add(component_B.getContentPanel());

		// Add the two tabs to the JTabbedPane
		tabbedPane.addTab("Test A (" + employees.getTotalRowCount() +")", page_A);
		tabbedPane.addTab("Test B (" + employees.getTotalRowCount() +")", page_B);

		employees = EmployeePaginationJFrameDemo.createDataProvider(NUMBER_073);
		provider = new DataProvider<>(employees.getAll());
		control = new Control(pageSizes, defaultSize, startPage, defaultComponents);
		paginator = Paginator.<Employee> toBuilder() //
				.provider(provider) //
				.control(control) //
				.build();

		// Create table(s)
		JTable table_C = new JTable(EmployeePaginationJFrameDemo.createObjectTableModel());
		table_C.setAutoCreateRowSorter(true);
		PaginatedTableDecorator<Employee> component_C = PaginatedTableDecorator.decorate(table_C, provider, paginator);

		// Create the third tab (page3) and add a JLabel to it
		JPanel page_C = new JPanel();
		page_C.add(component_C.getContentPanel());

		// Add the third tabs to the JTabbedPane
		tabbedPane.addTab("Test C (" + employees.getTotalRowCount() +")", page_C);

		// Create window / frame
		EmployeePaginationJFrameDemo.createFrame().add(tabbedPane);
	}

}
