/**
 * 
 */
package info.hersche.pagination;

import java.util.List;

/**
 * @author adm-jhersche
 *
 */
public interface PaginationDataProvider<T>
{
	/**
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	List<T> getRows(int startIndex, int endIndex);


	/**
	 * @return
	 */
	int getTotalRowCount();
}
