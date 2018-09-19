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
 *  Event handler called when the RHS of #set is null.  Lets an app approve / veto
 *  writing a log message based on the specific reference.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: NullSetEventHandler.java 685685 2008-08-13 21:43:27Z nbubna $
 */
public interface NullSetEventHandler extends EventHandler
{
    
    /**
	 * Called when the RHS of a #set() is null, which will result in a null LHS.
	 * All NullSetEventHandlers are called in sequence until a false is
	 * returned. If no NullSetEventHandler is registered all nulls will be
	 * logged.
	 *
	 * @author mqfdy
	 * @param lhs
	 *            reference literal of left-hand-side of set statement
	 * @param rhs
	 *            reference literal of right-hand-side of set statement
	 * @return true if log message should be written, false otherwise
	 * @Date 2018-9-3 11:38:28
	 */
    public boolean shouldLogOnNullSet( String lhs, String rhs );

    /**
	 * Defines the execution strategy for shouldLogOnNullSet.
	 *
	 * @since 1.5
	 */
    static class ShouldLogOnNullSetExecutor implements EventHandlerMethodExecutor
    {
        
        /** The context. */
        private Context context;
        
        /** The lhs. */
        private String lhs;
        
        /** The rhs. */
        private String rhs;

        /** when this is false, quit iterating. */
        private boolean result = true;
        
        /** The executed. */
        private boolean executed = false;
        
        /**
		 * Instantiates a new should log on null set executor.
		 *
		 * @param context
		 *            the context
		 * @param lhs
		 *            the lhs
		 * @param rhs
		 *            the rhs
		 */
        ShouldLogOnNullSetExecutor(
                Context context, 
                String lhs, 
                String rhs) 
        {
            this.context = context;
            this.lhs = lhs;
            this.rhs = rhs;
        }

        /**
		 * Call the method shouldLogOnNullSet()
		 * 
		 *
		 * @author mqfdy
		 * @param handler
		 *            call the appropriate method on this handler
		 * @Date 2018-9-3 11:38:28
		 */
        public void execute(EventHandler handler)
        {
            NullSetEventHandler eh = (NullSetEventHandler) handler;
            
            if (eh instanceof ContextAware)
                ((ContextAware) eh).setContext(context);

            executed = true;
            result = ((NullSetEventHandler) handler).shouldLogOnNullSet(lhs, rhs); 
        }

        /**
         * @see org.apache.velocity.app.event.EventHandlerMethodExecutor#getReturnValue()
         * @return ShouldLogOnNullSetExecutor
         */
        public Object getReturnValue()
        {            
            // return new Boolean(result);
            return result ? Boolean.TRUE : Boolean.FALSE;
        }

        /**
         * @see org.apache.velocity.app.event.EventHandlerMethodExecutor#isDone()
         * @return ShouldLogOnNullSetExecutor
         */
        public boolean isDone()
        {
            return executed && !result;
        }        
        
        
    }

}
