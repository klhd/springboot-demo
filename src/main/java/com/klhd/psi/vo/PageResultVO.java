package com.klhd.psi.vo;

import java.util.List;

/**
 * Created by cheng on 2017/8/27.
 */
public class PageResultVO {
    private PageVO pageVO;
    private List<? extends Object> result;
    private String message;

    public PageVO getPageVO() {
        return pageVO;
    }

    public void setPageVO(PageVO pageVO) {
        this.pageVO = pageVO;
    }

    public List<? extends Object> getResult() {
        return result;
    }

    public void setResult(List<? extends Object> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
