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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.directive.RuntimeMacro;
import org.apache.velocity.runtime.directive.BlockMacro;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.Parser;
import org.apache.velocity.util.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * This class is responsible for handling the pluggable
 * directives in VTL.
 *
 * For example :  #foreach()
 *
 * Please look at the Parser.jjt file which is
 * what controls the generation of this class.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @author <a href="mailto:kav@kav.dk">Kasper Nielsen</a>
 * @version $Id: ASTDirective.java 736677 2009-01-22 15:39:02Z nbubna $
 */
public class ASTDirective extends SimpleNode
{
    
    /** The directive. */
    private Directive directive = null;
    
    /** The directive name. */
    private String directiveName = "";
    
    /** The is directive. */
    private boolean isDirective;
    
    /** The is initialized. */
    private boolean isInitialized;

    /**
	 * Instantiates a new AST directive.
	 *
	 * @param id
	 *            the id
	 */
    public ASTDirective(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST directive.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTDirective(Parser p, int id)
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
	 * Inits the.
	 *
	 * @param context
	 *            the context
	 * @param data
	 *            the data
	 * @return the object
	 * @throws TemplateInitException
	 *             the template init exception
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#init(org.apache.velocity.context.InternalContextAdapter,
	 *      java.lang.Object)
	 */
    public synchronized Object init( InternalContextAdapter context, Object data)
    throws TemplateInitException
    {
        /** method is synchronized to avoid concurrent directive initialization **/
        
        if (!isInitialized)
        {
            super.init( context, data );

            /*
             *  only do things that are not context dependent
             */
    
            if (parser.isDirective( directiveName ))
            {
                isDirective = true;
    
                try
                {
                    directive = (Directive) parser.getDirective( directiveName )
                        .getClass().newInstance();
                } 
                catch (InstantiationException e)
                {
                    throw ExceptionUtils.createRuntimeException("Couldn't initialize " +
                            "directive of class " +
                            parser.getDirective(directiveName).getClass().getName(),
                            e);
                }
                catch (IllegalAccessException e)
                {
                    throw ExceptionUtils.createRuntimeException("Couldn't initialize " +
                            "directive of class " +
                            parser.getDirective(directiveName).getClass().getName(),
                            e);
                }
                        
                directive.setLocation(getLine(), getColumn(), getTemplateName());
                directive.init(rsvc, context,this);
            }
            else if( directiveName.startsWith("@") )
            {
                if( this.jjtGetNumChildren() > 0 )
                {
                    // block macro call (normal macro call but has AST body)
                    directiveName = directiveName.substring(1);

                    directive = new BlockMacro(directiveName);
                    directive.setLocation(getLine(), getColumn(), getTemplateName());

                    try
                    {
                        directive.init( rsvc, context, this );
                    }
                    catch (TemplateInitException die)
                    {
                        throw new TemplateInitException(die.getMessage(),
                            (ParseException) die.getWrappedThrowable(),
                            die.getTemplateName(),
                            die.getColumnNumber() + getColumn(),
                            die.getLineNumber() + getLine());
                    }
                    isDirective = true;
                }
                else
                {
                    // this is a fake block macro call without a body. e.g. #@foo
                    // just render as it is
                    isDirective = false;
                }
            }
            else
            {
                /**
                 * Create a new RuntimeMacro
                 */
                directive = new RuntimeMacro(directiveName);
                directive.setLocation(getLine(), getColumn(), getTemplateName());
        
                /**
                 * Initialize it
                 */
                try
                {
                    directive.init( rsvc, context, this );
                }
    
                /**
                 * correct the line/column number if an exception is caught
                 */
                catch (TemplateInitException die)
                {
                    throw new TemplateInitException(die.getMessage(),
                            (ParseException) die.getWrappedThrowable(),
                            die.getTemplateName(),
                            die.getColumnNumber() + getColumn(),
                            die.getLineNumber() + getLine());
                }
                isDirective = true;
            }
            
            isInitialized = true;
        }
           
        return data;
    }

    /**
	 * Render.
	 *
	 * @param context
	 *            the context
	 * @param writer
	 *            the writer
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @throws ResourceNotFoundException
	 *             the resource not found exception
	 * @throws ParseErrorException
	 *             the parse error exception
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#render(org.apache.velocity.context.InternalContextAdapter,
	 *      java.io.Writer)
	 */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException,MethodInvocationException, ResourceNotFoundException, ParseErrorException
    {
        /*
         *  normal processing
         */

        if (isDirective)
        {
            directive.render(context, writer, this);
        }
        else
        {
            writer.write( "#");
            writer.write( directiveName );
        }

        return true;
    }

    /**
	 * Sets the directive name. Used by the parser. This keeps us from having to
	 * dig it out of the token stream and gives the parse the change to
	 * override.
	 *
	 * @author mqfdy
	 * @param str
	 *            the new directive name
	 * @Date 2018-09-03 09:00
	 */
    public void setDirectiveName( String str )
    {
        directiveName = str;
    }

    /**
	 * Gets the name of this directive.
	 *
	 * @author mqfdy
	 * @return The name of this directive.
	 * @Date 2018-9-3 11:38:31
	 */
    public String getDirectiveName()
    {
        return directiveName;
    }
    
    /**
	 * To string.
	 *
	 * @return the string
	 * @since 1.5
	 */
    public String toString()
    {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("directiveName", getDirectiveName())
            .toString();
    }

}



