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

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeLogger;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.log.RuntimeLoggerLog;
import org.apache.velocity.util.introspection.Introspector;

// TODO: Auto-generated Javadoc
/**
 * Returned the value of object property when executed.
 *
 * @author mqfdy
 */
public class PropertyExecutor extends AbstractExecutor
{
    
    /** The introspector. */
    private final Introspector introspector;

    /**
	 * Instantiates a new property executor.
	 *
	 * @param log
	 *            the log
	 * @param introspector
	 *            the introspector
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @since 1.5
	 */
    public PropertyExecutor(final Log log, final Introspector introspector,
            final Class clazz, final String property)
    {
        this.log = log;
        this.introspector = introspector;

        // Don't allow passing in the empty string or null because
        // it will either fail with a StringIndexOutOfBounds error
        // or the introspector will get confused.
        if (StringUtils.isNotEmpty(property))
        {
            discover(clazz, property);
        }
    }

    /**
	 * Instantiates a new property executor.
	 *
	 * @param r
	 *            the r
	 * @param introspector
	 *            the introspector
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @deprecated RuntimeLogger is deprecated. Use the other constructor.
	 */
    public PropertyExecutor(final RuntimeLogger r, final Introspector introspector,
            final Class clazz, final String property)
    {
        this(new RuntimeLoggerLog(r), introspector, clazz, property);
    }

    /**
	 * Gets the introspector.
	 *
	 * @return The current introspector.
	 * @since 1.5
	 */
    protected Introspector getIntrospector()
    {
        return this.introspector;
    }

    /**
	 * Discover.
	 *
	 * @author mqfdy
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @Date 2018-09-03 09:00
	 */
    protected void discover(final Class clazz, final String property)
    {
        /*
         *  this is gross and linear, but it keeps it straightforward.
         */

        try
        {
            Object [] params = {};

            StringBuffer sb = new StringBuffer("get");
            sb.append(property);

            setMethod(introspector.getMethod(clazz, sb.toString(), params));

            if (!isAlive())
            {
                /*
                 *  now the convenience, flip the 1st character
                 */

                char c = sb.charAt(3);

                if (Character.isLowerCase(c))
                {
                    sb.setCharAt(3, Character.toUpperCase(c));
                }
                else
                {
                    sb.setCharAt(3, Character.toLowerCase(c));
                }

                setMethod(introspector.getMethod(clazz, sb.toString(), params));
            }
        }
        /**
         * pass through application level runtime exceptions
         */
        catch( RuntimeException e )
        {
            throw e;
        }
        catch(Exception e)
        {
            String msg = "Exception while looking for property getter for '" + property;
            log.error(msg, e);
            throw new VelocityException(msg, e);
        }
    }

    /**
	 * Execute.
	 *
	 * @param o
	 *            the o
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @see org.apache.velocity.runtime.parser.node.AbstractExecutor#execute(java.lang.Object)
	 */
    public Object execute(Object o)
        throws IllegalAccessException,  InvocationTargetException
    {
        return isAlive() ? getMethod().invoke(o, ((Object []) null)) : null;
    }
}
