package org.apache.velocity.util.introspection;

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
 *  Method used for regular method invocation
 *
 *    $foo.bar()
 *
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: VelMethod.java 510625 2007-02-22 19:06:28Z nbubna $
 */
public interface VelMethod
{
    
    /**
	 * invocation method - called when the method invocation should be performed
	 * and a value returned.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @param params
	 *            the params
	 * @return The resulting object.
	 * @throws Exception
	 *             the exception
	 * @Date 2018-9-3 11:38:38
	 */
    public Object invoke(Object o, Object[] params)
        throws Exception;

    /**
	 * specifies if this VelMethod is cacheable and able to be reused for this
	 * class of object it was returned for.
	 *
	 * @author mqfdy
	 * @return true if can be reused for this class, false if not
	 * @Date 2018-9-3 11:38:38
	 */
    public boolean isCacheable();

    /**
	 * returns the method name used.
	 *
	 * @author mqfdy
	 * @return The method name used
	 * @Date 2018-9-3 11:38:38
	 */
    public String getMethodName();

    /**
	 * returns the return type of the method invoked.
	 *
	 * @author mqfdy
	 * @return The return type of the method invoked
	 * @Date 2018-9-3 11:38:38
	 */
    public Class getReturnType();
}
