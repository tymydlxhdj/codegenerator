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
 * 生成器代码参数.
 *
 * @author mqf
 */
public class ClassParam {
	
	/** bc. */
	private BusinessClass bc;
	
	/** persistenceModel. */
	private IPersistenceModel persistenceModel;
	
	/** project. */
	private IProject project;
	
	/** bom. */
	private BusinessObjectModel bom;
	
	/** sceneTypeSet. */
	private Set<Integer> sceneTypeSet;
	
	/** parentId. */
	private String parentId;

	/** The paret column name. */
	private String paretColumnName;

	private String dbType;
	
	/**
	 * Instantiates a new class param.
	 *
	 * @param bc bc
	 * @param project project
	 * @param bom bom
	 */
	public ClassParam(BusinessClass bc,IProject project,BusinessObjectModel bom){
		this.bc = bc;
		this.project = project;
		this.bom = bom;
		this.persistenceModel = ConvertUtil.convertToPersistenceModel(bc, bom);
		this.sceneTypeSet = new HashSet<Integer>();
		init();
	}
	
	/**
	 * Instantiates a new class param.
	 *
	 * @param bc2 the bc 2
	 * @param project2 the project 2
	 * @param bom2 the bom 2
	 * @param config the config
	 */
	public ClassParam(BusinessClass bc2, IProject project2, BusinessObjectModel bom2, String dbType) {
		this(bc2, project2, bom2);
		this.dbType = dbType;
	}

	/**
	 * 方法描述：init.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 上午11:07:58
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
	 * 方法描述：setParentId.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 上午11:08:02
	 */
	private void setParentId() {
		for(IForeignKeyModel fkm : persistenceModel.getForeignKeyModels()){
			if(fkm.getForeignTableName().equals(bc.getTableName())){
				this.parentId = fkm.getForeignKey().getFkColumnJavaName();
				this.paretColumnName =  fkm.getForeignKey().getFkColumnName();
				break;
			}
			
		}
	}
	
	/**
	 * 方法描述：getSceneType.
	 *
	 * @author mqfdy
	 * @param ass ass
	 * @param bc bc
	 * @return Integer实例
	 * @Date 2018年8月31日 上午11:08:10
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
	 * 方法描述：getBc.
	 *
	 * @author mqfdy
	 * @return BusinessClass实例
	 * @Date 2018年8月31日 上午11:08:20
	 */
	public BusinessClass getBc() {
		return bc;
	}
	
	/**
	 * 方法描述：getPersistenceModel.
	 *
	 * @author mqfdy
	 * @return IPersistenceModel实例
	 * @Date 2018年8月31日 上午11:08:23
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}
	
	/**
	 * 方法描述：getProject.
	 *
	 * @author mqfdy
	 * @return IProject实例
	 * @Date 2018年8月31日 上午11:08:25
	 */
	public IProject getProject() {
		return project;
	}
	
	/**
	 * 方法描述：getBom.
	 *
	 * @author mqfdy
	 * @return BusinessObjectModel实例
	 * @Date 2018年8月31日 上午11:08:28
	 */
	public BusinessObjectModel getBom() {
		return bom;
	}
	
	/**
	 * 方法描述：setBc.
	 *
	 * @author mqfdy
	 * @param bc bc
	 * @Date 2018年8月31日 上午11:08:31
	 */
	public void setBc(BusinessClass bc) {
		this.bc = bc;
	}
	
	/**
	 * 方法描述：setPersistenceModel.
	 *
	 * @author mqfdy
	 * @param persistenceModel persistenceModel
	 * @Date 2018年8月31日 上午11:08:36
	 */
	public void setPersistenceModel(IPersistenceModel persistenceModel) {
		this.persistenceModel = persistenceModel;
	}
	
	/**
	 * 方法描述：setProject.
	 *
	 * @author mqfdy
	 * @param project project
	 * @Date 2018年8月31日 上午11:08:41
	 */
	public void setProject(IProject project) {
		this.project = project;
	}
	
	/**
	 * 方法描述：setBom.
	 *
	 * @author mqfdy
	 * @param bom bom
	 * @Date 2018年8月31日 上午11:08:45
	 */
	public void setBom(BusinessObjectModel bom) {
		this.bom = bom;
	}
	
	/**
	 * 方法描述：getParentId.
	 *
	 * @author mqfdy
	 * @return String实例
	 * @Date 2018年8月31日 上午11:08:50
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**
	 * 方法描述：setParentId.
	 *
	 * @author mqfdy
	 * @param parentId parentId
	 * @Date 2018年8月31日 上午11:08:53
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 方法描述：getSceneTypeSet.
	 *
	 * @author mqfdy
	 * @return Set<Integer>实例
	 * @Date 2018年8月31日 上午11:08:57
	 */
	public Set<Integer> getSceneTypeSet() {
		return sceneTypeSet;
	}
	
	/**
	 * 方法描述：setSceneTypeSet.
	 *
	 * @author mqfdy
	 * @param sceneTypeSet sceneTypeSet
	 * @Date 2018年8月31日 上午11:09:00
	 */
	public void setSceneTypeSet(Set<Integer> sceneTypeSet) {
		this.sceneTypeSet = sceneTypeSet;
	}

	public String getParetColumnName() {
		return paretColumnName;
	}

	public void setParetColumnName(String paretColumnName) {
		this.paretColumnName = paretColumnName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
}
