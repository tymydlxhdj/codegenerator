package com.mqfdy.code.reverse.views.constant;

import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;


// TODO: Auto-generated Javadoc
/**
 * The Interface IViewConstant.
 *
 * @author mqfdy
 */
public interface IViewConstant {
	
	
	/** The Constant TYPE_DATASOURCE. */
	public static final int TYPE_DATASOURCE = 1; 
	
	/** The Constant TYPE_REPOSITORY. */
	public static final int TYPE_REPOSITORY = 2; 
	
	/** The Constant TYPE_SCHEMAS. */
	public static final int TYPE_SCHEMAS = 3; 
	
	/** The Constant TYPE_USER. */
	public static final int TYPE_USER = 4;
	
	/** The Constant TYPE_TABLE_FOLDER. */
	public static final int TYPE_TABLE_FOLDER = 5;
	
	/** The Constant TYPE_TABLE_NODE. */
	public static final int TYPE_TABLE_NODE = 6;
	
	/** The Constant TYPE_COLUMNS_FOLDER. */
	public static final int TYPE_COLUMNS_FOLDER = 7; 
	
	/** The Constant TYPE_COLUMNS_NODE. */
	public static final int TYPE_COLUMNS_NODE = 8; 
	
	/** The Constant TYPE_CONSTRAINT_FOLDER. */
	public static final int TYPE_CONSTRAINT_FOLDER = 9;
	
	/** The Constant TYPE_PRIMARY_KEY. */
	public static final int TYPE_PRIMARY_KEY = 10; 
	
	/** The Constant TYPE_REFERENCE_KEY. */
	public static final int TYPE_REFERENCE_KEY = 11;
	
	/** The Constant TYPE_SEQUENCES_FOLDER. */
	public static final int TYPE_SEQUENCES_FOLDER = 12;
	
	/** The Constant TYPE_SEQUENCES_NODE. */
	public static final int TYPE_SEQUENCES_NODE = 13;
	
	/** The Constant TYPE_VIEWS_FOLDER. */
	public static final int TYPE_VIEWS_FOLDER = 14;
	
	/** The Constant TYPE_VIEWS_NODE. */
	public static final int TYPE_VIEWS_NODE = 15;
	 
	/** The Constant TYPE_TEMP. */
	public static final int TYPE_TEMP = 16;
	
	/** The Constant TYPE_UN_VISABLE. */
	public static final int TYPE_UN_VISABLE = 17;
	
	/** The Constant TYPE_PROJECTS. */
	public static final int TYPE_PROJECTS = 18;
	
	/** The Constant TYPE_DIRECTORY. */
	public static final int TYPE_DIRECTORY = 19;
	
	/** The Constant TYPE_OM_FILE. */
	public static final int TYPE_OM_FILE = 20;
	
	
	/** The Constant TYPE_NO_PK_TABLE. */
	public static final int TYPE_NO_PK_TABLE = 21;
	
	/** The Constant TYPE_MULTI_TABLE. */
	public static final int TYPE_MULTI_TABLE = 22;
	
	/** The Constant TYPE_SPECIAL_CHAR_TABLE. */
	public static final int TYPE_SPECIAL_CHAR_TABLE = 35;
	
	/** The Constant TYPE_DUPLICATE_NAME. */
	public static final int TYPE_DUPLICATE_NAME = 23;
	
	/** The Constant TYPE_BUSINESSCLASS. */
	public static final int TYPE_BUSINESSCLASS = 24;
	
	/** The Constant TYPE_PROPERTY_NODE. */
	public static final int TYPE_PROPERTY_NODE = 25;
	
	/** The Constant TYPE_PACKAGE. */
	public static final int TYPE_PACKAGE = 26;
	
	/** The Constant TYPE_PRIMARY_COLUMN. */
	public static final int TYPE_PRIMARY_COLUMN = 27; 
	
	/** The Constant TYPE_REFERENCE_COLUMN. */
	public static final int TYPE_REFERENCE_COLUMN = 28;
	
	/** The Constant TYPE_PRIMARY_PROPERTY. */
	public static final int TYPE_PRIMARY_PROPERTY = 29; 
	
	/** The Constant TYPE_RELATION. */
	public static final int TYPE_RELATION = 30;
	
	/** The Constant TYPE_BUSINESSCLASS_ERROR. */
	public static final int TYPE_BUSINESSCLASS_ERROR = 31;
	
	/** The Constant TYPE_OPERATION_ERROR. */
	public static final int TYPE_OPERATION_ERROR = 32;
	
	/** The Constant TYPE_PRIMARYKEY_ERROR. */
	public static final int TYPE_PRIMARYKEY_ERROR = 33;
	
	/** The Constant TYPE_PROPERTY_NODE_ERROR. */
	public static final int TYPE_PROPERTY_NODE_ERROR = 35;
	
	/** The Constant TYPE_BUSINESSCLASS_CONFLICT. */
	public static final int TYPE_BUSINESSCLASS_CONFLICT = 34;
	
	/** The Constant EXPAND_LEVEL_1. */
	public static final int EXPAND_LEVEL_1 = 1; 
	
	/** The Constant EXPAND_LEVEL_2. */
	public static final int EXPAND_LEVEL_2 = 2; 
	
	/** The Constant EXPAND_LEVEL_3. */
	public static final int EXPAND_LEVEL_3 = 3; 
	
	/** The Constant EXPAND_LEVEL_4. */
	public static final int EXPAND_LEVEL_4 = 4; 
	
	/** The Constant COLUMNS_FOLDER. */
	public static final String COLUMNS_FOLDER = "Columns";
	
	/** The Constant DATASOURCE. */
	public static final String DATASOURCE = "datasource"; 
	
	/** The Constant SCHEMAS. */
	public static final String SCHEMAS = "schemas"; 
	
	/** The Constant PRIMARY_KEY. */
	public static final String PRIMARY_KEY = "primary_key"; 
	
	/** The Constant REFERENCE_KEY. */
	public static final String REFERENCE_KEY = "reference_key"; 
	
	/** The Constant CONSTRAINT_FOLDER. */
	public static final String CONSTRAINT_FOLDER = "Constraints"; 
	
	/** The Constant REPOSITORY. */
	public static final String REPOSITORY = "repository"; 
	
	/** The Constant TABLES. */
	public static final String TABLES  = "tables"; 
	
	/** The Constant USER. */
	public static final String USER = "user";
	
	/** The Constant SEQUENCES. */
	public static final String SEQUENCES = "sequences";
	
	/** The Constant VIEWS. */
	public static final String VIEWS = "views";
	
	/** The Constant TEMP. */
	public static final String TEMP = "temp";
	
	/** The Constant DISABLED_COLOR. */
	public static final Color DISABLED_COLOR = new Color(null,244, 244, 244);
	
	/** The Constant LABEL_COLOR. */
	public static final Color LABEL_COLOR = new Color(null,0, 0, 0);
	
	/** The Constant BLACK_COLOR. */
	public static final Color BLACK_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	
	/** The Constant WHITE_COLOR. */
	public static final Color WHITE_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	
	/** The Constant GRAY_COLOR. */
	public static final Color GRAY_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	
	/** The Constant RED_COLOR. */
	public static final Color RED_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	
	/** The Constant DIALOG_WIDTH_PROPORTION. */
	public static final int DIALOG_WIDTH_PROPORTION = 37;
	
	/** The Constant DIALOG_HEIGHT_PROPORTION. */
	public static final int DIALOG_HEIGHT_PROPORTION = 65;
	
	/** The Constant SCREEN_WIDTH. */
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	/** The Constant SCREEN_HEIGHT. */
	public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;


}
