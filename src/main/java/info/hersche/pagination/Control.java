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
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Controls the behavior of the link components.
 * 
 * @author adm-jhersche
 * @since 0.0.1
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
	 * Number of pages to select
	 */
	private TreeSet<Integer> sizes;
	/**
	 * Number of items to display on page
	 */
	private int size;
	/**
	 * Number of start page
	 */
	private int start;
	/**
	 * The max. number of components to show
	 */
	private int maxComponents;

	/**
	 * Constructor
	 * 
	 * @param sizes List of possible sizes (items per page)
	 * @param size Current size (items per page). Will be added to parameter sizes if not already part of list.
	 * @param start Number of start page
	 * @param maxComponents Max. number of components to show
	 */
	public Control(Integer[] sizes, int size, int start, int maxComponents)
	{
		this.sizes = new TreeSet<>(Arrays.asList(sizes));
		this.size = size;
		this.start = start;
		this.maxComponents = maxComponents;

		this.init();
	}


	/**
	 * Constructor
	 * 
	 * @param sizes List of possible sizes (items per page)
	 * @param size Current size (items per page). Will be added to parameter sizes if not already part of list.
	 * @param start Number of start page
	 * @param maxComponents Max. number of components to show
	 */
	public Control(List<Integer> sizes, int size, int start, int maxComponents)
	{
		this.sizes = new TreeSet<>(sizes);
		this.size = size;
		this.start = start;
		this.maxComponents = maxComponents;

		this.init();
	}


	/**
	 * Initializes the control.
	 */
	private void init()
	{
		/**
		 * Adds given size value to sizes list. Lists increases by 1 if the size is not all ready available.
		 */
		this.sizes.add(this.size);
	}

}
