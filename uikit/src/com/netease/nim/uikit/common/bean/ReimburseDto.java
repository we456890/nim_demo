package com.netease.nim.uikit.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 78560 on 2017/10/31.
 */

public class ReimburseDto implements Serializable {
    private String total;
    private List<Reimburse> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Reimburse> getRows() {
        return rows;
    }

    public void setRows(List<Reimburse> rows) {
        this.rows = rows;
    }
}
