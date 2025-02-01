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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Holds the data which should be listed on the web page for example.
 * 
 * @author herscju
 * @since 0.0.1
 * @param <T> Generic, typically 'Page'
 * 
 */
@ToString
@EqualsAndHashCode
public class DataProvider<T> implements Provider<T>
{
	/**
	 * Members
	 */
	private Collection<T> collection = new ArrayList<>();
	private Long size = Long.valueOf(this.collection.size());

	/**
	 * Constructor
	 * 
	 * @param collection List of available items
	 */
	public DataProvider(Collection<T> collection)
	{
		this.collection = (collection != null) ? collection : this.collection;
		this.size = Integer.valueOf(this.collection.size()).longValue();
	}


	/**
	 * Constructor
	 * 
	 * @param collection List of available items. Might be just a reduced set.
	 * @param size Number of all available items
	 */
	public DataProvider(Collection<T> collection, Integer size)
	{
		this.collection = (collection != null) ? collection : this.collection;
		this.size = size.longValue();
	}


	/**
	 * Constructor
	 * 
	 * @param collection List of available items. Might be just a reduced set.
	 * @param size Number of all available items
	 */
	public DataProvider(Collection<T> collection, Long size)
	{
		this.collection = (collection != null) ? collection : this.collection;
		this.size = size;
	}


	/**
	 * @return the result rows between start and end index.
	 */
	@Override
	public List<T> getRows(long startIndex, long endIndex)
	{
		return this.collection.stream().skip(startIndex).limit(endIndex - startIndex).collect(Collectors.toList());
	}


	/**
	 * @return the size of data set/collection/...
	 */
	@Override
	public Long getSize()
	{
		return this.size;
	}

}
