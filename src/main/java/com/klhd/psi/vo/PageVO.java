package com.klhd.psi.vo;

/**
 * Created by cheng on 2017/8/27.
 */
public class PageVO {
    private int pageSize=20;
    private int curPage=1;
    private int totalRows;
    private int startRow;

    public static PageVO getInstance(){
        return new PageVO();
    }

    public PageVO setPageSze(int pageSize){
        this.pageSize = pageSize;
        this.startRow = (this.curPage - 1) * this.pageSize;
        return this;
    }

    public PageVO setStartRow(int startRow){
        this.startRow = startRow;
        return this;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.startRow = (this.curPage - 1) * this.pageSize;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
        this.startRow = (this.curPage - 1) * this.pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
