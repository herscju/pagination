/**
 * 
 */
package info.hersche.pagination;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.hersche.pagination.PaginatorBuilder.Values;

/**
 * @author adm-jhersche
 *
 */
public class PaginatedTableDecorator<T>
{

	/**
	 * Static member
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PaginatedTableDecorator.class);
	private static final int MAX_PAGING_COMPONENTS = 9;


	/**
	 * @param table
	 * @param provider
	 * @param paginator
	 * @return
	 */
	public static <T> PaginatedTableDecorator<T> decorate(JTable table, PaginationDataProvider<T> provider,
			Paginator paginator)
	{
		PaginatedTableDecorator<T> decorator = new PaginatedTableDecorator<>(table, provider, paginator);
		decorator.init();

		return decorator;
	}


	/**
	 * @param table
	 * @param provider
	 * @param pageSizes
	 * @param defaultSize
	 * @return PaginatedTableDecorator
	 */
	public static <T> PaginatedTableDecorator<T> decorate(JTable table, PaginationDataProvider<T> provider,
			int[] pageSizes, int defaultSize)
	{
		PaginatedTableDecorator<T> decorator = new PaginatedTableDecorator<>(table, provider, pageSizes, defaultSize);
		decorator.init();

		return decorator;
	}

	private int currentPage = 1;

	private JTable table;
	private PaginationDataProvider<T> dataProvider;
	private Paginator paginator;
	private int[] pageSizes;
	private JPanel contentPanel;
	private int defaultPageSize;
	private JPanel pageLinkPanel;

	private ObjectTableModel<T> objectTableModel;


	private PaginatedTableDecorator(JTable table, PaginationDataProvider<T> dataProvider, Paginator paginator)
	{
		this.table = table;
		this.dataProvider = dataProvider;
		this.paginator = paginator;
	}


	/**
	 * Private constructor
	 * 
	 * @param table
	 * @param dataProvider
	 * @param pageSizes
	 * @param defaultPageSize
	 */
	private PaginatedTableDecorator(JTable table, PaginationDataProvider<T> dataProvider, int[] pageSizes,
			int defaultPageSize)
	{
		this.table = table;
		this.dataProvider = dataProvider;
		this.pageSizes = pageSizes;
		this.defaultPageSize = defaultPageSize;

		Control control = new Control(pageSizes, defaultPageSize, 1);
		Paginator paginator = PaginatorBuilder.getInstance(dataProvider.getTotalRowCount()) //
				.setControl(control) //
				.build();

		this.paginator = paginator;
	}


	private void addSingleStepButton(JPanel parentPanel, ButtonGroup buttonGroup, Values value, int pages)
	{
		JToggleButton toggleButton = new JToggleButton(value.getLabel());
		toggleButton.setMargin(new Insets(1, 3, 1, 3));
		buttonGroup.add(toggleButton);
		parentPanel.add(toggleButton);
		toggleButton.addActionListener(ae -> {
			this.currentPage = this.getNextOrPreviousPage(value, pages);
			this.paginate();
		});

	}


	private void addPageButton(JPanel parentPanel, ButtonGroup buttonGroup, int pageNumber)
	{
		JToggleButton toggleButton = new JToggleButton(Integer.toString(pageNumber));
		toggleButton.setMargin(new Insets(1, 3, 1, 3));
		buttonGroup.add(toggleButton);
		parentPanel.add(toggleButton);
		if (pageNumber == this.currentPage)
		{
			toggleButton.setSelected(true);
		}
		toggleButton.addActionListener(ae -> {
			this.currentPage = Integer.parseInt(ae.getActionCommand());
			this.paginate();
		});
	}


	private void addPageButtonRange(JPanel parentPanel, ButtonGroup buttonGroup, int start, int end)
	{
		for (; start <= end; start++)
		{
			this.addPageButton(parentPanel, buttonGroup, start);
		}
	}


