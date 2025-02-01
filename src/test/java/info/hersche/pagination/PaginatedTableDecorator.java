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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import info.hersche.pagination.Paginator.Values;

/**
 * @author adm-jhersche
 *
 */
public class PaginatedTableDecorator<T>
{

	/**
	 * Static member
	 */
	//private static final Logger LOGGER = LoggerFactory.getLogger(PaginatedTableDecorator.class);

	/**
	 * Defines max. components for testing and demonstration purposes
	 */
	public static final int MAX_PAGING_COMPONENTS = 9;

	/**
	 * @param table
	 * @param provider
	 * @param pageSizes
	 * @param defaultSize
	 * @return PaginatedTableDecorator
	 */
	public static <T> PaginatedTableDecorator<T> decorate(JTable table, Provider<T> provider, Integer[] pageSizes, int defaultSize, int defaultComponents)
	{
		PaginatedTableDecorator<T> decorator = new PaginatedTableDecorator<>(table, provider, Arrays.asList(pageSizes), defaultSize, defaultComponents);
		decorator.init();

		return decorator;
	}


	/**
	 * @param table
	 * @param provider
	 * @param paginator
	 * @return
	 */
	public static <T> PaginatedTableDecorator<T> decorate(JTable table, Provider<T> provider, Paginator<T> paginator)
	{
		PaginatedTableDecorator<T> decorator = new PaginatedTableDecorator<>(table, provider, paginator);
		decorator.init();

		return decorator;
	}

	private int currentPage = 1;

	private JTable table;
	private Provider<T> provider;
	private Paginator<T> paginator;
	private int defaultSize;
	private JPanel contentPanel;
	private JPanel pageLinkPanel;

	private ObjectTableModel<T> objectTableModel;

	/**
	 * Private constructor
	 * 
	 * @param table
	 * @param dataProvider
	 * @param pageSizes
	 * @param defaultSize
	 */
	private PaginatedTableDecorator(JTable table, Provider<T> dataProvider, List<Integer> pageSizes, int defaultSize, int defaultComponents)
	{
		this.table = table;
		this.provider = dataProvider;
		this.defaultSize = defaultSize;

		Control control = new Control(pageSizes, this.defaultSize, 1, MAX_PAGING_COMPONENTS);

		Paginator<T> paginator = Paginator.<T> toBuilder() //
				.provider(dataProvider) //
				.control(control) //
				.build() //
				.init();

		this.paginator = paginator;
	}


	private PaginatedTableDecorator(JTable table, Provider<T> dataProvider, Paginator<T> paginator)
	{
		this.table = table;
		this.provider = dataProvider;
		this.paginator = paginator;

		this.defaultSize = paginator.getControl().getSize();
	}


	/**
	 * @param parentPanel
	 * @param buttonGroup
	 * @param pageNumber
	 */
	private void addNavigationComponent(JPanel parentPanel, ButtonGroup buttonGroup, int pageNumber)
	{
		JToggleButton toggleButton = new JToggleButton(Integer.toString(pageNumber));
		toggleButton.setMargin(new Insets(1, 3, 1, 3));
		buttonGroup.add(toggleButton);
		parentPanel.add(toggleButton);

		if (pageNumber == this.currentPage)
		{
			toggleButton.setSelected(true);
		}

		toggleButton.addActionListener((ActionEvent evnt) -> {
			this.currentPage = Integer.parseInt(evnt.getActionCommand());
			this.paginate();
		});
	}


	/**
	 * @param parentPanel
	 * @param buttonGroup
	 * @param start
	 * @param end
	 */
	private void addNavigationComponents(JPanel parentPanel, ButtonGroup buttonGroup, int start, int end)
	{
		for (; start <= end; start++)
		{
			this.addNavigationComponent(parentPanel, buttonGroup, start);
		}
	}


	/**
	 * @param parentPanel
	 * @param buttonGroup
	 * @param value
	 * @param pages
	 */
	private void addSingleStepButton(JPanel parentPanel, ButtonGroup buttonGroup, Values value, int pages)
	{
		JToggleButton toggleButton = new JToggleButton(value.getLabel());
		toggleButton.setMargin(new Insets(1, 3, 1, 3));
		buttonGroup.add(toggleButton);
		parentPanel.add(toggleButton);
		toggleButton.addActionListener((ActionEvent evnt) -> {
			this.currentPage = Paginator.getNextOrPreviousPage(this.currentPage, value, pages);
			this.paginate();
		});

	}


	/**
	 * @return
	 */
	private JPanel createPaginationPanel()
	{
		JPanel paginationPanel = new JPanel();
		this.pageLinkPanel = new JPanel(new GridLayout(1, MAX_PAGING_COMPONENTS, 3, 3));
		paginationPanel.add(this.pageLinkPanel);

		if (this.paginator != null)
		{
			Page page = this.paginator.paginate(this.currentPage);

			Stream<Integer> stream = page.getPageSizesAsStream();
			JComboBox<Integer> pageComboBox = new JComboBox<>(stream.toArray(Integer[]::new));
			pageComboBox.addActionListener((ActionEvent evnt) -> {
				// To preserve current rows position
				this.defaultSize = (Integer) pageComboBox.getSelectedItem();
				this.currentPage = ((page.getStartRow() - 1) / this.defaultSize) + 1;
				this.paginate();
			});

			page.setDefaultSize(this.defaultSize);
			page.setCurrentPage(this.currentPage);

			//LOGGER.info("Pagination panel data: {}", page);

			pageComboBox.setSelectedItem(this.defaultSize);
			paginationPanel.add(Box.createHorizontalStrut(15));
			paginationPanel.add(new JLabel("Page size: "));
			paginationPanel.add(pageComboBox);
		}

		// if (this.pageSizes != null)
		// {
		// Stream<Integer> stream = Arrays.stream(this.pageSizes).boxed();
		// JComboBox<Integer> pageComboBox = new JComboBox<>(stream.toArray(Integer[]::new));
		// pageComboBox.addActionListener((ActionEvent evnt) -> {
		// // to preserve current rows position
		// int currentPageStartRow = ((this.currentPage - 1) * this.defaultPageSize) + 1;
		// this.defaultPageSize = (Integer) pageComboBox.getSelectedItem();
		// this.currentPage = ((currentPageStartRow - 1) / this.defaultPageSize) + 1;
		// this.paginate();
		// });
		//
		// pageComboBox.setSelectedItem(this.defaultPageSize);
		// paginationPanel.add(Box.createHorizontalStrut(15));
		// paginationPanel.add(new JLabel("Page size: "));
		// paginationPanel.add(pageComboBox);
		// }

		return paginationPanel;
	}


