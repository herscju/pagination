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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import info.hersche.pagination.serializer.PageSerializer;

/**
 * @author herscju
 * 
 */
public class EmployeePaginationTest
{

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePaginationTest.class);
	private static final Integer[] PAGES_SIZES = new Integer[] { 8, 15, 29, 57, 113 };
	private static final int NUMBER_001 = 1;
	private static final int NUMBER_073 = 73;
	private static final int NUMBER_521 = 521;
	private static final int PAGE_001 = 1;
	private static final int PAGE_008 = 8;
	private static final int PAGE_013 = 13;

	/**
	 * Generic settings
	 */
	private static PaginationProvider<Employee> employees;

	private static Control control;
	private static Provider<Employee> provider;
	private static Paginator<Employee> paginator;

	private static SimpleModule serializer;
	private static ObjectMapper mapper;

	/**
	 * 
	 */
	@BeforeAll
	public static void configureTest()
	{
		BasicConfigurator.configure();

		serializer = new SimpleModule();
		serializer.addSerializer(new PageSerializer(Page.class));
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.registerModule(serializer);
	}


	/**
	 * Create data provider
	 * 
	 * @param number
	 * 
	 * @return the data provider
	 */
	private static PaginationProvider<Employee> createDataProvider(int number)
	{
		final List<Employee> list = new ArrayList<>();
		for (int i = 1; i <= number; i++)
		{
			Employee e = new Employee();
			e.setId(i);
			e.setName("Name '" + i + "'");
			e.setPhoneNumber("Phone '" + i + "'");
			e.setAddress("Address '" + i + "'");
			list.add(e);
		}

		return new PaginationProviderImpl<Employee>(list);
	}


	/**
	 * @throws JsonProcessingException
	 */
	@Test
	public void paginationTest008A() throws JsonProcessingException
	{
		control = new Control(PAGES_SIZES, 8, PAGE_001, PaginatedTableDecorator.MAX_PAGING_COMPONENTS);
		employees = EmployeePaginationTest.createDataProvider(NUMBER_521);
		provider = new DataProvider<>(employees.getAll());
		paginator = Paginator.<Employee> toBuilder() //
				.control(control) //
				.provider(provider) //
				.build() //
				.init();

		Page firstPage = paginator.paginate(PAGE_001);
		assertEquals(PaginatedTableDecorator.MAX_PAGING_COMPONENTS + 2, firstPage.getComponents().size());
		assertEquals(66, firstPage.getNumberOfPages());

		String json = mapper.writeValueAsString(firstPage);
		LOGGER.info("Page {}: {}", PAGE_001, json);

		Page testPage = paginator.paginate(PAGE_013);
		assertEquals(PaginatedTableDecorator.MAX_PAGING_COMPONENTS + 2, testPage.getComponents().size());
		assertEquals(66, testPage.getNumberOfPages());

		json = mapper.writeValueAsString(testPage);
		LOGGER.info("Page {}: {}", PAGE_013, json);
	}


	/**
	 * @throws JsonProcessingException
	 */
	@Test
	public void paginationTest008B() throws JsonProcessingException
	{
		control = new Control(Arrays.asList(PAGES_SIZES), 8, PAGE_001, PaginatedTableDecorator.MAX_PAGING_COMPONENTS);
		employees = EmployeePaginationTest.createDataProvider(NUMBER_001);
		provider = new DataProvider<>(employees.getAll());
		paginator = Paginator.<Employee> toBuilder() //
				.control(control) //
				.provider(provider) //
				.build() //
				.init();

		Page page = paginator.paginate(PAGE_001);
		assertEquals(1 + 2, page.getComponents().size());
		assertEquals(1, page.getNumberOfPages());

		String json = mapper.writeValueAsString(page);
		LOGGER.info("Page {}: {}", PAGE_001, json);
	}


	/**
	 * @throws JsonProcessingException
	 */
	@Test
	public void paginationTest008C() throws JsonProcessingException
	{
		control = new Control(Arrays.asList(PAGES_SIZES), 8, PAGE_001, PaginatedTableDecorator.MAX_PAGING_COMPONENTS);
		employees = EmployeePaginationTest.createDataProvider(NUMBER_073);
		provider = new DataProvider<>(employees.getAll());
		paginator = Paginator.<Employee> toBuilder() //
				.control(control) //
				.provider(provider) //
				.build() //
				.init();

		Page page = paginator.paginate(PAGE_008);
		assertEquals(11, page.getComponents().size());
		assertEquals(10, page.getNumberOfPages());

		String json = mapper.writeValueAsString(page);
		LOGGER.info("Page {}: {}", PAGE_008, json);
	}


	/**
	 * @throws JsonProcessingException
	 */
	@Test
	public void paginationTest113A() throws JsonProcessingException
	{
		control = new Control(PAGES_SIZES, 113, PAGE_001, PaginatedTableDecorator.MAX_PAGING_COMPONENTS);
		employees = EmployeePaginationTest.createDataProvider(NUMBER_521);
		provider = new DataProvider<>(employees.getAll());
		paginator = Paginator.<Employee> toBuilder() //
				.control(control) //
				.provider(provider) //
				.build() //
				.init();

		Page page = paginator.paginate(PAGE_001);
		assertEquals(5 + 2, page.getComponents().size());
		assertEquals(5, page.getNumberOfPages());

		String json = mapper.writeValueAsString(page);
		LOGGER.info("Page {}: {}", PAGE_001, json);
	}

}
