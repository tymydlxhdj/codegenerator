package org.apache.velocity.runtime.parser.node;

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.velocity.runtime.log.Log;

// TODO: Auto-generated Javadoc
/**
 * Abstract class that is used to execute an arbitrary
 * method that is in introspected. This is the superclass
 * for the GetExecutor and PropertyExecutor.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 * @version $Id: AbstractExecutor.java 685685 2008-08-13 21:43:27Z nbubna $
 */
public abstract class AbstractExecutor
{
    
    /** The log. */
    protected Log log = null;

    /**
     * Method to be executed.
     */
    private Method method = null;

    /**
	 * Execute method against context.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @return The resulting object.
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @Date 2018-09-03 09:00
	 */
     public abstract Object execute(Object o)
         throws IllegalAccessException, InvocationTargetException;

    /**
	 * Tell whether the executor is alive by looking at the value of the method.
	 *
	 * @author mqfdy
	 * @return True if executor is alive.
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isAlive()
    {
        return (method != null);
    }

    /**
	 * Gets the method.
	 *
	 * @author mqfdy
	 * @return The current method.
	 * @Date 2018-09-03 09:00
	 */
    public Method getMethod()
    {
        return method;
    }

    /**
	 * Sets the method.
	 *
	 * @param method
	 *            the new method
	 * @since 1.5
	 */
    protected void setMethod(final Method method)
    {
        this.method = method;
    }
}
