package com.jeffry.miaosha.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JEFFRY
 * @classNamr ：ValidationResult
 * @date ：Created in 2020/9/11 14:42
 * @description：参数校验类
 * @modified By：
 * @version: 1.0.0
 */
public class ValidationResult {
    //校验结果是否有错
    private boolean hasErrors = false;

    //存放错误信息的map
    private Map<String,String> errorMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //实现通用通过格式化字符串信息获取错误结果的msg方法

    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }

}
