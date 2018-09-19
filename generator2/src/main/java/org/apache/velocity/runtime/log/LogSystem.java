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
 * Old base interface that old logging systems needed to implement.
 *
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: LogSystem.java 730039 2008-12-30 03:53:19Z byron $
 * @deprecated Use LogChute instead!
 */
public interface LogSystem
{
    
    /**
	 * The Constant DEBUG_ON.
	 *
	 * @deprecated This is unused and meaningless
	 */
    public final static boolean DEBUG_ON = true;

    /**
     * ID for debug messages.
     */
    public final static int DEBUG_ID = 0;

    /**
     * ID for info messages.
     */
    public final static int INFO_ID = 1;

    /**
     * ID for warning messages.
     */
    public final static int WARN_ID = 2;

    /**
     * ID for error messages.
     */
    public final static int ERROR_ID = 3;

    /**
	 * Initializes this LogSystem.
	 *
	 * @author mqfdy
	 * @param rs
	 *            the rs
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
    public void init( RuntimeServices rs ) throws Exception;

    /**
	 * Log velocity message.
	 *
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @deprecated Use log(level, message).
	 */
    public void logVelocityMessage(int level, String message);
}
