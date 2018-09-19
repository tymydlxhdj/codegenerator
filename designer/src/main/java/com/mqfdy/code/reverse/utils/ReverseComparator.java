package com.mqfdy.code.reverse.utils;

import java.util.Comparator;

import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;

// TODO: Auto-generated Javadoc
/**
 * The Class ReverseComparator.
 *
 * @author mqfdy
 */
public class ReverseComparator implements Comparator<Object> {

	/**
	 * Compare.
	 *
	 * @author mqfdy
	 * @param o1
	 *            the o 1
	 * @param o2
	 *            the o 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public int compare(Object o1, Object o2) {
		if(o1 instanceof Table && o2 instanceof Table){
			return ((Table)o1).getName().compareTo(((Table)o2).getName());
		}else if(o1 instanceof Column && o2 instanceof Column){
			return ((Column)o1).getName().compareTo(((Column)o2).getName());
		}else if(o1 instanceof View && o2 instanceof View){
			return ((View)o1).getName().compareTo(((View)o2).getName());
		}
		return 0;
	}

}
