package cn.lp.controller;

import cn.lp.aop.SysLog;
import cn.lp.entity.SysUser;
import cn.lp.mapper.UserMapper;
import cn.lp.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/save")
    @SysLog("insert user")
    public HashMap<String, Object> insert(SysUser sysUser) {
        int insert = iUserService.insert(sysUser);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("success", "OK");
        resMap.put("code", 200);
        resMap.put("msg", "insert success!");
        logger.info("resp: {}", resMap);
        return resMap;
    }

    @RequestMapping("/test")
    public List<SysUser> select() {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        ArrayList<Integer> ids = new ArrayList<>();
//        ids.add(1);
        criteria.andNotIn("id", ids.size() > 0 ? ids : null);
        List<SysUser> sysUsers = userMapper.selectByExample(example);
        return sysUsers;
    }
}
