package com.jeffry.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.error.EmBusinessError;
import com.jeffry.miaosha.response.CommonReturnType;
import com.jeffry.miaosha.service.UserService;
import com.jeffry.miaosha.service.model.UserModel;
import com.jeffry.miaosha.controller.viewobject.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
@Slf4j
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders="*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录接口
    /**
     * @Author: JEFFRY
     * @Description: 用户登录接口
     * @Date: 2020/9/11 14:11
     * @param telphone
     * @param password
     * @return com.jeffry.miaosha.response.CommonReturnType
     */
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone,
                                  @RequestParam(name = "password") String password) throws BusinessException{

        //入参校验
        if (StringUtils.isEmpty(telphone) ||
        StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone,DigestUtils.md5DigestAsHex(password.getBytes()));

        //将登录凭证加入到登录成功session
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);
    }

   /**
    * @Author: JEFFRY
    * @Description: 用户注册
    * @Date: 2020/9/11 13:19
    * @param telphone
    * @param otpCode
    * @param name
    * @param gender
    * @param age
    * @param password
    * @return com.jeffry.miaosha.response.CommonReturnType
    */
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException {

        //验证手机号和验证码是否符合
        String insessionOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode,insessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    //用户获取otp短信的接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone){
        //按一定规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将otp验证码同对应用户手机号关联,使用httpsession方式绑定手机号
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        String session  = (String)httpServletRequest.getSession().getAttribute(telphone);
        log.info(session);


        //将otp验证码通过短信通道发送给用户。略
        log.info("telphone= {} & otpcode= {}",telphone,otpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //将核心领域模型对象转化为前端视图展示对象
        UserVO userVO = convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
