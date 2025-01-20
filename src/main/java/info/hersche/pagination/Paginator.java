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
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.FieldDefaults;

/**
 * @author adm-jhersche
 *
 */
@Value
@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, builderMethodName = "toBuilder")
public class Paginator<T> implements Serializable
{
	/**
	 * @author herscju
	 */
	public enum Values
	{
		PREVIOUS(-1, "«"), NEXT(1, "»"), SEPARATOR(0, "…");

		/**
		 * Member
		 */
		private int nextPage;
		private String label;

		/**
		 * Constructor
		 * 
		 * @param nextPage
		 * @param label
		 */
		private Values(int nextPage, String label)
		{
			this.nextPage = nextPage;
			this.label = label;
		}


		/**
		 * @return the label to display
		 */
		public String getLabel()
		{
			return this.label;
		}


		/**
		 * @return the next page
		 */
		public int getNextPage()
		{
			return this.nextPage;
		}
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = -8693097673863502204L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Paginator.class);
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 9;
	private static final int[] PAGE_SIZES = new int[] { 5, 10, 20, 50, 75, 100 };

	/**
	 * 
	 */
	public static final int MAX_PAGING_COMPONENTS = 9;

	/**
	 * 
	 */
	public static final Control DEFAULT_CONTROL = new Control(PAGE_SIZES, DEFAULT_SIZE, DEFAULT_PAGE, MAX_PAGING_COMPONENTS);

	/**
	 * @param currentPage
	 * @param value
	 * @param pages
	 * @return
	 */
	public static int getNextOrPreviousPage(int currentPage, Values value, int pages)
	{
		int page = currentPage + value.getNextPage();

		if (page < Values.NEXT.getNextPage())
		{
			return Values.NEXT.getNextPage();
		}

		if (page > pages)
		{
			return pages;
		}

		return currentPage + value.getNextPage();
	}

	/**
	 * 
	 */
	private Provider<T> provider;

	/**
	 * Values to control behavior of paginator.
	 */
	private Control control;

	/**
	 * Number of pages to display
	 */
	private int numberOfPages;

	/**
	 * Number of current page
	 */
	private int currentPage;

	/**
	 * (Total) Number of items in list to display.
	 */
	@Deprecated
	private long number;

	/**
	 * Constructor
	 */
	public Paginator()
	{
		// Nothing to do here
	}


	/**
	 * @param pageNumber
	 */
	private Component addNavigationComponent(int pageNumber)
	{
		Component component = Component.toBuilder() //
				.label(String.valueOf(pageNumber)) //
				.selected(Boolean.valueOf((pageNumber == this.currentPage))) //
				.separator(Boolean.FALSE) //
				.build();

		return component;
	}


	/**
	 * @param label
	 * @return
	 */
	private Component addNavigationComponent(Values label)
	{
		Component component = Component.toBuilder() //
				.label(String.valueOf(label.getLabel())) //
				.selected(Boolean.FALSE) //
				.separator(Boolean.FALSE) //
				.build();

		return component;
	}


	/**
	 * @param start
	 * @param end
	 * 
	 */
	private List<Component> addNavigationComponents(int start, int end)
	{
		List<Component> components = new LinkedList<>();

		for (; start <= end; start++)
		{
			Component component = this.addNavigationComponent(start);
			components.add(component);
		}

		return components;
	}


	/**
	 * @return
	 */
	private Component addSeparatorComponent()
	{
		return Component.toBuilder().label(Values.SEPARATOR.getLabel()).selected(Boolean.FALSE).separator(Boolean.TRUE).build();
	}


	/**
	 * Calculate number of pages totally
	 * 
	 * @return
	 */
	public int calculateNumberOfPages()
	{
		long totalRows = this.provider.getSize();
		long defaultSize = this.control.getSize();

		return (int) Math.ceil((double) totalRows / defaultSize);
	}


	/**
	 * @return
	 * 
	 */
	public Paginator<T> init()
	{
		this.numberOfPages = this.calculateNumberOfPages();

		return this;
	}


	/**
	 * @param currentPage
	 * @return
	 */
	public Page paginate(int currentPage)
	{
		this.currentPage = currentPage;

		int maxComponents = this.control.getMaxComponents();
		int start = 0;
		int end = 0;

		List<Component> components = new LinkedList<>();

		components.add(this.addNavigationComponent(Values.PREVIOUS));
		if (this.numberOfPages > maxComponents)
		{
			components.add(this.addNavigationComponent(1));

			if (this.currentPage > (this.numberOfPages - ((maxComponents + 1) / 2)))
			{
				// Case: 1 ... n->lastPage
				start = this.numberOfPages - maxComponents + 3;
				end = this.numberOfPages;
				LOGGER.trace("Case: '1 ... n->lastPage': Start -> End: {} -> {}", start, end);

				components.add(this.addSeparatorComponent()); // Add separator
				components.addAll(this.addNavigationComponents(start, end));
			}
			else if (this.currentPage <= (maxComponents + 1) / 2)
			{
				// Case: 1->n ... lastPage
				start = 2;
				end = maxComponents - 2;
				LOGGER.trace("Case: '1->n ... lastPage': Start -> End: {} -> {}", start, end);

				components.addAll(this.addNavigationComponents(start, end));
				components.add(this.addSeparatorComponent()); // Add separator
				components.add(this.addNavigationComponent(this.numberOfPages));
			}
			else
			{
				// Case: 1 ... x->n ... lastPage (CurrentPage is approximately mid point among total max-4 center links)
				start = this.currentPage - (maxComponents - 4) / 2;
				end = start + maxComponents - (maxComponents - 4);
				LOGGER.trace("Case: '1 ... x->n ... lastPage': Start -> End: {} -> {}", start, end);

				components.add(this.addSeparatorComponent()); // Add separator
				components.addAll(this.addNavigationComponents(start, end));
				components.add(this.addSeparatorComponent()); // Add separator
				components.add(this.addNavigationComponent(this.numberOfPages));
			}
		}
		else
		{
			components.addAll(this.addNavigationComponents(1, this.numberOfPages));
		}
		components.add(this.addNavigationComponent(Values.NEXT));

		// Create page object
		Page page = PageBuilder.toBuilder()//
				.setControl(this.control) //
				.setCurrentPage(this.currentPage) //
				.setNumberOfPages(this.numberOfPages) //
				.setComponents(components) //
				.build();

		return page;
	}

}
