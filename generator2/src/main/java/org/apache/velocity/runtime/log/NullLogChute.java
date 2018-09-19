package org.apache.velocity.runtime.log;

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

import org.apache.velocity.runtime.RuntimeServices;

// TODO: Auto-generated Javadoc
/**
 *  Logger used in case of failure. Does nothing.
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @author <a href="mailto:nbubna@optonline.net">Nathan Bubna.</a>
 * @version $Id: NullLogChute.java 730039 2008-12-30 03:53:19Z byron $
 * @since 1.5
 */
public class NullLogChute implements LogChute
{

    /**
	 * Inits the.
	 *
	 * @param rs
	 *            the rs
	 * @throws Exception
	 *             the exception
	 * @see org.apache.velocity.runtime.log.LogChute#init(org.apache.velocity.runtime.RuntimeServices)
	 */
    public void init(RuntimeServices rs) throws Exception
    {
    }

    /**
	 * logs messages to the great Garbage Collector in the sky.
	 *
	 * @author mqfdy
	 * @param level
	 *            severity level
	 * @param message
	 *            complete error message
	 * @Date 2018-9-3 11:38:38
	 */
    public void log(int level, String message)
    {
    }

    /**
	 * logs messages and their accompanying Throwables to the great Garbage
	 * Collector in the sky.
	 *
	 * @author mqfdy
	 * @param level
	 *            severity level
	 * @param message
	 *            complete error message
	 * @param t
	 *            the java.lang.Throwable
	 * @Date 2018-9-3 11:38:38
	 */
    public void log(int level, String message, Throwable t)
    {
    }

    /**
	 * Checks if is level enabled.
	 *
	 * @param level
	 *            the level
	 * @return true, if is level enabled
	 * @see org.apache.velocity.runtime.log.LogChute#isLevelEnabled(int)
	 */
    public boolean isLevelEnabled(int level)
    {
        return false;
    }

}
