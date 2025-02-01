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
 * Contains all the required data to build the navigation and to use it.
 * 
 * @author adm-jhersche
 * @since 0.0.1
 * @param <T> Generic, typically 'Page'
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
	 * Definitions of navigation values.
	 * 
	 * @author herscju
	 * @since 0.0.1
	 */
	public enum Values
	{
		/**
		 * Previous page
		 */
		PREVIOUS(-1, "«", "previous"), //
		/**
		 * Next page
		 */
		NEXT(1, "»", "next"), //
		/**
		 * Separator
		 */
		SEPARATOR(0, "…", "separator"), //
		/**
		 * Default link component
		 */
		LINK(Integer.MAX_VALUE, "", "link");

		/**
		 * Member
		 */
		private int value;
		private String label;
		private String style;

		/**
		 * Constructor
		 * 
		 * @param value Value of enumeration
		 * @param label Label of enumeration
		 * @param style Style of enumeration
		 */
		private Values(int value, String label, String style)
		{
			this.value = value;
			this.label = label;
			this.style = style;
		}


		/**
		 * Label of enumeration
		 * 
		 * @return the label to display
		 */
		public String getLabel()
		{
			return this.label;
		}


		/**
		 * Style name of enumeration
		 * 
		 * @return the style name to use in CSS
		 */
		public String getStyle()
		{
			return this.style;
		}


		/**
		 * The value of enumeration
		 * 
		 * @return the value
		 */
		public int getValue()
		{
			return this.value;
		}
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = -8693097673863502204L;

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Paginator.class);

	/**
	 * Start page
	 */
	private static final int DEFAULT_PAGE = 1;
	/**
	 * Default list of number of elements or items to display on page
	 */
	private static final Integer[] PAGE_SIZES = new Integer[] { 5, 10, 20, 50, 75, 100 };
	/**
	 * Default number of control components
	 */
	private static final int DEFAULT_SIZE = 9;
	/**
	 * Max. number of control components
	 */
	public static final int MAX_PAGING_COMPONENTS = 9;
	/**
	 * Default control
	 */
	public static final Control DEFAULT_CONTROL = new Control(PAGE_SIZES, DEFAULT_SIZE, DEFAULT_PAGE, MAX_PAGING_COMPONENTS);

	/**
	 * Detects either NEXT of PREVIOUS
	 * 
	 * @param currentPage Number of current page
	 * @param value Selected value
	 * @param pages Total number of pages available
	 * @return Number of page
	 */
	public static int getNextOrPreviousPage(int currentPage, Values value, int pages)
	{
		int page = currentPage + value.getValue();

		if (page < Values.NEXT.getValue())
		{
			return Values.NEXT.getValue();
		}

		if (page > pages)
		{
			return pages;
		}

		return currentPage + value.getValue();
	}

	/**
	 * Is TRUE in case if init() method has been used after building the instance
	 */
	@Builder.Default
	private boolean initialized = false;

	/**
	 * Data provider 
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
	 * Default constructor
	 */
	public Paginator()
	{
		// Nothing to do here
	}


	/**
	 * Adds a navigation component
	 * 
	 * @param pageNumber Given page number
	 */
	private Component addNavigationComponent(int pageNumber)
	{
		Component component = Component.toBuilder() //
				.label(String.valueOf(pageNumber)) //
				.value(0) //
				.selected(Boolean.valueOf((pageNumber == this.currentPage))) //
				.separator(Boolean.FALSE) //
				.style(Values.LINK.getStyle()) //
				.build();

		return component;
	}


	/**
	 * Adds a navigation component
	 * 
	 * @param label Label of new component
	 * @param value Value of new component
	 * @return Instance of new component
	 */
	private Component addNavigationComponent(Values label, int value)
	{
		Component component = Component.toBuilder() //
				.label(String.valueOf(label.getLabel())) //
				.value(value) //
				.selected(Boolean.FALSE) //
				.separator(Boolean.FALSE) //
				.style(label.getStyle()) //
				.build();

		return component;
	}


	/**
	 * Adds the number of new components from start to end
	 * 
	 * @param start Start number
	 * @param end End number
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
	 * Adds a separator component
	 * 
	 * @return New component
	 */
	private Component addSeparatorComponent()
	{
		return Component.toBuilder().label(Values.SEPARATOR.getLabel()).selected(Boolean.FALSE).separator(Boolean.TRUE).style(Values.SEPARATOR.getStyle()).build();
	}


	/**
	 * Calculate number of pages totally
	 * 
	 * @return Number of pages
	 */
	public int calculateNumberOfPages()
	{
		long totalRows = this.provider.getSize();
		long defaultSize = this.control.getSize();

		return (int) Math.ceil((double) totalRows / defaultSize);
	}


	/**
	 * Initializes the class instance
	 * 
	 * @return Given instance
	 */
	public Paginator<T> init()
	{
		this.numberOfPages = this.calculateNumberOfPages();
		this.initialized = true;

		return this;
	}


	/**
	 * Walks trough the navigation
	 * 
	 * @param currentPage Number of current page to navigate
	 * @return New instance of page
	 */
	public Page paginate(int currentPage)
	{
		if (!this.initialized)
		{
			throw new IllegalStateException("Instance of class is not initialised correctly. Run 'init' method.");
		}

		this.currentPage = currentPage;

		int maxComponents = this.control.getMaxComponents();
		int start = 1;
		int end = this.numberOfPages;

		int previous = Paginator.getNextOrPreviousPage(this.currentPage, Values.PREVIOUS, this.numberOfPages);
		int next = Paginator.getNextOrPreviousPage(this.currentPage, Values.NEXT, this.numberOfPages);

		List<Component> components = new LinkedList<>();
		components.add(this.addNavigationComponent(Values.PREVIOUS, previous));
		if (this.numberOfPages > maxComponents)
		{
			components.add(this.addNavigationComponent(start));

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
				start = this.currentPage - ((maxComponents - 4) / 2);
				end = (this.numberOfPages - (start + 4) == 1) ? start + 2 : start + 4;
				LOGGER.trace("Case: '1 ... x->n ... lastPage': Start -> End: {} -> {}", start, end);

				components.add(this.addSeparatorComponent()); // Add separator
				components.addAll(this.addNavigationComponents(start, end));
				components.add(this.addSeparatorComponent()); // Add separator
				components.add(this.addNavigationComponent(this.numberOfPages));
			}
		}
		else
		{
			components.addAll(this.addNavigationComponents(start, end));
		}
		components.add(this.addNavigationComponent(Values.NEXT, next));

		// Create page object and return
		return PageBuilder.toBuilder()//
				.setControl(this.control) //
				.setCurrentPage(this.currentPage) //
				.setNumberOfPages(this.numberOfPages) //
				.setComponents(components) //
				.build();
	}

}
