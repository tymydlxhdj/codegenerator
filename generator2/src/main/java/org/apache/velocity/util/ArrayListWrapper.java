package org.apache.velocity.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.lang.reflect.Array;
import java.util.AbstractList;

// TODO: Auto-generated Javadoc
/**
 * A class that wraps an array with a List interface.
 *
 * @author Chris Schultz &lt;chris@christopherschultz.net$gt;
 * @version $Revision: 685685 $ $Date: 2006-04-14 19:40:41 $
 * @since 1.6
 */
public class ArrayListWrapper extends AbstractList
{
    
    /** The array. */
    private Object array;

    /**
	 * Instantiates a new array list wrapper.
	 *
	 * @param array
	 *            the array
	 */
    public ArrayListWrapper(Object array)
    {
        this.array = array;
    }

    /**
	 * Gets the.
	 *
	 * @param index
	 *            the index
	 * @return ArrayListWrapper
	 * @see java.util.AbstractList#get(int)
	 */
    public Object get(int index)
    {
        return Array.get(array, index);
    }

    /**
	 * Sets the.
	 *
	 * @param index
	 *            the index
	 * @param element
	 *            the element
	 * @return ArrayListWrapper
	 * @see java.util.AbstractList#set(int, java.lang.Object)
	 */
    public Object set(int index, Object element)
    {
        Object old = get(index);
        Array.set(array, index, element);
        return old;
    }

    /**
	 * Size.
	 *
	 * @return ArrayListWrapper
	 * @see java.util.AbstractCollection#size()
	 */
    public int size()
    {
        return Array.getLength(array);
    }

}
