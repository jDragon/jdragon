package com.jdragon.system.element;

import com.jdragon.system.BaseElement;

public class JDErrorHandler extends BaseElement
{
	private String _error=null;
	
	public String getError()
	{
		return _error;
	}

	public void setError(String error)
	{
		this._error = error;
	}

	public String mainContent() throws Exception
	{
		if(_error==null)
			_error="Error Occured";
		return _error;
	}
}
