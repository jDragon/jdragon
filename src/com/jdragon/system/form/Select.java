package com.jdragon.system.form;

import java.util.*;

import com.jdragon.util.XMLBuilder;

public class Select extends FormComponent
{
	Map<String, String> _value=null;
	private List<String> _keys=new ArrayList<String>();
	private List<String> _values=new ArrayList<String>();
	
	private String _selected="";
	
	public Select(String name){super(name); super.type("select");}
	@Override
	public String Render()
	{
		Map<String, String[]> valueMap=Form.getFormValues();
		if(valueMap!=null)
		{
			String[] valArr= valueMap.get(name());
			if(valArr!=null && valArr.length>0)
				_selected=valArr[0];
		}
		
		XMLBuilder builder = new XMLBuilder();
		builder
		.tag("div")
			.tag("label").text(title()).end()
			.tag("div")
				.tag("select").attr("name", name());

					for(int i=0; i<_keys.size(); i++)
					{
						builder.tag("option").attr("value", _keys.get(i));
						if(_keys.get(i).equals(_selected))
							builder.attr("selected", "selected");
						builder.text(_values.get(i)).end();
					}

				builder
				.end()
			.end()
		.end();
		
		return builder.toString();
	}
	@Override
	public FormComponent value(Object value) throws ClassCastException
	{
		_selected=(String)value;
		return this;
	}
	@Override
	public Object value()
	{
		return _selected;
	}
	
	public Select add(String k, String v)
	{
		if(k==null)k="";
		if(v==null)v="";
		_keys.add(k); _values.add(v);
		return this;
	}
}
