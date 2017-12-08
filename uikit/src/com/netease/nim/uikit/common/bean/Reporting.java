package com.netease.nim.uikit.common.bean;

import java.io.Serializable;

/**
 * Created by 78560 on 2017/8/21.
 */

public class Reporting implements Serializable {

    //        @ChineseName("回报标题")
    private String title;

    //        @ChineseName("汇报人")
    private String reporter;

    //汇报人部门
    private String department;
    private String departmentName;

    //        @ChineseName("汇报时间")
    private String reportTime;

    //        @ChineseName("汇报类型")
    private String reportType;

    //        @ChineseName("汇报内容")
    private String context;

    //        @ChineseName("审阅人")
    private String checker;

    //        @ChineseName("拥有者")
    private String owner;

    //        @ChineseName("当前步骤")
    private String steps;

    //        @ChineseName("经办人")
    private String informre;

    // 检查人标记
    private String checkerinob;

    // 自动生成标题
    private String autoTitle;

    /** 实体标识. */
    private String id;

    /** 删除标识 */
    private String status;

    /** 创建人 */
    private String created;

    /** 创建者部门IDS */
    private String createdDept;

    /** 创建时间 */
    private String createTime;

    /** 更新人 */
    private String updated;

    /** 更新时间 */
    private String updateTime;

    /**
     * 数据所属的部门id
     */
//    @ChineseName("部门id")
    private String org;
//    @ChineseName("内容")
    private String reType;

    public String getReType() {
        return reType;
    }

    public void setReType(String reType) {
        this.reType = reType;
    }

    public String getRecontext() {
        return recontext;
    }

    public void setRecontext(String recontext) {
        this.recontext = recontext;
    }

    //    @ChineseName("详细")
    private String recontext;

    /**
     * 数据所属的部门用户结构（上上级部门id-上级部门id-用户id）
     */


//    @ChineseName("部门用户结构")
    private String orgTree;
    /**
     * 用于SQLMapper中动态数据的条件字段
     */
//    @PersistenceIgnore
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatedDept() {
        return createdDept;
    }

    public void setCreatedDept(String createdDept) {
        this.createdDept = createdDept;
    }



    public String getAutoTitle() {
        return autoTitle;
    }

    public void setAutoTitle(String autoTitle) {
        this.autoTitle = autoTitle;
    }

    public String getCheckerinob() {
        return checkerinob;
    }

    public void setCheckerinob(String checkerinob) {
        this.checkerinob = checkerinob;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getInformre() {
        return informre;
    }

    public void setInformre(String informre) {
        this.informre = informre;
    }

    @Override
    public String toString() {
        return "Reporting{" +
                "title='" + title + '\'' +
                ", reporter='" + reporter + '\'' +
                ", department='" + department + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", reportType='" + reportType + '\'' +
                ", context='" + context + '\'' +
                ", checker='" + checker + '\'' +
                ", owner='" + owner + '\'' +
                ", steps='" + steps + '\'' +
                ", informre='" + informre + '\'' +
                ", checkerinob='" + checkerinob + '\'' +
                ", autoTitle='" + autoTitle + '\'' +
                '}';
    }
}

