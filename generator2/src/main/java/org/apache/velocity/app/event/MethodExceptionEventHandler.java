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
 *  Event handler called when a method throws an exception.  This gives the
 *  application a chance to deal with it and either
 *  return something nice, or throw.
 *
 *  Please return what you want rendered into the output stream.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: MethodExceptionEventHandler.java 685685 2008-08-13 21:43:27Z nbubna $
 */
public interface MethodExceptionEventHandler extends EventHandler
{
    
    /**
	 * Called when a method throws an exception. Only the first registered
	 * MethodExceptionEventHandler is called. If none are registered a
	 * MethodInvocationException is thrown.
	 *
	 * @author mqfdy
	 * @param claz
	 *            the class of the object the method is being applied to
	 * @param method
	 *            the method
	 * @param e
	 *            the thrown exception
	 * @return an object to insert in the page
	 * @throws Exception
	 *             an exception to be thrown instead inserting an object
	 * @Date 2018-9-3 11:38:31
	 */
    public Object methodException( Class claz, String method, Exception e )
         throws Exception;

    /**
	 * Defines the execution strategy for methodException.
	 *
	 * @since 1.5
	 */
    static class MethodExceptionExecutor implements EventHandlerMethodExecutor
    {
        
        /** The context. */
        private Context context;
        
        /** The claz. */
        private Class claz;
        
        /** The method. */
        private String method;
        
        /** The e. */
        private Exception e;
        
        /** The result. */
        private Object result;
        
        /** The executed. */
        private boolean executed = false;
    
        /**
		 * Instantiates a new method exception executor.
		 *
		 * @param context
		 *            the context
		 * @param claz
		 *            the claz
		 * @param method
		 *            the method
		 * @param e
		 *            the e
		 */
        MethodExceptionExecutor(
                Context context, 
                Class claz,
                String method,
                Exception e)
        {
            this.context = context;
            this.claz = claz;
            this.method = method;
            this.e = e;
        }

        /**
		 * Call the method methodException().
		 *
		 * @author mqfdy
		 * @param handler
		 *            call the appropriate method on this handler
		 * @exception Exception
		 *                generic exception thrown by methodException event
		 *                handler method call
		 * @Date 2018-9-3 11:38:31
		 */
        public void execute(EventHandler handler) throws Exception
        {
            MethodExceptionEventHandler eh = (MethodExceptionEventHandler) handler;
            
            if (eh instanceof ContextAware)
                ((ContextAware) eh).setContext(context);

            executed = true;
            result = ((MethodExceptionEventHandler) handler).methodException(claz, method, e);
        }

        /**
		 * Gets the return value.
		 *
		 * @return MethodExceptionExecutor
		 * @see org.apache.velocity.app.event.EventHandlerMethodExecutor#getReturnValue()
		 */
        public Object getReturnValue()
        {
            return result;
        }

        /**
		 * Only run the first MethodExceptionEventHandler.
		 *
		 * @author mqfdy
		 * @return true after this is executed once.
		 * @Date 2018-9-3 11:38:31
		 */
        public boolean isDone()
        {
           return executed;
        }        
        
        
    }

}
