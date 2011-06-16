package com.jdragon.util;

import java.util.Stack;

public class XMLBuilder
{
	String _str="";
	Stack<String> stk=new Stack<String>();
	
	public XMLBuilder tag(String name)
	{
		stk.push(name);
		append("<"+name);
		
		return this;
	}
	
	public XMLBuilder attr(String name, String val)
	{
		append(" "+name+"=\""+val+"\"");
		
		return this;
	}
	
	public XMLBuilder close()
	{
		append(">");
		return this;
	}

	public XMLBuilder text(String txt)
	{
		append(txt);
		return this;
	}
	
	public XMLBuilder end()
	{
		String name=stk.pop();
		append("</"+name+">");
		
		return this;
	}

	@Override 
	public String toString() 
	{
		return _str;
	}
	
	private void append(String str)
	{
		_str=_str+str;
	}
}
