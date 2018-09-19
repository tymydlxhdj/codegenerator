package org.apache.velocity.io;

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

// TODO: Auto-generated Javadoc
/**
 * Implementation of a fast Writer. It was originally taken from JspWriter
 * and modified to have less syncronization going on.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author Anil K. Vijendran
 * @version $Id: VelocityWriter.java 463298 2006-10-12 16:10:32Z henning $
 */
public final class VelocityWriter extends Writer
{
    
    /** constant indicating that the Writer is not buffering output. */
    public static final int	NO_BUFFER = 0;

    /**
	 * constant indicating that the Writer is buffered and is using the
	 * implementation default buffer size.
	 */
    public static final int	DEFAULT_BUFFER = -1;

    /**
	 * constant indicating that the Writer is buffered and is unbounded; this is
	 * used in BodyContent.
	 */
    public static final int	UNBOUNDED_BUFFER = -2;

    /** The buffer size. */
    private int     bufferSize;
    
    /** The auto flush. */
    private boolean autoFlush;

    /** The writer. */
    private Writer writer;

    /** The cb. */
    private char cb[];
    
    /** The next char. */
    private int nextChar;

    /** The default char buffer size. */
    private static int defaultCharBufferSize = 8 * 1024;

    /**
	 * Create a buffered character-output stream that uses a default-sized
	 * output buffer.
	 *
	 * @param writer
	 *            the writer
	 */
    public VelocityWriter(Writer writer)
    {
        this(writer, defaultCharBufferSize, true);
    }

    /**
	 * private constructor.
	 *
	 * @param bufferSize
	 *            the buffer size
	 * @param autoFlush
	 *            the auto flush
	 */
    private VelocityWriter(int bufferSize, boolean autoFlush)
    {
        this.bufferSize = bufferSize;
        this.autoFlush  = autoFlush;
    }

    /**
	 * This method returns the size of the buffer used by the JspWriter.
	 *
	 * @author mqfdy
	 * @return the size of the buffer in bytes, or 0 is unbuffered.
	 * @Date 2018-9-3 11:38:37
	 */
    public int getBufferSize() { return bufferSize; }

    /**
	 * This method indicates whether the JspWriter is autoFlushing.
	 *
	 * @author mqfdy
	 * @return if this JspWriter is auto flushing or throwing IOExceptions on
	 *         buffer overflow conditions
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isAutoFlush() { return autoFlush; }

    /**
	 * Create a new buffered character-output stream that uses an output buffer
	 * of the given size.
	 *
	 * @param writer
	 *            the writer
	 * @param sz
	 *            the sz
	 * @param autoFlush
	 *            the auto flush
	 */
    public VelocityWriter(Writer writer, int sz, boolean autoFlush)
    {
        this(sz, autoFlush);
        if (sz < 0)
            throw new IllegalArgumentException("Buffer size <= 0");
        this.writer = writer;
        cb = sz == 0 ? null : new char[sz];
        nextChar = 0;
    }

    /**
	 * Flush the output buffer to the underlying character stream, without
	 * flushing the stream itself. This method is non-private only so that it
	 * may be invoked by PrintStream.
	 *
	 * @author mqfdy
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    private final void flushBuffer() throws IOException
    {
        if (bufferSize == 0)
            return;
        if (nextChar == 0)
            return;
        writer.write(cb, 0, nextChar);
        nextChar = 0;
    }

    /**
	 * Discard the output buffer.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:37
	 */
    public final void clear()
    {
        nextChar = 0;
    }

