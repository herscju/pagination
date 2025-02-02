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

import java.util.List;

/**
 * Interface to data provider.
 * 
 * @author herscju
 * @since 0.0.1
 * @param <T> Generic, typically 'Page'
 * 
 */
public interface Provider<T>
{

	/**
	 * Get the result rows between start and end index.
	 * 
	 * @param startIndex Index of first row to show
	 * @param endIndex Index of last row to show
	 * @return List of items
	 */
	public List<T> getRows(long startIndex, long endIndex);


	/**
	 * Get the size of available data.
	 * 
	 * @return Number of items available
	 */
	public Long getSize();

}
