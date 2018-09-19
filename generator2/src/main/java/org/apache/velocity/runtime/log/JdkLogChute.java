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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.runtime.RuntimeServices;

// TODO: Auto-generated Javadoc
/**
 * Implementation of a simple java.util.logging LogChute.
 *
 * @author <a href="mailto:nbubna@apache.org>Nathan Bubna</a>
 * @version $Id: JdkLogChute.java 703541 2008-10-10 18:09:42Z nbubna $
 * @since 1.5
 */
public class JdkLogChute implements LogChute
{
    
    /** Property key for specifying the name for the logger instance. */
    public static final String RUNTIME_LOG_JDK_LOGGER =
        "runtime.log.logsystem.jdk.logger";

    /** The Constant RUNTIME_LOG_JDK_LOGGER_LEVEL. */
    public static final String RUNTIME_LOG_JDK_LOGGER_LEVEL =
        "runtime.log.logsystem.jdk.logger.level";

    /** Default name for the JDK logger instance. */
    public static final String DEFAULT_LOG_NAME = "org.apache.velocity";

    /** The logger. */
    protected Logger logger = null;

    /**
	 * Inits the.
	 *
	 * @param rs
	 *            the rs
	 * @see org.apache.velocity.runtime.log.LogChute#init(org.apache.velocity.runtime.RuntimeServices)
	 */
    public void init(RuntimeServices rs)
    {
        String name = (String)rs.getProperty(RUNTIME_LOG_JDK_LOGGER);
        if (name == null)
        {
            name = DEFAULT_LOG_NAME;
        }
        logger = Logger.getLogger(name);

        /* get and set specified level for this logger, */
        String lvl = rs.getString(RUNTIME_LOG_JDK_LOGGER_LEVEL);
        if (lvl != null)
        {
            Level level = Level.parse(lvl);
            logger.setLevel(level);
            log(LogChute.DEBUG_ID, "JdkLogChute will use logger '"
                +name+'\''+" at level '"+level+'\'');
        }

    }

    /**
	 * Returns the java.util.logging.Level that matches to the specified
	 * LogChute level.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @return The current log level of the JDK Logger.
	 * @Date 2018-09-03 09:00
	 */
    protected Level getJdkLevel(int level)
    {
        switch (level)
        {
            case LogChute.WARN_ID:
                return Level.WARNING;
            case LogChute.INFO_ID:
                return Level.INFO;
            case LogChute.DEBUG_ID:
                return Level.FINE;
            case LogChute.TRACE_ID:
                return Level.FINEST;
            case LogChute.ERROR_ID:
                return Level.SEVERE;
            default:
                return Level.FINER;
        }
    }

    /**
	 * Logs messages.
	 *
	 * @author mqfdy
	 * @param level
	 *            severity level
	 * @param message
	 *            complete error message
	 * @Date 2018-9-3 11:38:28
	 */
    public void log(int level, String message)
    {
        log(level, message, null);
    }

    /**
	 * Send a log message from Velocity along with an exception or error.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-9-3 11:38:28
	 */
    public void log(int level, String message, Throwable t)
    {
        Level jdkLevel = getJdkLevel(level);
        if (t == null)
        {
            logger.log(jdkLevel, message);
        }
        else
        {
            logger.log(jdkLevel, message, t);
        }
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
        Level jdkLevel = getJdkLevel(level);
        return logger.isLoggable(jdkLevel);
    }

}
