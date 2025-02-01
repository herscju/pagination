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

import java.util.LinkedList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Helps to build the page instance.
 * 
 * @author herscju
 * @since 0.0.1
 *
 */
@Getter
@ToString
@EqualsAndHashCode
public class PageBuilder
{
	/**
	 * Creates a new instance of builder
	 * 
	 * @return New instance of class
	 */
	public static PageBuilder toBuilder()
	{
		return new PageBuilder();
	}

	/**
	 * Member
	 */
	private Control control = Paginator.DEFAULT_CONTROL;
	private int currentPage = this.control.getStart();
	private int numberOfPages = this.control.getSize();
	private List<Component> components = new LinkedList<>();

	/**
	 * Private constructor
	 */
	private PageBuilder()
	{
		// Nothing to do here
	}


	/**
	 * Builds the new page instance
	 * 
	 * @return New instance of page
	 */
	public Page build()
	{
		return new Page(this);
	}


	/**
	 * Sets the components list
	 * 
	 * @param components List of components
	 * @return the builder object
	 */
	public PageBuilder setComponents(List<Component> components)
	{
		this.components = components;

		return this;
	}


	/**
	 * Set the control
	 * 
	 * @param control Configured control
	 * @return the builder object
	 */
	public PageBuilder setControl(Control control)
	{
		this.control = control;

		return this;
	}


	/**
	 * Sets the number of the current page
	 * 
	 * @param currentPage Number of current page
	 * @return the builder object
	 */
	public PageBuilder setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;

		return this;
	}


	/**
	 * Sets the number of pages overall
	 * 
	 * @param numberOfPages Number of pages overall
	 * @return the builder object
	 */
	public PageBuilder setNumberOfPages(int numberOfPages)
	{
		this.numberOfPages = numberOfPages;

		return this;
	}

}
