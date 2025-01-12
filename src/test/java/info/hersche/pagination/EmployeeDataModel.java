/**
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


	/**
	 * Default constructor
	 */
	public EmployeeDataModel()
	{
		// Nothing to do here
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		return 4;
	}


	/* (non-Javadoc)
	 * @see info.hersche.pagination.ObjectTableModel#getColumnName(int)
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


	/* (non-Javadoc)
	 * @see info.hersche.pagination.ObjectTableModel#getValueAt(java.lang.Object, int)
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