	/**
	 * @return
	 */
	private Component createSeparatorComponent()
	{
		return new JLabel(Values.SEPARATOR.getLabel(), SwingConstants.CENTER);
	}


	/**
	 * @return
	 */
	public JPanel getContentPanel()
	{
		return this.contentPanel;
	}


	/**
	 * 
	 */
	private void init()
	{
		this.initDataModel();
		this.initPaginationComponents();
		this.initListeners();
		this.paginate();
	}


	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void initDataModel()
	{
		TableModel model = this.table.getModel();
		if (!(model instanceof ObjectTableModel))
		{
			throw new IllegalArgumentException("TableModel must be a subclass of ObjectTableModel");
		}
		this.objectTableModel = (ObjectTableModel<T>) model;
	}


	/**
	 * 
	 */
	private void initListeners()
	{
		this.objectTableModel.addTableModelListener(this::refreshPageButtonPanel);
	}


	/**
	 * 
	 */
	private void initPaginationComponents()
	{
		JPanel paginationPanel = this.createPaginationPanel();
		this.contentPanel = new JPanel(new BorderLayout());
		this.contentPanel.add(paginationPanel, BorderLayout.NORTH);
		this.contentPanel.add(new JScrollPane(this.table));
	}


	/**
	 * 
	 */
	private void paginate()
	{
		long startIndex = (this.currentPage - 1) * this.defaultSize;
		long endIndex = startIndex + this.defaultSize;

		if (endIndex > this.provider.getSize())
		{
			endIndex = this.provider.getSize();
		}

		List<T> rows = this.provider.getRows(startIndex, endIndex);
		this.objectTableModel.setObjectRows(rows);
		this.objectTableModel.fireTableDataChanged();
	}


	/**
	 * @param evnt
	 */
	private void refreshPageButtonPanel(TableModelEvent evnt)
	{
		this.pageLinkPanel.removeAll();

		long totalRows = this.provider.getSize();
		int numberOfPages = (int) Math.ceil((double) totalRows / this.defaultSize);
		int maxComponents = this.paginator.getControl().getMaxComponents();
		int start = 1;
		int end = numberOfPages;
		ButtonGroup buttonGroup = new ButtonGroup();

		this.addSingleStepButton(this.pageLinkPanel, buttonGroup, Values.PREVIOUS, numberOfPages);
		if (numberOfPages > maxComponents)
		{
			this.addNavigationComponent(this.pageLinkPanel, buttonGroup, 1);
			if (this.currentPage > (numberOfPages - ((maxComponents + 1) / 2)))
			{
				// Case: 1 ... n->lastPage
				start = numberOfPages - maxComponents + 3;
				end = numberOfPages;
				//LOGGER.trace("Case: '1 ... n->lastPage': Start -> End: {} -> {}", start, end);

				this.pageLinkPanel.add(this.createSeparatorComponent());
				this.addNavigationComponents(this.pageLinkPanel, buttonGroup, start, end);
			}
			else if (this.currentPage <= (maxComponents + 1) / 2)
			{
				// Case: 1->n ... lastPage
				start = 2;
				end = maxComponents - 2;
				//LOGGER.trace("Case: '1->n ... lastPage': Start -> End: {} -> {}", start, end);

				this.addNavigationComponents(this.pageLinkPanel, buttonGroup, start, end);
				this.pageLinkPanel.add(this.createSeparatorComponent());
				this.addNavigationComponent(this.pageLinkPanel, buttonGroup, numberOfPages);
			}
			else
			{
				// Case: 1 ... x->n ... lastPage (CurrentPage is approximately mid point among total max-4 center links)
				start = this.currentPage - (maxComponents - 4) / 2;
				end = (numberOfPages - (start + 4) == 1) ? start + 2 : start + 4;
				//LOGGER.trace("Case: '1 ... x->n ... lastPage': Start -> End: {} -> {}", start, end);

				this.pageLinkPanel.add(this.createSeparatorComponent()); // first ellipses
				this.addNavigationComponents(this.pageLinkPanel, buttonGroup, start, end);
				this.pageLinkPanel.add(this.createSeparatorComponent()); // last ellipsis
				this.addNavigationComponent(this.pageLinkPanel, buttonGroup, numberOfPages); // last page link
			}
		}
		else
		{
			this.addNavigationComponents(this.pageLinkPanel, buttonGroup, start, end);
		}
		this.addSingleStepButton(this.pageLinkPanel, buttonGroup, Values.NEXT, numberOfPages);

		this.pageLinkPanel.getParent().validate();
		this.pageLinkPanel.getParent().repaint();
	}
}
