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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeSet;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author herscju
 * @since 0.0.1
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
