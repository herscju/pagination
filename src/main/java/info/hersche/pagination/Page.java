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
import java.util.List;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the current page (e.g. web page).
 * 
 * @author herscju
 * @since 0.0.1
 *
 */
@Getter
@ToString
@EqualsAndHashCode
public class Page implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6824303794309996822L;

	/**
	 * Control
	 */
	private Control control;
	/**
	 * Number of current page
	 */
	private int currentPage;
	/**
	 * Total number of pages
	 */
	private int numberOfPages;
	/**
	 * List of components
	 */
	private List<Component> components;

	/**
	 * Constructor
	 * 
	 * @param builder Builder instance
	 */
	public Page(PageBuilder builder)
	{
		this.control = builder.getControl();
		this.currentPage = builder.getCurrentPage();
		this.numberOfPages = builder.getNumberOfPages();
		this.components = builder.getComponents();
	}


	/**
	 * Get page sizes
	 * 
	 * @return the page sizes
	 */
	public Stream<Integer> getPageSizesAsStream()
	{
		return this.control.getSizes().stream();
	}


	/**
	 * Get the current start row
	 * 
	 * @return the current row
	 */
	public int getStartRow()
	{
		return ((this.getCurrentPage() - 1) * this.control.getSize()) + 1;
	}


	/**
	 * Set the list of components
	 * 
	 * @param components Collection of components to show for pagination
	 * @return the page object
	 */
	public Page setComponents(List<Component> components)
	{
		this.components = components;

		return this;
	}


	/**
	 * Set the current page number
	 * 
	 * @param currentPage current page number
	 * @return the page object
	 */
	public Page setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;

		return this;
	}


	/**
	 * Set the current default page size
	 * 
	 * @param size current default page size
	 * @return the page object
	 */
	public Page setDefaultSize(int size)
	{
		this.control.setSize(size);

		return this;
	}

}
