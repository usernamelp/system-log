package cn.lp.aop;

import cn.lp.entity.SysLogEntity;
import cn.lp.service.SysLogService;
import cn.lp.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;
    private static Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint joinPoint, SysLog sysLog) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        SysLogEntity sysLogEntity = new SysLogEntity();
        if(sysLog != null){
            //注解上的描述
            sysLogEntity.setOperation(sysLog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");
        //请求uri
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        sysLogEntity.setUri(request.getRequestURI());

        Map<String, String> headers = WebUtils.getHeaders(request);
        logger.info("headers: {}",headers);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        sysLogEntity.setParams(params);

        //设置IP地址
        sysLogEntity.setIp(WebUtils.getIpAddr(request));
        /*//用户名
        String username = SecurityUtils.getSysUser().getUsername();
        sysLogEntity.setUsername(username);*/
        //耗时
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(time));
        BigDecimal useTime = bigDecimal.divide(new BigDecimal("1000"), 3, RoundingMode.HALF_UP);
        sysLogEntity.setUseTime(useTime.doubleValue());
        //保存系统日志
        sysLogService.save(sysLogEntity);
        return result;
    }

}
