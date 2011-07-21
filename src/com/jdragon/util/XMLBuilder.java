package com.jdragon.util;

import java.util.Stack;

public class XMLBuilder
{
	String _str="";
	Stack<String> tagStack=new Stack<String>();
	Stack<Boolean> tagCloseStack=new Stack<Boolean>();
	
	int _level=0;
	
	public XMLBuilder tag(String name)
	{
		_level++;

		checkClosePrev();
		tagStack.push(name);
		tabs();
		append("<"+name);
		tagCloseStack.push(Boolean.FALSE);
		
		return this;
	}
	private void checkClosePrev()
	{
		if(tagCloseStack.size()<=0)
			return;
		Boolean isPrevClosed=tagCloseStack.peek();
		if(isPrevClosed.equals(Boolean.FALSE))
		{
			tagCloseStack.pop();
			append(">");
			tagCloseStack.push(Boolean.TRUE);
		}		
	}
	
	public XMLBuilder attr(String name, String val)
	{
		append(" "+name+"=\""+val+"\"");
		
		return this;
	}
	
	public XMLBuilder text(String txt)
	{
		checkClosePrev();

		append(txt);
		return this;
	}
	
	public XMLBuilder end()
	{
		Boolean isClosed=tagCloseStack.pop();
		String name=tagStack.pop();
		if(isClosed.equals(Boolean.TRUE))
		{
			tabs();
			append("</"+name+">");
		}
		else
			append(" />");
		_level--;
		
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
	
	private void tabs()
	{
		append("\n");
		for(int i=0; i<_level; i++)
			append("\t");
	}
	
	public XMLBuilder div()
	{
		return tag("div");
	}
	
	public XMLBuilder input()
	{
		return tag("input");
	}
	
	public XMLBuilder label()
	{
		return tag("label");
	}
	
	public XMLBuilder id(String value)
	{
		return attr("id", value);
	}
	
	public XMLBuilder name(String value)
	{
		return attr("name", value);
	}

	public XMLBuilder cssclass(String value)
	{
		return attr("class", value);
	}

	public XMLBuilder type(String value)
	{
		return attr("type", value);
	}

	public XMLBuilder value(String value)
	{
		return attr("value", value);
	}

	public XMLBuilder enddiv()
	{
		return end();
	}
}
