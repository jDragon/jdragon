package com.jdragon.system.schema;

import java.util.ArrayList;
import java.util.List;

public class Schema
{
	private List<Table> _tables=new ArrayList<Table>();
	public Schema AddTable(Table table)
	{
		_tables.add(table);
		return this;
	}
	
	public void Install()
	{
		for(Table table : _tables)
		{
			table.Install();
		}
	}
}
