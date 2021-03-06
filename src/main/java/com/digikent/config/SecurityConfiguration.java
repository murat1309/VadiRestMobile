package com.digikent.config;

import com.digikent.general.util.UtilOperationSystem;
import com.vadi.digikent.base.util.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Enumeration;
import java.util.Properties;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * This section defines the user accounts which can be used for
     * authentication as well as the roles each user has.
     *
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String path = System.getenv("DIGIKENT_PATH") + "\\services\\users.properties";
        if (!UtilOperationSystem.isWindows()) {
            path = System.getenv("DIGIKENT_PATH") + "/services/users.properties";
        }
        Properties userProp = FileUtil.getPropsFromFile(path);

        Enumeration<Object> keys = userProp.keys();
        while (keys.hasMoreElements()) {
            String userName = (String) keys.nextElement();

            String sifreRollerVeEnabledDisabled = (String) userProp
                    .get(userName);
            String[] bolunmusSifreRollerVeEnabledDisabled = sifreRollerVeEnabledDisabled
                    .split(",");
            if (bolunmusSifreRollerVeEnabledDisabled.length < 3) {
                throw new Exception(
                        "users.propertiesde gecersiz satir, kullanici adi: "
                                + userName);
            }
            if (!"enabled".trim()
                    .equals(bolunmusSifreRollerVeEnabledDisabled[bolunmusSifreRollerVeEnabledDisabled.length - 1].trim())
                    && !"disabled"
                    .equals(bolunmusSifreRollerVeEnabledDisabled[bolunmusSifreRollerVeEnabledDisabled.length - 1].trim())) {
                System.out.println(bolunmusSifreRollerVeEnabledDisabled[bolunmusSifreRollerVeEnabledDisabled.length - 1]);
                throw new Exception(
                        "users.properties satirinda enabled disabled eksik kullanici: "
                                + userName);
            }
            String sifre = bolunmusSifreRollerVeEnabledDisabled[0];
            String enabledDisabled = bolunmusSifreRollerVeEnabledDisabled[bolunmusSifreRollerVeEnabledDisabled.length - 1];
            UserDetailsManagerConfigurer.UserDetailsBuilder udb = auth.inMemoryAuthentication().withUser(
                    userName);
            udb.password(sifre);
            udb.disabled("disabled".equals(enabledDisabled));
            String[] roller = new String[bolunmusSifreRollerVeEnabledDisabled.length - 2];

            for (int i = 1; i < bolunmusSifreRollerVeEnabledDisabled.length - 1; i++) {
                String rol=bolunmusSifreRollerVeEnabledDisabled[i];
                if(!rol.startsWith("ROLE_")){
                    throw new Exception("Roller ROLE_ ile baslamali, kullanici: "+userName);
                }
                rol=rol.substring(rol.indexOf('_')+1);
                roller[i - 1] = rol;
            }
            udb.roles(roller);
        }
// auth.inMemoryAuthentication().//
// withUser("greg").password("turnquist").roles("USER").and().//
// withUser("ollie").password("gierke").roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and().authorizeRequests()
                .//
                antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
                .//
                antMatchers(HttpMethod.PUT, "/employees/**").hasRole("ADMIN")
                .//
                antMatchers(HttpMethod.PATCH, "/employees/**").hasRole("ADMIN")
                .and().//
                csrf().disable().rememberMe().disable();
    }
}
