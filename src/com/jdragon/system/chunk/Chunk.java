/**
 * 
 */
package com.jdragon.system.chunk;

/**
 * @author RIS
 *
 */
public class Chunk
{
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	private String title=null;
	private String content=null;
}
