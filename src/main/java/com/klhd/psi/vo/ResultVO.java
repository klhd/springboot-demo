package com.klhd.psi.vo;

/**
 * Created by cheng on 2017/8/29.
 */
public class ResultVO {
    private boolean success = true;
    private Integer code;
    private String message;
    private Object result;

    public ResultVO fail(){
        return fail(null);
    }
    public ResultVO fail(String msg){
        this.message = msg;
        this.success = false;
        return this;
    }

    public static ResultVO getInstance(){
        return new ResultVO();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
