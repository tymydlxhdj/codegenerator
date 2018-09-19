/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.dbs;


// TODO: Auto-generated Javadoc
/**
 * A ConnectionListener is notified of any changes to a connection.
 * 
 * @see org.eclipse.datatools.connectivity.IManagedConnectionListener
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionListener {

	/**
	 * Opened.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @Date 2018-09-03 09:00
	 */
	public void opened(ConnectionProfile profile);
	
	/**
	 * Modified.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @Date 2018-09-03 09:00
	 */
	public void modified(ConnectionProfile profile);
	
	/**
	 * Ok to close.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean okToClose(ConnectionProfile profile);
	
	/**
	 * About to close.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @Date 2018-09-03 09:00
	 */
	public void aboutToClose(ConnectionProfile profile);
	
	/**
	 * Closed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @Date 2018-09-03 09:00
	 */
	public void closed(ConnectionProfile profile);

	/**
	 * Database changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param database
	 *            the database
	 * @Date 2018-09-03 09:00
	 */
	public void databaseChanged(ConnectionProfile profile, Database database);
	
	/**
	 * Catalog changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param catalog
	 *            the catalog
	 * @Date 2018-09-03 09:00
	 */
	public void catalogChanged(ConnectionProfile profile, Catalog catalog);
	
	/**
	 * Schema changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param schema
	 *            the schema
	 * @Date 2018-09-03 09:00
	 */
	public void schemaChanged(ConnectionProfile profile, Schema schema);
	
	/**
	 * Sequence changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param sequence
	 *            the sequence
	 * @Date 2018-09-03 09:00
	 */
	public void sequenceChanged(ConnectionProfile profile, Sequence sequence);
	
	/**
	 * Table changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param table
	 *            the table
	 * @Date 2018-09-03 09:00
	 */
	public void tableChanged(ConnectionProfile profile, Table table);
	
	/**
	 * Column changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param column
	 *            the column
	 * @Date 2018-09-03 09:00
	 */
	public void columnChanged(ConnectionProfile profile, Column column);
	
	/**
	 * Foreign key changed.
	 *
	 * @author mqfdy
	 * @param profile
	 *            the profile
	 * @param foreignKey
	 *            the foreign key
	 * @Date 2018-09-03 09:00
	 */
	public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey);

}
