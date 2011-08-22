package com.jdragon.system;

public class JDException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JDException(String arg0)
	{
		super(arg0);
	}

	public JDException(Throwable arg0)
	{
		super(arg0);
	}

	public JDException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}
