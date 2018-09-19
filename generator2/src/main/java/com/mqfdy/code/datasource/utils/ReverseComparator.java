package com.mqfdy.code.datasource.utils;

import java.util.Comparator;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.mapping.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class ReverseComparator.
 *
 * @author mqfdy
 */
public class ReverseComparator implements Comparator<Object> {

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 * @param o1
	 * @param o2
	 * @return ReverseComparator
	 */
	public int compare(Object o1, Object o2) {
		if(o1 instanceof Table && o2 instanceof Table){
			return ((Table)o1).getName().compareTo(((Table)o2).getName());
		}else if(o1 instanceof Column && o2 instanceof Column){
			return ((Column)o1).getName().compareTo(((Column)o2).getName());
		}
		return 0;
	}

}
