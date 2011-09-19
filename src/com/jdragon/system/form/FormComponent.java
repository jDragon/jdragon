package com.jdragon.system.form;

import java.util.*;

public abstract class FormComponent
{
	
	private String _type, _name, _title;
	protected Map<String, String> _errorMap=null;
	protected Map<String, String[]> _valueMap=null;
	private Object _value=null;
	private boolean _readonly=true, _enabled=true;
	
	public FormComponent(String name)
	{
		_name=name;
		_errorMap=Form.getErrorMap();
		_valueMap=Form.getFormValues();
	}
	
	protected String getError()
	{
		return _errorMap.get(name());
	}

	protected void type(String type) { _type=type; }
	protected String type() { return _type; }
	protected String name() { return _name; }
	protected String title() { return _title; }
	
	public final FormComponent title(String titleStr)
	{
		_title=titleStr;
		return this;
	}
	
	public FormComponent value(Object valueObj)
	{
		_value=valueObj;
		return this;
	}
	
	public Object value()
	{
		if(_valueMap.get(name())!=null)
			if(multiValued())
				_value=_valueMap.get(name());
			else
				_value=((String[])_valueMap.get(name()))[0];
		return _value;
	}
	
	protected boolean multiValued()
	{
		return false;
	}
	
	public boolean readonly()
	{
		return _readonly;
	}

	public FormComponent readonly(boolean readonly)
	{
		_readonly = readonly;
		return this;
	}

	public boolean enabled()
	{
		return _enabled;
	}

	public FormComponent enabled(boolean enabled)
	{
		_enabled = enabled;
		return this;
	}

	public abstract String Render();
	
}
