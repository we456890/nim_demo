package com.netease.nim.uikit.common.bean;

import java.io.Serializable;

/**
 * 实体编号
 *
 * @DateTime: 2012-9-10
 * @author lushigai
 * @version 1.0
 */

public interface IdEntity extends Serializable
{
    /**
     * 
     * @return long
     */
    String getId();

    /**
     * 
     * @param id id
     */
    void setId(String id);
}
