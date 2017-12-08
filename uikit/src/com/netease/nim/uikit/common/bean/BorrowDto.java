package com.netease.nim.uikit.common.bean;


import java.io.Serializable;
import java.util.List;

public class BorrowDto implements Serializable {
    private String total;
    private List<Borrow> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Borrow> getRows() {
        return rows;
    }

    public void setRows(List<Borrow> rows) {
        this.rows = rows;
    }

}
