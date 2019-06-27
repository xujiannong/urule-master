package com.bstek.urule.console.servlet.decisiontable;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-05 10:38
 **/
public class Header
{
    private String a;
    private HeaderType b;

    public Header()
    {
        this.b = HeaderType.condition; }

    public String getName() { return this.a; }

    public void setName(String paramString) {
        this.a = paramString; }

    public HeaderType getType() {
        return this.b; }

    public void setType(HeaderType paramHeaderType) {
        this.b = paramHeaderType;
    }
}
