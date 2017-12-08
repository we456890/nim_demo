package com.netease.nim.uikit.common.bean;

/**
 * 基础的Pojo类主要实现ID.
 * 
 * 增加添加人的部门信息
 *
 * @DateTime: 2012-9-10
 * @author lushigai
 * @author zxy
 * @version 2.0
 */
@SuppressWarnings("serial")
public class BasePojo implements IdEntity
{
      
    /** 实体标识. */
    private String id;

	/** 删除标识 */
	private String status;
	
	/** 创建人 */
	private String created;
	
	/** 创建者部门IDS */
	private String createdDept;
	
	/** 创建时间 */
	private long createTime;
	
	/** 更新人 */
	private String updated;
	
	/** 更新时间 */
	private long updateTime;
	
	/**
	 * 数据所属的部门id
	 */
//	@ChineseName("部门id")
	private String org;
	/**
	 * 数据所属的部门用户结构（上上级部门id-上级部门id-用户id）
	 */
//	@ChineseName("部门用户结构")
	private String orgTree;
	/**
	 * 用于SQLMapper中动态数据的条件字段
	 */
//	@PersistenceIgnore
	private String condition;
	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgTree() {
		return orgTree;
	}

	public void setOrgTree(String orgTree) {
		this.orgTree = orgTree;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/* (non-Javadoc)
     * @see com.fh.iasp.platform.core.pojo.IdEntity#getId()
     */
	
    public String getId()
    {
        return id;
    }

    /* (non-Javadoc)
     * @see com.fh.iasp.platform.core.pojo.IdEntity#setId(java.lang.String)
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getStatus() {
  		return status;
  	}

  	public void setStatus(String status) {
  		this.status = status;
  	}

  	public String getCreated() {
  		return created;
  	}

  	public void setCreated(String created) {
  		this.created = created;
  	}

  	public long getCreateTime() {
  		return createTime;
  	}

  	public void setCreateTime(long createTime) {
  		this.createTime = createTime;
  	}

  	public String getUpdated() {
  		return updated;
  	}

  	public void setUpdated(String updated) {
  		this.updated = updated;
  	}

  	public long getUpdateTime() {
  		return updateTime;
  	}

  	public void setUpdateTime(long updateTime) {
  		this.updateTime = updateTime;
  	}

	public String getCreatedDept() {
		return createdDept;
	}

	public void setCreatedDept(String createdDept) {
		this.createdDept = createdDept;
	}
 
}
