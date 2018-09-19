package org.apache.velocity.runtime.directive;

// TODO: Auto-generated Javadoc
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

/**
 * This represents scoping and metadata for #foreach,
 * adding index, count, hasNext, isFirst and isLast info.
 *
 * @author Nathan Bubna
 * @version $Id$
 */
public class ForeachScope extends Scope
{
    
    /** The index. */
    protected int index = -1;
    
    /** The has next. */
    protected boolean hasNext = false;

    /**
	 * Instantiates a new foreach scope.
	 *
	 * @param owner
	 *            the owner
	 * @param replaces
	 *            the replaces
	 */
    public ForeachScope(Object owner, Object replaces)
    {
        super(owner, replaces);
    }

    /**
	 * Gets the index.
	 *
	 * @author mqfdy
	 * @return the index
	 * @Date 2018-09-03 09:00
	 */
    public int getIndex()
    {
        return index;
    }

    /**
	 * Gets the count.
	 *
	 * @author mqfdy
	 * @return the count
	 * @Date 2018-09-03 09:00
	 */
    public int getCount()
    {
        return index + 1;
    }

    /**
	 * Checks for next.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
    public boolean hasNext()
    {
        return getHasNext();
    }

    /**
	 * Gets the checks for next.
	 *
	 * @author mqfdy
	 * @return the checks for next
	 * @Date 2018-09-03 09:00
	 */
    public boolean getHasNext()
    {
        return hasNext;
    }

    /**
	 * Checks if is first.
	 *
	 * @author mqfdy
	 * @return true, if is first
	 * @Date 2018-09-03 09:00
	 */
    public boolean isFirst()
    {
        return index < 1;
    }

    /**
	 * Gets the first.
	 *
	 * @author mqfdy
	 * @return the first
	 * @Date 2018-09-03 09:00
	 */
    public boolean getFirst()
    {
        return isFirst();
    }

    /**
	 * Checks if is last.
	 *
	 * @author mqfdy
	 * @return true, if is last
	 * @Date 2018-09-03 09:00
	 */
    public boolean isLast()
    {
        return !hasNext;
    }

    /**
	 * Gets the last.
	 *
	 * @author mqfdy
	 * @return the last
	 * @Date 2018-09-03 09:00
	 */
    public boolean getLast()
    {
        return isLast();
    }

}
