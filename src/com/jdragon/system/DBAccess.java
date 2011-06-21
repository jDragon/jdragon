/**
 * 
 */
package com.jdragon.system;

import java.sql.Connection;

/**
 * @author raghukr
 *
 */
public interface DBAccess 
{
	public Connection getConnection();
	public String resolveSQL(String sql); 
}
