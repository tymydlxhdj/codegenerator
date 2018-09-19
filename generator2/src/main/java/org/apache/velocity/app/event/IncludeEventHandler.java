package org.apache.velocity.app.event;

import org.apache.velocity.context.Context;
import org.apache.velocity.util.ContextAware;

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
 *  Event handler for include type directives (e.g. <code>#include()</code>, <code>#parse()</code>)
 *  Allows the developer to modify the path of the resource returned.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 * @version $Id: IncludeEventHandler.java 685685 2008-08-13 21:43:27Z nbubna $
 * @since 1.5
 */
public interface  IncludeEventHandler extends EventHandler
{
    
    /**
	 * Called when an include-type directive is encountered (
	 * <code>#include</code> or <code>#parse</code>). May modify the path of the
	 * resource to be included or may block the include entirely. All the
	 * registered IncludeEventHandlers are called unless null is returned. If
	 * none are registered the template at the includeResourcePath is retrieved.
	 *
	 * @author mqfdy
	 * @param includeResourcePath
	 *            the path as given in the include directive.
	 * @param currentResourcePath
	 *            the path of the currently rendering template that includes the
	 *            include directive.
	 * @param directiveName
	 *            name of the directive used to include the resource. (With the
	 *            standard directives this is either "parse" or "include").
	 * @return a new resource path for the directive, or null to block the
	 *         include from occurring.
	 * @Date 2018-9-3 11:38:33
	 */
    public String includeEvent( String includeResourcePath, String currentResourcePath, String directiveName );



    /**
	 * Defines the execution strategy for includeEvent.
	 *
	 * @author mqfdy
	 */
    static class IncludeEventExecutor implements EventHandlerMethodExecutor
    {
        
        /** The context. */
        private Context context;
        
        /** The include resource path. */
        private String includeResourcePath;
        
        /** The current resource path. */
        private String currentResourcePath;
        
        /** The directive name. */
        private String directiveName;
        
        /** The executed. */
        private boolean executed = false;
        
        /**
		 * Instantiates a new include event executor.
		 *
		 * @param context
		 *            the context
		 * @param includeResourcePath
		 *            the include resource path
		 * @param currentResourcePath
		 *            the current resource path
		 * @param directiveName
		 *            the directive name
		 */
        IncludeEventExecutor(
                Context context, 
                String includeResourcePath,
                String currentResourcePath,
                String directiveName)
        {
            this.context = context;
            this.includeResourcePath = includeResourcePath;
            this.currentResourcePath = currentResourcePath;
            this.directiveName = directiveName;
        }

        /**
		 * Call the method includeEvent()
		 * 
		 *
		 * @author mqfdy
		 * @param handler
		 *            call the appropriate method on this handler
		 * @Date 2018-9-3 11:38:33
		 */
        public void execute(EventHandler handler)
        {
            IncludeEventHandler eh = (IncludeEventHandler) handler;
            
            if (eh instanceof ContextAware)
                ((ContextAware) eh).setContext(context);

            executed = true;
            includeResourcePath = ((IncludeEventHandler) handler)
                .includeEvent(includeResourcePath, currentResourcePath, directiveName); 
        }

        /**
         * @see org.apache.velocity.app.event.EventHandlerMethodExecutor#getReturnValue()
         * @return IncludeEventExecutor
         */
        public Object getReturnValue()
        {
            return includeResourcePath;
        }

        /**
         * @see org.apache.velocity.app.event.EventHandlerMethodExecutor#isDone()
         * @return IncludeEventExecutor
         */
        public boolean isDone()
        {
            return executed && (includeResourcePath == null);
        }        
        
        
    }

}
