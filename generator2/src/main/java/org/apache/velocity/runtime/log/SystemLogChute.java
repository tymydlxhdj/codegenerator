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

import java.io.PrintStream;
import org.apache.velocity.runtime.RuntimeServices;

// TODO: Auto-generated Javadoc
/**
 * Logger used when no other is configured.  By default, all messages
 * will be printed to the System.err output stream.
 *
 * @author <a href="mailto:nbubna@apache.org">Nathan Bubna</a>
 * @version $Id: SystemLogChute.java 730039 2008-12-30 03:53:19Z byron $
 * @since 1.5
 */
public class SystemLogChute implements LogChute
{
    
    /** The Constant RUNTIME_LOG_LEVEL_KEY. */
    public static final String RUNTIME_LOG_LEVEL_KEY = 
        "runtime.log.logsystem.system.level";
    
    /** The Constant RUNTIME_LOG_SYSTEM_ERR_LEVEL_KEY. */
    public static final String RUNTIME_LOG_SYSTEM_ERR_LEVEL_KEY = 
        "runtime.log.logsystem.system.err.level";

    /** The enabled. */
    private int enabled = WARN_ID;
    
    /** The err level. */
    private int errLevel = TRACE_ID;

    /**
     * @see org.apache.velocity.runtime.log.LogChute#init(org.apache.velocity.runtime.RuntimeServices)
     * @param rs
     * @throws Exception SystemLogChute
     */
    public void init(RuntimeServices rs) throws Exception
    {
        // look for a level config property
        String level = (String)rs.getProperty(RUNTIME_LOG_LEVEL_KEY);
        if (level != null)
        {
            // and set it accordingly
            setEnabledLevel(toLevel(level));
        }

        // look for an errLevel config property
        String errLevel = (String)rs.getProperty(RUNTIME_LOG_SYSTEM_ERR_LEVEL_KEY);
        if (errLevel != null)
        {
            setSystemErrLevel(toLevel(errLevel));
        }
    }

    /**
	 * To level.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
    protected int toLevel(String level) {
        if (level.equalsIgnoreCase("debug"))
        {
            return DEBUG_ID;
        }
        else if (level.equalsIgnoreCase("info"))
        {
            return INFO_ID;
        }
        else if (level.equalsIgnoreCase("warn"))
        {
            return WARN_ID;
        }
        else if (level.equalsIgnoreCase("error"))
        {
            return ERROR_ID;
        }
        else
        {
            return TRACE_ID;
        }
    }

    /**
	 * Gets the prefix.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @return the prefix
	 * @Date 2018-09-03 09:00
	 */
    protected String getPrefix(int level)
    {
        switch (level)
        {
            case WARN_ID:
                return WARN_PREFIX;
            case DEBUG_ID:
                return DEBUG_PREFIX;
            case TRACE_ID:
                return TRACE_PREFIX;
            case ERROR_ID:
                return ERROR_PREFIX;
            case INFO_ID:
            default:
                return INFO_PREFIX;
        }
    }

    /**
	 * Logs messages to either std.out or std.err depending on their severity.
	 *
	 * @author mqfdy
	 * @param level
	 *            severity level
	 * @param message
	 *            complete error message
	 * @Date 2018-9-3 11:38:37
	 */
    public void log(int level, String message)
    {
        // pass it off
        log(level, message, null);
    }

    /**
	 * Logs messages to the system console so long as the specified level is
	 * equal to or greater than the level this LogChute is enabled for. If the
	 * level is equal to or greater than LogChute.ERROR_ID, messages will be
	 * printed to System.err. Otherwise, they will be printed to System.out. If
	 * a java.lang.Throwable accompanies the message, it's stack trace will be
	 * printed to the same stream as the message.
	 *
	 * @author mqfdy
	 * @param level
	 *            severity level
	 * @param message
	 *            complete error message
	 * @param t
	 *            the java.lang.Throwable
	 * @Date 2018-9-3 11:38:37
	 */
    public void log(int level, String message, Throwable t)
    {
        if (!isLevelEnabled(level))
        {
            return;
        }

        String prefix = getPrefix(level);
        if (level >= this.errLevel)
        {
            write(System.err, prefix, message, t);
        }
        else
        {
            write(System.out, prefix, message, t);
        }
    }

    /**
	 * Write.
	 *
	 * @author mqfdy
	 * @param stream
	 *            the stream
	 * @param prefix
	 *            the prefix
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    protected void write(PrintStream stream, String prefix, String message, Throwable t)
    {
        stream.print(prefix);
        stream.println(message);
        if (t != null)
        {
            stream.println(t.getMessage());
            t.printStackTrace(stream);
        }
    }

    /**
	 * Set the minimum level at which messages will be printed.
	 *
	 * @author mqfdy
	 * @param level
	 *            the new enabled level
	 * @Date 2018-09-03 09:00
	 */
    public void setEnabledLevel(int level)
    {
        this.enabled = level;
    }

    /**
	 * Returns the current minimum level at which messages will be printed.
	 *
	 * @author mqfdy
	 * @return the enabled level
	 * @Date 2018-09-03 09:00
	 */
    public int getEnabledLevel()
    {
        return this.enabled;
    }

    /**
	 * Set the minimum level at which messages will be printed to System.err
	 * instead of System.out.
	 *
	 * @author mqfdy
	 * @param level
	 *            the new system err level
	 * @Date 2018-09-03 09:00
	 */
    public void setSystemErrLevel(int level)
    {
        this.errLevel = level;
    }

    /**
	 * Returns the current minimum level at which messages will be printed to
	 * System.err instead of System.out.
	 *
	 * @author mqfdy
	 * @return the system err level
	 * @Date 2018-09-03 09:00
	 */
    public int getSystemErrLevel()
    {
        return this.errLevel;
    }

    /**
	 * This will return true if the specified level is equal to or higher than
	 * the level this LogChute is enabled for.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @return true, if is level enabled
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isLevelEnabled(int level)
    {
        return (level >= this.enabled);
    }

}
