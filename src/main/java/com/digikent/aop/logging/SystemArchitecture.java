package com.digikent.aop.logging;


import com.digikent.aspect.SystemContext;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Pageable;

/**
 * Created by Onur on 16.12.2015.
 */
@Aspect
public class SystemArchitecture {

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.xyz.someapp.web package or any sub-package
     * under that.W
     */
    //@Pointcut("within(com.vadi.efatura.web.rest..*)")
    public void inWebLayer() {
    }

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.xyz.someapp.service package or any sub-package
     * under that.
     */
    //@Pointcut("within(com.vadi.efatura.business.service..*)")
    public void inServiceLayer() {
    }

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.xyz.someapp.dao package or any sub-package
     * under that.
     */
    //@Pointcut("within(com.vadi.efatura.business.repository..*)")
    public void inDataAccessLayer() {
    }

    /**
     * web or service
     */
    @Pointcut("inWebLayer() || inServiceLayer()") // || inDataAccessLayer()
    public void inServiceORWebLayer() {
    }

    //@Pointcut("within(org.springframework.data.repository.CrudRepository)") /* deployed ! not working */
    //@Pointcut("execution(* com.vadi.efatura.business.repository+.*(..))")
    //@Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    // @Pointcut("execution(* com.vadi.efatura.business.repository.VeinenvelopeRepository.*(..))")
    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
    public void inDataLayer() {}

    /**
     * Pageable check in Weblayer
     *
     * @param pageable
     */
    @Pointcut("execution(* com.vadi.efatura.web..*.*(..)) && (args(pageable,..) || args(..,pageable))")
    public void isPageableExists(Pageable pageable) {
    }

    @Pointcut("execution(* com.vadi.efatura..*.*(..))")
    public void inAllLayers(){}


    @Pointcut(value = "@target(systemContext)", argNames = "systemContext")
    public void targetUserContext(SystemContext systemContext){}




   /* @Pointcut("inInvoiceDataAccess() || inEnvelopeDataAccess()")
    public void inCustomerArea(){}

    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..)) && !execution(* com.vadi.efatura.business.repository.UserRepository.*(..))")
    public void notInAuthenticationArea(){}

    @Pointcut(value = "@target(customerRequired)",argNames = "customerRequired")
    public void targetCustomer(@SuppressWarnings("unused") CustomerRequired customerRequired) {} */

}
