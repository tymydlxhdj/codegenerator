package org.apache.velocity.context;

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
 * interface for internal context wrapping functionality.
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: InternalWrapperContext.java 471908 2006-11-06 22:39:28Z henning
 *          $
 */
public interface InternalWrapperContext
{
    
    /**
	 * Returns the wrapped user context.
	 *
	 * @author mqfdy
	 * @return The wrapped user context.
	 * @Date 2018-9-3 11:38:31
	 */
    Context getInternalUserContext();

    /**
	 * Returns the base full context impl.
	 *
	 * @author mqfdy
	 * @return The base full context impl.
	 * @Date 2018-9-3 11:38:31
	 */
    InternalContextAdapter getBaseContext();

    /**
	 * Allows callers to explicitly put objects in the local context. Objects
	 * added to the context through this method always end up in the top-level
	 * context of possible wrapped contexts.
	 *
	 * @author mqfdy
	 * @param key
	 *            name of item to set.
	 * @param value
	 *            object to set to key.
	 * @return old stored object
	 * @Date 2018-9-3 11:38:31
	 */
    Object localPut(final String key, final Object value);
}
