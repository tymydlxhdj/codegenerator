package com.mqfdy.code.designer.editor;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageRegistry;

import com.mqfdy.code.designer.editor.utils.ElementFactory;
import com.mqfdy.code.designer.editor.utils.IConstants;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.model.Annotation;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.utils.AssociationType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DiagramEditorPalette objects.
 *
 * @author mqfdy
 * @title:om编辑器的组件面板类部分
 * @description:
 */
public class DiagramEditorPaletteFactory {

	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "DiagramEditorPaletteFactory.Location";

	/** Preference ID used to persist the palette length. */
	private static final String PALETTE_SIZE = "DiagramEditorPaletteFactory.Size";

	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "DiagramEditorPaletteFactory.State";

	/** The image registry. */
	private static ImageRegistry imageRegistry = BusinessModelEditorPlugin
			.getDefault().getImageRegistry();

	/**
	 * Creates a new DiagramEditorPalette object.
	 *
	 * @return the palette root
	 */
	// 创建面板
	protected static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();

		palette.add(createBasicToolsGroup(palette));
		palette.add(createNodeDrawer(palette));
		palette.add(createConnectionDrawer(palette));

		return palette;
	}

	/**
	 * Creates a new DiagramEditorPalette object.
	 *
	 * @param palette
	 *            the palette
	 * @return the palette container
	 */
	// add basic tools to palette : selection tool, marquee tool and a seperator
	private static PaletteContainer createBasicToolsGroup(PaletteRoot palette) {
		PaletteGroup toolGroup = new PaletteGroup("选择");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry("选择", "Select");
		toolGroup.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolGroup.add(new MarqueeToolEntry("选择框", "Marquee"));

		// Add a (unnamed) separator to the group
		toolGroup.add(new PaletteSeparator());

		return toolGroup;
	}

	/**
	 * Creates a new DiagramEditorPalette object.
	 *
	 * @param palette
	 *            the palette
	 * @return the palette container
	 */
	private static PaletteContainer createNodeDrawer(PaletteRoot palette) {
		PaletteDrawer elementDrawer = new PaletteDrawer("对象");
		// Class
		CombinedTemplateCreationEntry classbutton = new CombinedTemplateCreationEntry(
				"业务实体", "BusinessClass",
				BusinessClass.class,
				new ElementFactory(BusinessClass.class), // new TableFactory()
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS),
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
		elementDrawer.add(classbutton);

		// CombinedTemplateCreationEntry dtobutton = new
		// CombinedTemplateCreationEntry(
		// "DTO", "DataTransferObject", DataTransferObject.class, new
		// ElementFactory(DataTransferObject.class), //new TableFactory()
		// imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_DTO),
		// imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_DTO));
		// elementDrawer.add(dtobutton);
		// CombinedTemplateCreationEntry combutton = new
		// CombinedTemplateCreationEntry(
		// "复合数据类型", "ComplexDataType", ComplexDataType.class, new
		// ElementFactory(ComplexDataType.class), //new TableFactory()
		// imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS),
		// imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
		// elementDrawer.add(combutton);
		CombinedTemplateCreationEntry enumbutton = new CombinedTemplateCreationEntry(
				"枚举", "Enumeration",
				Enumeration.class,
				new ElementFactory(Enumeration.class), // new TableFactory()
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ENUMERATION),
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ENUMERATION));
		elementDrawer.add(enumbutton);
		// 注释annotation
		CombinedTemplateCreationEntry annobutton = new CombinedTemplateCreationEntry(
				"注释", "Annotation", Annotation.class, new ElementFactory(Annotation.class),
				imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ANNOTATION),
				imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ANNOTATION));
		elementDrawer.add(annobutton);
		// Column
		// CombinedTemplateCreationEntry columnEntry = new
		// CombinedTemplateCreationEntry(
		// "字段", "Column",ColumnElement.class, new
		// ElementFactory(ColumnElement.class),
		// imageRegistry.getDescriptor(ImageKeys.GRAPHICS_ATTR_PUBL),
		// imageRegistry.getDescriptor(ImageKeys.GRAPHICS_ATTR_PUBL));
		// elementDrawer.add(columnEntry);

		return elementDrawer;

	}

	/**
	 * Creates a new DiagramEditorPalette object.
	 *
	 * @param palette
	 *            the palette
	 * @return the palette container
	 */
	private static PaletteContainer createConnectionDrawer(PaletteRoot palette) {
		PaletteDrawer elementDrawer = new PaletteDrawer("关系");

		// Association
		ConnectionCreationToolEntry associationbutton = new ConnectionCreationToolEntry(
				AssociationType.one2one.getDisplayValue(),
				"Association",
				new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					public Object getObjectType() {
						return IConstants.ASSOCIATION_STR1_1;
					}
				},
				BusinessModelEditorPlugin
						.getDefault()
						.getImageRegistry()
						.getDescriptor(
								ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE),
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE));
		elementDrawer.add(associationbutton);
		ConnectionCreationToolEntry associationbutton2 = new ConnectionCreationToolEntry(
				AssociationType.one2mult.getDisplayValue(),
				"Association",
				new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					public Object getObjectType() {
						return IConstants.ASSOCIATION_STR1_N;
					}
				},
				BusinessModelEditorPlugin
						.getDefault()
						.getImageRegistry()
						.getDescriptor(
								ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT),
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT));
		elementDrawer.add(associationbutton2);

		ConnectionCreationToolEntry associationbutton3 = new ConnectionCreationToolEntry(
				AssociationType.mult2one.getDisplayValue(),
				"Association",
				new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					public Object getObjectType() {
						return IConstants.ASSOCIATION_STRN_1;
					}
				},
				BusinessModelEditorPlugin
						.getDefault()
						.getImageRegistry()
						.getDescriptor(
								ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE),
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE));
		elementDrawer.add(associationbutton3);

		ConnectionCreationToolEntry associationbutton4 = new ConnectionCreationToolEntry(
				AssociationType.mult2mult.getDisplayValue(),
				"Association",
				new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					public Object getObjectType() {
						return IConstants.ASSOCIATION_STRN_N;
					}
				},
				BusinessModelEditorPlugin
						.getDefault()
						.getImageRegistry()
						.getDescriptor(
								ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT),
				imageRegistry
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT));
		elementDrawer.add(associationbutton4);
		// 连线  表示继承
		ConnectionCreationToolEntry inheritancebutton = new
		 ConnectionCreationToolEntry(
		 "继承", "Inheritance", new CreationFactory() {
		 public Object getNewObject() {
		 return null;
		 }
		
		 public Object getObjectType() {
		 return IConstants.INHERITANCE;
		 }
		 }, BusinessModelEditorPlugin.getDefault().getImageRegistry()
		 .getDescriptor(ImageKeys.IMG_MODEL_TYPE_INHERITANCE),
		 imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_INHERITANCE));
		 elementDrawer.add(inheritancebutton);
