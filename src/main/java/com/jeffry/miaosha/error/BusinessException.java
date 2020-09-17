package com.jeffry.miaosha.error;

public class BusinessException extends Exception implements CommonError {
    private CommonError commonError;
    //直接接收emBusiness的传参构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }
    //自定义异常信息
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);

    }
    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
       this.commonError.setErrMsg(errMsg);
        return this;
    }
}
