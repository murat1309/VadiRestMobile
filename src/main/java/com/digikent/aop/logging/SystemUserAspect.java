package com.digikent.aop.logging;


import com.digikent.aspect.SystemContext;
import com.digikent.security.SecurityUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * Created by Onur on 4.01.2016.
 */
@Aspect
@Order( value = 1500)
public class SystemUserAspect {

    private static final Logger LOG = LoggerFactory.getLogger(SystemUserAspect.class);


//    @Before(value = "com.vadi.efatura.aop.logging.SystemArchitecture.targetUserContext(systemContext) && com.vadi.efatura.aop.logging.SystemArchitecture.inAllLayers()")
//    public void activateSystemUser(JoinPoint joinPoint, SystemContext systemContext){
//        LOG.debug("################# in system Context area #################");
//    }
//
//    @After(value = "com.vadi.efatura.aop.logging.SystemArchitecture.targetUserContext(systemContext) && com.vadi.efatura.aop.logging.SystemArchitecture.inAllLayers()")
//    public void deactivateSystemUser(JoinPoint joinPoint, SystemContext systemContext){
//        LOG.debug("################# exit system Context area #################");
//    }

    @Before(value = "com.digikent.aop.logging.SystemArchitecture.inAllLayers() && @annotation(systemContext)",argNames = "joinPoint,systemContext")
    public void systemContextEnter(JoinPoint joinPoint, SystemContext systemContext){
        LOG.debug("################# in system Context area #################");
        SecurityUtils.enterSystemUserArea();
    }
    @After(value = "com.digikent.aop.logging.SystemArchitecture.inAllLayers() && @annotation(systemContext)",argNames = "joinPoint,systemContext")
    public void systemContextExit(JoinPoint joinPoint, SystemContext systemContext){
        LOG.debug("################# exit system Context area #################");
        SecurityUtils.exitSystemUserArea();
    }

}
