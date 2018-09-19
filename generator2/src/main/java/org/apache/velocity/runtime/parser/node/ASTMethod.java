package org.apache.velocity.runtime.parser.node;

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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.event.EventHandlerUtil;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.directive.StopCommand;
import org.apache.velocity.runtime.parser.Parser;
import org.apache.velocity.util.ClassUtils;
import org.apache.velocity.util.introspection.Info;
import org.apache.velocity.util.introspection.VelMethod;

// TODO: Auto-generated Javadoc
/**
 *  ASTMethod.java
 *
 *  Method support for references :  $foo.method()
 *
 *  NOTE :
 *
 *  introspection is now done at render time.
 *
 *  Please look at the Parser.jjt file which is
 *  what controls the generation of this class.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: ASTMethod.java 898032 2010-01-11 19:51:03Z nbubna $
 */
public class ASTMethod extends SimpleNode
{
    
    /** The method name. */
    private String methodName = "";
    
    /** The param count. */
    private int paramCount = 0;

    /** The uber info. */
    protected Info uberInfo;

    /**
     * Indicates if we are running in strict reference mode.
     */
    protected boolean strictRef = false;

    /**
	 * Instantiates a new AST method.
	 *
	 * @param id
	 *            the id
	 */
    public ASTMethod(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST method.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTMethod(Parser p, int id)
    {
        super(p, id);
    }

    /**
	 * Jjt accept.
	 *
	 * @param visitor
	 *            the visitor
	 * @param data
	 *            the data
	 * @return the object
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#jjtAccept(org.apache.velocity.runtime.parser.node.ParserVisitor,
	 *      java.lang.Object)
	 */
    public Object jjtAccept(ParserVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    /**
	 * simple init - init our subtree and get what we can from the AST.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param data
	 *            the data
	 * @return The init result
	 * @throws TemplateInitException
	 *             the template init exception
	 * @Date 2018-9-3 11:38:24
	 */
    public Object init(  InternalContextAdapter context, Object data)
        throws TemplateInitException
    {
        super.init(  context, data );

        /*
         * make an uberinfo - saves new's later on
         */

        uberInfo = new Info(getTemplateName(),
                getLine(),getColumn());
        /*
         *  this is about all we can do
         */

        methodName = getFirstToken().image;
        paramCount = jjtGetNumChildren() - 1;

        strictRef = rsvc.getBoolean(RuntimeConstants.RUNTIME_REFERENCES_STRICT, false);
        
        return data;
    }

    /**
	 * invokes the method. Returns null if a problem, the actual return if the
	 * method returns something, or an empty string "" if the method returns
	 * void
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @param context
	 *            the context
	 * @return Result or null.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @Date 2018-9-3 11:38:24
	 */
    public Object execute(Object o, InternalContextAdapter context)
        throws MethodInvocationException
    {
        /*
         *  new strategy (strategery!) for introspection. Since we want
         *  to be thread- as well as context-safe, we *must* do it now,
         *  at execution time.  There can be no in-node caching,
         *  but if we are careful, we can do it in the context.
         */
        Object [] params = new Object[paramCount];

          /*
           * sadly, we do need recalc the values of the args, as this can
           * change from visit to visit
           */
        final Class[] paramClasses =       
            paramCount > 0 ? new Class[paramCount] : ArrayUtils.EMPTY_CLASS_ARRAY;

        for (int j = 0; j < paramCount; j++)
        {
            params[j] = jjtGetChild(j + 1).value(context);
            if (params[j] != null)
            {
                paramClasses[j] = params[j].getClass();
            }
        }
            
        VelMethod method = ClassUtils.getMethod(methodName, params, paramClasses, 
            o, context, this, strictRef);
        if (method == null) return null;

        try
        {
            /*
             *  get the returned object.  It may be null, and that is
             *  valid for something declared with a void return type.
             *  Since the caller is expecting something to be returned,
             *  as long as things are peachy, we can return an empty
             *  String so ASTReference() correctly figures out that
             *  all is well.
             */

            Object obj = method.invoke(o, params);

            if (obj == null)
            {
                if( method.getReturnType() == Void.TYPE)
                {
                    return "";
                }
            }

            return obj;
        }
        catch( InvocationTargetException ite )
        {
            return handleInvocationException(o, context, ite.getTargetException());
        }
        
        /** Can also be thrown by method invocation **/
        catch( IllegalArgumentException t )
        {
            return handleInvocationException(o, context, t);
        }
        
        /**
         * pass through application level runtime exceptions
         */
        catch( RuntimeException e )
        {
            throw e;
        }
        catch( Exception e )
        {
            String msg = "ASTMethod.execute() : exception invoking method '"
                         + methodName + "' in " + o.getClass();
            log.error(msg, e);
            throw new VelocityException(msg, e);
        }
    }

    /**
	 * Handle invocation exception.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @param context
	 *            the context
	 * @param t
	 *            the t
	 * @return the object
	 * @Date 2018-9-3 11:38:24
	 */
    private Object handleInvocationException(Object o, InternalContextAdapter context, Throwable t)
    {
        /*
         * We let StopCommands go up to the directive they are for/from
         */
        if (t instanceof StopCommand)
        {
            throw (StopCommand)t;
        }

        /*
         *  In the event that the invocation of the method
         *  itself throws an exception, we want to catch that
         *  wrap it, and throw.  We don't log here as we want to figure
         *  out which reference threw the exception, so do that
         *  above
         */
        else if (t instanceof Exception)
        {
            try
            {
                return EventHandlerUtil.methodException( rsvc, context, o.getClass(), methodName, (Exception) t );
            }

            /**
             * If the event handler throws an exception, then wrap it
             * in a MethodInvocationException.  Don't pass through RuntimeExceptions like other
             * similar catchall code blocks.
             */
            catch( Exception e )
            {
                throw new MethodInvocationException(
                    "Invocation of method '"
                    + methodName + "' in  " + o.getClass()
                    + " threw exception "
                    + e.toString(),
                    e, methodName, getTemplateName(), this.getLine(), this.getColumn());
            }
        }

        /*
         *  let non-Exception Throwables go...
         */
        else
        {
            /*
             * no event cartridge to override. Just throw
             */

            throw new MethodInvocationException(
            "Invocation of method '"
            + methodName + "' in  " + o.getClass()
            + " threw exception "
            + t.toString(),
            t, methodName, getTemplateName(), this.getLine(), this.getColumn());
        }
    }

    /**
     * Internal class used as key for method cache.  Combines
     * ASTMethod fields with array of parameter classes.  Has
     * public access (and complete constructor) for unit test 
     * purposes.
     * @since 1.5
     */
    public static class MethodCacheKey
    {
        
        /** The method name. */
        private final String methodName;  
        
        /** The params. */
        private final Class[] params;

        /**
		 * Instantiates a new method cache key.
		 *
		 * @param methodName
		 *            the method name
		 * @param params
		 *            the params
		 */
        public MethodCacheKey(String methodName, Class[] params)
        {
            /** 
             * Should never be initialized with nulls, but to be safe we refuse 
             * to accept them.
             */
            this.methodName = (methodName != null) ? methodName : StringUtils.EMPTY;
            this.params = (params != null) ? params : ArrayUtils.EMPTY_CLASS_ARRAY;
        }

        /**
		 * Equals.
		 *
		 * @param o
		 *            the o
		 * @return true, if successful
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
        public boolean equals(Object o)
        {
            /** 
             * note we skip the null test for methodName and params
             * due to the earlier test in the constructor
             */            
            if (o instanceof MethodCacheKey) 
            {
                final MethodCacheKey other = (MethodCacheKey) o;                
                if (params.length == other.params.length && 
                        methodName.equals(other.methodName)) 
                {              
                    for (int i = 0; i < params.length; ++i) 
                    {
                        if (params[i] == null)
                        {
                            if (params[i] != other.params[i])
                            {
                                return false;
                            }
                        }
                        else if (!params[i].equals(other.params[i]))
                        {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        

        /**
		 * Hash code.
		 *
		 * @return the int
		 * @see java.lang.Object#hashCode()
		 */
        public int hashCode()
        {
            int result = 17;

            /** 
             * note we skip the null test for methodName and params
             * due to the earlier test in the constructor
             */            
            for (int i = 0; i < params.length; ++i)
            {
                final Class param = params[i];
                if (param != null)
                {
                    result = result * 37 + param.hashCode();
                }
            }
            
            result = result * 37 + methodName.hashCode();

            return result;
        } 
    }

    /**
	 * Gets the method name.
	 *
	 * @return Returns the methodName.
	 * @since 1.5
	 */
    public String getMethodName()
    {
        return methodName;
    }
    
    
}
