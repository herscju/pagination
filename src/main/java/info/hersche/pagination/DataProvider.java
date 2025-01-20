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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author herscju
 */
@ToString
@EqualsAndHashCode
public class DataProvider<T> implements Provider<T>
{
	private Collection<T> collection;

	/**
	 * Constructor
	 * 
	 * @param collection
	 */
	public DataProvider(Collection<T> collection)
	{
		this.collection = collection;
	}


	/**
	 * @return the size of data set/collection/...
	 */
	@Override
	public long getSize()
	{
		return this.collection.size();
	}


	/**
	 *
	 */
	@Override
	public List<T> getRows(long startIndex, long endIndex)
	{
		return this.collection.stream().skip(startIndex).limit(endIndex - startIndex).collect(Collectors.toList());
	}

}
