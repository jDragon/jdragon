package com.jdragon.system.schema;

import java.util.ArrayList;
import java.util.List;

import com.jdragon.system.schema.Column.ColumnType;

public class Table
{
	String _name="";
	private List<Column> _columns=new ArrayList<Column>();
	
	public Table(String name)
	{
		_name=name;
	}

	public Table addField(Column col)
	{
		_columns.add(col);
		return this;
	}
	
	public void Install()
	{
		boolean firstCol=true;
		String sql="CREATE TABLE ["+_name+"] (";
		for(Column col : _columns)
		{
			String type="";
			if(col.type()==ColumnType.INT)
				type="INTEGER";
			else if(col.type()==ColumnType.FLOAT)
				type="FLOAT";
			else if(col.type()==ColumnType.VARCHAR)
				type="VARCHAR";
			if(firstCol)
				firstCol=false;
			else
				sql=sql+", ";
			
			sql=sql+col.name()+" "+type;
		}
		sql=sql+")";
	}
}
