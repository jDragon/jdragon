package com.jdragon.system.schema;

public class Column
{
	enum ColumnType {INT, FLOAT, VARCHAR};
	
	private String _name="";
	ColumnType _type=ColumnType.INT;
	
	public Column(String name)
	{
		_name=name;
	}
	public String name() { return _name; }
	
	public void type(ColumnType type)
	{
		this._type = type;
	}
	public ColumnType type()
	{
		return _type;
	}
}
