package com.jdragon.util;

import java.util.HashMap;

public class JDHashMap<K, V> extends HashMap<K, V>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JDHashMap<K, V> add(K k, V v)
	{
		super.put(k, v);
		return this;
	}
}
