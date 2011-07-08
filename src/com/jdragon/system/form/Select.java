package com.jdragon.system.form;

import java.util.*;

import com.jdragon.util.XMLBuilder;

public class Select extends FormComponent
{
	private List<String> _optionkeys=new ArrayList<String>();
	private List<String> _optionvalues=new ArrayList<String>();
	private List<String> _selected=new ArrayList<String>();
	
	private boolean _multiselect=false;
	
	
	public Select(String name)
	{
		super(name); super.type("select");
	}
	
	@Override
	public String Render()
	{
		if(value()!=null)
			_selected=new ArrayList<String>(Arrays.asList((String[])value()));

		XMLBuilder builder = new XMLBuilder();
		builder
		.tag("div")
			.tag("label").text(title()).end()
			.tag("div")
				.tag("select").attr("name", name());
					
					if(multiSelect())
						builder.attr("multiple", "multiple");
		
					for(int i=0; i<_optionkeys.size(); i++)
					{
						builder.tag("option").attr("value", _optionkeys.get(i));
						if(isSelected(_optionkeys.get(i)))
							builder.attr("selected", "selected");
						builder.text(_optionvalues.get(i)).end();
					}

				builder
				.end()
			.end()
		.end();
		
		return builder.toString();
	}
	
	@Override
	protected boolean multiValued()
	{
		return true;
	}
	
	public Select option(String k, String v)
	{
		if(k==null)k="";
		if(v==null)v="";
		_optionkeys.add(k); _optionvalues.add(v);
		return this;
	}

	public Select selectedoption(String k, String v)
	{
		option(k, v);
		_selected.add(k);
		return this;
	}

	public void multiSelect(boolean isMulti)
	{
		_multiselect=isMulti;
	}

	public boolean multiSelect()
	{
		return _multiselect;
	}
	
	private boolean isSelected(String key)
	{
		return _selected.contains(key);
	}
}
