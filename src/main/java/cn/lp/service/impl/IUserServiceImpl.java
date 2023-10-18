package cn.lp.service.impl;

import cn.lp.entity.SysUser;
import cn.lp.mapper.UserMapper;
import cn.lp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService {

   @Autowired
    private UserMapper userMapper;

    public int insert(SysUser sysUser){
       return userMapper.insert(sysUser);
    }
}
