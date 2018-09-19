package org.apache.velocity.util.introspection;

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

import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * 'Federated' introspection/reflection interface to allow the introspection
 *  behavior in Velocity to be customized.
 *
 * @author <a href="mailto:geirm@apache.org">Geir Magusson Jr.</a>
 * @version $Id: Uberspect.java 774412 2009-05-13 15:54:07Z nbubna $
 */
public interface Uberspect
{
    
    /**
	 * Initializer - will be called before use.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:25
	 */
    public void init();

    /**
	 * To support iteratives - #foreach().
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @param info
	 *            the info
	 * @return An Iterator.
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:25
	 */
    public Iterator getIterator(Object obj, Info info) throws Exception;

    /**
	 * Returns a general method, corresponding to $foo.bar( $woogie )
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @param info
	 *            the info
	 * @return A Velocity Method.
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:25
	 */
    public VelMethod getMethod(Object obj, String method, Object[] args, Info info) throws Exception;

    /**
	 * Property getter - returns VelPropertyGet appropos for #set($foo =
	 * $bar.woogie)
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @param identifier
	 *            the identifier
	 * @param info
	 *            the info
	 * @return A Velocity Getter.
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:25
	 */
    public VelPropertyGet getPropertyGet(Object obj, String identifier, Info info) throws Exception;

    /**
	 * Property setter - returns VelPropertySet appropos for #set($foo.bar =
	 * "geir")
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @param identifier
	 *            the identifier
	 * @param arg
	 *            the arg
	 * @param info
	 *            the info
	 * @return A Velocity Setter.
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:25
	 */
    public VelPropertySet getPropertySet(Object obj, String identifier, Object arg, Info info) throws Exception;
}