    /**
	 * Buffer overflow.
	 *
	 * @author mqfdy
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    private final void bufferOverflow() throws IOException
    {
        throw new IOException("overflow");
    }

    /**
	 * Flush the stream.
	 *
	 * @author mqfdy
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void flush()  throws IOException
    {
        flushBuffer();
        if (writer != null)
        {
            writer.flush();
        }
    }

    /**
	 * Close the stream.
	 *
	 * @author mqfdy
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void close() throws IOException {
        if (writer == null)
            return;
        flush();
    }

    /**
	 * Gets the remaining.
	 *
	 * @author mqfdy
	 * @return the number of bytes unused in the buffer
	 * @Date 2018-09-03 09:00
	 */
    public final int getRemaining()
    {
        return bufferSize - nextChar;
    }

    /**
	 * Write a single character.
	 *
	 * @author mqfdy
	 * @param c
	 *            the c
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void write(int c) throws IOException
    {
        if (bufferSize == 0)
        {
            writer.write(c);
        }
        else
        {
            if (nextChar >= bufferSize)
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
            cb[nextChar++] = (char) c;
        }
    }

    /**
	 * Our own little min method, to avoid loading <code>java.lang.Math</code>
	 * if we've run out of file descriptors and we're trying to print a stack
	 * trace.
	 *
	 * @author mqfdy
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @return the int
	 * @Date 2018-9-3 11:38:37
	 */
    private final int min(int a, int b)
    {
	    return (a < b ? a : b);
    }

    /**
	 * Write a portion of an array of characters.
	 * 
	 * <p>
	 * Ordinarily this method stores characters from the given array into this
	 * stream's buffer, flushing the buffer to the underlying stream as needed.
	 * If the requested length is at least as large as the buffer, however, then
	 * this method will flush the buffer and write the characters directly to
	 * the underlying stream. Thus redundant
	 * <code>DiscardableBufferedWriter</code>s will not copy data unnecessarily.
	 *
	 * @author mqfdy
	 * @param cbuf
	 *            the cbuf
	 * @param off
	 *            the off
	 * @param len
	 *            the len
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void write(char cbuf[], int off, int len)
        throws IOException
    {
        if (bufferSize == 0)
        {
            writer.write(cbuf, off, len);
            return;
        }

        if (len == 0)
        {
            return;
        }

        if (len >= bufferSize)
        {
            /* If the request length exceeds the size of the output buffer,
            flush the buffer and then write the data directly.  In this
            way buffered streams will cascade harmlessly. */
            if (autoFlush)
                flushBuffer();
            else
                bufferOverflow();
                writer.write(cbuf, off, len);
            return;
        }

        int b = off, t = off + len;
        while (b < t)
        {
            int d = min(bufferSize - nextChar, t - b);
            System.arraycopy(cbuf, b, cb, nextChar, d);
            b += d;
            nextChar += d;
            if (nextChar >= bufferSize)
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
        }
    }

    /**
	 * Write an array of characters. This method cannot be inherited from the
	 * Writer class because it must suppress I/O exceptions.
	 *
	 * @author mqfdy
	 * @param buf
	 *            the buf
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void write(char buf[]) throws IOException
    {
    	write(buf, 0, buf.length);
    }

    /**
	 * Write a portion of a String.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @param off
	 *            the off
	 * @param len
	 *            the len
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void write(String s, int off, int len) throws IOException
    {
        if (bufferSize == 0)
        {
            writer.write(s, off, len);
            return;
        }
        int b = off, t = off + len;
        while (b < t)
        {
            int d = min(bufferSize - nextChar, t - b);
            s.getChars(b, b + d, cb, nextChar);
            b += d;
            nextChar += d;
            if (nextChar >= bufferSize)
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
        }
    }

    /**
	 * Write a string. This method cannot be inherited from the Writer class
	 * because it must suppress I/O exceptions.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:37
	 */
    public final void write(String s) throws IOException
    {
        if (s != null)
        {
            write(s, 0, s.length());
        }
    }

    /**
	 * resets this class so that it can be reused.
	 *
	 * @author mqfdy
	 * @param writer
	 *            the writer
	 * @Date 2018-09-03 09:00
	 */
    public final void recycle(Writer writer)
    {
        this.writer = writer;
        clear();
    }
}
