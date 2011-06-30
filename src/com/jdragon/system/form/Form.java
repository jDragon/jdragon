package com.jdragon.system.form;

import java.util.*;

public class Form
{
	private String _name="";
	private List<FormComponent> _elements=new ArrayList<FormComponent>();

	public String getName()
	{
		return _name;
	}

	public void setName(String _name)
	{
		this._name = _name;
	}
	
	public void addComponent(FormComponent comp)
	{
		_elements.add(comp);
	}
	
	public String Render(HashMap<String, String> errorMap)
	{
		String str="";
		for(int i=0; i<_elements.size(); i++)
		{
			FormComponent comp=(FormComponent)_elements.get(i);
			str=str+comp.Render(errorMap);
		}
		return str;
	}
}
