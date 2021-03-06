/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.mqfdy.code.springboot.utilities.Filter;
import com.mqfdy.code.springboot.utilities.internal.iterators.ArrayIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.CompositeIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.FilteringIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.TransformationIterator;


// TODO: Auto-generated Javadoc
/**
 * TODO
 */
public class Classpath
	implements Serializable
{
	/** The entries in the classpath */
	private final Entry[] entries;

	private static final long serialVersionUID = 1L;


	// ********** static methods **********

	// ***** factory methods for "standard" classpaths *****

	/**
	 * Return the Java "boot" classpath. This includes rt.jar.
	 *
	 * @author mqfdy
	 * @return the classpath
	 * @Date 2018-09-03 09:00
	 */
	public static Classpath bootClasspath() {
		return new Classpath(System.getProperty("sun.boot.class.path"));
	}
	
	/**
	 * Return a "virtual classpath" that contains all the jars that would be
	 * used by the Java Extension Mechanism.
	 *
	 * @author mqfdy
	 * @return the classpath
	 * @Date 2018-09-03 09:00
	 */
	public static Classpath javaExtensionClasspath() {
		File[] dirs = javaExtensionDirectories();
		List<String> jarFileNames = new ArrayList<String>();
		for (File dir : dirs) {
			if (dir.isDirectory()) {
				addJarFileNamesTo(dir, jarFileNames);
			}
		}
		return new Classpath(jarFileNames);
	}

	/**
	 * Return the Java "system" classpath.
	 *
	 * @author mqfdy
	 * @return the classpath
	 * @Date 2018-09-03 09:00
	 */
	public static Classpath javaClasspath() {
		return new Classpath(System.getProperty("java.class.path"));
	}

	/**
	 * Return the unretouched "complete" classpath. This includes the boot
	 * classpath, the Java Extension Mechanism classpath, and the normal
	 * "system" classpath.
	 *
	 * @author mqfdy
	 * @return the classpath
	 * @Date 2018-09-03 09:00
	 */
	public static Classpath completeClasspath() {
		return new Classpath(new Classpath[] {
				bootClasspath(),
				javaExtensionClasspath(),
				javaClasspath()
		});
	}

	/**
	 * Return a classpath that contains the location of the specified class.
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the classpath
	 * @Date 2018-09-03 09:00
	 */
	public static Classpath classpathFor(Class<?> javaClass) {
		return new Classpath(locationFor(javaClass));
	}


	// ***** file => class *****

	/**
	 * Convert a relative file name to a class name; this will work for any file
	 * that has a single extension beyond the base class name. e.g.
	 * "java/lang/String.class" is converted to "java.lang.String" e.g.
	 * "java/lang/String.java" is converted to "java.lang.String"
	 *
	 * @author mqfdy
	 * @param classFileName
	 *            the class file name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToClassName(String classFileName) {
		String className = FileTools.stripExtension(classFileName);
		// do this for archive entry names
		className = className.replace('/', '.');
		// do this for O/S-specific file names
		if (File.separatorChar != '/') {
			className = className.replace(File.separatorChar, '.');
		}
		return className;
	}

	/**
	 * Convert a file to a class name; e.g. File(java/lang/String.class) is
	 * converted to "java.lang.String"
	 *
	 * @author mqfdy
	 * @param classFile
	 *            the class file
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToClassName(File classFile) {
		return convertToClassName(classFile.getPath());
	}

	/**
	 * Convert a relative file name to a class; e.g. "java/lang/String.class" is
	 * converted to java.lang.String.class
	 *
	 * @author mqfdy
	 * @param classFileName
	 *            the class file name
	 * @return the class
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @Date 2018-09-03 09:00
	 */
	public static Class<?> convertToClass(String classFileName) throws ClassNotFoundException {
		return Class.forName(convertToClassName(classFileName));
	}

	/**
	 * Convert a relative file to a class; e.g. File(java/lang/String.class) is
	 * converted to java.lang.String.class
	 *
	 * @author mqfdy
	 * @param classFile
	 *            the class file
	 * @return the class
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @Date 2018-09-03 09:00
	 */
	public static Class<?> convertToClass(File classFile) throws ClassNotFoundException {
		return convertToClass(classFile.getPath());
	}


	// ***** class => JAR entry *****

	/**
	 * Convert a class name to an archive entry name base; e.g.
	 * "java.lang.String" is converted to "java/lang/String"
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToArchiveEntryNameBase(String className) {
		return className.replace('.', '/');
	}
	
	/**
	 * Convert a class to an archive entry name base; e.g.
	 * java.lang.String.class is converted to "java/lang/String"
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToArchiveEntryNameBase(Class<?> javaClass) {
		return convertToArchiveEntryNameBase(javaClass.getName());
	}
	
	/**
	 * Convert a class name to an archive class file entry name; e.g.
	 * "java.lang.String" is converted to "java/lang/String.class"
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToArchiveClassFileEntryName(String className) {
		return convertToArchiveEntryNameBase(className) + ".class";
	}
	
	/**
	 * Convert a class to an archive class file entry name; e.g.
	 * java.lang.String.class is converted to "java/lang/String.class"
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToArchiveClassFileEntryName(Class<?> javaClass) {
		return convertToArchiveClassFileEntryName(javaClass.getName());
	}
	

	// ***** class => file (.class or .java) *****

	/**
	 * Convert a class name to a file name base for the current O/S; e.g.
	 * "java.lang.String" is converted to "java/lang/String" on Unix and
	 * "java\\lang\\String" on Windows
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToFileNameBase(String className) {
		return className.replace('.', File.separatorChar);
	}
	
	/**
	 * Convert a class to a file name base for the current O/S; e.g.
	 * java.lang.String.class is converted to "java/lang/String" on Unix and
	 * "java\\lang\\String" on Windows
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToFileNameBase(Class<?> javaClass) {
		return convertToFileNameBase(javaClass.getName());
	}
	
	/**
	 * Convert a class name to a class file name for the current O/S; e.g.
	 * "java.lang.String" is converted to "java/lang/String.class" on Unix and
	 * "java\\lang\\String.class" on Windows
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToClassFileName(String className) {
		return convertToFileNameBase(className) + ".class";
	}
	
	/**
	 * Convert a class to a class file name for the current O/S; e.g.
	 * java.lang.String.class is converted to "java/lang/String.class" on Unix
	 * and "java\\lang\\String.class" on Windows
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToClassFileName(Class<?> javaClass) {
		return convertToClassFileName(javaClass.getName());
	}
	
	/**
	 * Convert a class name to a class file for the current O/S; e.g.
	 * "java.lang.String" is converted to File(java/lang/String.class)
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the file
	 * @Date 2018-09-03 09:00
	 */
	public static File convertToClassFile(String className) {
		return new File(convertToClassFileName(className));
	}
	
	/**
	 * Convert a class to a class file for the current O/S; e.g.
	 * java.lang.String.class is converted to File(java/lang/String.class)
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the file
	 * @Date 2018-09-03 09:00
	 */
	public static File convertToClassFile(Class<?> javaClass) {
		return convertToClassFile(javaClass.getName());
	}
	
	/**
	 * Convert a class name to a java file name for the current O/S; e.g.
	 * "java.lang.String" is converted to "java/lang/String.java" on Unix and
	 * "java\\lang\\String.java" on Windows
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToJavaFileName(String className) {
		return convertToFileNameBase(className) + ".java";
	}

	/**
	 * Convert a class to a java file name for the current O/S; e.g.
	 * java.lang.String.class is converted to "java/lang/String.java" on Unix
	 * and "java\\lang\\String.java" on Windows
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToJavaFileName(Class<?> javaClass) {
		return convertToJavaFileName(javaClass.getName());
	}

	/**
	 * Convert a class name to a java file for the current O/S; e.g.
	 * "java.lang.String" is converted to File(java/lang/String.java)
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the file
	 * @Date 2018-09-03 09:00
	 */
	public static File convertToJavaFile(String className) {
		return new File(convertToJavaFileName(className));
	}

	/**
	 * Convert a class to a java file for the current O/S; e.g.
	 * java.lang.String.class is converted to File(java/lang/String.java)
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the file
	 * @Date 2018-09-03 09:00
	 */
	public static File convertToJavaFile(Class<?> javaClass) {
		return convertToJavaFile(javaClass.getName());
	}


	// ***** class => resource *****

	/**
	 * Convert a class to a resource name; e.g. java.lang.String.class is
	 * converted to "/java/lang/String.class".
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToResourceName(Class<?> javaClass) {
		return '/' + convertToArchiveClassFileEntryName(javaClass);
	}

	/**
	 * Convert a class to a resource; e.g. java.lang.String.class is converted
	 * to URL(jar:file:/C:/jdk/1.4.2_04/jre/lib/rt.jar!/java/lang/String.class).
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the url
	 * @Date 2018-09-03 09:00
	 */
	public static URL convertToResource(Class<?> javaClass) {
		return javaClass.getResource(convertToResourceName(javaClass));
	}


	// ***** utilities *****

	/**
	 * Return whether the specified file is an archive file; i.e. its name ends
	 * with ".zip" or ".jar"
	 *
	 * @author mqfdy
	 * @param fileName
	 *            the file name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean fileNameIsArchive(String fileName) {
		String ext = FileTools.extension(fileName).toLowerCase();
		return ext.equals(".jar") || ext.equals(".zip");
	}
	
	/**
	 * Return whether the specified file is an archive file; i.e. its name ends
	 * with ".zip" or ".jar"
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean fileIsArchive(File file) {
		return fileNameIsArchive(file.getName());
	}
	
	/**
	 * Return what should be the fully-qualified file name for the JRE runtime
	 * JAR; e.g. "C:\jdk1.4.2_04\jre\lib\rt.jar".
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String rtJarName() {
		return locationFor(java.lang.Object.class);
	}
	
	/**
	 * Return the location from where the specified class was loaded.
	 *
	 * @author mqfdy
	 * @param javaClass
	 *            the java class
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String locationFor(Class<?> javaClass) {
		URL url = convertToResource(javaClass);
		String path;
		try {
			path = FileTools.buildFile(url).getPath();
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
		String protocol = url.getProtocol().toLowerCase();
		if (protocol.equals("jar")) {
			// if the class is in a JAR, the URL will look something like this:
			//     jar:file:/C:/jdk/1.4.2_04/jre/lib/rt.jar!/java/lang/String.class
			return path.substring(0, path.indexOf('!'));
		} else if (protocol.equals("file")) {
			// if the class is in a directory, the URL will look something like this:
			//     file:/C:/dev/main/mwdev/class/org/eclipse/dali/utility/Classpath.class
			return path.substring(0, path.length() - convertToClassFileName(javaClass).length() - 1);
		} else if (protocol.equals("bundleresource")) {
			// if the class is in a bundle resource (Eclipse?), the URL will look something like this:
			//     bundleresource://43/org/eclipse/dali/utility/Classpath.class
			return path.substring(0, path.length() - convertToClassFileName(javaClass).length() - 1);
		}

		throw new IllegalStateException(url.toString());
	}
	
	/**
	 * Return the directories used by the Java Extension Mechanism.
	 *
	 * @author mqfdy
	 * @return the file[]
	 * @Date 2018-09-03 09:00
	 */
	public static File[] javaExtensionDirectories() {
		return convertToFiles(javaExtensionDirectoryNames());
	}

	/**
	 * Return the directory names used by the Java Extension Mechanism.
	 *
	 * @author mqfdy
	 * @return the string[]
	 * @Date 2018-09-03 09:00
	 */
	public static String[] javaExtensionDirectoryNames() {
		if(null != System.getProperty("java.ext.dirs"))
			return System.getProperty("java.ext.dirs").split(File.pathSeparator);
		return null;
	}


	// ***** internal *****

	private static File[] convertToFiles(String[] fileNames) {
		File[] files = new File[fileNames.length];
		
		for (int i = fileNames.length; i-- > 0; ) {
			
			
			files[i] = new File(fileNames[i]);
		}
		return files;
	}

	private static void addJarFileNamesTo(File dir, List<String> jarFileNames) {
		File[] jarFiles = jarFilesIn(dir);
		for (File jarFile : jarFiles) {
			jarFileNames.add(FileTools.canonicalFile(jarFile).getPath());
		}
	}

	private static File[] jarFilesIn(File directory) {
		return directory.listFiles(jarFileFilter());
	}

	private static FileFilter jarFileFilter() {
		return new FileFilter() {
			public boolean accept(File file) {
				return FileTools.extension(file.getName()).toLowerCase().equals(".jar");
			}
		};
	}


	// ********** constructors **********

	/**
	 * Construct a classpath with the specified entries.
	 */
	private Classpath(Entry[] entries) {
		super();
		this.entries = entries;
	}

	/**
	 * Construct a classpath with the specified entries.
	 *
	 * @param fileNames
	 *            the file names
	 */
	public Classpath(String[] fileNames) {
		this(buildEntries(fileNames));
	}

	/**
	 * Skip empty file names because they will end up expanding to the current
	 * working directory, which is not what we want. Empty file names actually
	 * occur with some frequency; such as when the classpath has been built up
	 * dynamically with too many separators. For example:
	 *     "C:\dev\foo.jar;;C:\dev\bar.jar"
	 * will be parsed into three file names:
	 *     { "C:\dev\foo.jar", "", "C:\dev\bar.jar" }
	 */
	private static Entry[] buildEntries(String[] fileNames) {
		List<Entry> entries = new ArrayList<Entry>();
		for (String fileName : fileNames) {
			if ((fileName != null) && (fileName.length() != 0)) {
				entries.add(new Entry(fileName));
			}
		}
		return entries.toArray(new Entry[entries.size()]);
	}

	/**
	 * Construct a classpath with the specified path.
	 *
	 * @param path
	 *            the path
	 */
	public Classpath(String path) {
		this(path.split(File.pathSeparator));
	}

	/**
	 * Construct a classpath with the specified entries.
	 *
	 * @param fileNames
	 *            the file names
	 */
	public Classpath(List<String> fileNames) {
		this(fileNames.toArray(new String[fileNames.size()]));
	}

	/**
	 * Consolidate the specified classpaths into a single classpath.
	 *
	 * @param classpaths
	 *            the classpaths
	 */
	public Classpath(Classpath[] classpaths) {
		this(consolidateEntries(classpaths));
	}

	private static Entry[] consolidateEntries(Classpath[] classpaths) {
		List<Entry> entries = new ArrayList<Entry>();
		for (Classpath classpath : classpaths) {
			CollectionTools.addAll(entries, classpath.entries());
		}
		return entries.toArray(new Entry[entries.size()]);
	}


	// ********** public API **********

	/**
	 * Return the classpath's entries.
	 *
	 * @author mqfdy
	 * @return the entry[]
	 * @Date 2018-09-03 09:00
	 */
	public Entry[] entries() {
		return this.entries;
	}

	/**
	 * Return the classpath's path.
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public String path() {
		Entry[] localEntries = this.entries;
		int max = localEntries.length - 1;
		if (max == -1) {
			return "";
		}
		StringBuilder sb = new StringBuilder(2000);
		// stop one short of the end of the array
		for (int i = 0; i < max; i++) {
			sb.append(localEntries[i].fileName());
			sb.append(File.pathSeparatorChar);
		}
		sb.append(localEntries[max].fileName());
		return sb.toString();
	}

	/**
	 * Search the classpath for the specified (unqualified) file and return its
	 * entry. Return null if an entry is not found. For example, you could use
	 * this method to find the entry for "rt.jar" or "toplink.jar".
	 *
	 * @author mqfdy
	 * @param shortFileName
	 *            the short file name
	 * @return the entry
	 * @Date 2018-09-03 09:00
	 */
	public Entry entryForFileNamed(String shortFileName) {
		Entry[] localEntries = this.entries;
		for (Entry entry : localEntries) {
			if (entry.file().getName().equals(shortFileName)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Return the first entry file in the classpath that contains the specified
	 * class. Return null if an entry is not found.
	 *
	 * @author mqfdy
	 * @param className
	 *            the class name
	 * @return the entry
	 * @Date 2018-09-03 09:00
	 */
	public Entry entryForClassNamed(String className) {
		String relativeClassFileName = convertToClassFileName(className);
		String archiveEntryName = convertToArchiveClassFileEntryName(className);
		Entry[] localEntries = this.entries;
		for (Entry entry : localEntries) {
			if (entry.contains(relativeClassFileName, archiveEntryName)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Return the names of all the classes discovered on the classpath, with
	 * duplicates removed.
	 *
	 * @author mqfdy
	 * @return the string[]
	 * @Date 2018-09-03 09:00
	 */
	public String[] classNames() {
		return this.classNames(Filter.Null.<String>instance());
	}

	/**
	 * Return the names of all the classes discovered on the classpath and
	 * accepted by the specified filter, with duplicates removed.
	 *
	 * @author mqfdy
	 * @param filter
	 *            the filter
	 * @return the string[]
	 * @Date 2018-09-03 09:00
	 */
	public String[] classNames(Filter<String> filter) {
		Collection<String> classNames = new HashSet<String>(10000);
		this.addClassNamesTo(classNames, filter);
		return classNames.toArray(new String[classNames.size()]);
	}

	/**
	 * Add the names of all the classes discovered on the classpath to the
	 * specified collection.
	 *
	 * @author mqfdy
	 * @param classNames
	 *            the class names
	 * @Date 2018-09-03 09:00
	 */
	public void addClassNamesTo(Collection<String> classNames) {
		this.addClassNamesTo(classNames, Filter.Null.<String>instance());
	}

	/**
	 * Add the names of all the classes discovered on the classpath and accepted
	 * by the specified filter to the specified collection.
	 *
	 * @author mqfdy
	 * @param classNames
	 *            the class names
	 * @param filter
	 *            the filter
	 * @Date 2018-09-03 09:00
	 */
	public void addClassNamesTo(Collection<String> classNames, Filter<String> filter) {
		Entry[] localEntries = this.entries;
		for (Entry entry : localEntries) {
			entry.addClassNamesTo(classNames, filter);
		}
	}

	/**
	 * Return the names of all the classes discovered on the classpath. Just a
	 * bit more performant than #classNames().
	 *
	 * @author mqfdy
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	public Iterator<String> classNamesStream() {
		return this.classNamesStream(Filter.Null.<String>instance());
	}

	/**
	 * Return the names of all the classes discovered on the classpath that are
	 * accepted by the specified filter. Just a bit more performant than
	 * #classNames(Filter).
	 *
	 * @author mqfdy
	 * @param filter
	 *            the filter
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	public Iterator<String> classNamesStream(Filter<String> filter) {
		return new CompositeIterator<String>(this.entryClassNamesStreams(filter));
	}

	private Iterator<Iterator<String>> entryClassNamesStreams(final Filter<String> filter) {
		return new TransformationIterator<Entry, Iterator<String>>(new ArrayIterator<Entry>(this.entries)) {
			@Override
			protected Iterator<String> transform(Entry entry) {
				return entry.classNamesStream(filter);
			}
		};
	}

	/**
	 * Return a "compressed" version of the classpath with its duplicate entries
	 * eliminated.
	 *
	 * @author mqfdy
	 * @return the classpath
	 * @Date 2018-09-03 09:00
	 */
	public Classpath compressed() {
		return new Classpath(CollectionTools.removeDuplicateElements(this.entries));
	}

	/**
	 * Convert the classpath to an array of URLs (that can be used to
	 * instantiate a URLClassLoader).
	 *
	 * @author mqfdy
	 * @return the UR l[]
	 * @Date 2018-09-03 09:00
	 */
	public URL[] urls() {
		Entry[] localEntries = this.entries;
		int len = localEntries.length;
		URL[] urls = new URL[len];
		for (int i = 0; i < len; i++) {
			urls[i] = localEntries[i].url();
		}
		return urls;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.path());
	}


	// ********** inner class **********

	/**
	 * TODO
	 */
	public static class Entry implements Serializable {
		private final String fileName;
		private final File file;
		private final File canonicalFile;

		private static final long serialVersionUID = 1L;

		Entry(String fileName) {
			super();
			if ((fileName == null) || (fileName.length() == 0)) {
				throw new IllegalArgumentException("'fileName' must be non-empty");
			}
			this.fileName = fileName;
			this.file = new File(fileName);
			this.canonicalFile = FileTools.canonicalFile(this.file);
		}

		/**
		 * File name.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		public String fileName() {
			return this.fileName;
		}

		/**
		 * File.
		 *
		 * @author mqfdy
		 * @return the file
		 * @Date 2018-09-03 09:00
		 */
		public File file() {
			return this.file;
		}

		/**
		 * Canonical file.
		 *
		 * @author mqfdy
		 * @return the file
		 * @Date 2018-09-03 09:00
		 */
		public File canonicalFile() {
			return this.canonicalFile;
		}

		/**
		 * Canonical file name.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		public String canonicalFileName() {
			return this.canonicalFile.getAbsolutePath();
		}

		@Override
		public boolean equals(Object o) {
			if ( ! (o instanceof Entry)) {
				return false;
			}
			return ((Entry) o).canonicalFile.equals(this.canonicalFile);
		}

		@Override
		public int hashCode() {
			return this.canonicalFile.hashCode();
		}

		/**
		 * Return the entry's "canonical" URL.
		 *
		 * @author mqfdy
		 * @return the url
		 * @Date 2018-09-03 09:00
		 */
		public URL url() {
			try {
				return this.canonicalFile.toURL();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		/**
		 * Return whether the entry contains the specified class.
		 *
		 * @author mqfdy
		 * @param javaClass
		 *            the java class
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		public boolean contains(Class<?> javaClass) {
			return this.contains(javaClass.getName());
		}

		/**
		 * Return whether the entry contains the specified class.
		 *
		 * @author mqfdy
		 * @param className
		 *            the class name
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		public boolean contains(String className) {
			return this.contains(convertToClassFileName(className), convertToArchiveClassFileEntryName(className));
		}

		/**
		 * Return whether the entry contains either the specified relative
		 * class file or the specified archive entry.
		 * Not the prettiest signature, but it's internal....
		 */
		boolean contains(String relativeClassFileName, String archiveEntryName) {
			if ( ! this.canonicalFile.exists()) {
				return false;
			}
			if (this.canonicalFile.isDirectory() && (new File(this.canonicalFile, relativeClassFileName)).exists()) {
				return true;
			}
			return (fileIsArchive(this.canonicalFile) && this.archiveContainsEntry(archiveEntryName));
		}

		/**
		 * Return whether the entry's archive contains the specified entry.
		 */
		private boolean archiveContainsEntry(String zipEntryName) {
			ZipFile zipFile = null;
			ZipEntry zipEntry = null;
			try {
				zipFile = new ZipFile(this.canonicalFile);
				zipEntry = zipFile.getEntry(zipEntryName);
			} catch (IOException ex) {
				// something is wrong, leave the entry null
			} finally {
				try {
					if (zipFile != null) {
						zipFile.close();
					}
				} catch (IOException ex) {
					zipEntry = null;	// something is wrong, clear out the entry
				}
			}
			return zipEntry != null;
		}

		/**
		 * Return the names of all the classes discovered in the entry.
		 *
		 * @author mqfdy
		 * @return the string[]
		 * @Date 2018-09-03 09:00
		 */
		public String[] classNames() {
			return this.classNames(Filter.Null.<String>instance());
		}

		/**
		 * Return the names of all the classes discovered in the entry and
		 * accepted by the specified filter.
		 *
		 * @author mqfdy
		 * @param filter
		 *            the filter
		 * @return the string[]
		 * @Date 2018-09-03 09:00
		 */
		public String[] classNames(Filter<String> filter) {
			Collection<String> classNames = new ArrayList<String>(2000);
			this.addClassNamesTo(classNames, filter);
			return classNames.toArray(new String[classNames.size()]);
		}

		/**
		 * Add the names of all the classes discovered in the entry to the
		 * specified collection.
		 *
		 * @author mqfdy
		 * @param classNames
		 *            the class names
		 * @Date 2018-09-03 09:00
		 */
		public void addClassNamesTo(Collection<String> classNames) {
			this.addClassNamesTo(classNames, Filter.Null.<String>instance());
		}

		/**
		 * Add the names of all the classes discovered in the entry and accepted
		 * by the specified filter to the specified collection.
		 *
		 * @author mqfdy
		 * @param classNames
		 *            the class names
		 * @param filter
		 *            the filter
		 * @Date 2018-09-03 09:00
		 */
		public void addClassNamesTo(Collection<String> classNames, Filter<String> filter) {
			if (this.canonicalFile.exists()) {
				if (this.canonicalFile.isDirectory()) {
					this.addClassNamesForDirectoryTo(classNames, filter);
				} else if (fileIsArchive(this.canonicalFile)) {
					this.addClassNamesForArchiveTo(classNames, filter);
				}
			}
		}

		/**
		 * Add the names of all the classes discovered
		 * under the entry's directory and accepted by
		 * the specified filter to the specified collection.
		 */
		private void addClassNamesForDirectoryTo(Collection<String> classNames, Filter<String> filter) {
			int start = this.canonicalFile.getAbsolutePath().length() + 1;
			for (Iterator<File> stream = this.classFilesForDirectory(); stream.hasNext(); ) {
				String className = convertToClassName(stream.next().getAbsolutePath().substring(start));
				if (filter.accept(className)) {
					classNames.add(className);
				}
			}
		}

		/**
		 * Return an iterator on all the class files discovered
		 * under the entry's directory.
		 */
		private Iterator<File> classFilesForDirectory() {
			return new FilteringIterator<File, File>(FileTools.filesInTree(this.canonicalFile)) {
				@Override
				protected boolean accept(File next) {
					return Entry.this.fileNameMightBeForClassFile(next.getName());
				}
			};
		}

		/**
		 * Add the names of all the classes discovered
		 * in the entry's archive file and accepted by the
		 * specified filter to the specified collection.
		 */
		private void addClassNamesForArchiveTo(Collection<String> classNames, Filter<String> filter) {
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(this.canonicalFile);
			} catch (IOException ex) {
				return;
			}
			for (Enumeration<? extends ZipEntry> stream = zipFile.entries(); stream.hasMoreElements(); ) {
				ZipEntry zipEntry = stream.nextElement();
				String zipEntryName = zipEntry.getName();
				if (this.fileNameMightBeForClassFile(zipEntryName)) {
					String className = convertToClassName(zipEntryName);
					if (filter.accept(className)) {
						classNames.add(className);
					}
				}
			}
			try {
				zipFile.close();
			} catch (IOException ex) {
				return;
			}
		}

		/**
		 * Return whether the specified file might be a Java class file.
		 * The file name must at least end with ".class" and contain no spaces.
		 * (Neither class names nor package names may contain spaces.)
		 * Whether it actually is a class file will need to be determined by
		 * a class loader.
		 */
		boolean fileNameMightBeForClassFile(String name) {
			return FileTools.extension(name).toLowerCase().equals(".class")
					&& (name.indexOf(' ') == -1);
		}

		/**
		 * Return the names of all the classes discovered on the classpath. Just
		 * a bit more performant than #classNames().
		 *
		 * @author mqfdy
		 * @return the iterator
		 * @Date 2018-09-03 09:00
		 */
		public Iterator<String> classNamesStream() {
			return this.classNamesStream(Filter.Null.<String>instance());
		}

		/**
		 * Return the names of all the classes discovered on the classpath that
		 * are accepted by the specified filter. Just a bit more performant than
		 * #classNames(Filter).
		 *
		 * @author mqfdy
		 * @param filter
		 *            the filter
		 * @return the iterator
		 * @Date 2018-09-03 09:00
		 */
		public Iterator<String> classNamesStream(Filter<String> filter) {
			if (this.canonicalFile.exists()) {
				if (this.canonicalFile.isDirectory()) {
					return this.classNamesForDirectory(filter);
				}
				if (fileIsArchive(this.canonicalFile)) {
					return this.classNamesForArchive(filter);
				}
			}
			return EmptyIterator.instance();
		}

		/**
		 * Return the names of all the classes discovered
		 * under the entry's directory and accepted by
		 * the specified filter.
		 */
		private Iterator<String> classNamesForDirectory(Filter<String> filter) {
			return new FilteringIterator<String, String>(this.classNamesForDirectory(), filter);
		}

		/**
		 * Transform the class files to class names.
		 */
		private Iterator<String> classNamesForDirectory() {
			final int start = this.canonicalFile.getAbsolutePath().length() + 1;
			return new TransformationIterator<File, String>(this.classFilesForDirectory()) {
				@Override
				protected String transform(File f) {
					return convertToClassName(f.getAbsolutePath().substring(start));
				}
			};
		}

		/**
		 * Return the names of all the classes discovered
		 * in the entry's archive file and accepted by the
		 * specified filter.
		 */
		private Iterator<String> classNamesForArchive(Filter<String> filter) {
			// we can't simply wrap iterators here because we need to close the archive file...
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(this.canonicalFile);
			} catch (IOException ex) {
				return EmptyIterator.instance();
			}
			Collection<String> classNames = new HashSet<String>(zipFile.size());
			for (Enumeration<? extends ZipEntry> stream = zipFile.entries(); stream.hasMoreElements(); ) {
				ZipEntry zipEntry = stream.nextElement();
				String zipEntryName = zipEntry.getName();
				if (this.fileNameMightBeForClassFile(zipEntryName)) {
					String className = convertToClassName(zipEntryName);
					if (filter.accept(className)) {
						classNames.add(className);
					}
				}
			}
			try {
				zipFile.close();
			} catch (IOException ex) {
				return EmptyIterator.instance();
			}
			return classNames.iterator();
		}

	}

}
