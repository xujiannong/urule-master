package com.bstek.urule.console.repository;

import com.bstek.urule.model.library.variable.VariableCategory;

import java.util.List;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-05 15:07
 **/
public class ProjectVariable
{
    private String a;
    private List<VariableCategory> b;

    public String getPath()
    {
        return this.a; }

    public void setPath(String paramString) {
        this.a = paramString; }

    public List<VariableCategory> getVariableCategories() {
        return this.b; }

    public void setVariableCategories(List<VariableCategory> paramList) {
        this.b = paramList;
    }
}
