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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author adm-jhersche
 *
 */
public abstract class ObjectTableModel<T> extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5097758556519117463L;

	/**
	 * Member
	 */
	private List<T> objectRows = new ArrayList<>();

	/**
	 * Default constructor of abstract class
	 */
	public ObjectTableModel()
	{
		// Nothing to do here
	}


	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if (this.objectRows.isEmpty())
		{
			return Object.class;
		}

		Object valueAt = this.getValueAt(0, columnIndex);

		return valueAt != null ? valueAt.getClass() : Object.class;
	}


	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public abstract String getColumnName(int column);


	/**
	 * @return the rows of object
	 */
	public List<T> getObjectRows()
	{
		return this.objectRows;
	}


	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		return this.objectRows.size();
	}


	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		T t = this.objectRows.get(rowIndex);

		return this.getValueAt(t, columnIndex);
	}


	/**
	 * @param t
	 * @param columnIndex
	 * @return
	 */
	public abstract Object getValueAt(T t, int columnIndex);


	/**
	 * @param objectRows the rows of object to set
	 */
	public void setObjectRows(List<T> objectRows)
	{
		this.objectRows = objectRows;
	}
}
