/**
 * @(#)JDClassLoader.java	1.0 12/09/2008
 */
package com.jdragon.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * JDClassLoader extends URLClassLoader to provide functionality for
 * loading a class from a file or byte array.
 * 
 * @author raghukr
 * @see URLClassLoader
 */
public class JDClassLoader extends URLClassLoader {

   /**
    * Constructs a new JDClassLoader with the default delegation parent
    * ClassLoader.  URLs can be added to the JDClassLoader using the
    * addURL(URL) method.
    * <P>
    * This constructor invokes JDClassLoader(URL[]) with an empty URL array.
    * 
    * @see #addURL(URL) 
    * @see #JDClassLoader(URL[]) 
    */
   public JDClassLoader() {
      this(new URL[]{});
   }

   /**
    * Constructs a new JDClassLoader for the specified URLs with the default
    * delegation parent ClassLoader.
    * <P>
    * This constructor invokes JDClassLoader(URL[], ClassLoader) with the
    * URLs and a null parent.
    * 
    * @param urls the URLs from which to load classes and resources
    * @see #JDClassLoader(URL[] urls, ClassLoader)
    */
   public JDClassLoader(URL[] urls) {
      this(urls, null);
   }

   /**
    * Constructs a new JDClassLoader using the specified delegation parent
    * ClassLoader.  URLs can be added to the JDClassLoader using the
    * addURL(URL) method.
    * <P>
    * When loading resources, this ClassLoader will be searched after first
    * searching in the specified parent class loader.
    * <P>
    * This constructor invokes JDClassLoader(URL[], ClassLoader) with an
    * empty URL array and the specified parent.
    * 
    * @param parent the parent class loader for delegation
    * @see #addURL(URL) 
    * @see #JDClassLoader(URL[], ClassLoader) 
    */
   public JDClassLoader(ClassLoader parent) {
      this(new URL[]{}, parent);
   }

   /**
    * Constructs a new JDClassLoader for the specified URLs using the
    * specified delegation parent ClassLoader.  The URLs will be searched in
    * the order specified for classes and resources after first searching in
    * the specified parent class loader. Any URL that ends with a '/' is
    * assumed to refer to a directory. Otherwise, the URL is assumed to refer
    * to a JAR file which will be downloaded and opened as needed.
    * 
    * @param urls the URLs from which to load classes and resources
    * @param parent the parent class loader for delegation
    * @see URLClassLoader#URLClassLoader(URL[], ClassLoader) 
    */
   public JDClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);
   }

   /**
    * Appends the specified URL to the list of URLs to search for classes
    * and resources. Overrides the super method to provide public access.
    * 
    * @param url the URL to be added to the search path of URLs
    * @see URLClassLoader#addURL(URL) 
    */
   @Override
   public void addURL(URL url) {
      super.addURL(url);
   }

/**
    * Converts a file into an instance of class Class and resolves the Class
    * so it can be used.  This method extracts the file content as a byte
    * array and invokes createClass(byte[]).
    * <P>
    * The file content should have the format of a valid class file as
    * defined by the <a href="http://java.sun.com/docs/books/vmspec/">
    * Java Virtual Machine Specification</a>.
    * 
    * @param file the file to be loaded as a class
    * @return The Class object that was created from the file, or null if any
    * exception was encountered
    * @see #createClass(byte[]) 
    */
   public Class<?> createClass(File file) throws IOException {
      FileInputStream fis = null;
      try {
         fis = new FileInputStream(file);
         byte[] bytes = new byte[fis.available()];
         int read = fis.read(bytes);
         if (read != bytes.length) {
            return null;
         }
         return createClass(bytes);
      } finally {
         fis.close();
      }
   }

   /**
    * Converts an array of bytes into an instance of class Class and resolves
    * the Class so it can be used.
    * <P>
    * The byte array should have the format of a valid class file as defined
    * by the <a href="http://java.sun.com/docs/books/vmspec/">
    * Java Virtual Machine Specification</a>.
    * 
    * @param bytes The bytes that make up the class data
    * @return The Class object that was created from the byte array
    * @see ClassLoader#defineClass(String, byte[], int, int) 
    * @see ClassLoader#resolveClass(Class) 
    */
   public Class<?> createClass(byte[] bytes) {
      Class<?> clazz = defineClass(null, bytes, 0, bytes.length);
      resolveClass(clazz);
      return clazz;
   }
   
//   /**
//    * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
//    *
//    * @param packageName The base package
//    * @return The classes
//    * @throws ClassNotFoundException
//    * @throws IOException
//    */
//   @SuppressWarnings("unchecked")
//	private static List<Class> getClasses(String packageName)
//           throws ClassNotFoundException, IOException 
//   {
//       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//       assert classLoader != null;
//       String path = packageName.replace('.', '/');
//       Enumeration<URL> resources = classLoader.getResources(path);
//       List<File> dirs = new ArrayList<File>();
//       while (resources.hasMoreElements()) {
//           URL resource = resources.nextElement();
////           String fileName = resource.getFile();
////           String fileNameDecoded = URLDecoder.decode(fileName, "UTF-8");
////           dirs.add(new File(fileNameDecoded));
//           dirs.add(new File(resource.toURI()));
//       }
//       ArrayList<Class> classes = new ArrayList<Class>();
//       for (File directory : dirs) {
//           classes.addAll(findClasses(directory, packageName));
//       }
//       return classes;
//   }
//
//   /**
//    * Recursive method used to find all classes in a given directory and subdirs.
//    *
//    * @param directory   The base directory
//    * @param packageName The package name for classes found inside the base directory
//    * @return The classes
//    * @throws ClassNotFoundException
//    */
//   @SuppressWarnings("unchecked")
//	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException 
//	{
//       List<Class> classes = new ArrayList<Class>();
//       if (!directory.exists()) {
//           return classes;
//       }
//       File[] files = directory.listFiles();
//       for (File file : files) {
//       	String fileName = file.getName();
//           if (file.isDirectory()) {
//               assert !fileName.contains(".");
//           	classes.addAll(findClasses(file, packageName + "." + fileName));
//           } else if (fileName.endsWith(".class") && !fileName.contains("$")) {
//           	Class _class;
//				try {
//					_class = Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6));
//				} catch (ExceptionInInitializerError e) {
//					// happen, for example, in classes, which depend on 
//					// Spring to inject some beans, and which fail, 
//					// if dependency is not fulfilled
//					_class = Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6),
//							false, Thread.currentThread().getContextClassLoader());
//				}
//				classes.add(_class);
//           }
//       }
//       return classes;
//   }   
}
