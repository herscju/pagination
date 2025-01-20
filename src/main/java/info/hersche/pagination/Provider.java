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
 * @author herscju
 */
public interface Provider<T>
{

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<T> getRows(long startIndex, long endIndex);

	/**
	 * @return
	 */
	public long getSize();

}
