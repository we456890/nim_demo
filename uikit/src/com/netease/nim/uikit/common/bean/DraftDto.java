package com.netease.nim.uikit.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 78560 on 2017/8/24.
 */

public class DraftDto implements Serializable {
    public String id;
    public List<Draft> list;

    public DraftDto(String id,  List<Draft> list) {
        this.id = id;
        this.list = list;
    }

    public DraftDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Draft> getList() {
        return list;
    }

    public void setList(List<Draft> list) {
        this.list = list;
    }



    @Override
    public String toString() {
        return "DraftDto{" +
                "id='" + id + '\'' +
                ", list=" + list +
                '}';
    }
}
