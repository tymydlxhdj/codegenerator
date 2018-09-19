package com.mqfdy.code.designer.utils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorTypeUtil.
 *
 * @author mqfdy
 */
public class EditorTypeUtil {
	
	/**
	 * Gets the image.
	 *
	 * @author mqfdy
	 * @param editorType
	 *            the editor type
	 * @return the image
	 * @Date 2018-09-03 09:00
	 */
	public static Image getImage(EditorType editorType) {
		Image editorImage;
		editorImage = ImageManager.getInstance().getImage(
				"resource/images/editor/default.jpg");
		if (editorImage != null) {
			Rectangle rec = editorImage.getBounds();
			switch (editorType) {
			case TextEditor:
				rec.width = 127;
				rec.height = 25;
				break;
			case MultTextEditor:
				rec.width = 151;
				rec.height = 64;
				break;
			case ComboBox:
				rec.width = 110;
				rec.height = 25;
				break;
			case CheckComboBox:
				rec.width = 110;
				rec.height = 25;
				break;
			case PasswordTextEditor:
				rec.width = 131;
				rec.height = 22;
				break;
			}
		} else {
			editorImage = ImageManager.getInstance().getImage(
					"resource/images/editor/default.jpg");
			if (editorImage != null) {
				Rectangle rec = editorImage.getBounds();
				rec.width = 127;
				rec.height = 25;
			}
		}
		return editorImage;
	}
	
	/**
	 * 根据数据类型返回默认的编辑器类型.
	 *
	 * @author mqfdy
	 * @param selectType
	 *            the select type
	 * @return the editor type
	 * @Date 2018-09-03 09:00
	 */
	public static EditorType getEditorType(DataType selectType){
		EditorType type = EditorType.TextEditor;
		switch (selectType) {
		case Long:
			type = EditorType.NumberEditor;
			break;
		case Short:
			type = EditorType.NumberEditor;
			break;
		case Byte:
			type = EditorType.NumberEditor;
			break;
		case Float:
			type = EditorType.NumberEditor;
			break;
		case Double:
			type = EditorType.NumberEditor;
			break;
		case Integer:
			type = EditorType.NumberEditor;
			break;
		case Big_decimal:
			type = EditorType.NumberEditor;
			break;
		case String:
			type = EditorType.TextEditor;
			break;
		case Boolean:
			type = EditorType.TextEditor;
			break;
		case Date:
			type = EditorType.DateEditor;
			break;
		case Time:
			type = EditorType.TimeEditor;
			break;
		case Timestamp:
			type = EditorType.DateTimeEditor;
			break;
		case Clob:
			type = EditorType.RichTextEditor;
			break;
		/*
		 * case Blob: type = EditorType.FileEditor; break;
		 */
		}
		return type;
	}
}
