package com.mqfdy.code.designer.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.contentoutline.ContentOutline;

import com.mqfdy.code.designer.constant.IProjectConstant;
import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;
import com.mqfdy.code.designer.views.modelresource.page.ObjectModelOutlinePage;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.resource.BomManager;
import com.mqfdy.code.reverse.utils.ReverseUtil;

public class BusinessModelUtil {

	/**
	 * get the Editing BusinessModelManager;
	 * 
	 * @return
	 */
	public static BusinessModelManager getEditorBusinessModelManager() {
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof BusinessModelEditor) {
			return ((BusinessModelEditor) editorPart).getBuEditor()
					.getBusinessModelManager();
		} else {
			IViewPart[] parts = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getViews();
			for (int i = 0; i < parts.length; i++) {
				if (parts[i].getClass() == ContentOutline.class) {
					IPage page = ((ContentOutline) (parts[i].getViewSite()
							.getPart())).getCurrentPage();
					if (page instanceof ObjectModelOutlinePage) {
						return ((ObjectModelOutlinePage) page)
								.getBusinessModelManager();
					}
				}
			}
		}
		return null;
	}

	public static GraphicalViewer getGefViewer() {
		return getBusinessModelDiagramEditor().getViewer();
	}

	public static List getSelectedEditParts() {
		return getGefViewer().getSelectedEditParts();
	}

	/**
	 * get the editing Editor
	 * 
	 * @return
	 */
	public static BusinessModelDiagramEditor getBusinessModelDiagramEditor() {
		IWorkbenchPage acpage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (acpage == null) {
			return null;
		}
		IEditorPart editorPart = acpage.getActiveEditor();
		if(editorPart!=null){
			if (editorPart instanceof BusinessModelEditor) {
				return ((BusinessModelEditor) editorPart).getBuEditor();
			} else {
				IViewPart[] parts = acpage.getViews();
				for (int i = 0; i < parts.length; i++) {
					if (parts[i].getClass() == ContentOutline.class) {
						IPage page = ((ContentOutline) (parts[i].getViewSite()
								.getPart())).getCurrentPage();
						if (page instanceof ObjectModelOutlinePage) {
							return (BusinessModelDiagramEditor) ((ObjectModelOutlinePage) page)
									.getBusinessModelManager()
									.getBusinessModelDiagramEditor();
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * get the editing view
	 * 
	 * @param classType
	 * @return
	 */
	public static ValiView getView(Class<? extends IViewPart> classType) {
		try{
			IViewPart[] parts = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getViews();
			for (int i = 0; i < parts.length; i++) {
				if (parts[i].getClass() == classType) {
					return ((ValiView) parts[i]);
				}
			}
		}catch (Exception e) {
			return null;
		}
		return null;
	}

	public static ObjectModelOutlinePage getOutlinePage() {
		IViewPart[] parts = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViews();
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].getClass() == ContentOutline.class) {
				IPage page = ((ContentOutline) (parts[i].getViewSite()
						.getPart())).getCurrentPage();
				if (page instanceof ObjectModelOutlinePage) {
					return (ObjectModelOutlinePage) page;
				}
			}
		}
		return null;
	}
	/**
	 * 解析引用模型  如果出错 弹出确认窗口删掉
	 * @param businessModelEditor 
	 * @param businessObjectModel
	 * @param filePath
	 */
	public static boolean isHasReferenceObject(
			BusinessModelEditor businessModelEditor, BusinessObjectModel businessObjectModel,String filePath/*,BusinessModelDiagramEditor buEditor*/) {
		// 解析引用模型
		boolean isDel = true;
		List<ReferenceObject> ros = businessObjectModel.getReferenceObjects();
		String pathList = "";
		if (ros != null && ros.size() > 0) {
			List<BusinessObjectModel> repositoryModels = new ArrayList<BusinessObjectModel>();
			List<String> modelIds = new ArrayList<String>();
			List<ReferenceObject> removeList = new ArrayList<ReferenceObject>();
			for (ReferenceObject ro : ros) {
				String refModelId = ro.getReferenceModelId();
				BusinessObjectModel repositoryBom = null;
				String path = ro.getReferenceModePath();
				if (!modelIds.contains(refModelId)) {
					modelIds.add(refModelId);
					String omfilePath = EditorOperation.getProjectPath(path);
					try {
						repositoryBom = BomManager.xml2Model(omfilePath);
						repositoryBom.getExtendAttributies().put(
								IModelElement.REFMODELPATH, path);
						repositoryModels.add(repositoryBom);
					} catch (DocumentException e) {
					}
				} else {
					for (BusinessObjectModel rbom : repositoryModels) {
						if (rbom.getId().equals(refModelId)) {
							repositoryBom = rbom;
							break;
						}
					}
				}
				if (repositoryBom != null) {
					String refObjectId = ro.getReferenceObjectId();
					AbstractModelElement element = (AbstractModelElement) repositoryBom
							.getModelElementById(refObjectId);
					if (element == null) {
						pathList = pathList + path + "\n";
						removeList.add(ro);
					} else {
						AbstractModelElement copyElement = element.clone();
						copyElement
								.setStereotype(IModelElement.STEREOTYPE_REFERENCE);
						copyElement.getExtendAttributies().put("FullName",
								element.getFullName());
						ro.setReferenceObject(copyElement);
					}
				}
				if (repositoryBom == null) {
					pathList = pathList + path + "\n";
					removeList.add(ro);
				}
			}
			List<Association> associations = businessObjectModel
					.getAssociations();
			List<Association> removeAssList = new ArrayList<Association>();
			for (Association association : associations) {
				String classAid = association.getClassAid();
				String classBid = association.getClassBid();
				for (ReferenceObject ro : ros) {
					if (classAid.equals(ro.getReferenceObjectId())) {
						if (isDel && removeList.contains(ro)) {
							association.setClassA(null);
							if(!removeAssList.contains(association))
								removeAssList.add(association);
						} else
							association.setClassA((BusinessClass) ro
									.getReferenceObject());
					}
					if (classBid.equals(ro.getReferenceObjectId())) {
						if (isDel && removeList.contains(ro)) {
							association.setClassB(null);
							if(!removeAssList.contains(association))
								removeAssList.add(association);
						} else
							association.setClassB((BusinessClass) ro
									.getReferenceObject());
					}
				}
			}
			if (isDel) {
				for (Association association : removeAssList) {
					businessObjectModel.removeAssociation(association);
				}
				for (ReferenceObject ro : removeList) {
					businessObjectModel.removeReferenceObject(ro);
				}
			}
		}

		if (!pathList.equals("")) {
			businessModelEditor.setHasErrorRefObj(true);
			businessModelEditor.setPathList(pathList);
			return true;
		}
		businessModelEditor.setHasErrorRefObj(false);
		return false;
	}
	/**
	 * 解析引用模型  如果出错 弹出确认窗口删掉
	 * @param businessObjectModel
	 * @param filePath
	 */
	public static void assembReferenceObject(
			BusinessObjectModel businessObjectModel,String filePath) {
		// 解析引用模型
		List<ReferenceObject> ros = businessObjectModel.getReferenceObjects();
		if (ros != null && ros.size() > 0) {
			List<BusinessObjectModel> repositoryModels = new ArrayList<BusinessObjectModel>();
			List<String> modelIds = new ArrayList<String>();
			List<ReferenceObject> removeList = new ArrayList<ReferenceObject>();
			String pathList = "";
			for (ReferenceObject ro : ros) {
				String refModelId = ro.getReferenceModelId();
				BusinessObjectModel repositoryBom = null;
				String path = ro.getReferenceModePath();
				if (!modelIds.contains(refModelId)) {
					modelIds.add(refModelId);
//					String workspacePath = Platform.getLocation().toString();
					String omfilePath = EditorOperation.getProjectPath(path);//workspacePath + "/" + path;
					try {
						repositoryBom = BomManager.xml2Model(omfilePath);
						repositoryBom.getExtendAttributies().put(
								IModelElement.REFMODELPATH, path);
						// ModelUtil.transformModelStereotype(repositoryBom,IModelElement.STEREOTYPE_REFERENCE);
						repositoryModels.add(repositoryBom);
					} catch (DocumentException e) {
//						Logger.log(e);
					}
				} else {
					for (BusinessObjectModel rbom : repositoryModels) {
						if (rbom.getId().equals(refModelId)) {
							repositoryBom = rbom;
							break;
						}
					}
				}
				if (repositoryBom != null) {
					String refObjectId = ro.getReferenceObjectId();
					AbstractModelElement element = (AbstractModelElement) repositoryBom
							.getModelElementById(refObjectId);
					if (element == null) {
						pathList = pathList + path + "\n";
						removeList.add(ro);
					} else {
						AbstractModelElement copyElement = element.clone();
						copyElement
								.setStereotype(IModelElement.STEREOTYPE_REFERENCE);
						copyElement.getExtendAttributies().put("FullName",
								element.getFullName());
						ro.setReferenceObject(copyElement);
					}
				}
				if (repositoryBom == null) {
					pathList = pathList + path + "\n";
					removeList.add(ro);
				}
			}
			int s = 1;
			if (removeList.size() > 0) {
				String mes = "模型中包含错误的引用，可能是由于引用模型的位置改变，或者原模型被删除，原模型路径如下：\n"
						+ pathList + "如想打开该模型，请修改以上模型或者删除本模型中的引用， 是否删除这些引用？";
				MessageDialog dia = new MessageDialog(
						BusinessModelEditorPlugin.getShell(), "提示", null, mes,
						0, new String[] { "删除", "取消" }, 0);
				s = dia.open();
			}
			List<Association> associations = businessObjectModel
					.getAssociations();
			List<Association> removeAssList = new ArrayList<Association>();
			for (Association association : associations) {
				String classAid = association.getClassAid();
				String classBid = association.getClassBid();
				for (ReferenceObject ro : ros) {
					if (classAid.equals(ro.getReferenceObjectId())) {
						if (s == TitleAreaDialog.OK && removeList.contains(ro)) {
							association.setClassA(null);
							if(!removeAssList.contains(association))
								removeAssList.add(association);
						} else
							association.setClassA((BusinessClass) ro
									.getReferenceObject());
					}
					if (classBid.equals(ro.getReferenceObjectId())) {
						if (s == TitleAreaDialog.OK && removeList.contains(ro)) {
							association.setClassB(null);
							if(!removeAssList.contains(association))
								removeAssList.add(association);
						} else
							association.setClassB((BusinessClass) ro
									.getReferenceObject());
					}
				}
			}
			for (Association association : removeAssList) {
				businessObjectModel.removeAssociation(association);
			}
			for (ReferenceObject ro : removeList) {
				businessObjectModel.removeReferenceObject(ro);
			}
			if (s == TitleAreaDialog.OK) {
				saveModelAfterDelRef(businessObjectModel, filePath);
			}
		}
		assertRefEnum(businessObjectModel);
	}
	
	private static void assertRefEnum(BusinessObjectModel businessObjectModel){
		if(businessObjectModel!=null){
			List<BusinessClass> listBusinessClasses = businessObjectModel.getBusinessClasses();
			if(listBusinessClasses!=null){
				StringBuffer sb = new StringBuffer("");
				List<Property> errorProperties = new ArrayList();
				for(BusinessClass bc : listBusinessClasses){
					List<Property> listProps = bc.getProperties();
					for(Property property : listProps){
						String enumId = property.getEditor().getEditorParams().get(PropertyEditor.PARAM_KEY_ENUMERATION_ID);
						boolean isFound = false;
						if(enumId!=null && !"".equals(enumId)){
							List<Enumeration> listEnums = businessObjectModel.getEnumerations();
							if(listEnums!=null){
								for(Enumeration enumer : listEnums){
									if(enumId  != null && enumId.equals(enumer.getId())){
										isFound = true;
									}
								}
							}
							if(!isFound){
								sb.append(bc.getName()+"："+property.getName()+",");
								errorProperties.add(property);
							}
						}
					}
				}
				if(sb.length()>0){
					//没找到，提示用户
					String mes = "模型中以下属性缺失枚举对象：\n"+sb.toString()
							 + "如想打开该模型，请修改以上模型， 是否修改这些属性编辑器为普通文本编辑器？";
					MessageDialog dia = new MessageDialog(
							BusinessModelEditorPlugin.getShell(), "提示", null, mes,
							0, new String[] { "修复", "取消" }, 0);
					if(dia.open() == TitleAreaDialog.OK){
						for(Property property:errorProperties){
							PropertyEditor propertyEditor = new PropertyEditor();
							propertyEditor.setEditorType(EditorType.TextEditor.getValue());
							property.setEditor(propertyEditor);
						}
					}
				}
			}
		}
	}
	
	//删除错误的引用后保存模型
	public static void saveModelAfterDelRef(BusinessObjectModel businessObjectModel,String filePath){
		//删除错误的引用后保存模型
		IPath path = new Path(filePath);
		IFile sourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		BusinessModelManager.saveModel(businessObjectModel, filePath);

		try {
			sourceFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			Logger.log(e);
		}
	}
	public static  String getTableName(String oldName){
		String name = oldName;
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		String prefix = store.getString(ModelPreferencePage.TABLENAMEPREFIX);
		name = prefix + name;
		boolean isTableUpperCase = store.getBoolean(ModelPreferencePage.ISTABLENAMEUPPERCASE);
		if(isTableUpperCase)
			name = name.toUpperCase(Locale.getDefault());
		return name;
	}
	
	public static  String getProColumnName(String oldName){
		String name = oldName;
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		boolean isTableUpperCase = store.getBoolean(ModelPreferencePage.ISPRONAMEUPPERCASE);
		if(isTableUpperCase)
			name = name.toUpperCase(Locale.getDefault());
		return name;
	}
	public static BusinessObjectModel getBom(AbstractModelElement modelElement) {
		BusinessObjectModel businessObjectModel = null;
		if (modelElement instanceof BusinessClass) {
			AbstractModelElement ab = (AbstractModelElement) modelElement;
			do {
				ab = ab.getParent();
			} while (!(ab instanceof BusinessObjectModel));
			businessObjectModel = (BusinessObjectModel) ab;
		}
		return businessObjectModel;
	}
	
	/**
	 * 在MANIFEST.MF中，指定的属性中新增一个值
	 * @param manifestPath
	 * @param packageName
	 * @throws Exception 
	 */
	public static boolean addValueToManifest(String manifestPath, String attributeName, String value) {
		if(attributeName == null || attributeName.trim().length() == 0) {
			return false;
		}
		BufferedReader reader = null;
		BufferedWriter writer = null;
		FileReader fr = null;
		FileWriter fw = null;
		try {
			fr = new FileReader(manifestPath);
			reader = new BufferedReader(fr);
			//读取的总的字符串 
			StringBuffer resultString = new StringBuffer("");
			//当前属性头字符串
			String currentAttrString = "";
			//读取过的属性头
			Map<String, String> readAttributes = new HashMap<String, String>();
			//当前读取的字符串
			String currentReadStr = "";
			//指定的属性头后边是否有其他属性
			boolean isFoundAfter = false;
			
			//扫描所有字符串, 看有没有当前指定的属性
			if(isExistAttribute(manifestPath, attributeName)) {
				while((currentReadStr = reader.readLine()) != null) {
					if(isAttributeRow(currentReadStr)) {
						//如果是属性的行, 记住当前读取的属性名
						currentAttrString = currentReadStr.substring(0, currentReadStr.indexOf(":"));
						//记住读取过的属性头
						readAttributes.put(currentAttrString, currentAttrString);
					}
					
					if (readAttributes.get(attributeName) != null) {//读取过指定属性
						if (currentAttrString.equals(attributeName)) {// 如果当前属性字符是指定属性
							if (currentReadStr.indexOf(value) != -1 && currentReadStr.indexOf(value + ".") == -1) {
								// 已经设置过该值，直接返回。
								return true;
							}
						} else {
							if (isAttributeRow(currentReadStr)) {// 如果碰到非指定属性,并且是一个属性头
								resultString.append(",\n " + value);
								isFoundAfter = true;
							}
						}
					}
					
					if(resultString.toString() == null || resultString.toString().length() == 0) {
						resultString.append(currentReadStr);
					} else {
						resultString.append("\n" + currentReadStr);
					}
				}
				
				if(!isFoundAfter) {
					resultString.append(",\n " + value);
				}
				resultString.append("\n");
			} else {
				while((currentReadStr = reader.readLine()) != null) {
					if(resultString.toString() == null || resultString.toString().length() == 0) {
						resultString.append(currentReadStr);
					} else {
						resultString.append("\n" + currentReadStr);
					}
				}
				resultString.append("\n" + attributeName + ": " + value);
				resultString.append("\n");
			}
	
			fw = new FileWriter(manifestPath);
			writer = new BufferedWriter(fw);
			writer.write(resultString.toString());
			writer.flush();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 是否是属性头的行
	 * @param attributeValue
	 * @return
	 */
	public static boolean isAttributeRow(String attributeValue) {
		if(attributeValue == null || attributeValue.length() == 0) {
			return false;
		}
		return !attributeValue.substring(0, 1).contains(" ") && attributeValue.indexOf(":") != -1;
	}
	
	/**
	 * 是否存在当前属性
	 * @param manifestPath
	 * @param attributeName
	 * @return
	 */
	public static boolean isExistAttribute(String manifestPath, String attributeName) {
		BufferedReader reader = null;
		FileReader fr = null;
		try {
			fr = new FileReader(manifestPath);
			reader = new BufferedReader(fr);
			String currentReadStr = "";
			while((currentReadStr = reader.readLine()) != null) {
				//找到制定的属性
				int index = currentReadStr.indexOf(attributeName + ":");
				if(index != -1) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
	/*
	 * 判断是否是自定义类型的模型
	 */
	public static boolean isCustomObjectModel(AbstractModelElement obj){
		return obj.getStereotype() == null ||  "".equals(obj.getStereotype())
			||IModelElement.STEREOTYPE_CUSTOM.equals(obj.getStereotype());
	}
	
	/*
	 * 判断是否是自定义类型的模型
	 */
	public static boolean isReverseObjectModel(AbstractModelElement obj){
		return obj.getStereotype() != null &&
			IModelElement.STEREOTYPE_REVERSE.equals(obj.getStereotype());
	}
}