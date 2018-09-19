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
import org.apache.velocity.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * Wrapper to make user's custom LogSystem implementations work
 * with the new LogChute setup.
 *
 * @author <a href="mailto:nbubna@apache.org">Nathan Bubna</a>
 * @version $Id: LogChuteSystem.java 730039 2008-12-30 03:53:19Z byron $
 * @since 1.5
 */
public class LogChuteSystem implements LogChute
{

    /** The log system. */
    private LogSystem logSystem;

    /**
	 * Only classes in this package should be creating this. Users should not
	 * have to mess with this class.
	 *
	 * @param wrapMe
	 *            the wrap me
	 */
    protected LogChuteSystem(LogSystem wrapMe)
    {
        this.logSystem = wrapMe;
    }

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
        logSystem.init(rs);
    }

    /**
	 * Log.
	 *
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @see org.apache.velocity.runtime.log.LogChute#log(int, java.lang.String)
	 */
    public void log(int level, String message)
    {
        logSystem.logVelocityMessage(level, message);
    }

    /**
	 * First passes off the message at the specified level, then passes off
	 * stack trace of the Throwable as a 2nd message at the same level.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-9-3 11:38:36
	 */
    public void log(int level, String message, Throwable t)
    {
        logSystem.logVelocityMessage(level, message);
        logSystem.logVelocityMessage(level, StringUtils.stackTrace(t));
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
        return true;
    }

}
