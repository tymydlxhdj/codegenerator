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

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.app.event.EventHandlerUtil;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.Parser;
import org.apache.velocity.util.introspection.Info;

// TODO: Auto-generated Javadoc
/**
 * Node for the #set directive.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: ASTSetDirective.java 720228 2008-11-24 16:58:33Z nbubna $
 */
public class ASTSetDirective extends SimpleNode
{
    
    /** The left reference. */
    private String leftReference = "";
    
    /** The right. */
    private Node right = null;
    
    /** The left. */
    private ASTReference left = null;
    
    /** The log on null. */
    boolean logOnNull = false;
    
    /** The allow null. */
    private boolean allowNull = false;
    
    /** The is initialized. */
    private boolean isInitialized;

    /** This is really immutable after the init, so keep one for this node. */
    protected Info uberInfo;

    /**
     * Indicates if we are running in strict reference mode.
     */
    protected boolean strictRef = false;

    /**
	 * Instantiates a new AST set directive.
	 *
	 * @param id
	 *            the id
	 */
    public ASTSetDirective(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST set directive.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTSetDirective(Parser p, int id)
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
	 * simple init. We can get the RHS and LHS as the the tree structure is
	 * static
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param data
	 *            the data
	 * @return Init result.
	 * @throws TemplateInitException
	 *             the template init exception
	 * @Date 2018-9-3 11:38:35
	 */
    public synchronized Object init(InternalContextAdapter context, Object data)
    throws TemplateInitException
    {
        /** This method is synchronized to prevent double initialization or initialization while rendering **/

        if (!isInitialized)
        {
            /*
             *  init the tree correctly
             */
    
            super.init( context, data );
    
            uberInfo = new Info(getTemplateName(),
                    getLine(), getColumn());
    
            right = getRightHandSide();
            left = getLeftHandSide();
    
            logOnNull = rsvc.getBoolean(RuntimeConstants.RUNTIME_LOG_REFERENCE_LOG_INVALID, true);
            allowNull = rsvc.getBoolean(RuntimeConstants.SET_NULL_ALLOWED, false);
            strictRef = rsvc.getBoolean(RuntimeConstants.RUNTIME_REFERENCES_STRICT, false);
            if (strictRef) allowNull = true;  // strictRef implies allowNull
            
            /*
             *  grab this now.  No need to redo each time
             */
            leftReference = left.getFirstToken().image.substring(1);
        
            isInitialized = true;
        }
            
        return data;
    }

    /**
	 * puts the value of the RHS into the context under the key of the LHS.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param writer
	 *            the writer
	 * @return True if rendering was sucessful.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @Date 2018-9-3 11:38:35
	 */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException, MethodInvocationException
    {
        /*
         *  get the RHS node, and its value
         */

        Object value = right.value(context);

        /*
         * it's an error if we don't have a value of some sort AND
         * it is not allowed by configuration
         */

        if( !allowNull )
        {
            if ( value == null )
            {                
                /*
                 *  first, are we supposed to say anything anyway?
                 */
                if(logOnNull)
                {
                    boolean doit = EventHandlerUtil.shouldLogOnNullSet( rsvc, context, left.literal(), right.literal() );

                    if (doit && rsvc.getLog().isDebugEnabled())
                    {
                        rsvc.getLog().debug("RHS of #set statement is null. Context will not be modified. "
                                      + Log.formatFileString(this));
                    }
                }
                
                String rightReference = null;
                if (right instanceof ASTExpression)
                {
                    rightReference = ((ASTExpression) right).getLastToken().image;
                }
                EventHandlerUtil.invalidSetMethod(rsvc, context, leftReference, rightReference, uberInfo);
                
                return false;
            }
        }

        if ( value == null && !strictRef)
        {
            String rightReference = null;
            if (right instanceof ASTExpression)
            {
                rightReference = ((ASTExpression) right).getLastToken().image;
            }
            EventHandlerUtil.invalidSetMethod(rsvc, context, leftReference, rightReference, uberInfo);

            /*
             * if RHS is null, remove simple LHS from context
             * or call setValue() with a null value for complex LHS
             */
            if (left.jjtGetNumChildren() == 0)
            {
                context.remove( leftReference );
            }
            else
            {
                left.setValue(context, null);
            }

            return false;

        }
        else
        {
            /*
             *  if the LHS is simple, just punch the value into the context
             *  otherwise, use the setValue() method do to it.
             *  Maybe we should always use setValue()
             */

            if (left.jjtGetNumChildren() == 0)
            {
                context.put( leftReference, value);
            }
            else
            {
                left.setValue(context, value);
            }
        }

        return true;
    }

    /**
	 * returns the ASTReference that is the LHS of the set statememt
	 * 
	 *
	 * @author mqfdy
	 * @return left hand side of #set statement
	 * @Date 2018-9-3 11:38:35
	 */
    private ASTReference getLeftHandSide()
    {
        return (ASTReference) jjtGetChild(0);
    }

    /**
	 * returns the RHS Node of the set statement
	 * 
	 *
	 * @author mqfdy
	 * @return right hand side of #set statement
	 * @Date 2018-9-3 11:38:35
	 */
    private Node getRightHandSide()
    {
        return jjtGetChild(1);
    }
}
