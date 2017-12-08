package com.netease.nim.uikit.common.bean;

import java.io.Serializable;

/**
 * 用户dto为了作显示用
 *
 * @author zxy
 */
@SuppressWarnings("serial")
public class UserDTO implements Serializable {

    public static final String JSON_TAG_ID = "id";
    public static final String JSON_TAG_ACCOUNT = "account";
    public static final String JSON_TAG_PASSWORD = "password";
    public static final String JSON_TAG_USERNAME = "userName";
    public static final String JSON_TAG_STATUS = "status";
    public static final String JSON_TAG_BUSINESS = "business";
    public static final String JSON_TAG_DEPTID = "deptId";
    public static final String JSON_TAG_USERTYPE = "userType";
    public static final String JSON_TAG_LOGINTIME = "loginTime";
    public static final String JSON_TAG_LASTLOGINTIME = "lastLoginTime";
    public static final String JSON_TAG_LOSECOUNT = "loseCount";
    public static final String JSON_TAG_DEPTNAME = "deptName";
    public static final String JSON_TAG_PARENTID = "parentId";
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_ORGTREE = "orgTree";
    public static final String JSON_TAG_ORG = "org";
    public static final String JSON_TAG_TOKEN = "token";




    private String operateSuccess;

    private String operateMessage;

    /**
     * 状态
     */
    private String status;

    /**
     * 用户名
     */
    private String userId;
    /**
     * 姓名
     */
    private String realName;

    private String departmentId;
    private String departmentName;
    /**
     * 职务
     */
    private String business;

    private String isZf;
    /**
     * 密码
     */
    private String password;
    private String sessionId;
    /**
     * 用户ID
     */
    private String Id;
    private String org;
    /**
     * 网易云token
     */
    private String token;

    private String accid;

    public String getOperateSuccess() {
        return operateSuccess;
    }

    public void setOperateSuccess(String operateSuccess) {
        this.operateSuccess = operateSuccess;
    }

    public String getOperateMessage() {
        return operateMessage;
    }

    public void setOperateMessage(String operateMessage) {
        this.operateMessage = operateMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getIsZf() {
        return isZf;
    }

    public void setIsZf(String isZf) {
        this.isZf = isZf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "operateSuccess='" + operateSuccess + '\'' +
                ", operateMessage='" + operateMessage + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", realName='" + realName + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", business='" + business + '\'' +
                ", isZf='" + isZf + '\'' +
                ", password='" + password + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", Id='" + Id + '\'' +
                ", org='" + org + '\'' +
                ", token='" + token + '\'' +
                ", accid='" + accid + '\'' +
                '}';
    }
}