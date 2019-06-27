package com.bstek.urule.console;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-05 13:31
 **/
public abstract interface Principal
{
    public abstract String getName();

    public abstract String getDisplayName();

    public abstract String getCompanyId();

    public abstract boolean isAdmin();
}
