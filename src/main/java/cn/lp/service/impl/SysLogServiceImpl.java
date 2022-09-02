package cn.lp.service.impl;

import cn.lp.entity.SysLogEntity;
import cn.lp.mapper.SysLogMapper;
import cn.lp.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 * @since 1.0
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper mapper;
    @Override
    public void save(SysLogEntity entity) {
        mapper.insert(entity);
    }
}
