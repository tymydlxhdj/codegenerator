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

/**
 * 
 * @author mqf
 *
 */
public class ClassParam {
	private BusinessClass bc;
	private IPersistenceModel persistenceModel;
	private IProject project;
	private BusinessObjectModel bom;
	private Set<Integer> sceneTypeSet;
	private String parentId;
	private String outputPath;
	
	public ClassParam(BusinessClass bc,IProject project,BusinessObjectModel bom,String outputPath){
		this.bc = bc;
		this.project = project;
		this.bom = bom;
		this.persistenceModel = ConvertUtil.convertToPersistenceModel(bc, bom);
		this.sceneTypeSet = new HashSet<Integer>();
		this.outputPath = outputPath;
		init();
	}
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
	 * 设置树父节点id
	 */
	private void setParentId() {
		for(IForeignKeyModel fkm : persistenceModel.getForeignKeyModels()){
			if(fkm.getForeignTableName().equals(bc.getTableName())){
				this.parentId = fkm.getForeignKey().getFkColumnJavaName();
			}
			
		}
	}
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
	public BusinessClass getBc() {
		return bc;
	}
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}
	public IProject getProject() {
		return project;
	}
	public BusinessObjectModel getBom() {
		return bom;
	}
	public void setBc(BusinessClass bc) {
		this.bc = bc;
	}
	public void setPersistenceModel(IPersistenceModel persistenceModel) {
		this.persistenceModel = persistenceModel;
	}
	public void setProject(IProject project) {
		this.project = project;
	}
	public void setBom(BusinessObjectModel bom) {
		this.bom = bom;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Set<Integer> getSceneTypeSet() {
		return sceneTypeSet;
	}
	public void setSceneTypeSet(Set<Integer> sceneTypeSet) {
		this.sceneTypeSet = sceneTypeSet;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
}
