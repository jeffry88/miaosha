package com.jeffry.miaosha.error;

public enum EmBusinessError  implements CommonError{
    //通用错误码100001
    PARAMETER_VALIDATION_ERROR(100001,"参数不合法"),
    UNKNOW_ERROR(100002,"未知错误"),
    //20000开头为用户错误
    USER_NOT_EXIST(200001,"用户不存在"),
    USER_LOGIN_FAIL(200002,"用户手机号不存在或密码不正确"),

    //3000开头为交易信息错误
    STOCK_NOT_ENOUGH(30001,"商品库存不足"),
    ;

    private EmBusinessError(int errCode,String errMsg){
        this.errcode = errCode;
        this.errMsg = errMsg;
    }

    private int errcode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errcode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
