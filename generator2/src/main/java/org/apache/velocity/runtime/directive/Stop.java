package org.apache.velocity.runtime.directive;

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

import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.Node;

// TODO: Auto-generated Javadoc
/**
 * This class implements the #stop directive which allows a user to stop the
 * merging and rendering process. The #stop directive will accept a single
 * message argument with info about the reason for stopping.
 *
 * @author mqfdy
 */
public class Stop extends Directive
{  
    
    /** The Constant STOP_ALL. */
    private static final StopCommand STOP_ALL = new StopCommand("StopCommand to exit merging");

    /** The has message. */
    private boolean hasMessage = false;

    /**
	 * Return name of this directive.
	 *
	 * @author mqfdy
	 * @return The name of this directive.
	 * @Date 2018-9-3 11:38:27
	 */
    public String getName()
    {
        return "stop";
    }

    /**
	 * Return type of this directive.
	 *
	 * @author mqfdy
	 * @return The type of this directive.
	 * @Date 2018-9-3 11:38:27
	 */
    public int getType()
    {
        return LINE;
    }

    /**
	 * Since there is no processing of content, there is never a need for an
	 * internal scope.
	 *
	 * @author mqfdy
	 * @return true, if is scope provided
	 * @Date 2018-9-3 11:38:27
	 */
    public boolean isScopeProvided()
    {
        return false;
    }

    /**
     * @see org.apache.velocity.runtime.directive.Directive#init(org.apache.velocity.runtime.RuntimeServices, org.apache.velocity.context.InternalContextAdapter, org.apache.velocity.runtime.parser.node.Node)
     * @param rs
     * @param context
     * @param node Stop
     */
    public void init(RuntimeServices rs, InternalContextAdapter context, Node node)
    {
        super.init(rs, context, node);

        int kids = node.jjtGetNumChildren();
        if (kids > 1)
        {  
            throw new VelocityException("The #stop directive only accepts a single message parameter at "
                 + Log.formatFileString(this));
        }
        else
        {
            hasMessage = (kids == 1);
        }
    }

    /**
     * @see org.apache.velocity.runtime.directive.Directive#render(org.apache.velocity.context.InternalContextAdapter, java.io.Writer, org.apache.velocity.runtime.parser.node.Node)
     * @param context
     * @param writer
     * @param node
     * @return Stop
     */
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
    {
        if (!hasMessage)
        {
            throw STOP_ALL;
        }

        Object argument = node.jjtGetChild(0).value(context);

        // stop all and use specified message
        throw new StopCommand(String.valueOf(argument));
    }

}

