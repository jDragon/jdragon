package com.jdragon.system;

import java.util.List;

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

	@Override
	public String mainContent(List<String> args) throws Exception
	{
		if(_error==null)
			_error="Error Occured";
		return _t(_error);
	}
}