	private JPanel createPaginationPanel()
	{
		JPanel paginationPanel = new JPanel();
		this.pageLinkPanel = new JPanel(new GridLayout(1, MAX_PAGING_COMPONENTS, 3, 3));
		paginationPanel.add(this.pageLinkPanel);

		LOGGER.info("{}", this.paginator);

		if (this.paginator != null)
		{
			Page page = this.paginator.paginate(this.currentPage);

			Stream<Integer> stream = page.getPageSizesAsStream();
			JComboBox<Integer> pageComboBox = new JComboBox<>(stream.toArray(Integer[]::new));
			pageComboBox.addActionListener((ActionEvent evnt) -> {
				// to preserve current rows position
				this.defaultPageSize = (Integer) pageComboBox.getSelectedItem();
				this.currentPage = ((page.getStartRow() - 1) / this.defaultPageSize) + 1;
				this.paginate();
			});

			page.setDefaultSize(this.defaultPageSize);
			page.setCurrentPage(this.currentPage);

			LOGGER.info("Pagination panel data: {}", page);

			pageComboBox.setSelectedItem(this.defaultPageSize);
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


	private Component createSeparatorComponent()
	{
		return new JLabel(Values.SEPARATOR.getLabel(), SwingConstants.CENTER);
	}


	public JPanel getContentPanel()
	{
		return this.contentPanel;
	}


	private int getNextOrPreviousPage(Values value, int pages)
	{
		int page = this.currentPage + value.getNextPage();

		if (page < 1)
		{
			return 1;
		}

		if (page > pages)
		{
			return pages;
		}

		return this.currentPage + value.getNextPage();
	}


	private void init()
	{
		this.initDataModel();
		this.initPaginationComponents();
		this.initListeners();
		this.paginate();
	}


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


	private void initListeners()
	{
		this.objectTableModel.addTableModelListener(this::refreshPageButtonPanel);
	}


	private void initPaginationComponents()
	{
		JPanel paginationPanel = this.createPaginationPanel();
		this.contentPanel = new JPanel(new BorderLayout());
		this.contentPanel.add(paginationPanel, BorderLayout.NORTH);
		this.contentPanel.add(new JScrollPane(this.table));
	}


	private void paginate()
	{
		int startIndex = (this.currentPage - 1) * this.defaultPageSize;
		int endIndex = startIndex + this.defaultPageSize;
		if (endIndex > this.dataProvider.getTotalRowCount())
		{
			endIndex = this.dataProvider.getTotalRowCount();
		}
		List<T> rows = this.dataProvider.getRows(startIndex, endIndex);
		this.objectTableModel.setObjectRows(rows);
		this.objectTableModel.fireTableDataChanged();
	}


	private void refreshPageButtonPanel(TableModelEvent evnt)
	{
		this.pageLinkPanel.removeAll();

		int totalRows = this.dataProvider.getTotalRowCount();
		int pages = (int) Math.ceil((double) totalRows / this.defaultPageSize);
		ButtonGroup buttonGroup = new ButtonGroup();

		this.addSingleStepButton(this.pageLinkPanel, buttonGroup, Values.PREVIOUS, pages);
		if (pages > MAX_PAGING_COMPONENTS)
		{
			this.addPageButton(this.pageLinkPanel, buttonGroup, 1);
			if (this.currentPage > (pages - ((MAX_PAGING_COMPONENTS + 1) / 2)))
			{
				// case: 1 ... n->lastPage
				this.pageLinkPanel.add(this.createSeparatorComponent());
				this.addPageButtonRange(this.pageLinkPanel, buttonGroup, pages - MAX_PAGING_COMPONENTS + 3, pages);
			}
			else if (this.currentPage <= (MAX_PAGING_COMPONENTS + 1) / 2)
			{
				// case: 1->n ... lastPage
				this.addPageButtonRange(this.pageLinkPanel, buttonGroup, 2, MAX_PAGING_COMPONENTS - 2);
				this.pageLinkPanel.add(this.createSeparatorComponent());
				this.addPageButton(this.pageLinkPanel, buttonGroup, pages);
			}
			else
			{
				// case: 1 ... x->n ... lastPage
				this.pageLinkPanel.add(this.createSeparatorComponent());// first ellipses
				// currentPage is approx mid point among total max-4 center links
				int start = this.currentPage - (MAX_PAGING_COMPONENTS - 4) / 2;
				int end = start + MAX_PAGING_COMPONENTS - 5;
				this.addPageButtonRange(this.pageLinkPanel, buttonGroup, start, end);
				this.pageLinkPanel.add(this.createSeparatorComponent());// last ellipsis
				this.addPageButton(this.pageLinkPanel, buttonGroup, pages);// last page link
			}
		}
		else
		{
			this.addPageButtonRange(this.pageLinkPanel, buttonGroup, 1, pages);
		}
		this.addSingleStepButton(this.pageLinkPanel, buttonGroup, Values.NEXT, pages);

		this.pageLinkPanel.getParent().validate();
		this.pageLinkPanel.getParent().repaint();
	}
}
