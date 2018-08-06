package com.mqfdy.code.reverse.utils;

import java.util.Comparator;

import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;

public class ReverseComparator implements Comparator<Object> {

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
