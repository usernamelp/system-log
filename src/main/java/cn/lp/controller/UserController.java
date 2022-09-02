package cn.lp.controller;

import cn.lp.aop.SysLog;
import cn.lp.entity.SysUser;
import cn.lp.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lipeng
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/save")
    @SysLog("insert user")
    public HashMap<String, Object> insert(SysUser sysUser){
        iUserService.insert(sysUser);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("success","OK");
        resMap.put("code",200);
        resMap.put("msg","insert success!");
        logger.info("resp: {}",resMap);
        return resMap;
    }
}
