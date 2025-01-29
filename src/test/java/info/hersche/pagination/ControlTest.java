/**
 * 
 */
package info.hersche.pagination;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeSet;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class ControlTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ControlTest.class);
	private static final Integer[] PAGES_SIZES = new Integer[] { 8, 15, 29, 57, 113 };
	private static final int PAGE_001 = 1;

	private static Control control;

	@BeforeAll
	public static void configureTest()
	{
		BasicConfigurator.configure();
	}


	@Test
	public void controlTest()
	{
		control = new Control(PAGES_SIZES, 43, PAGE_001, PaginatedTableDecorator.MAX_PAGING_COMPONENTS);

		TreeSet<Integer> set = new TreeSet<>();
		set.add(8);
		set.add(15);
		set.add(29);
		set.add(43);
		set.add(57);
		set.add(113);

		assertEquals(set, control.getSizes());
		
		LOGGER.info("Page sizes {}: {}", 43, control.getSizes());
	}
}
