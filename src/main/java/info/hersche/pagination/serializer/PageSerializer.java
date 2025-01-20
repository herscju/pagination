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
package info.hersche.pagination.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.hersche.pagination.Page;

/**
 * 
 */
public class PageSerializer extends StdSerializer<Page>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7991444522513768549L;

	/**
	 * @param src
	 */
	public PageSerializer(Class<Page> t)
	{
		super(t);
	}


	/**
	 *
	 */
	@Override
	public void serialize(Page page, JsonGenerator generator, SerializerProvider provider) throws IOException
	{
		generator.writeStartObject();
		generator.writeObjectField("components", page.getComponents());
		generator.writeObjectField("control", page.getControl());
		generator.writeNumberField("startRow", page.getStartRow());
		generator.writeNumberField("currentPage", page.getCurrentPage());
		generator.writeEndObject();
	}
}