//		 连线 链接注释和节点
		ConnectionCreationToolEntry linebutton = new ConnectionCreationToolEntry(
				"连线", "Link", new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					public Object getObjectType() {
						return IConstants.LINK;
					}
				}, BusinessModelEditorPlugin.getDefault().getImageRegistry()
						.getDescriptor(ImageKeys.IMG_MODEL_TYPE_LINK),
				imageRegistry.getDescriptor(ImageKeys.IMG_MODEL_TYPE_LINK));
		elementDrawer.add(linebutton);

		return elementDrawer;

	}

	/**
	 * 编辑器调色板的布局.
	 *
	 * @return the flyout preferences
	 */
	protected static FlyoutPreferences createPalettePreferences() {
		return new FlyoutPreferences() {
			private IPreferenceStore getPreferenceStore() {
				return BusinessModelEditorPlugin.getDefault()
						.getPreferenceStore();
			}

			// 取得调色板的位置
			public int getDockLocation() {
				return 8;// getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}

			// 取得调色板各部分边界边框值
			public int getPaletteState() {
				return getPreferenceStore().getInt(PALETTE_STATE);
			}

			// 获取调色板的宽度
			public int getPaletteWidth() {
				return getPreferenceStore().getInt(PALETTE_SIZE);
			}

			// 设置调色板位置
			public void setDockLocation(int location) {
				getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}

			// 为调色板各部分之间边界设置边框
			public void setPaletteState(int state) {
				getPreferenceStore().setValue(PALETTE_STATE, state);
			}

			// 设置调色板宽度
			public void setPaletteWidth(int width) {
				getPreferenceStore().setValue(PALETTE_SIZE, width);
			}
		};
	}
}
