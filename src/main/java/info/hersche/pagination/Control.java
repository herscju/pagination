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

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author adm-jhersche
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Control implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3213432628027693222L;

	/**
	 * Member
	 */
	private int[] sizes;
	private int size;
	private int start;
	/**
	 * The max. number of components to show
	 */
	private int maxComponents;

	/**
	 * Constructor
	 * 
	 * @param sizes
	 * @param size
	 * @param start
	 * @param maxPagingComponents 
	 */
	public Control(int[] sizes, int size, int start, int maxComponents)
	{
		this.sizes = sizes;
		this.size = size;
		this.start = start;
		this.maxComponents = maxComponents;
	}

}
