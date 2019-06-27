package com.bstek.urule.console.servlet.decisiontable;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-05 10:37
 **/
import java.util.List;

public class TableData {
    private List<Header> a;
    private List<ContentRow> b;

    public TableData(List<Header> paramList, List<ContentRow> paramList1) {
        this.a = paramList;
        this.b = paramList1;
    }

    public List<Header> getHeaders() {
        return this.a;
    }

    public List<ContentRow> getRows() {
        return this.b;
    }
}