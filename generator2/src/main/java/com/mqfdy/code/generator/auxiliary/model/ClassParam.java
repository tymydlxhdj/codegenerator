package com.mqfdy.code.generator.auxiliary.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.convert.ConvertUtil;
import com.mqfdy.code.generator.persistence.IForeignKeyModel;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.scence.IScenceType;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassParam.
 *
 * @author mqf
 */
public class ClassParam {
	
	/** The bc. */
	private BusinessClass bc;
	
	/** The persistence model. */
	private IPersistenceModel persistenceModel;
	
	/** The project. */
	private IProject project;
	
	/** The bom. */
	private BusinessObjectModel bom;
	
	/** The scene type set. */
	private Set<Integer> sceneTypeSet;
	
	/** The parent id. */
	private String parentId;
	
	/** The output path. */
	private String outputPath;
	
	/**
	 * Instantiates a new class param.
	 *
	 * @param bc
	 *            the bc
	 * @param project
	 *            the project
	 * @param bom
	 *            the bom
	 * @param outputPath
	 *            the output path
	 */
	public ClassParam(BusinessClass bc,IProject project,BusinessObjectModel bom,String outputPath){
		this.bc = bc;
		this.project = project;
		this.bom = bom;
		this.persistenceModel = ConvertUtil.convertToPersistenceModel(bc, bom);
		this.sceneTypeSet = new HashSet<Integer>();
		this.outputPath = outputPath;
		init();
	}
	
	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:33
	 */
	private void init() {
		this.parentId = "";
		for(Association ass : bom.getAssociations()){
			BusinessClass classA = ass.getClassA();
			BusinessClass classB = ass.getClassB();
			if(bc.getId().equals(classA.getId()) || bc.getId().equals(classB.getId())){
				Integer sceneType = getSceneType(ass,bc);
				if(IScenceType.SINGLE_TABLE_SCENE_TYPE != sceneType){
					if(IScenceType.TREE_SCENE_TYPE == sceneType){
						setParentId();
					}else if(IScenceType.DOUBLE_TABLE_SCENE_TYPE_MASTER == sceneType){
						
					}else if(IScenceType.DOUBLE_TABLE_SCENE_TYPE_SLAVE == sceneType){
						//
						setParentId();
					}
					sceneTypeSet.add(sceneType);
				}
			}
		}
	}
	
	/**
	 * 设置树父节点id.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:33
	 */
	private void setParentId() {
		for(IForeignKeyModel fkm : persistenceModel.getForeignKeyModels()){
			if(fkm.getForeignTableName().equals(bc.getTableName())){
				this.parentId = fkm.getForeignKey().getFkColumnJavaName();
			}
			
		}
	}
	
	/**
	 * Gets the scene type.
	 *
	 * @author mqfdy
	 * @param ass
	 *            the ass
	 * @param bc
	 *            the bc
	 * @return the scene type
	 * @Date 2018-9-3 11:38:33
	 */
	private Integer getSceneType(Association ass, BusinessClass bc) {
		Integer sceneType = IScenceType.SINGLE_TABLE_SCENE_TYPE;
		if(ass.getClassAid().equals(ass.getClassBid())){
			sceneType = IScenceType.TREE_SCENE_TYPE;
		}else if(AssociationType.one2one.getValue().equals(ass.getAssociationType())){
			sceneType = IScenceType.ONE2ONE_SCENE_TYPE;
		}else if(AssociationType.one2mult.getValue().equals(ass.getAssociationType())){
			if(bc.getId().equals(ass.getClassAid())){
				sceneType = IScenceType.DOUBLE_TABLE_SCENE_TYPE_MASTER;
			}else if(bc.getId().equals(ass.getClassBid())){
				sceneType = IScenceType.DOUBLE_TABLE_SCENE_TYPE_SLAVE;
			}
		}else if(AssociationType.mult2one.getValue().equals(ass.getAssociationType())){
			if(bc.getId().equals(ass.getClassBid())){
				sceneType = IScenceType.DOUBLE_TABLE_SCENE_TYPE_MASTER;
			}else if(bc.getId().equals(ass.getClassAid())){
				sceneType = IScenceType.DOUBLE_TABLE_SCENE_TYPE_SLAVE;
			}
		}else if(AssociationType.mult2mult.getValue().equals(ass.getAssociationType())){
			//sceneType = IScenceType.DOUBLE_TABLE_SCENE_TYPE;
		}
		return sceneType;
	}
	
	/**
	 * Gets the bc.
	 *
	 * @author mqfdy
	 * @return the bc
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getBc() {
		return bc;
	}
	
	/**
	 * Gets the persistence model.
	 *
	 * @author mqfdy
	 * @return the persistence model
	 * @Date 2018-09-03 09:00
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}
	
	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		return project;
	}
	
	/**
	 * Gets the bom.
	 *
	 * @author mqfdy
	 * @return the bom
	 * @Date 2018-09-03 09:00
	 */
	public BusinessObjectModel getBom() {
		return bom;
	}
	
	/**
	 * Sets the bc.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the new bc
	 * @Date 2018-09-03 09:00
	 */
	public void setBc(BusinessClass bc) {
		this.bc = bc;
	}
	
	/**
	 * Sets the persistence model.
	 *
	 * @author mqfdy
	 * @param persistenceModel
	 *            the new persistence model
	 * @Date 2018-09-03 09:00
	 */
	public void setPersistenceModel(IPersistenceModel persistenceModel) {
		this.persistenceModel = persistenceModel;
	}
	
	/**
	 * Sets the project.
	 *
	 * @author mqfdy
	 * @param project
	 *            the new project
	 * @Date 2018-09-03 09:00
	 */
	public void setProject(IProject project) {
		this.project = project;
	}
	
	/**
	 * Sets the bom.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the new bom
	 * @Date 2018-09-03 09:00
	 */
	public void setBom(BusinessObjectModel bom) {
		this.bom = bom;
	}
	
	/**
	 * Gets the parent id.
	 *
	 * @author mqfdy
	 * @return the parent id
	 * @Date 2018-09-03 09:00
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**
	 * Sets the parent id.
	 *
	 * @author mqfdy
	 * @param parentId
	 *            the new parent id
	 * @Date 2018-09-03 09:00
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * Gets the scene type set.
	 *
	 * @author mqfdy
	 * @return the scene type set
	 * @Date 2018-09-03 09:00
	 */
	public Set<Integer> getSceneTypeSet() {
		return sceneTypeSet;
	}
	
	/**
	 * Sets the scene type set.
	 *
	 * @author mqfdy
	 * @param sceneTypeSet
	 *            the new scene type set
	 * @Date 2018-09-03 09:00
	 */
	public void setSceneTypeSet(Set<Integer> sceneTypeSet) {
		this.sceneTypeSet = sceneTypeSet;
	}
	
	/**
	 * Gets the output path.
	 *
	 * @author mqfdy
	 * @return the output path
	 * @Date 2018-09-03 09:00
	 */
	public String getOutputPath() {
		return outputPath;
	}
	
	/**
	 * Sets the output path.
	 *
	 * @author mqfdy
	 * @param outputPath
	 *            the new output path
	 * @Date 2018-09-03 09:00
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
}
