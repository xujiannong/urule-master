package com.bstek.urule.console.servlet.decisiontable;


import com.bstek.urule.console.servlet.CellContent;

import java.util.List;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-05 10:40
 **/
public class ContentRow
{
    private List<CellContent> a;

    public List<CellContent> getContents()
    {
        return this.a;
    }

    public void setContents(List<CellContent> paramList) {
        this.a = paramList;
    }
}
