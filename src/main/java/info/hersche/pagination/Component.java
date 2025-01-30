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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, builderMethodName = "toBuilder")
public class Component implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8681649795632926762L;

	/**
	 * Content to display
	 */
	@Builder.Default
	private String label = "";

	/**
	 * Value of step (forwards or backwards)
	 */
	@Builder.Default
	private int value = Paginator.Values.SEPARATOR.getValue();

	/**
	 * TRUE, if current page / component is selected
	 */
	@Builder.Default
	private Boolean selected = Boolean.FALSE;

	/**
	 * TRUE, if current component is separator only
	 */
	@Builder.Default
	private Boolean separator = Boolean.FALSE;

	/**
	 * CSS class name
	 */
	@Builder.Default
	private String style = Paginator.Values.LINK.getStyle();

	/**
	 * Private default constructor
	 */
	private Component()
	{
		// Nothing to do here
	}
}
