/**
 * 
 */
package info.hersche.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adm-jhersche
 *
 */
public class ImplPaginationDataProvider<T> implements PaginationDataProvider<Employee>
{
	List<Employee> list = new ArrayList<>();


	/**
	 * Constructor
	 * 
	 * @param list
	 */
	public ImplPaginationDataProvider(List<Employee> list)
	{
		this.list = list;
	}


	/* (non-Javadoc)
	 * @see info.hersche.pagination.PaginationDataProvider#getRows(int, int)
	 */
	@Override
	public List<Employee> getRows(int startIndex, int endIndex)
	{
		return list.subList(startIndex, endIndex);
	}


	/* (non-Javadoc)
	 * @see info.hersche.pagination.PaginationDataProvider#getTotalRowCount()
	 */
	@Override
	public int getTotalRowCount()
	{
		return this.list.size();
	}

}
