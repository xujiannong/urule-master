package com.bstek.urule.console.servlet;


import com.bstek.urule.console.servlet.decisiontable.Header;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-05 10:43
 **/
public class CellContent {
    private int a;
    private int b;
    private int c;
    private Header d;
    private String e;
    private String f;

    public CellContent()
    {
        this.f = "value"; }

    public int getSpan() { return this.a; }

    public void setSpan(int paramInt) {
        this.a = paramInt; }

    public int getRow() {
        return this.b; }

    public void setRow(int paramInt) {
        this.b = paramInt; }

    public int getCol() {
        return this.c; }

    public void setCol(int paramInt) {
        this.c = paramInt; }

    public String getContent() {
        return this.e; }

    public void setContent(String paramString) {
        this.e = paramString; }

    public Header getHeader() {
        return this.d; }

    public void setHeader(Header paramHeader) {
        this.d = paramHeader; }

    public String getType() {
        return this.f; }

    public void setType(String paramString) {
        this.f = paramString;
    }
}
