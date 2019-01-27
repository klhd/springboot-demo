package com.klhd.psi.vo;

/**
 * Created by cheng on 2017/8/29.
 */
public class ResultVO {
    private Integer code;
    private String message;
    private Object result;

    public ResultVO(){
        this.code = 200;
    }

    public ResultVO fail(){
        return fail(null);
    }
    public ResultVO fail(String msg){
        this.message = msg;
        this.code = 500;
        return this;
    }

    public static ResultVO getInstance(){
        return new ResultVO();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
