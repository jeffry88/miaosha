package com.jeffry.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author ：JEFFRY
 * @classNamr ：ValidatorImpl
 * @date ：Created in 2020/9/11 15:09
 * @description：参数校验实现类
 * @version: 1.0.0
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法返回结果
    public ValidationResult validate(Object bean){
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0 ){
            //有错误
             result.setHasErrors(true);
             constraintViolationSet.forEach(constraintViolation -> {
                 String errMsg = constraintViolation.getMessage();
                 String propertyName = constraintViolation.getPropertyPath().toString();
                 result.getErrorMsgMap().put(propertyName,errMsg);
             });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator 通过工厂的初始化方法使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
