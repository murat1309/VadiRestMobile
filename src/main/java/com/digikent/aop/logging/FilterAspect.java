package com.digikent.aop.logging;

import com.digikent.aspect.CustomerRequired;
import com.digikent.filter.FilterType;
import com.digikent.security.SecurityUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Onur on 16.12.2015.
 */
@Aspect
@Order(value = 2)
public class FilterAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entitymanager;

    @Value("${efatura.max.pagesize:200}")
    private int MAX_PAGE_SIZE;

    //@Around(value = "com.vadi.efatura.aop.logging.SystemArchitecture.isPageableExists(pageable)")
    public Object pageableCheck(ProceedingJoinPoint joinPoint, Pageable pageable) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("######## Pageable with size: {} ########", pageable.getPageSize());
        }
        try {
            int pageableSize = pageable.getPageSize();
            if (pageableSize > MAX_PAGE_SIZE) {
                pageable = getPageable(pageable);
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Pageable) {
                        args[i] = pageable;
                    }
                }
                return joinPoint.proceed(args);
            }
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw throwable;
        }
    }

    @SuppressWarnings("unchecked")
    //@Before(value = "com.vadi.efatura.aop.logging.SystemArchitecture.inDataLayer())")
    public void customerCheck(JoinPoint joinPoint) {

        if (SecurityUtils.isSystemUserActivated()) {
            setFilterForRequest(FilterType.CUSTOMER, false);
            return;
        }

        if (log.isDebugEnabled()) {

            String methodName = joinPoint.getSignature().getName();
            String declaringType = joinPoint.getSignature().getDeclaringTypeName();
            String args = Arrays.toString(joinPoint.getArgs());

            Annotation annotated = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(CustomerRequired.class);

            log.debug("Class : {} , MethodName : {}  , Arguments : {} ", joinPoint.getSignature().getDeclaringTypeName(), methodName, args);
            if (joinPoint.getSignature().getDeclaringType().getAnnotation(CustomerRequired.class) != null) {
                log.debug("Class has annotation ", declaringType);
            }
        }
        Method methodSignature = ((MethodSignature) joinPoint.getSignature()).getMethod();

        if (methodSignature.getAnnotation(CustomerRequired.class) != null) {
            setFilterForRequest(FilterType.CUSTOMER, true);
            return;
        }

        for (Class<?> superType : joinPoint.getTarget().getClass().getInterfaces()) {
            log.debug("Class Interface: {} ", superType);
            if (superType.getAnnotation(CustomerRequired.class) != null) {
                setFilterForRequest(FilterType.CUSTOMER, true);
                return;
            }
        }
        setFilterForRequest(FilterType.CUSTOMER, false);
    }

    /**
     * enable or disables given Filter for in session over entitymanager,
     * if active is given as true, filter is enabled, otherwise disabled
     *
     * @param filterType
     * @param active
     */
    private void setFilterForRequest(FilterType filterType, Boolean active) {

        if (TransactionSynchronizationManager.isActualTransactionActive() && SecurityUtils.isAuthenticated()) {
            Session session = entitymanager.unwrap(Session.class);
            if (active) {
                session.enableFilter(filterType.getFilterName())
                    .setParameter(filterType.getParameter(), SecurityUtils.getCurrentCustomerId());
            } else {
                session.disableFilter(filterType.getFilterName());
            }
        }
    }

    /**
     * creates new Pageable from given except page size. its fixed to max page size
     *
     * @param pageable
     * @return
     */
    private Pageable getPageable(final Pageable pageable) {

        Pageable createPageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return pageable.getPageNumber();
            }

            @Override
            public int getPageSize() {
                return MAX_PAGE_SIZE;
            }

            @Override
            public int getOffset() {
                return pageable.getOffset();
            }

            @Override
            public Sort getSort() {
                return pageable.getSort();
            }

            @Override
            public Pageable next() {
                return pageable.next();
            }

            @Override
            public Pageable previousOrFirst() {
                return pageable.previousOrFirst();
            }

            @Override
            public Pageable first() {
                return pageable.first();
            }

            @Override
            public boolean hasPrevious() {
                return pageable.hasPrevious();
            }
        };

        return createPageable;
    }


//    @Before(value = "com.vadi.efatura.aop.logging.SystemArchitecture.inCustomerArea() && @annotation(customerRequired)", argNames = "customerRequired")
//    public void doAccessCheck(JoinPoint joinPoint, CustomerRequired customerRequired) {
//        if (TransactionSynchronizationManager.isActualTransactionActive() && SecurityUtils.isAuthenticated()) {
//            Session session = entitymanager.unwrap(Session.class);
//            session.enableFilter("GLOBAL_FILTER").setParameter("customerId", SecurityUtils.getCurrentCustomerId());
//        }
//    }

    /*
      try type level
      try not with ||
      ...
     */

//    @Before("com.vadi.efatura.aop.logging.SystemArchitecture.inDataLayer() && @annotation(customerRequired)")
//    public void doAccessCheck(CustomerRequired customerRequired){
//        if (TransactionSynchronizationManager.isActualTransactionActive() && SecurityUtils.isAuthenticated()) {
//            Session session = entitymanager.unwrap(Session.class);
//            session.enableFilter("GLOBAL_FILTER").setParameter("customerId", SecurityUtils.getCurrentCustomerId());
//        }
//    }


//    @Before(value = "com.vadi.efatura.aop.logging.SystemArchitecture.targetCustomer(customerRequired) && com.vadi.efatura.aop.logging.SystemArchitecture.inCustomerArea()") //  "execution(* com.vadi.efatura.business.repository.VeininvoiceRepository.*(..))"
//    public void doAccessCheck(JoinPoint joinPoint, CustomerRequired customerRequired) {
//        if (TransactionSynchronizationManager.isActualTransactionActive() && SecurityUtils.isAuthenticated()) {
//            Session session = entitymanager.unwrap(Session.class);
//            session.enableFilter("GLOBAL_FILTER").setParameter("customerId", SecurityUtils.getCurrentCustomerId());
//        }
//    }

}
