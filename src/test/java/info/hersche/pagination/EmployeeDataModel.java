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

/**
 * @author adm-jhersche
 *
 */
public class EmployeeDataModel<T> extends ObjectTableModel<Employee>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4410376566783567402L;
	private static final int COLS = 4;

	/**
	 * Default constructor
	 */
	public EmployeeDataModel()
	{
		// Nothing to do here
	}


	/**
	 * 
	 */
	@Override
	public int getColumnCount()
	{
		return COLS;
	}


	/**
	 *
	 */
	@Override
	public String getColumnName(int column)
	{
		switch (column)
		{
			case 0:
				return "Id";
			case 1:
				return "Name";
			case 2:
				return "Phone";
			case 3:
				return "Address";
		}

		return null;
	}


	/**
	 *
	 */
	@Override
	public Object getValueAt(Employee employee, int columnIndex)
	{
		switch (columnIndex)
		{
			case 0:
				return employee.getId();
			case 1:
				return employee.getName();
			case 2:
				return employee.getPhoneNumber();
			case 3:
				return employee.getAddress();
		}

		return null;
	}

}
