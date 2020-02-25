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
import org.apache.commons.lang.text.StrBuilder;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.Parser;
import org.apache.velocity.runtime.parser.Token;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleNode.
 *
 * @author mqfdy
 */
public class SimpleNode implements Node
{
    
    /** The rsvc. */
    protected RuntimeServices rsvc = null;

    /** The log. */
    protected Log log = null;

    /** The parent. */
    protected Node parent;

    /** The children. */
    protected Node[] children;

    /** The id. */
    protected int id;

    /** The parser. */
    // TODO - It seems that this field is only valid when parsing, and should not be kept around.    
    protected Parser parser;

    /** The info. */
    protected int info; // added

    /** The state. */
    public boolean state;

    /** The invalid. */
    protected boolean invalid = false;

    /** The first. */
    protected Token first;

    /** The last. */
    protected Token last;
    
    
    /** The template name. */
    protected String templateName;

    
    /**
	 * Gets the runtime services.
	 *
	 * @author mqfdy
	 * @return the runtime services
	 * @Date 2018-09-03 09:00
	 */
    public RuntimeServices getRuntimeServices()
    {
      return rsvc;
    }
    
    /**
	 * Instantiates a new simple node.
	 *
	 * @param i
	 *            the i
	 */
    public SimpleNode(int i)
    {
        id = i;
    }

    /**
	 * Instantiates a new simple node.
	 *
	 * @param p
	 *            the p
	 * @param i
	 *            the i
	 */
    public SimpleNode(Parser p, int i)
    {
        this(i);
        parser = p;
        templateName = parser.currentTemplateName;
    }

    /**
	 * Jjt open.
	 *
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtOpen()
	 */
    public void jjtOpen()
    {
        first = parser.getToken(1); // added
    }

    /**
	 * Jjt close.
	 *
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtClose()
	 */
    public void jjtClose()
    {
        last = parser.getToken(0); // added
    }

    /**
	 * Sets the first token.
	 *
	 * @author mqfdy
	 * @param t
	 *            the new first token
	 * @Date 2018-09-03 09:00
	 */
    public void setFirstToken(Token t)
    {
        this.first = t;
    }

    /**
	 * Gets the first token.
	 *
	 * @return the first token
	 * @see org.apache.velocity.runtime.parser.node.Node#getFirstToken()
	 */
    public Token getFirstToken()
    {
        return first;
    }

    /**
	 * Gets the last token.
	 *
	 * @return the last token
	 * @see org.apache.velocity.runtime.parser.node.Node#getLastToken()
	 */
    public Token getLastToken()
    {
        return last;
    }

    /**
	 * Jjt set parent.
	 *
	 * @param n
	 *            the n
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtSetParent(org.apache.velocity.runtime.parser.node.Node)
	 */
    public void jjtSetParent(Node n)
    {
        parent = n;
    }

    /**
	 * Jjt get parent.
	 *
	 * @return the node
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtGetParent()
	 */
    public Node jjtGetParent()
    {
        return parent;
    }

