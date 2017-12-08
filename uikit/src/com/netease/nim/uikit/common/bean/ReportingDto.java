package com.netease.nim.uikit.common.bean;


import java.io.Serializable;
import java.util.List;

public class ReportingDto implements Serializable {
    private String total;
    private List<Reporting> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Reporting> getRows() {
        return rows;
    }

    public void setRows(List<Reporting> rows) {
        this.rows = rows;
    }

}
