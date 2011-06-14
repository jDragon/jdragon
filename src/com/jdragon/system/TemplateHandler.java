/**
 * 
 */
package com.jdragon.system;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author raghukr
 *
 */
public class TemplateHandler 
{
	public static String processTemplate(Object vars, String templateName) throws IOException, TemplateException
	{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(
                new File("E:\\jdragon\\WebContent\\Templates"));
		Template tpl=cfg.getTemplate(templateName);
		
		Writer writer=new StringWriter();
		tpl.process(vars, writer);

		return writer.toString();
	}
}
