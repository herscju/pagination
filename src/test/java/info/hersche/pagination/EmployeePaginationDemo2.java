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
 */
package info.hersche.pagination;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author herscju
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

		//  Number of items to display on page
		int dataRows = 3;
		// Number of items available totally
		int dataSize = 25;
		PaginationProvider<Employee> employees = EmployeePaginationDemo2.createDataProvider(dataRows);
		Provider<Employee> provider = new DataProvider<>(employees.getAll(), dataSize);
		
		
		int controlSize = 3;
		int startPage = 1;
		int maxComponents = 7;
		Control control = new Control(new Integer[] { 8, 16, 30, 60, 120 }, controlSize, startPage, maxComponents);
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
					output.append("<").append(component.getLabel()).append("> ");
				}
				else
				{
					output.append(" ").append(component.getLabel()).append("  ");
				}
			}

			LOGGER.info("{}", output);
		}

	}

}