    /**
	 * Jjt add child.
	 *
	 * @param n
	 *            the n
	 * @param i
	 *            the i
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtAddChild(org.apache.velocity.runtime.parser.node.Node,
	 *      int)
	 */
    public void jjtAddChild(Node n, int i)
    {
        if (children == null)
        {
            children = new Node[i + 1];
        }
        else if (i >= children.length)
        {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    /**
	 * Jjt get child.
	 *
	 * @param i
	 *            the i
	 * @return the node
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtGetChild(int)
	 */
    public Node jjtGetChild(int i)
    {
        return children[i];
    }

    /**
	 * Jjt get num children.
	 *
	 * @return the int
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtGetNumChildren()
	 */
    public int jjtGetNumChildren()
    {
        return (children == null) ? 0 : children.length;
    }


    /**
	 * Jjt accept.
	 *
	 * @param visitor
	 *            the visitor
	 * @param data
	 *            the data
	 * @return the object
	 * @see org.apache.velocity.runtime.parser.node.Node#jjtAccept(org.apache.velocity.runtime.parser.node.ParserVisitor,
	 *      java.lang.Object)
	 */
    public Object jjtAccept(ParserVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }


    /**
	 * Children accept.
	 *
	 * @param visitor
	 *            the visitor
	 * @param data
	 *            the data
	 * @return the object
	 * @see org.apache.velocity.runtime.parser.node.Node#childrenAccept(org.apache.velocity.runtime.parser.node.ParserVisitor,
	 *      java.lang.Object)
	 */
    public Object childrenAccept(ParserVisitor visitor, Object data)
    {
        if (children != null)
        {
            for (int i = 0; i < children.length; ++i)
            {
                children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    /* You can override these two methods in subclasses of SimpleNode to
        customize the way the node appears when the tree is dumped.  If
        your output uses more than one line you should override
        toString(String), otherwise overriding toString() is probably all
        you need to do. */

    //    public String toString()
    // {
    //    return ParserTreeConstants.jjtNodeName[id];
    // }
    /**
	 * To string.
	 *
	 * @author mqfdy
	 * @param prefix
	 *            the prefix
	 * @return String representation of this node.
	 * @Date 2018-09-03 09:00
	 */
    public String toString(String prefix)
    {
        return prefix + toString();
    }

    /**
	 * Override this method if you want to customize how the node dumps out its
	 * children.
	 *
	 * @author mqfdy
	 * @param prefix
	 *            the prefix
	 * @Date 2018-09-03 09:00
	 */
    public void dump(String prefix)
    {
        System.out.println(toString(prefix));
        if (children != null)
        {
            for (int i = 0; i < children.length; ++i)
            {
                SimpleNode n = (SimpleNode) children[i];
                if (n != null)
                {
                    n.dump(prefix + " ");
                }
            }
        }
    }

    /**
	 * Return a string that tells the current location of this node.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @return the location
	 * @Date 2018-09-03 09:00
	 */
    protected String getLocation(InternalContextAdapter context)
    {
        return Log.formatFileString(this);
    }

    // All additional methods

    /**
	 * Literal.
	 *
	 * @return the string
	 * @see org.apache.velocity.runtime.parser.node.Node#literal()
	 */
    public String literal()
    {
        // if we have only one string, just return it and avoid
        // buffer allocation. VELOCITY-606
        if (first == last)
        {
            return NodeUtils.tokenLiteral(first);
        }

        Token t = first;
        StrBuilder sb = new StrBuilder(NodeUtils.tokenLiteral(t));
        while (t != last)
        {
            t = t.next;
            sb.append(NodeUtils.tokenLiteral(t));
        }
        return sb.toString();
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
	 * @see org.apache.velocity.runtime.parser.node.Node#init(org.apache.velocity.context.InternalContextAdapter,
	 *      java.lang.Object)
	 */
    public Object init( InternalContextAdapter context, Object data) throws TemplateInitException
    {
        /*
         * hold onto the RuntimeServices
         */

        rsvc = (RuntimeServices) data;
        log = rsvc.getLog();

        int i, k = jjtGetNumChildren();

        for (i = 0; i < k; i++)
        {
            jjtGetChild(i).init( context, data);
        }

        return data;
    }

    /**
	 * Evaluate.
	 *
	 * @param context
	 *            the context
	 * @return true, if successful
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @see org.apache.velocity.runtime.parser.node.Node#evaluate(org.apache.velocity.context.InternalContextAdapter)
	 */
    public boolean evaluate( InternalContextAdapter  context)
        throws MethodInvocationException
    {
        return false;
    }

    /**
	 * Value.
	 *
	 * @param context
	 *            the context
	 * @return the object
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @see org.apache.velocity.runtime.parser.node.Node#value(org.apache.velocity.context.InternalContextAdapter)
	 */
    public Object value( InternalContextAdapter context)
        throws MethodInvocationException
    {
        return null;
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
	 * @throws ParseErrorException
	 *             the parse error exception
	 * @throws ResourceNotFoundException
	 *             the resource not found exception
	 * @see org.apache.velocity.runtime.parser.node.Node#render(org.apache.velocity.context.InternalContextAdapter,
	 *      java.io.Writer)
	 */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException, MethodInvocationException, ParseErrorException, ResourceNotFoundException
    {
        int i, k = jjtGetNumChildren();

        for (i = 0; i < k; i++)
            jjtGetChild(i).render(context, writer);

        return true;
    }

    /**
	 * Execute.
	 *
	 * @param o
	 *            the o
	 * @param context
	 *            the context
	 * @return the object
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @see org.apache.velocity.runtime.parser.node.Node#execute(java.lang.Object,
	 *      org.apache.velocity.context.InternalContextAdapter)
	 */
    public Object execute(Object o, InternalContextAdapter context)
      throws MethodInvocationException
    {
        return null;
    }

    /**
	 * Gets the type.
	 *
	 * @return the type
	 * @see org.apache.velocity.runtime.parser.node.Node#getType()
	 */
    public int getType()
    {
        return id;
    }

    /**
	 * Sets the info.
	 *
	 * @param info
	 *            the new info
	 * @see org.apache.velocity.runtime.parser.node.Node#setInfo(int)
	 */
    public void setInfo(int info)
    {
        this.info = info;
    }

    /**
	 * Gets the info.
	 *
	 * @return the info
	 * @see org.apache.velocity.runtime.parser.node.Node#getInfo()
	 */
    public int getInfo()
    {
        return info;
    }

    /**
	 * Sets the invalid.
	 *
	 * @see org.apache.velocity.runtime.parser.node.Node#setInvalid()
	 */
    public void setInvalid()
    {
        invalid = true;
    }

    /**
	 * Checks if is invalid.
	 *
	 * @return true, if is invalid
	 * @see org.apache.velocity.runtime.parser.node.Node#isInvalid()
	 */
    public boolean isInvalid()
    {
        return invalid;
    }

    /**
	 * Gets the line.
	 *
	 * @return the line
	 * @see org.apache.velocity.runtime.parser.node.Node#getLine()
	 */
    public int getLine()
    {
        return first.beginLine;
    }

    /**
	 * Gets the column.
	 *
	 * @return the column
	 * @see org.apache.velocity.runtime.parser.node.Node#getColumn()
	 */
    public int getColumn()
    {
        return first.beginColumn;
    }
    
    /**
	 * To string.
	 *
	 * @return the string
	 * @since 1.5
	 */
    public String toString()
    {
        StrBuilder tokens = new StrBuilder();
        
        for (Token t = getFirstToken(); t != null; )
        {
            tokens.append("[").append(t.image).append("]");
            if (t.next != null)
            {
                if (t.equals(getLastToken()))
                {
                    break;
                }
                else
                {
                    tokens.append(", ");
                }
            }
            t = t.next;
        }

        return new ToStringBuilder(this)
            .append("id", getType())
            .append("info", getInfo())
            .append("invalid", isInvalid())
            .append("children", jjtGetNumChildren())
            .append("tokens", tokens)
            .toString();
    }

    /**
	 * Gets the template name.
	 *
	 * @return SimpleNode
	 * @see org.apache.velocity.runtime.parser.node.Node#getTemplateName()
	 */
    public String getTemplateName()
    {
      return templateName;
    }
}

