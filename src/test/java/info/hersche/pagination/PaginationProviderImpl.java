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

import java.util.ArrayList;
import java.util.List;

/**
 * @author adm-jhersche
 * @since 0.0.1
 *
 */
public class PaginationProviderImpl<T> implements PaginationProvider<T>
{
	List<T> list = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param list
	 */
	public PaginationProviderImpl(List<T> list)
	{
		this.list = list;
	}


	/**
	 *
	 */
	@Override
	public List<T> getAll()
	{
		return this.list;
	}


	/**
	 *
	 */
	@Override
	public List<T> getRows(int startIndex, int endIndex)
	{
		return this.list.subList(startIndex, endIndex);
	}


	/**
	 *
	 */
	@Override
	public int getTotalRowCount()
	{
		return this.list.size();
	}

}
